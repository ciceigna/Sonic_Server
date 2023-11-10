package Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.sonic.servidor.SonicProject;

import Escenas.Hud;

public class Anillos extends ObjetoInteractivo {
	
	public Anillos(World mundo, TiledMap mapa, Rectangle bordes) {
		super(mundo, mapa, bordes);	
	}
	
//	@Override
//	public void recogeAnillo() {
//		Gdx.app.log("Anillo", "Colision");
//		if(getCell().getTile().getId() == ANILLO_VACIO)
//			SonicProject.admin.get("audio/sonidos/anillo.wav", Sound.class).play();
//		getCell().setTile(tileSet.getTile(ANILLO_VACIO));
//		Hud.addPuntuacion(100);
//	}
	
}
