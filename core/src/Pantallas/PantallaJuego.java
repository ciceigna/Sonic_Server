package Pantallas;

import java.util.PriorityQueue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.reactive.sonicserver.red.HiloServidor;
import com.sonic.servidor.SonicServer;
import Escenas.FondoParallax;
import Escenas.Hud;
import Herramientas.B2CreaMundos;
import Herramientas.WorldContactListener;
import Items.Anillo;
import Items.DefItem;
import Items.Item;
import Sprites.Buzzer;
import Sprites.Sonic;
import Sprites.Tails;
import utiles.Global;

public class PantallaJuego implements Screen {
	//base
	private SonicServer juego;
	private OrthographicCamera camJuego;
	private Viewport vistaJuego; 
	
	private TextureAtlas sonic;
	private TextureAtlas tails;
	private TextureAtlas enemigos;
	private TextureAtlas objetos; 
	
	public Sonic jugador;
	public Tails jugadorAlt;
	private Buzzer buzzer;
//	public Anillos anillo;
	
	private Array<Item> items;
	private PriorityQueue<DefItem> itemsASpawnear;
	
    float limMapa1 = 0.25f;
    float limMapa2 = 23.5f;
    private HiloServidor hs;
    Texture esperaTextura;
    Image esperaImagen;
    Stage stage;
    Table table;
    
	//hud
	private Hud hud;
	
	//mapa tiled
	private TmxMapLoader cargaMapa;
	private TiledMap mapa;
	private OrthogonalTiledMapRenderer renderizar;
	private FondoParallax fondo;
	
	//box2d obj. y colisiones
	private World mundo;
	private Box2DDebugRenderer b2dr;
	
	private float tiempoEspera = 0.85f;
	private boolean cambioPantalla = false;
	
	public PantallaJuego(SonicServer juego) {
        this.juego = juego;
		hs = new HiloServidor(this);
		hs.start();
		
        esperaTextura = new Texture("esperaImagen.png");
        esperaImagen = new Image(esperaTextura);
        stage = new Stage(new FitViewport(SonicServer.V_ANCHO, SonicServer.V_ALTO));
		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		
		sonic = new TextureAtlas("texturaSonic.atlas");
		tails = new TextureAtlas("texturaTails.atlas");
		enemigos = new TextureAtlas("texturaEnemigos.atlas");
		objetos = new TextureAtlas("objetos.atlas");
		
		camJuego = new OrthographicCamera();
		vistaJuego = new FitViewport(SonicServer.V_ANCHO / SonicServer.PPM,SonicServer.V_ALTO / SonicServer.PPM,camJuego);
		
		//hud
		hud = new Hud(juego.batch);
		
		//tiled
		cargaMapa = new TmxMapLoader();
		mapa = cargaMapa.load("prueba.tmx");
		renderizar = new OrthogonalTiledMapRenderer(mapa, 1 / SonicServer.PPM);
		fondo = new FondoParallax(camJuego); 
		
		camJuego.position.set(vistaJuego.getWorldWidth() / 2, vistaJuego.getWorldHeight() / 2, 0);
		
		//box2d obj. y colisiones
		mundo = new World(new Vector2(0, -9), true);
		b2dr = new Box2DDebugRenderer();
		
		new B2CreaMundos(this);
		
		jugador = new Sonic(this);
		jugadorAlt = new Tails(this);
		buzzer = new Buzzer(this, 1000 / SonicServer.PPM, 790 / SonicServer.PPM );
//		anillo = new Anillos(this, 200 / SonicServer.PPM, 780 / SonicServer.PPM);
		items = new Array<Item>();
		itemsASpawnear = new PriorityQueue<DefItem>();
		
		mundo.setContactListener(new WorldContactListener());
	}
	
	public void manejaSpawnearItems() {
	    if (!itemsASpawnear.isEmpty()) {
	        DefItem idef = itemsASpawnear.poll();
	        if (idef.tipo == Anillo.class) {
	            spawnearAnillo(300 / SonicServer.PPM, 775 / SonicServer.PPM);
	        }
	    }
	}

	private void spawnearAnillo(float x, float y) {
	    Item anillo = new Anillo(this, x, y);
	    items.add(anillo);
	}
	
	public TextureAtlas getTextura(TipoTextura tipo) {
	    switch (tipo) {
	        case SONIC:
	            return sonic;
	        case TAILS:
	            return tails;
	        case ENEMIGOS:
	            return enemigos;
	        case OBJETOS:
	            return objetos;
	        default:
	            return null;
	    }
	}
	
	public enum TipoTextura {
	    SONIC,
	    TAILS,
	    ENEMIGOS,
	    OBJETOS
	}

	public synchronized Sonic getJugador() {
		return jugador;
    }

	public synchronized Tails getJugadorAlt() {
        return jugadorAlt;
	}	
	
	@Override
	public void show() {
	}
	
	public void update(float dt) {
		manejaSpawnearItems();
		
	    mundo.step(1 / 60f, 6, 2);

	    jugador.update(dt);
	    jugadorAlt.update(dt);
	    buzzer.update(dt);
//	    anillo.update(dt);
	    
	    for(Item item : items) {
	    	item.update(dt);
	    }
	    
	    hud.update(dt);

	    float limiteIzquierdo = 3.5f;
	    float limiteDerecho = 20.2f;
	    float limiteInferior = 1.30f; 
	    float limiteSuperior = 30.1f;
	    
	    // Asegurarse de que el personaje no se salga del límite izquierdo
	    if (jugador.b2cuerpo.getPosition().x < limMapa1) {
	        jugador.b2cuerpo.setTransform(limMapa1, jugador.b2cuerpo.getPosition().y, 0);
	    } else if (jugadorAlt.b2cuerpo.getPosition().x < limMapa1) {
	        jugadorAlt.b2cuerpo.setTransform(limMapa1, jugadorAlt.b2cuerpo.getPosition().y, 0);
	    }
	    
	    // Asegurarse de que el personaje no se salga del límite derecho
	    if (jugador.b2cuerpo.getPosition().x > limMapa2) {
	        jugador.b2cuerpo.setTransform(limMapa2, jugador.b2cuerpo.getPosition().y, 0);
	    }
	    
	    if (jugador.b2cuerpo.getPosition().y < limiteInferior) {
	       jugador.estadoActual = Sonic.Estado.MUERTO;
	    } else if (jugadorAlt.b2cuerpo.getPosition().y < limiteInferior) {
	    	jugadorAlt.estadoActual = Tails.Estado.MUERTO;
	    }
	    
	    if (jugador.b2cuerpo.getPosition().x > limMapa2) {
	        jugador.b2cuerpo.setTransform(limMapa2, jugador.b2cuerpo.getPosition().y, 0);
	    }
	    
	    if (jugador.b2cuerpo.getPosition().x > limiteIzquierdo && jugador.b2cuerpo.getPosition().x < limiteDerecho &&
	    		jugador.b2cuerpo.getPosition().y > limiteInferior && jugador.b2cuerpo.getPosition().y < limiteSuperior) {
	    	camJuego.position.x = jugador.getX();
	    	camJuego.position.y = jugador.getY();
	    }

	    camJuego.update();
	    renderizar.setView(camJuego);
	}

	@Override
	public void render(float delta) {
		if(!Global.empieza) {
	        table.clear(); // Limpia los actores anteriores de la tabla
	        table.add(esperaImagen).expand().center().row();
		}else {
			update(delta);
			
			SonicServer.admin.get("audio/musica/pantallaJuego.mp3", Music.class).play();
			
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			fondo.render(0.80f, 75);
			renderizar.render();
			
			hs.enviarMsgATodos("Actualizar-jugador-"+ (jugador.b2cuerpo.getPosition().x - jugador.getWidth() / 2) +
					"-" + (jugador.b2cuerpo.getPosition().y - jugador.getHeight() / 2));
			hs.enviarMsgATodos("Estado-jugador-" + jugador.estadoActual);
			hs.enviarMsgATodos("Actualizar-jugadorAlt-"+jugadorAlt.b2cuerpo.getPosition().x + "-" + jugadorAlt.b2cuerpo.getPosition().y );
			hs.enviarMsgATodos("Estado-jugadorAlt-" + jugadorAlt.estadoActual);
			
			//box2d
			b2dr.render(mundo, camJuego.combined);
			
			juego.batch.setProjectionMatrix(camJuego.combined);
			juego.batch.begin();
			jugador.draw(juego.batch);
			jugadorAlt.draw(juego.batch);
		    buzzer.draw(juego.batch);
//		    anillo.draw(juego.batch);
			for(Item item : items) {
				item.draw(juego.batch);
			}
			juego.batch.end();
			
			juego.batch.setProjectionMatrix(hud.escenario.getCamera().combined);
			hud.escenario.draw();
			
		    if (FinJuego()) {
		        tiempoEspera -= delta; // Reduzca el tiempo de espera
		        if (tiempoEspera <= 0 && !cambioPantalla) {
		            juego.setScreen(new PantallaGameOver(juego));
		            cambioPantalla = true; // Evita que cambiemos de pantalla varias veces
		        }
		    }
		}
		
	}
	
	public boolean FinJuego() {
		if(jugador.estadoActual == Sonic.Estado.MUERTO || jugadorAlt.estadoActual == Tails.Estado.MUERTO) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public void resize(int width, int height) {
		vistaJuego.update(width, height);
	}
	
	public TiledMap getMapa() {
		return mapa;
	}

	public World getMundo() {
		return mundo;
	}
	
	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		mapa.dispose();
		renderizar.dispose();
		mundo.dispose();
		b2dr.dispose();
		hud.dispose();
	}

}