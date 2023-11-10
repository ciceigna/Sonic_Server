package Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.reactive.sonicserver.red.HiloServidor;

public class PantallaServidor extends ScreenAdapter {
	private HiloServidor hs;
	
    public PantallaServidor() {
		hs = new HiloServidor();
		hs.start();
	}

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    }

    @Override
    public void dispose() {
    }
}
