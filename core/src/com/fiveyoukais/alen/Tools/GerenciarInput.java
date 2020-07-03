package com.fiveyoukais.alen.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.fiveyoukais.alen.Sprites.Outros.AlenP;

/**
 * Created by Arthur on 02/07/2020.
 */

public class GerenciarInput extends Thread{
    public void run(AlenP player, float dt) {
        Gdx.app.log("Thread","Thread " + getName());
        if(player.currentstate != AlenP.State.Dead) {

            if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.Z) && player.getGun())
                player.fire(dt);

            if ((Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.DPAD_UP) ||
                    Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.W)) && !player.getPulo()) {
                player.setPulo(true);
                player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
            }

            if ((Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.DPAD_LEFT) && player.b2body.getLinearVelocity().x >= -2) ||
                    (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.A) && player.b2body.getLinearVelocity().x >= -2))
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);

            if ((Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.DPAD_RIGHT) && player.b2body.getLinearVelocity().x <= 2) ||
                    (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.D) && player.b2body.getLinearVelocity().x <= 2))
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        }
    }
}
