package Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import Pantallas.PantallaJuego;

public abstract class Enemigo extends Sprite {
	protected World mundo;
	protected PantallaJuego pantalla;
	public Body b2cuerpo;
	
	public Enemigo(PantallaJuego pantalla, float x, float y) {
//		this.mundo = pantalla.getWorld();
		this.pantalla = pantalla;
		setPosition(x, y);
	}
	
	protected abstract void defineEnemigo();
}
