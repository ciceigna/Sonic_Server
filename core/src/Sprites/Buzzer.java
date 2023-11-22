package Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.sonic.servidor.SonicServer;

import Escenas.Hud;
import Pantallas.PantallaJuego;
import Pantallas.PantallaJuego.TipoTextura;

public class Buzzer extends Enemigo {

	private float estadoTiempo;
	private Animation<TextureRegion> animacionVuela;
	private Animation<TextureRegion> animacionExplosion;
	private Array<TextureRegion> cuadros;
	private boolean muereBuzzer = false;
	private boolean muerto = false;
	
	public Buzzer(PantallaJuego pantalla, float x, float y) {
	    super(pantalla, x, y);

	    cuadros = new Array<TextureRegion>();
	    String regionName;

	    for (int i = 1; i <= 5; i++) {
	        regionName = "buzzerMuere" + i;
	        cuadros.add(pantalla.getTextura(TipoTextura.ENEMIGOS).findRegion(regionName));
	    }
	    animacionExplosion = new Animation<TextureRegion>(0.25f, cuadros);
	    cuadros.clear();
	    
	    for (int i = 1; i <= 3; i++) {
	        regionName = "buzzer" + i;
	        cuadros.add(pantalla.getTextura(TipoTextura.ENEMIGOS).findRegion(regionName));
	    }
	    animacionVuela = new Animation<TextureRegion>(0.25f, cuadros);
	    cuadros.clear();

	    defineEnemigo();
	    setBounds(getX(), getY(), 48 / SonicServer.PPM, 26 / SonicServer.PPM);
	    setRegion(animacionVuela.getKeyFrame(estadoTiempo, true));
	    estadoTiempo = 0;
	}
	
	public void update(float dt) {
		estadoTiempo += dt;
		if (muereBuzzer && !muerto) {
//        	mundo.destroyBody(b2cuerpo);
        	muerto = true;
			setRegion(animacionExplosion.getKeyFrame(estadoTiempo, true));
	   	    Hud.addPuntaje(100); 
		} else if (!muerto){
			setPosition(b2cuerpo.getPosition().x - getWidth() / 2, b2cuerpo.getPosition().y - getHeight() / 2);
			setRegion(animacionVuela.getKeyFrame(estadoTiempo, true));
	    }
	}

	public void muerte() {
		muereBuzzer = true; 
	}
	
	@Override
	protected void defineEnemigo() {
	    BodyDef cdef = new BodyDef();
	    cdef.position.set(1000 / SonicServer.PPM, 790 / SonicServer.PPM);
	    cdef.type = BodyDef.BodyType.KinematicBody;
	    b2cuerpo = mundo.createBody(cdef);

	    FixtureDef fdef = new FixtureDef();
	    PolygonShape forma = new PolygonShape();
	    
	    float ancho = 48 / 2 / SonicServer.PPM;
	    float alto = 26 / 2 / SonicServer.PPM;
	    
	    forma.setAsBox(ancho, alto);
	    
	    fdef.filter.categoryBits = SonicServer.BIT_ENEMIGO;
	    fdef.filter.maskBits = SonicServer.BIT_PISO |
	            SonicServer.BIT_ANILLO |
	            SonicServer.BIT_ENEMIGO |
	            SonicServer.BIT_OBJETO |
	            SonicServer.BIT_SONIC;

	    fdef.shape = forma;
	    b2cuerpo.createFixture(fdef);
	    b2cuerpo.setUserData(this);
	}

}
