package Escenas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FondoParallax {
    private Texture[] capas; // Arreglo de texturas para las capas de fondo
    private float[] velocidades;   // Velocidades de las capas de fondo
    private OrthographicCamera camara;
	private SpriteBatch batch;

    public FondoParallax(OrthographicCamera camara) {
        this.camara = camara;

        batch = new SpriteBatch();
        // Carga las texturas de las capas de fondo
        capas = new Texture[4];
        capas[0] = new Texture(Gdx.files.internal("fondo01.png"));
        capas[1] = new Texture(Gdx.files.internal("fondo02.png"));
        capas[2] = new Texture(Gdx.files.internal("fondo03.png"));
        capas[3] = new Texture(Gdx.files.internal("fondo04.png"));

        // Define las velocidades de las capas de fondo (ajusta según tus necesidades)
        velocidades = new float[]{1.0f, 0.75f, 0.5f, 0.25f};
    }

    public void render(float zoom, float yOffset) {
        batch.begin();
        for (int i = 0; i < capas.length; i++) {
            float parallaxX = (camara.position.x * velocidades[i]) - (camara.viewportWidth / 2);
            float parallaxY = yOffset; // Ajusta esta posición Y
            float parallaxZoom = zoom; // Ajusta este valor para el zoom
            batch.draw(capas[i], -parallaxX, -parallaxY, Gdx.graphics.getWidth() / parallaxZoom, Gdx.graphics.getHeight() / parallaxZoom);
        }
        batch.end();
    }

    public void dispose() {
        for (Texture capa : capas) {
            capa.dispose();
        }
    }
}