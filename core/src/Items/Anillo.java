package Items;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.sonic.servidor.SonicServer;

import Pantallas.PantallaJuego;
import Pantallas.PantallaJuego.TipoTextura;

public class Anillo extends Item{

	public Anillo(PantallaJuego pantalla, float x, float y) {
		super(pantalla, x, y);
		setRegion(pantalla.getTextura(TipoTextura.OBJETOS).findRegion("anillo1"), 0, 0, 16, 16);
	}

	@Override
	public void defineItem() {
		BodyDef cdef = new BodyDef();
	    cdef.position.set(300 / SonicServer.PPM, 790 / SonicServer.PPM);
	    cdef.type = BodyDef.BodyType.KinematicBody;
	    cuerpo = mundo.createBody(cdef);

	    FixtureDef fdef = new FixtureDef();
	    CircleShape forma = new CircleShape();
	    forma.setRadius(6 / SonicServer.PPM);
	    fdef.shape = forma;
	    cuerpo.createFixture(fdef);
		
	}

	@Override
	public void update(float dt) {
		super.update(dt);
		setPosition(cuerpo.getPosition().x - getWidth() / 2, cuerpo.getPosition().y - getHeight() / 2);
	
	}
	
	@Override
	public void recoger() {
		destruir();
		
	}

}
