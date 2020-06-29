package com.fiveyoukais.alen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fiveyoukais.alen.Screens.MenuScreen;
import com.fiveyoukais.alen.Screens.PlayScreen;

public class Alen extends Game{
    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
    public static final float PPM = 100;
	public SpriteBatch batch;

	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short ALEN_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short AGUA_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static final short ENEMY_HEAD_BIT = 128;
	public static final short ITEM_BIT = 256;
	public static final short ALEN_HEAD_BIT = 512;
    public static final short ALEN_FEET_BIT = 1024;
    public static final short FIREBALL_BIT = 2048;
	public static final short FIREBALL_FEET_BIT = 4096;
    public static final short VOLTAR_BIT = 8192;
    public static final short BOSS_BIT = 16384;
    public static final short PEDRA_BIT = (short) 32768;
    public static final short BOSS_HEAD_BIT = (short) 65536;

    public static AssetManager manager;

    public static int fase = 0;
    public static boolean fim = false;
    public static boolean hittable = false;
    public static boolean cabo = true;

	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();

		//SFX
		manager.load("sounds/break.wav", Sound.class);
		manager.load("sounds/bump.wav", Sound.class);
		manager.load("sounds/coin.wav", Sound.class);
        manager.load("sounds/select.wav", Sound.class);
        manager.load("sounds/pisao.wav", Sound.class);

        //Music
        manager.load("music/Esgoto3.wav", Music.class);
        manager.load("music/AlenMenu.wav", Music.class);
		manager.finishLoading();

		setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		manager.dispose();
		batch.dispose();
	}


}
