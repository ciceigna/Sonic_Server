package com.sonic.servidor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import Pantallas.PantallaJuego;

public class SonicServer extends Game {
	public static final int V_ANCHO = 520;
	public static final int V_ALTO = 390;
	public static final float PPM = 75;
	
	//Box2D Collision Bits
	public static final short BIT_VACIO = 0;
	public static final short BIT_PISO = 1;
	public static final short BIT_SONIC = 2;
	public static final short BIT_ANILLO = 8;
	public static final short BIT_DESTRUIDO = 16;
	public static final short BIT_OBJETO = 32;
	public static final short BIT_ENEMIGO = 64;
	public static final short BIT_CAPOCHA_E = 128;
	public static final short BIT_ITEM = 256;
	public static final short BIT_CAPOCHA_J = 512;
	
	public SpriteBatch batch;
	
	public static AssetManager admin;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		
		admin = new AssetManager();
		admin.load("audio/musica/menu.mp3", Music.class);
		admin.load("audio/musica/pantallaJuego.mp3", Music.class);
		admin.load("audio/musica/gameOver.mp3", Music.class);
		admin.load("audio/sonidos/s_anillo.wav", Sound.class);
		admin.load("audio/sonidos/s_muerte.wav", Sound.class);
		admin.load("audio/sonidos/s_salto.wav", Sound.class);
		admin.finishLoading();
		
		setScreen(new PantallaJuego(this));
	}

	@Override
	public void render() {
		super.render();
		admin.update();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		admin.dispose();
		batch.dispose();
	}
}
