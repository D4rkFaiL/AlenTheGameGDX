package com.fiveyoukais.alen.Tools;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
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


/**
 * Created by Arthur on 04/04/2020.
 */

public class B2WorldCreator{


    private Screen screen;

    public static Array<Morcego> goombas;
    public static Array<Ratao> ratao;
    public static Array<Slime> slime;
    public static Array<Boss> boss;
    public static Array<Pedras> pedras;

    public B2WorldCreator(PlayScreen screen) {
        this.screen = screen;
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        goombas = new Array<>();
        ratao = new Array<>();
        slime = new Array<>();
        boss = new Array<>();
        pedras = new Array<>();

        CriarBlocos criarBlocos = new CriarBlocos();
        CriarInimigos criarInimios = new CriarInimigos();

        criarBlocos.start();
        criarBlocos.setName("GerarBlocos");
        criarBlocos.run(world,map,bdef,shape,fdef,screen);

        criarInimios.start();
        criarInimios.setName("GerarInimigos");
        criarInimios.run(map,screen);

    }

    public Array<Morcego> getGoombas() {
        return goombas;
    }
    public Array<Ratao> getRatao() {
        return ratao;
    }
    public Array<Slime> getSlime() {
        return slime;
    }
    public Array<Boss> getBoss() {
        return boss;
    }
    public Array<Pedras> getPedras() {
        return pedras;
    }
}
