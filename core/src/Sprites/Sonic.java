package Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.sonic.servidor.SonicServer;
import com.badlogic.gdx.utils.Array;
import Pantallas.PantallaJuego;
import Pantallas.PantallaJuego.TipoTextura;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;


public class Sonic extends Sprite {
	public enum Estado{CAYENDO,SALTANDO,PARADO,CORRIENDO, CORRIENDO_IZQ, MUERTO}
	public Estado estadoActual;
	public Estado estadoPrevio;
	public World mundo;
	public Body b2cuerpo;
	
	private TextureRegion sonicQuieto;
	private TextureRegion sonicMuerto;
	private Animation<TextureRegion> sonicCorre;
	private Animation<TextureRegion> sonicSalta;
	
	private float estadoTiempo;
	private boolean murioSonic;
	
	public Sonic(PantallaJuego pantalla) {
	    super(pantalla.getTextura(TipoTextura.SONIC).findRegion("basicMotion1")); // Inicializa con la primera región de "basicMotion"
	    this.mundo = pantalla.getMundo();
	    estadoActual = Estado.PARADO;
	    estadoPrevio = Estado.PARADO;
	    estadoTiempo = 0;
	    String regionName;
	    
	    Array<TextureRegion> frames = new Array<TextureRegion>();

	    // Carga las regiones etiquetadas como "basicMotion" desde el atlas
	    for (int i = 1; i <= 8; i++) {
	        regionName = "basicMotion" + i;
	        frames.add(pantalla.getTextura(TipoTextura.SONIC).findRegion(regionName));
	    }

	    Array<TextureAtlas.AtlasRegion> regionesSalto = new Array<>();
	    for (int i = 1; i <= 4; i++) {
	    	regionName = "bola" + i;
	        regionesSalto.add(pantalla.getTextura(TipoTextura.SONIC).findRegion(regionName));
	    }
	    sonicSalta = new Animation<TextureRegion>(0.08f, regionesSalto);

	    // Crea la animación
	    sonicCorre = new Animation<TextureRegion>(0.1f, frames);
	    frames.clear();

	    defineSonic();
	    sonicQuieto = new TextureRegion(getTexture(), 1410, 2, 27, 59);
	    sonicMuerto = new TextureRegion(getTexture(), 768, 2, 34, 59);
	    setBounds(0, 0, getWidth() / SonicServer.PPM, getHeight() / SonicServer.PPM);
	    setRegion(sonicQuieto);
	    
	}

	public void golpe(Enemigo enemigo) {
	    if (estadoActual.equals(Estado.SALTANDO)) {
	    	enemigo.muerte();
	    } else {
	        Filter filtro = new Filter();
	        filtro.maskBits = SonicServer.BIT_VACIO;

	        for (Fixture fixture : b2cuerpo.getFixtureList()) {
	            fixture.setFilterData(filtro);
	        }
	    }
	}

	
	public void update(float dt) {
		setPosition(b2cuerpo.getPosition().x - getWidth() / 2, b2cuerpo.getPosition().y - getHeight() / 2);
		setRegion(getFrame(dt));
	}
	
	public TextureRegion getFrame(float dt) {
	    estadoActual = getEstado();

	    TextureRegion region;
	    switch (estadoActual) {
		    case MUERTO:
		    	region = sonicMuerto;
		        b2cuerpo.applyLinearImpulse(new Vector2(0, 4f), b2cuerpo.getWorldCenter(), true);
		        murioSonic = true;
		    	break;
	        case SALTANDO:
	            region = sonicSalta.getKeyFrame(estadoTiempo, true);
	            break;
	        case CORRIENDO:
	            region = sonicCorre.getKeyFrame(estadoTiempo, true);
	            break;
	        case CORRIENDO_IZQ:
	            region = sonicCorre.getKeyFrame(estadoTiempo, true);
	            break;
	        case CAYENDO:
	        case PARADO:
	        default:
	            region = sonicQuieto;
	            break;
	    }

	    if (b2cuerpo.getLinearVelocity().x < 0) {
	        if (!region.isFlipX()) {
	            region.flip(true, false);
	        }
	    } else if (b2cuerpo.getLinearVelocity().x > 0) {
	        if (region.isFlipX()) {
	            region.flip(true, false);
	        }
	    }

	    estadoTiempo = estadoActual == estadoPrevio ? estadoTiempo + dt : 0;
	    estadoPrevio = estadoActual;
	    return region;
	}

	
	public Estado getEstado() {
		if(murioSonic)
			return Estado.MUERTO;
		else if(b2cuerpo.getLinearVelocity().y > 0 || (b2cuerpo.getLinearVelocity().y < 0 && estadoPrevio == Estado.SALTANDO)) 
			return Estado.SALTANDO;
		else if(b2cuerpo.getLinearVelocity().y < 0)
			return Estado.CAYENDO;
		else if(b2cuerpo.getLinearVelocity().x > 0)
			return Estado.CORRIENDO;
		else if(b2cuerpo.getLinearVelocity().x < 0)
			return Estado.CORRIENDO_IZQ;
		else 
			return Estado.PARADO;
	}
	
	public void defineSonic() {
		BodyDef cdef = new BodyDef();
		cdef.position.set(300 / SonicServer.PPM,775 / SonicServer.PPM);
		cdef.type = BodyDef.BodyType.DynamicBody;
		b2cuerpo = mundo.createBody(cdef);
		
		FixtureDef fdef = new FixtureDef();
		CircleShape forma = new CircleShape();
		forma.setRadius(16 / SonicServer.PPM);
		
	    fdef.filter.categoryBits = SonicServer.BIT_SONIC;
	    fdef.filter.maskBits = SonicServer.BIT_PISO |
	            SonicServer.BIT_ANILLO |
	            SonicServer.BIT_ENEMIGO |
	            SonicServer.BIT_OBJETO;
	    
		fdef.shape = forma;
		b2cuerpo.createFixture(fdef);
		b2cuerpo.setUserData(this);
		fdef.isSensor = true;
	}
	
	
}
