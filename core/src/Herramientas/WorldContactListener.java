package Herramientas;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.sonic.servidor.SonicServer;
import Sprites.Enemigo;
import Sprites.Sonic;

public class WorldContactListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
	    Fixture fixA = contact.getFixtureA();
	    Fixture fixB = contact.getFixtureB();

	    int cdef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

	    switch (cdef) {
	        case SonicServer.BIT_ENEMIGO | SonicServer.BIT_SONIC:
	            handleSonicEnemigoContact(fixA, fixB);
	            break;

	        default:
	            break;
	    }
	}

	private void handleSonicEnemigoContact(Fixture fixA, Fixture fixB) {
	    Object userDataA = fixA.getUserData();
	    Object userDataB = fixB.getUserData();

	    if (userDataA != null && userDataB != null) {
	        if (userDataA instanceof Sonic && userDataB instanceof Enemigo) {
	            ((Sonic) userDataA).golpe((Enemigo) userDataB);
	        } else if (userDataB instanceof Sonic && userDataA instanceof Enemigo) {
	            ((Sonic) userDataB).golpe((Enemigo) userDataA);
	        }
	    }
	}
	
	// Método para verificar si la fixture es del jugador
//	private boolean esJugador(Fixture fix) {
//	    return "jugador".equals(fix.getUserData());
//	}
	
	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
}
