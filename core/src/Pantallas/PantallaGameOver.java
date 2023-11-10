package Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sonic.servidor.SonicProject;

public class PantallaGameOver extends ScreenAdapter {
    private Stage stage;
    Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

    public PantallaGameOver(final SonicProject juego) {
        stage = new Stage(new FitViewport(SonicProject.V_ANCHO, SonicProject.V_ALTO));

        SonicProject.admin.get("audio/musica/pantallaJuego.mp3", Music.class).stop();
        SonicProject.admin.get("audio/musica/gameOver.mp3", Music.class).play();
        
        Gdx.input.setInputProcessor(stage);

        // Cargar la textura "gOver" de objetos.png usando el archivo .atlas
        Texture gOverTexture = new Texture("gOver.png");
        Image gOverImage = new Image(gOverTexture);

        // Crear una tabla para organizar elementos en el escenario
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Agregar la imagen "gOver" al centro de la pantalla
        table.add(gOverImage).expand().center().row();

        // Crear botón para reiniciar
        TextButton restartButton = new TextButton("Reiniciar", skin);
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Agregar aquí la lógica para reiniciar el juego
                juego.setScreen(new PantallaJuego(juego));
            }
        });

        // Crear botón para ir al menú principal
        TextButton menuButton = new TextButton("Menu Principal", skin);
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Agregar aquí la lógica para ir al menú principal
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        // Agregar botones a la tabla
        table.add(restartButton).padBottom(20f).row();
        table.add(menuButton);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0.5f); // Fondo negro con transparencia
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
