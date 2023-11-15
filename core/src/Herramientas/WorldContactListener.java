package Herramientas;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.sonic.servidor.SonicServer;

import Sprites.Enemigo;
import Sprites.Sonic;
import Sprites.Tails;

public class WorldContactListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
	    Fixture fixA = contact.getFixtureA();
	    Fixture fixB = contact.getFixtureB();

	    // Verificar si hay colisión con el enemigo Buzzer
	    if (esColisionBuzzer(fixA, fixB)) {
	        Enemigo enemigo = fixA.getUserData() instanceof Enemigo ? (Enemigo) fixA.getUserData() : (Enemigo) fixB.getUserData();

	        if (fixA.getUserData() instanceof Sonic) {
	            Sonic sonic = (Sonic) fixA.getUserData();
	            sonic.golpe();
	        } else if (fixB.getUserData() instanceof Sonic) {
	            Sonic sonic = (Sonic) fixB.getUserData();
	            sonic.golpe();
	        }

	        if (fixA.getUserData() instanceof Tails) {
	            Tails tails = (Tails) fixA.getUserData();
	            tails.golpe();
	        } else if (fixB.getUserData() instanceof Tails) {
	            Tails tails = (Tails) fixB.getUserData();
	            tails.golpe();
	        }
	    }
	}

	private boolean esColisionBuzzer(Fixture fixA, Fixture fixB) {
	    return (fixA.getFilterData().categoryBits == SonicServer.BIT_ENEMIGO && fixB.getFilterData().categoryBits == SonicServer.BIT_SONIC) ||
	           (fixA.getFilterData().categoryBits == SonicServer.BIT_SONIC && fixB.getFilterData().categoryBits == SonicServer.BIT_ENEMIGO);
	}

    private boolean esColisionBuzzer(int cDef) {
        return (cDef & SonicServer.BIT_ENEMIGO) != 0;
    }

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
