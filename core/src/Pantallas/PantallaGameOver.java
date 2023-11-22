package Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sonic.servidor.SonicServer;

public class PantallaGameOver extends ScreenAdapter {
    private Stage stage;

    public PantallaGameOver(final SonicServer juego) {
        stage = new Stage(new FitViewport(SonicServer.V_ANCHO, SonicServer.V_ALTO));

        SonicServer.admin.get("audio/musica/pantallaJuego.mp3", Music.class).stop();
        SonicServer.admin.get("audio/musica/gameOver.mp3", Music.class).play();
        
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
