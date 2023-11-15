package Herramientas;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
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
	        crearCuerposYFixtures(mundo, mapa, "piso", BodyDef.BodyType.StaticBody);
	        crearCuerposYFixtures(mundo, mapa, "colinas", BodyDef.BodyType.StaticBody);
	    }    private void crearCuerposYFixtures(World mundo, TiledMap mapa, String capa, BodyDef.BodyType tipo) {
	        System.out.println("Creando cuerpos y fixtures para la capa: " + capa);

	        BodyDef cdef = new BodyDef();
	        PolygonShape forma = new PolygonShape();
	        FixtureDef fdef = new FixtureDef();
	        Body cuerpo;

	        // Obtener los objetos de la capa actual
	        MapObjects objetos = mapa.getLayers().get(capa).getObjects();

	        // Iterar sobre los objetos de la capa
	        for (MapObject objeto : objetos.getByType(RectangleMapObject.class)) {
	            Rectangle rect = ((RectangleMapObject) objeto).getRectangle();

	            cdef.type = tipo;
	            cdef.position.set(rect.getX() + rect.getWidth() / 2 / SonicServer.PPM, rect.getY() + rect.getHeight() / 2 / SonicServer.PPM);

	            forma.setAsBox(rect.getWidth() / 2 / SonicServer.PPM, rect.getHeight() / 2 / SonicServer.PPM);
	            fdef.shape = forma;

	            cuerpo = mundo.createBody(cdef);
	            cuerpo.createFixture(fdef);

	            System.out.println("Creado cuerpo y fixture para objeto en posición: " + cdef.position);
	        }

	        forma.dispose(); // Liberar recursos
	    }

}
