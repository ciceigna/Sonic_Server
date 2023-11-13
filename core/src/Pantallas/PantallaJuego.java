package Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.reactive.sonicserver.red.HiloServidor;
import com.sonic.servidor.SonicServer;

import Escenas.FondoParallax;
import Escenas.Hud;
import Herramientas.B2CreaMundos;
import Herramientas.WorldContactListener;
import Sprites.Sonic;
import Sprites.Tails;
import utiles.Global;

public class PantallaJuego implements Screen {
	//base
	private SonicServer juego;
	private OrthographicCamera camJuego;
	private Viewport vistaJuego; 
	private TextureAtlas atlas;
	private TextureAtlas atlasAlt;
	public Sonic jugador;
	public Tails jugadorAlt;
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
		
		atlas = new TextureAtlas("texturaSonic.atlas");
		atlasAlt = new TextureAtlas("texturaTails.atlas");
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
		
		new B2CreaMundos(mundo, mapa);
		
		jugador = new Sonic(mundo, this);
		jugadorAlt = new Tails(mundo, this);
		
		mundo.setContactListener(new WorldContactListener());
		
        SonicServer.admin.get("audio/musica/menu.mp3", Music.class).stop();
        SonicServer.admin.get("audio/musica/gameOver.mp3", Music.class).stop();
        SonicServer.admin.get("audio/musica/pantallaJuego.mp3", Music.class).play();
	}
	
	public TextureAtlas getAtlas() {
		return atlas;
	}
	
	public TextureAtlas getAtlasAlt() {
		return atlasAlt;
	}
	
	@Override
	public void show() {
	}
	
	public void handleInput() {
		if(jugador.estadoActual != Sonic.Estado.MUERTO) {
			if(Gdx.input.isKeyJustPressed(Input.Keys.UP) && jugador.estadoActual != Sonic.Estado.SALTANDO) {
			
			}
			if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			}
			if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			}
		}
	}
	
	public void update(float dt) {
	    handleInput();

	    mundo.step(1 / 60f, 6, 2);

	    jugador.update(dt);
	    jugadorAlt.update(dt);
	    hud.update(dt);

	    float limiteIzquierdo = 3.5f;
	    float limiteDerecho = 20.25f;
	    float limiteInferior = 2.55f; 
	    float limiteSuperior = 55.0f;
	    float posX = jugador.b2cuerpo.getPosition().x;
	    float posY = jugador.b2cuerpo.getPosition().y;


	    // Asegurarse de que el personaje no se salga del límite izquierdo
	    if (posX < limMapa1) {
	        jugador.b2cuerpo.setTransform(limMapa1, jugador.b2cuerpo.getPosition().y, 0);
	    }
	    
	    if (posY < limMapa1) {
	        jugador.golpe();
	        jugadorAlt.golpe();
	    }

	    // Asegurarse de que el personaje no se salga del límite derecho
	    if (posX > limMapa2) {
	        jugador.b2cuerpo.setTransform(limMapa2, jugador.b2cuerpo.getPosition().y, 0);
	    }

	    
	    if (posX > limiteIzquierdo && posX < limiteDerecho) {
	        camJuego.position.x = posX;
	    }
	    
	    if (posY > limiteInferior && posY < limiteSuperior) {
	        camJuego.position.y = posY;
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
			
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			fondo.render(0.80f, 75);
			renderizar.render();
			
			//box2d
			b2dr.render(mundo, camJuego.combined);
			
			juego.batch.setProjectionMatrix(camJuego.combined);
			juego.batch.begin();
			jugador.draw(juego.batch);
			jugadorAlt.draw(juego.batch);
			juego.batch.end();
			
			juego.batch.setProjectionMatrix(hud.escenario.getCamera().combined);
			hud.escenario.draw();
			
			hs.enviarMsgATodos("Actualizar-jugador-"+jugador.getX()+"-Actualizar-jugadorAlt-"+jugadorAlt.getX());
			
		    if (FinJuego()) {
		        tiempoEspera -= delta; // Reduzca el tiempo de espera

		        if (tiempoEspera <= 0 && !cambioPantalla) {
		            // Cuando el tiempo de espera haya transcurrido y no hayamos cambiado de pantalla aún
		            juego.setScreen(new PantallaGameOver(juego));
		            cambioPantalla = true; // Evita que cambiemos de pantalla varias veces
		        }
		    }
		}
		
	}
	
	public boolean FinJuego() {
		if(jugador.estadoActual == Sonic.Estado.MUERTO) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public void resize(int width, int height) {
		vistaJuego.update(width, height);
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