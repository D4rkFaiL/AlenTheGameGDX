package com.fiveyoukais.alen.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.fiveyoukais.alen.Alen;
import com.fiveyoukais.alen.Screens.PlayScreen;
import com.fiveyoukais.alen.Sprites.Blocos.Agua;
import com.fiveyoukais.alen.Sprites.Blocos.Brick;
import com.fiveyoukais.alen.Sprites.Blocos.Coin;
import com.fiveyoukais.alen.Sprites.Blocos.Limite;
import com.fiveyoukais.alen.Sprites.Inimigos.Boss;
import com.fiveyoukais.alen.Sprites.Inimigos.Morcego;
import com.fiveyoukais.alen.Sprites.Inimigos.Pedras;
import com.fiveyoukais.alen.Sprites.Inimigos.Ratao;
import com.fiveyoukais.alen.Sprites.Inimigos.Slime;

import static com.fiveyoukais.alen.Tools.B2WorldCreator.boss;
import static com.fiveyoukais.alen.Tools.B2WorldCreator.goombas;
import static com.fiveyoukais.alen.Tools.B2WorldCreator.pedras;
import static com.fiveyoukais.alen.Tools.B2WorldCreator.ratao;
import static com.fiveyoukais.alen.Tools.B2WorldCreator.slime;

/**
 * Created by Arthur on 25/06/2020.
 */

public class CriarInimigos extends Thread{

    public void run(TiledMap map, PlayScreen screen) {

        Gdx.app.log("Thread","É a Thread: " + getName());
        //cria todos os goombas;

        for (MapObject object: map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            goombas.add(new Morcego(screen,rect.getX()/Alen.PPM,rect.getY()/Alen.PPM));

        }

        //Cria ratao

        for (MapObject object: map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            ratao.add(new Ratao(screen,rect.getX()/Alen.PPM,rect.getY()/Alen.PPM));
        }

        //Cria slime

        for (MapObject object: map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            slime.add(new Slime(screen,rect.getX()/Alen.PPM,rect.getY()/Alen.PPM));
        }

        //Cria boss

        for (MapObject object: map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            boss.add(new Boss(screen,rect.getX()/Alen.PPM,rect.getY()/Alen.PPM));
        }

        //Cria boss

        for (MapObject object: map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            pedras.add(new Pedras(screen,rect.getX()/Alen.PPM,rect.getY()/Alen.PPM));
        }
        //super.run();
    }
}
