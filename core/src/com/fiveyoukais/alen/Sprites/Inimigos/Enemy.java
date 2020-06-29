package com.fiveyoukais.alen.Sprites.Inimigos;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.fiveyoukais.alen.Alen;
import com.fiveyoukais.alen.Screens.PlayScreen;

/**
 * Created by Arthur on 08/06/2020.
 */

public abstract class Enemy extends Sprite {

    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    public Vector2 velocity;

    public Enemy(PlayScreen screen,float x,float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x,y);
        defineEnemy();
        velocity = new Vector2(1,0);
        b2body.setActive(false);
    }

    protected abstract void defineEnemy();

    public abstract void update(float dt);
    public abstract void hitOnHead();

    public void reverseVelocity(boolean x,boolean y){
        if(x)
            velocity.x = -velocity.x;
        if(y)
            velocity.y = -velocity.y;
    }
}
