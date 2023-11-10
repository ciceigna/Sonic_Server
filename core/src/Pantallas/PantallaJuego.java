package Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sonic.servidor.SonicProject;
import Escenas.FondoParallax;
import Escenas.Hud;
import Herramientas.B2CreaMundos;
import Herramientas.WorldContactListener;
import Sprites.Sonic;
import Sprites.Tails;

public class PantallaJuego implements Screen {
	//base
	private SonicProject juego;
	private OrthographicCamera camJuego;
	private Viewport vistaJuego; 
	private TextureAtlas atlas;
	private TextureAtlas atlasAlt;
	private Sonic jugador;
	private Tails jugadorAlt;
    float limMapaIzq = 0.25f;
    float limMapaDer = 23.5f;
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
	
	public PantallaJuego(SonicProject juego) {
        this.juego = juego;
		atlas = new TextureAtlas("texturaSonic.atlas");
		atlasAlt = new TextureAtlas("texturaTails.atlas");
		camJuego = new OrthographicCamera();
		vistaJuego = new FitViewport(SonicProject.V_ANCHO / SonicProject.PPM,SonicProject.V_ALTO / SonicProject.PPM,camJuego);
		
		//hud
		hud = new Hud(juego.batch);
		
		//tiled
		cargaMapa = new TmxMapLoader();
		mapa = cargaMapa.load("prueba.tmx");
		renderizar = new OrthogonalTiledMapRenderer(mapa, 1 / SonicProject.PPM);
		fondo = new FondoParallax(camJuego); 
		
		camJuego.position.set(vistaJuego.getWorldWidth() / 2, vistaJuego.getWorldHeight() / 2, 0);
		
		//box2d obj. y colisiones
		mundo = new World(new Vector2(0, -9), true);
		b2dr = new Box2DDebugRenderer();
		
		new B2CreaMundos(mundo, mapa);
		
		jugador = new Sonic(mundo, this);
		jugadorAlt = new Tails(mundo, this);
		
		mundo.setContactListener(new WorldContactListener());
		
        SonicProject.admin.get("audio/musica/menu.mp3", Music.class).stop();
        SonicProject.admin.get("audio/musica/gameOver.mp3", Music.class).stop();
        SonicProject.admin.get("audio/musica/pantallaJuego.mp3", Music.class).play();
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
	
	public void handleInput(float dt) {
		if(jugador.estadoActual != Sonic.Estado.MUERTO) {
			if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
				jugador.b2cuerpo.applyLinearImpulse(new Vector2(0, 6f), jugador.b2cuerpo.getWorldCenter(), true);
				SonicProject.admin.get("audio/sonidos/s_salto.wav", Sound.class).play();
			}
			if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//				jugador.golpe();
				jugador.b2cuerpo.applyLinearImpulse(new Vector2(0.105f, 0), jugador.b2cuerpo.getWorldCenter(), true);
			}
			if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				jugador.b2cuerpo.applyLinearImpulse(new Vector2(-0.105f, 0), jugador.b2cuerpo.getWorldCenter(), true);
			}
		}
		
		if(jugadorAlt.estadoActual != Tails.Estado.MUERTO) {
			if(Gdx.input.isKeyJustPressed(Input.Keys.W)) {
				jugadorAlt.b2cuerpo.applyLinearImpulse(new Vector2(0, 6f), jugadorAlt.b2cuerpo.getWorldCenter(), true);
				SonicProject.admin.get("audio/sonidos/s_salto.wav", Sound.class).play();
			}
			if(Gdx.input.isKeyPressed(Input.Keys.D)) {
//				jugadorAlt.golpe();
				jugadorAlt.b2cuerpo.applyLinearImpulse(new Vector2(0.1f, 0), jugadorAlt.b2cuerpo.getWorldCenter(), true);
			}
			if(Gdx.input.isKeyPressed(Input.Keys.A)) {
				jugadorAlt.b2cuerpo.applyLinearImpulse(new Vector2(-0.1f, 0), jugadorAlt.b2cuerpo.getWorldCenter(), true);
			}
		}
	}
	
	public void update(float dt) {
	    handleInput(dt);

	    mundo.step(1 / 60f, 6, 2);

	    jugador.update(dt);
	    jugadorAlt.update(dt);
	    hud.update(dt);

	    float limiteIzquierdo = 3.5f;
	    float limiteDerecho = 20.25f;
	    float limiteInferior = 0.0f; 
	    float limiteSuperior = 55.0f;
	    float posX = jugador.b2cuerpo.getPosition().x;
	    float posY = jugador.b2cuerpo.getPosition().y;


	    // Asegurarse de que el personaje no se salga del límite izquierdo
	    if (posX < limMapaIzq) {
	        jugador.b2cuerpo.setTransform(limMapaIzq, jugador.b2cuerpo.getPosition().y, 0);
	    }

	    // Asegurarse de que el personaje no se salga del límite derecho
	    if (posX > limMapaDer) {
	        jugador.b2cuerpo.setTransform(limMapaDer, jugador.b2cuerpo.getPosition().y, 0);
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
	
	/*public void update(float dt) {
    handleInput(dt);

    mundo.step(1 / 60f, 6, 2);

    jugador.update(dt);
    jugadorAlt.update(dt);
    hud.update(dt);

    float limiteIzquierdo = 10.0f; // Ajusta este valor según tu escenario
    float limiteDerecho = 100.0f; // Ajusta este valor según tu escenario

    float posX = jugador.b2cuerpo.getPosition().x;

    // Actualizar la posición X de la cámara solo si el personaje está dentro de los límites
    if (posX > limiteIzquierdo && posX < limiteDerecho) {
        camJuego.position.x = posX;
    }

    camJuego.update();
    renderizar.setView(camJuego);
}
*/

	@Override
	public void render(float delta) {
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
		
	    if (FinJuego()) {
	        tiempoEspera -= delta; // Reduzca el tiempo de espera

	        if (tiempoEspera <= 0 && !cambioPantalla) {
	            // Cuando el tiempo de espera haya transcurrido y no hayamos cambiado de pantalla aún
	            juego.setScreen(new PantallaGameOver(juego));
	            cambioPantalla = true; // Evita que cambiemos de pantalla varias veces
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