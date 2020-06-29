package com.fiveyoukais.alen.Sprites.Blocos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.fiveyoukais.alen.Alen;
import com.fiveyoukais.alen.Scenes.HUD;
import com.fiveyoukais.alen.Screens.PlayScreen;
import com.fiveyoukais.alen.Sprites.Items.ItemDef;
import com.fiveyoukais.alen.Sprites.Items.Mushroom;


/**
 * Created by Arthur on 04/04/2020.
 */

public class Coin extends InteractiveTileObject{

    private static TiledMapTileSet tileSet;
    //Valor do sprite que troca ao ser hitado!
    private final int semPower = 25;

    public Coin(PlayScreen screen, MapObject object){
        super(screen,object);
        tileSet = map.getTileSets().getTileSet("ChaoPedra");
        fixture.setUserData(this);
        setCategoryFilter(Alen.COIN_BIT);
    }

    @Override
    public void onHeadHit() {
        //Same thing as other sounds coments,nao temos os sprites ainda :)
        if(getCell().getTile().getId() == semPower)
            Alen.manager.get("sounds/bump.wav", Sound.class).play();
        else
        {
            //Ve se tem a caracterisca no bloco de spawnar mushroom;
            if (object.getProperties().containsKey("mushroom")) {
                //Cria o cogumelo!
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / Alen.PPM), Mushroom.class));
            }

            Alen.manager.get("sounds/coin.wav", Sound.class).play();
            //Seta o sprite para ser trocado baseado no id/valor de BLANK_COIN;
            getCell().setTile(tileSet.getTile(semPower));

            HUD.addScore(100);
        }
    }
}
