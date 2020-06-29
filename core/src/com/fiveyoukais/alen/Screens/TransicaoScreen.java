package com.fiveyoukais.alen.Screens;

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

import static com.fiveyoukais.alen.Alen.fase;

/**
 * Created by Arthur on 17/06/2020.
 */

public class TransicaoScreen implements Screen {

    private Viewport viewport;
    private Stage stage;
    private String levels[] = new String[3];
    private Game game;

    private Texture texture;
    private Sprite sprite;
    private int cutsceneIndex = 1;

    Music music;

    public TransicaoScreen(Game game){
        this.game = game;
        levels[0] = "Esgoto 1-1";
        levels[1] = "Esgoto 2-1";
        levels[2] = "BOSS";

        viewport = new FitViewport(Alen.V_WIDTH,Alen.V_HEIGHT,new OrthographicCamera());
        stage = new Stage(viewport,((Alen)game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("nokia.fnt")), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label(levels[fase],new Label.LabelStyle(new BitmapFont(Gdx.files.internal("nokia.fnt")), Color.WHITE));
        table.add(gameOverLabel).expandX();
        table.row();

        stage.addActor(table);

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

        if (fase == 0){
            texture = new Texture(Gdx.files.internal("cutscene/Cutscene"+cutsceneIndex+".png"));
            sprite = new Sprite(texture,200,191);
            sprite.setPosition(Alen.V_WIDTH - 345,0);
            sprite.setSize(200 * 1.5f,150 * 1.5f);

            stage.getBatch().begin();
            sprite.draw(stage.getBatch());
            stage.getBatch().end();
        }
        else{
            Gdx.gl.glClearColor(0,0,0,1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            stage.draw();
        }

        inputManager();
    }

    public void inputManager(){

        if(Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            if (fase == 0){
                cutsceneIndex++;
                if(cutsceneIndex == 6){
                    game.setScreen(new PlayScreen((Alen)game));
                    dispose();
                    music.stop();
                }
            }
            else{
                game.setScreen(new PlayScreen((Alen)game));
                dispose();
                music.stop();
            }
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
