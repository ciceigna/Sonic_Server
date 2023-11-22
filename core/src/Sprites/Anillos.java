package Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.sonic.servidor.SonicServer;

import Escenas.Hud;
import Pantallas.PantallaJuego;
import Pantallas.PantallaJuego.TipoTextura;

public class Anillos extends ObjetoInteractivo {

	private float estadoTiempo;
	private Animation<TextureRegion> anillo;
	private Array<TextureRegion> cuadros;
	
	public Anillos(PantallaJuego pantalla, float x, float y) {
	    super(pantalla, x, y);
	    estadoTiempo = 0;
	    cuadros = new Array<TextureRegion>();
	    String regionName;

	    for (int i = 1; i <= 4; i++) {
	        regionName = "anillo" + i;
	        cuadros.add(pantalla.getTextura(TipoTextura.OBJETOS).findRegion(regionName));
	    }
	    anillo = new Animation<TextureRegion>(0.4f, cuadros);
	    cuadros.clear();
	    
	    defineObjeto();
	    setBounds(getX(), getY(), getRegionWidth() / SonicServer.PPM, getRegionHeight() / SonicServer.PPM);
	    setRegion(anillo.getKeyFrame(estadoTiempo, true));

	}

	public void update(float dt) {
		estadoTiempo += dt;
		setPosition(b2cuerpo.getPosition().x - getWidth() / 2, b2cuerpo.getPosition().y - getHeight() / 2);
		setRegion(anillo.getKeyFrame(estadoTiempo, true));
	}
	
	@Override
	protected void defineObjeto() {
	    BodyDef cdef = new BodyDef();
	    cdef.position.set(200 / SonicServer.PPM, 780 / SonicServer.PPM);
	    cdef.type = BodyDef.BodyType.KinematicBody;
	    b2cuerpo = mundo.createBody(cdef);

	    FixtureDef fdef = new FixtureDef();
	    PolygonShape forma = new PolygonShape();
	    
	    float ancho = 16 / 2 / SonicServer.PPM;
	    float alto = 16 / 2 / SonicServer.PPM;
	    
	    forma.setAsBox(ancho, alto);
	    
	    fdef.filter.categoryBits = SonicServer.BIT_ENEMIGO;
	    fdef.filter.maskBits = SonicServer.BIT_PISO |
	            SonicServer.BIT_ANILLO |
	            SonicServer.BIT_ENEMIGO |
	            SonicServer.BIT_OBJETO |
	            SonicServer.BIT_SONIC;

	    fdef.shape = forma;
	    b2cuerpo.createFixture(fdef);

	    forma.dispose();
	}

	  @Override
	  public void esTocado() {
	      Gdx.app.log("Anillo", "Colision");
	      Hud.addPuntaje(100);
	      Hud.addAnillo(1);
	  }

}


//package Sprites;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.g2d.Animation;
//import com.badlogic.gdx.graphics.g2d.TextureAtlas;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.maps.tiled.TiledMap;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.physics.box2d.World;
//import com.badlogic.gdx.utils.Array;
//import com.sonic.servidor.SonicServer;
//
//import Escenas.Hud;
//import Pantallas.PantallaJuego;
//
//public class Anillos extends ObjetoInteractivo {
//
//    private float tiempo;
//    private Animation<TextureRegion> animacion;
//	private Array<TextureRegion> cuadros;
//	
//    public Anillos(PantallaJuego pantalla, float x, float y) {
//    	super(pantalla, x, y);
//        fixture.setUserData(this);
//        setCategoryFilter(SonicServer.BIT_ANILLO);
//
//        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("objetos.atlas"));
//        animacion = new Animation<TextureRegion>(1 / 12f, atlas.findRegions("anillo"), Animation.PlayMode.LOOP);
//
//        tiempo = 0;
//    }
//
//    @Override
//    public void esTocado() {
//        Gdx.app.log("Anillo", "Colision");
//        Hud.addPuntaje(100);
//        Hud.addAnillo(1);
//    }
//
//    @Override
//    public void update(float dt) {
//        tiempo += dt;
//        TextureRegion region = animacion.getKeyFrame(tiempo, true);
//        setRegion(region);
//    }
//}
