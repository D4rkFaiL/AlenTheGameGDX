package com.fiveyoukais.alen.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.fiveyoukais.alen.Alen;
import com.fiveyoukais.alen.Screens.GameOverScreen;
import com.fiveyoukais.alen.Screens.MenuScreen;
import com.fiveyoukais.alen.Screens.TransicaoScreen;
import com.fiveyoukais.alen.Sprites.Outros.AlenP;

import static com.fiveyoukais.alen.Alen.fase;
import static com.fiveyoukais.alen.Alen.fim;

/**
 * Created by Arthur on 02/07/2020.
 */

public class GerenciarVitoria extends Thread{
    public boolean run(AlenP player) {
        Gdx.app.log("Thread","Thread " + getName());
        if((player.currentstate == AlenP.State.Dead && player.getStatetimer()>1.5f) || Alen.cabo){
            return true;
        }
        return false;
    }
}
