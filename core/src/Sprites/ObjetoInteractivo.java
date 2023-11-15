//package Sprites;
//
//import com.badlogic.gdx.maps.tiled.TiledMap;
//import com.badlogic.gdx.maps.tiled.TiledMapTile;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.physics.box2d.Body;
//import com.badlogic.gdx.physics.box2d.BodyDef;
//import com.badlogic.gdx.physics.box2d.FixtureDef;
//import com.badlogic.gdx.physics.box2d.PolygonShape;
//import com.badlogic.gdx.physics.box2d.World;
//import com.sonic.servidor.SonicServer;
//
//public abstract class ObjetoInteractivo {
//
//	protected World mundo;
//	protected TiledMap mapa;
//	protected TiledMapTile tile;
//	protected Rectangle bordes;
//	protected Body cuerpo;
//	
//	public ObjetoInteractivo(World mundo, TiledMap mapa, Rectangle bordes) {
//		this.mundo = mundo;
//		this.mapa = mapa;
//		this.bordes = bordes;
//		BodyDef cdef = new BodyDef();
//		FixtureDef fdef = new FixtureDef();
//		PolygonShape forma = new PolygonShape();
//		
//		//anillos
//		cdef.type = BodyDef.BodyType.StaticBody;
//		cdef.position.set(bordes.getX() + bordes.getWidth() / 2 / SonicServer.PPM, bordes.getY() + bordes.getHeight() / 2 / SonicServer.PPM);
//			
//		cuerpo = mundo.createBody(cdef);
//				
//		forma.setAsBox(bordes.getWidth() / 2  / SonicServer.PPM, bordes.getHeight() / 2  / SonicServer.PPM);
//		fdef.shape = forma;
//		cuerpo.createFixture(fdef);
//	}
//}
