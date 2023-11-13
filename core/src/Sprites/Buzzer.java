package Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.sonic.servidor.SonicServer;

import Pantallas.PantallaJuego;

public class Buzzer extends Enemigo {

	private float estadoTiempo;
	private Animation animacionVuela;
	private Array<TextureRegion> cuadros;
	
	public Buzzer(PantallaJuego pantalla, float x, float y) {
		super(pantalla, x, y);
		cuadros = new Array<TextureRegion>();
		for(int i = 0; i<3; i++) {
			cuadros.add(new TextureRegion(pantalla.getAtlas().findRegion("buzzer"), i * 36, 0, 36,36));
		}
		animacionVuela = new Animation(0.4f, cuadros);
		estadoTiempo = 0;
	}
	
	public void update(float dt) {
		estadoTiempo += dt;
		setPosition(b2cuerpo.getPosition().x - getWidth() / 2, b2cuerpo.getPosition().y);
	}

	@Override
	protected void defineEnemigo() {
		BodyDef cdef = new BodyDef();
		cdef.position.set(90 / SonicServer.PPM,775 / SonicServer.PPM);
		cdef.type = BodyDef.BodyType.DynamicBody;
		b2cuerpo = mundo.createBody(cdef);
		
		FixtureDef fdef = new FixtureDef();
		CircleShape forma = new CircleShape();
		forma.setRadius(16 / SonicServer.PPM);
		fdef.filter.categoryBits = SonicServer.BIT_ENEMIGO;
		fdef.filter.maskBits = SonicServer.BIT_PISO |
				SonicServer.BIT_ANILLO |
				SonicServer.BIT_ENEMIGO |
				SonicServer.BIT_OBJETO;
		
		fdef.shape = forma;
		b2cuerpo.createFixture(fdef);
	}
}
