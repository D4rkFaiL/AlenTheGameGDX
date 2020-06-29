package com.fiveyoukais.alen.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fiveyoukais.alen.Alen;

import org.lwjgl.openal.AL;

/**
 * Created by Arthur on 17/06/2020.
 */

public class GameOverScreen implements Screen {

    private Viewport viewport;
    private Stage stage;
    private Game game;
    private Music music;

    private Texture texture;
    private Sprite sprite;
    private int valor = 0;

    public GameOverScreen(Game game){
        this.game = game;
        viewport = new FitViewport(Alen.V_WIDTH/(Alen.PPM/1) ,Alen.V_HEIGHT /(Alen.PPM/1),new OrthographicCamera());
        stage = new Stage(viewport,((Alen)game).batch);

        music = Alen.manager.get("music/AlenMenu.wav",Music.class);
        music.setLooping(true);
        music.setVolume(0.15f);
        music.play();

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        texture = new Texture(Gdx.files.internal("cutscene/GameOver.png"));

        sprite = new Sprite(texture,200,150);
        sprite.setPosition(Alen.V_WIDTH - 345,0);
        sprite.setSize(200 * 1.5f,150 * 1.5f);

        stage.getBatch().begin();
        sprite.draw(stage.getBatch());
        stage.getBatch().end();
        inputManager();
    }

    public void inputManager(){


        if(Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            music.stop();
            game.setScreen(new MenuScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
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
    }
}
