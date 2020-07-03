package com.fiveyoukais.alen.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.fiveyoukais.alen.Scenes.HUD;

/**
 * Created by Arthur on 02/07/2020.
 */

public class ContagemTempo extends Thread {
    public void run(){
        Gdx.app.log("Thread","Thread " + getName());
        if (HUD.timecount >= 1f){
            Gdx.app.log("a","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            HUD.worldtimer--;
            HUD.timecount = 0;
        }
    }
}
