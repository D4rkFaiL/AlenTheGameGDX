package com.fiveyoukais.alen.Sprites.Blocos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.fiveyoukais.alen.Alen;
import com.fiveyoukais.alen.Scenes.HUD;
import com.fiveyoukais.alen.Screens.PlayScreen;

/**
 * Created by Arthur on 04/04/2020.
 */

public class Brick extends InteractiveTileObject{
    public Brick(PlayScreen screen, MapObject object){
        super(screen,object);
        fixture.setUserData(this);
        setCategoryFilter(Alen.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        setCategoryFilter(Alen.DESTROYED_BIT);
        getCell().setTile(null);
        HUD.addScore(200);
        Alen.manager.get("sounds/break.wav", Sound.class).play();
    }
}
