package com.fiveyoukais.alen.Sprites.Blocos;

import com.badlogic.gdx.maps.MapObject;
import com.fiveyoukais.alen.Alen;
import com.fiveyoukais.alen.Screens.PlayScreen;

/**
 * Created by Arthur on 19/06/2020.
 */

public class Limite extends InteractiveTileObject{
    public Limite(PlayScreen screen, MapObject object){
        super(screen,object);
        fixture.setUserData(this);
        setCategoryFilter(Alen.VOLTAR_BIT);
    }

    @Override
    public void onHeadHit() {}
}
