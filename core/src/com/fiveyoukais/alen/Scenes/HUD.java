package com.fiveyoukais.alen.Scenes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fiveyoukais.alen.Alen;
import com.fiveyoukais.alen.Tools.ContagemTempo;



/**
 * Created by Arthur on 04/04/2020.
 */

public class HUD implements Disposable{
    public Stage stage;
    private Viewport viewport;

    public static int worldtimer;
    public static float timecount;
    private static int score;

    Label countdownlabel;
    static Label scorelabel;

    ContagemTempo contagemTempo;

    public HUD(SpriteBatch sb){

        worldtimer = 300;
        timecount = 0;
        score = 0;

        viewport = new FitViewport(Alen.V_WIDTH,Alen.V_HEIGHT,new OrthographicCamera());

        stage = new Stage(viewport,sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("nokia.fnt")), Color.WHITE);

        countdownlabel = new Label(String.format("%03d",worldtimer), font);
        scorelabel = new Label(String.format("%06d",score),font);
        table.row();
        table.add(scorelabel).expandX().padTop(23).padRight(55);
        table.add(countdownlabel).expandX().padTop(23).padRight(19);

        stage.addActor(table);

        contagemTempo = new ContagemTempo();
        contagemTempo.start();
        contagemTempo.setName("Tempo");

    }

    public void update(float dt){
        timecount += dt;
        contagemTempo.run();
        countdownlabel.setText(String.format("%03d",worldtimer));
    }

    public static void addScore(int value){
        score += value;
        scorelabel.setText(String.format("%06d",score));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
