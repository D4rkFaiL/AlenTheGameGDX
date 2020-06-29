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
import com.fiveyoukais.alen.Alen;
import com.fiveyoukais.alen.Screens.PlayScreen;
import com.fiveyoukais.alen.Sprites.Blocos.Agua;
import com.fiveyoukais.alen.Sprites.Blocos.Brick;
import com.fiveyoukais.alen.Sprites.Blocos.Coin;
import com.fiveyoukais.alen.Sprites.Blocos.Limite;

/**
 * Created by Arthur on 25/06/2020.
 */

public class CriarBlocos extends Thread{

    public void run(World world, TiledMap map, BodyDef bdef, PolygonShape shape, FixtureDef fdef, PlayScreen screen) {

        Gdx.app.log("Thread","É a Thread: " + getName());
        Body body;
        //Cria o chão
        for (MapObject object: map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2 ) / Alen.PPM,(rect.getY() + rect.getHeight()/2) / Alen.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2/Alen.PPM,rect.getHeight()/2/Alen.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);

        }

        //Cria o bloco de powerup
        for (MapObject object: map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Coin(screen,object);

        }

        //Bota tijolao
        for (MapObject object: map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Brick(screen,object);
        }

        //Bota os limites de beirada
        for (MapObject object: map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Limite(screen,object);
        }

        //Bota as agua
        for (MapObject object: map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Agua(screen,object);
        }
        super.run();
    }
}
