package Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.sonic.servidor.SonicServer;
import Pantallas.PantallaJuego;

public class Buzzer extends Enemigo {

	private float estadoTiempo;
	private Animation<TextureRegion> animacionVuela;
	private Animation<TextureRegion> animacionExplosion;
	private Array<TextureRegion> cuadros;
	private boolean muereBuzzer = false;
	
	public Buzzer(PantallaJuego pantalla, float x, float y) {
	    super(pantalla, x, y);
	    estadoTiempo = 0;
	    cuadros = new Array<TextureRegion>();
	    String regionName;

	    for (int i = 1; i <= 5; i++) {
	        regionName = "buzzerMuere" + i;
	        cuadros.add(pantalla.getEnemigos().findRegion(regionName));
	    }
	    animacionExplosion = new Animation<TextureRegion>(0.5f, cuadros);
	    cuadros.clear();
	    
	    for (int i = 1; i <= 4; i++) {
	        regionName = "buzzer" + i;
	        cuadros.add(pantalla.getEnemigos().findRegion(regionName));
	    }
	    animacionVuela = new Animation<TextureRegion>(0.4f, cuadros);
	    cuadros.clear();

	    defineEnemigo();
	    setBounds(getX(), getY(), getRegionWidth() / SonicServer.PPM, getRegionHeight() / SonicServer.PPM);
	    setRegion(animacionVuela.getKeyFrame(estadoTiempo, true));

	}

	
	public void update(float dt) {
		estadoTiempo += dt;
		if (muereBuzzer) {
			setRegion(animacionExplosion.getKeyFrame(estadoTiempo, true));
	        if (animacionExplosion.isAnimationFinished(estadoTiempo)) {
	        	mundo.destroyBody(b2cuerpo);
	        }
		} else if (!muereBuzzer){
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
	    cdef.position.set(300 / SonicServer.PPM, 790 / SonicServer.PPM);
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

	    forma.dispose();
	}

}
