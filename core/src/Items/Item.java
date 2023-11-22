package Items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.sonic.servidor.SonicServer;

import Pantallas.PantallaJuego;

public abstract class Item extends Sprite{
	protected PantallaJuego pantalla;
	protected World mundo;
	protected boolean paraDestruir;
	protected boolean destruido;
	protected Body cuerpo;
	
	public Item(PantallaJuego pantalla, float x, float y) {
		this.pantalla = pantalla;
		this.mundo = pantalla.getMundo();
		setPosition(x,y);
		setBounds(getX(),getY(), 16 / SonicServer.PPM, 16 / SonicServer.PPM);
		defineItem();
		paraDestruir = false;
		destruido = false;
	}
	
	public abstract void defineItem();
	public abstract void recoger();
	
	public void update(float dt) {
		if(paraDestruir && !destruido) {
			mundo.destroyBody(cuerpo);
			destruido = true;
		}
	}
	
	public void draw(Batch batch) {
		if(!destruido) {
			super.draw(batch);
		}
	}
	
	public void destruir() {
		paraDestruir = true;
	}
	
}
