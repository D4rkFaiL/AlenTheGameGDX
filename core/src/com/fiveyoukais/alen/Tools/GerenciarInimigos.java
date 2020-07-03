package com.fiveyoukais.alen.Tools;

import com.badlogic.gdx.Gdx;
import com.fiveyoukais.alen.Alen;
import com.fiveyoukais.alen.Sprites.Inimigos.Enemy;
import com.fiveyoukais.alen.Sprites.Outros.AlenP;

/**
 * Created by Arthur on 02/07/2020.
 */

public class GerenciarInimigos extends Thread {
    public void run(float dt, B2WorldCreator creator, AlenP player) {
        Gdx.app.log("Thread","Thread " + getName());
        for (Enemy enemy : creator.getGoombas()) {
            enemy.update(dt);
            if (enemy.getX() < player.getX() + 224 / Alen.PPM)
                enemy.b2body.setActive(true);
        }

        for (Enemy enemy : creator.getRatao()) {
            enemy.update(dt);
            if (enemy.getX() < player.getX() + 224 / Alen.PPM)
                enemy.b2body.setActive(true);
        }

        for (Enemy enemy : creator.getSlime()) {
            enemy.update(dt);
            if (enemy.getX() < player.getX() + 224 / Alen.PPM)
                enemy.b2body.setActive(true);
        }

        for (Enemy enemy : creator.getBoss()) {
            enemy.update(dt);
            if (enemy.getX() < player.getX() + 224 / Alen.PPM)
                enemy.b2body.setActive(true);
        }

        for (Enemy enemy : creator.getPedras()) {
            enemy.update(dt);
            if (enemy.getX() < player.getX() + 224 / Alen.PPM)
                enemy.b2body.setActive(true);
        }
    }
}
