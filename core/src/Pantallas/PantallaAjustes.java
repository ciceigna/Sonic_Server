package Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Graphics.Monitor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sonic.servidor.SonicServer;

public class PantallaAjustes extends ScreenAdapter {
    private Stage stage;
    private Table table;
    private Preferences preferencias;
    Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
    
    private Slider barraVolMusica;
	private Slider barraVolSonido;
    private TextButton aplicarButton; // Botón para aplicar y guardar cambios
    private CheckBox fullscreenCheckbox;

    public PantallaAjustes(final SonicServer juego) {
        stage = new Stage(new FitViewport(SonicServer.V_ANCHO, SonicServer.V_ALTO));
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        preferencias = Gdx.app.getPreferences("GameSettings");
        
	     // Cargar valores actuales
	     float musicVolume = preferencias.getFloat("musicVolume", 0.5f);
	     float soundVolume = preferencias.getFloat("soundVolume", 0.5f);
	     float generalVolume = preferencias.getFloat("generalVolume", 0.5f);
	     boolean fullscreen = preferencias.getBoolean("fullscreen", false);

        
        // Agrega etiquetas y controles deslizantes
        table.row();
        Label musicVolumeLabel = new Label("Volumen de Musica", skin);
        barraVolMusica = new Slider(0, 1, 0.1f, false, skin);
        table.add(musicVolumeLabel).padRight(10);
        table.add(barraVolMusica).padBottom(20);
        
        table.row();
        Label soundVolumeLabel = new Label("Volumen de Sonidos", skin);
        barraVolSonido = new Slider(0, 1, 0.1f, false, skin);
        table.add(soundVolumeLabel).padRight(10);
        table.add(barraVolSonido).padBottom(20);
        
        // Agrega casilla de verificación para pantalla completa
        table.row();
        fullscreenCheckbox = new CheckBox("Pantalla Completa", skin);
        table.add(fullscreenCheckbox).padBottom(20);
        
        table.row();
        aplicarButton = new TextButton("Aplicar", skin);
        aplicarButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	juego.setScreen(new PantallaMenu(juego));
            }
        });
        table.add(aplicarButton).padBottom(20);

        aplicarButton.addListener(new ClickListener() {
        	
            @Override
            public void clicked(InputEvent event, float x, float y) {

                boolean pantallaCompleta = fullscreenCheckbox.isChecked();
                Graphics graphics = Gdx.graphics;

                if (pantallaCompleta) {
                    Monitor primaryMonitor = graphics.getPrimaryMonitor();
                    DisplayMode currentMode = graphics.getDisplayMode(primaryMonitor);
                    graphics.setFullscreenMode(currentMode);
                } else {
                    graphics.setWindowedMode(640, 480);
                }
                
                preferencias.putFloat("musicVolume", barraVolMusica.getValue());
                preferencias.putFloat("soundVolume", barraVolSonido.getValue());
                preferencias.putBoolean("fullscreen", pantallaCompleta);
                preferencias.flush(); // Guardar las preferencias
                
                juego.setScreen(new PantallaMenu(juego));
            }
            
        });	

        stage.addActor(table);
    }
        // Guardar configuraciones en las preferencias

    
    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0.5f); // Fondo negro con transparencia
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        float musicVolume = barraVolMusica.getValue();
        float soundVolume = barraVolSonido.getValue();
        boolean fullscreen = fullscreenCheckbox.isChecked();
        
        // Guardar las configuraciones actualizadas en las preferencias
        Preferences preferencias = Gdx.app.getPreferences("GameSettings");
        preferencias.putFloat("musicVolume", musicVolume);
        preferencias.putFloat("soundVolume", soundVolume);
        preferencias.putBoolean("fullscreen", fullscreen);
        preferencias.flush();
        
        stage.draw();
    }



    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
        skin.dispose();
    }
}
