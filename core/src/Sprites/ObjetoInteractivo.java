package Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import Pantallas.PantallaJuego;

public abstract class ObjetoInteractivo extends Sprite {
	protected World mundo;
	protected PantallaJuego pantalla;
	public Body b2cuerpo;
	
	public ObjetoInteractivo(PantallaJuego pantalla, float x, float y) {
		this.mundo = pantalla.getMundo();
		this.pantalla = pantalla;
		setPosition(x, y);
		defineObjeto();
	}
	
	protected abstract void defineObjeto();
	public abstract void esTocado();
}


//package Sprites;
//
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.maps.tiled.TiledMap;
//import com.badlogic.gdx.maps.tiled.TiledMapTile;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.physics.box2d.Body;
//import com.badlogic.gdx.physics.box2d.BodyDef;
//import com.badlogic.gdx.physics.box2d.Filter;
//import com.badlogic.gdx.physics.box2d.Fixture;
//import com.badlogic.gdx.physics.box2d.FixtureDef;
//import com.badlogic.gdx.physics.box2d.PolygonShape;
//import com.badlogic.gdx.physics.box2d.World;
//import com.sonic.servidor.SonicServer;
//
//import Pantallas.PantallaJuego;
//
//public abstract class ObjetoInteractivo {
//
//	protected World mundo;
//	protected PantallaJuego pantalla;
//	protected Body b2cuerpo;
//	protected TiledMap mapa;
//	protected TiledMapTile tile;
//	protected Rectangle bordes;
//	protected Fixture fixture;
//	
//	public ObjetoInteractivo(PantallaJuego pantalla, float x, float y) {
//		this.mundo = pantalla.getMundo();
//		this.pantalla = pantalla;
//		
//		BodyDef cdef = new BodyDef();
//		FixtureDef fdef = new FixtureDef();
//		PolygonShape forma = new PolygonShape();
//		
//		//anillos
//		cdef.type = BodyDef.BodyType.StaticBody;
//		cdef.position.set(bordes.getX() + bordes.getWidth() / 2 / SonicServer.PPM, bordes.getY() + bordes.getHeight() / 2 / SonicServer.PPM);
//			
//		b2cuerpo = mundo.createBody(cdef);
//				
//		forma.setAsBox(bordes.getWidth() / 2  / SonicServer.PPM, bordes.getHeight() / 2  / SonicServer.PPM);
//		fdef.shape = forma;
//		fixture = b2cuerpo.createFixture(fdef);
//	}
//	
//	public abstract void esTocado();
//	public void destruir() {
//	    mundo.destroyBody(b2cuerpo);
//	}
//	public void setCategoryFilter(short filtroBit) {
//		Filter filtro = new Filter();
//		filtro.categoryBits = filtroBit;
//		fixture.setFilterData(filtro);
//	}
//	public abstract void update(float dt);
//    public void setRegion(TextureRegion region) {
//        setRegion(region);
//    }
//}
