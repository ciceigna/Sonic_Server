 package Escenas;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sonic.servidor.SonicServer;

public class Hud implements Disposable{
	public Stage escenario;
	private Viewport viewport;
	
	private float cuentaTiempo;
	private static Integer puntaje;
	private static Integer anillos;

	private Label etiquetaTiempo;
	private Label etiquetaTiempepopo;
	private Label etiquetaPuntaje;
	private static Label etiquetaPunputapajepe;
	private Label etiquetaAnillos;
	private static Label etiquetaApanipillospo;
	
	public Hud(SpriteBatch sb) {
		cuentaTiempo = 0;
		puntaje = 0;
		anillos = 0;
		
		viewport = new FitViewport(SonicServer.V_ANCHO,SonicServer.V_ALTO,new OrthographicCamera());
		escenario = new Stage(viewport, sb);
		
		Table mesa = new Table();
		mesa.top();
		mesa.left();
		mesa.setFillParent(true);
		

		etiquetaTiempepopo = new Label(String.format("%06f", cuentaTiempo), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		etiquetaPunputapajepe = new Label(String.format("%06d", puntaje), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		etiquetaApanipillospo = new Label(String.format("%01d", anillos = 0), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		etiquetaPuntaje = new Label("  PUNTAJE", new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
		etiquetaTiempo = new Label("TIEMPO", new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
		etiquetaAnillos = new Label(" ANILLOS", new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
		
		mesa.add(etiquetaPuntaje);
		mesa.add(etiquetaPunputapajepe);
		mesa.row();
		mesa.add(etiquetaTiempo);
		mesa.add(etiquetaTiempepopo);
		mesa.row();
		mesa.add(etiquetaAnillos);
		mesa.add(etiquetaApanipillospo);
		
		escenario.addActor(mesa);
	}
	
	public void update(float dt) {
		   cuentaTiempo += dt;
		    int minutos = (int) cuentaTiempo / 60;
		    int segundos = (int) cuentaTiempo % 60;
		    etiquetaTiempepopo.setText(String.format("%02d:%02d", minutos, segundos));
	}

	public static void addPuntaje(int valor) {
		puntaje += valor;
		etiquetaPunputapajepe.setText(String.format("%06d", puntaje));
	}
	
	public static void addAnillo(int valor) {
		anillos += valor;
		etiquetaApanipillospo.setText(String.format("%01d", anillos));
	}
	
	@Override
	public void dispose() {
		escenario.dispose();
		
	}
}
