//package Pantallas;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.ScreenAdapter;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.ui.Image;
//import com.badlogic.gdx.scenes.scene2d.ui.Table;
//import com.reactive.sonicserver.red.HiloServidor;
//import com.sonic.servidor.SonicServer;
//
//import utiles.Global;
//
//public class PantallaEspera extends ScreenAdapter {
//	private Stage stage;
//	private HiloServidor hs;
//	
//    public PantallaEspera(SonicServer juego) {
//        stage = new Stage();
//		hs = new HiloServidor();
//        Gdx.input.setInputProcessor(stage);
//
//        // Cargar la textura "esperaImagen" de objetos.png usando el archivo .atlas
//        Texture esperaTextura = new Texture("esperaImagen.png");
//        Image esperaImagen = new Image(esperaTextura);
////
////      // Crear una tabla para organizar elementos en el escenario
//        Table table = new Table();
//        table.setFillParent(true);
//        stage.addActor(table);
//
//        // Agregar la imagen "gOver" al centro de la pantalla
//        table.add(esperaImagen).expand().center().row();
//        
//        if(Global.empieza) {
//        	juego.setScreen(new PantallaJuego(juego));
//        }
//    }
//
//    @Override
//    public void show() {
//        super.show();
//    }
//
//    @Override
//    public void render(float delta) {
//        Gdx.gl.glClearColor(0, 0, 0, 0.5f); // Fondo negro con transparencia
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        stage.draw();
//    }
//
//    @Override
//    public void dispose() {
//        stage.dispose();
//    }
//}
