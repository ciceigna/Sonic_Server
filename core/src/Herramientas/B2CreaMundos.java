package Herramientas;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.sonic.servidor.SonicServer;
public class B2CreaMundos {

	public B2CreaMundos(World mundo, TiledMap mapa) {
		BodyDef cdef = new BodyDef();
		PolygonShape forma = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body cuerpo;
	
		//piso
		for(MapObject objeto : mapa.getLayers().get("piso").getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) objeto).getRectangle();
		
			cdef.type = BodyDef.BodyType.StaticBody;
			cdef.position.set(rect.getX() + rect.getWidth() / 2 / SonicServer.PPM, rect.getY() + rect.getHeight() / 2 / SonicServer.PPM);
			
			
			forma.setAsBox(rect.getWidth() / 2 / SonicServer.PPM, rect.getHeight() / 2 / SonicServer.PPM);
			fdef.shape = forma;
			
			cuerpo = mundo.createBody(cdef);
			cuerpo.createFixture(fdef);
		}
		
		//colinas
		for(MapObject objeto : mapa.getLayers().get("colinas").getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) objeto).getRectangle();
		
			cdef.type = BodyDef.BodyType.StaticBody;
			cdef.position.set(rect.getX() + rect.getWidth() / 2 / SonicServer.PPM, rect.getY() + rect.getHeight() / 2 / SonicServer.PPM);
		
			cuerpo = mundo.createBody(cdef);
			
			forma.setAsBox(rect.getWidth() / 2  / SonicServer.PPM, rect.getHeight() / 2  / SonicServer.PPM);
			fdef.shape = forma;
			cuerpo.createFixture(fdef);
		}

	}
}
