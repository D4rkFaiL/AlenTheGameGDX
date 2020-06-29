package com.fiveyoukais.alen.Sprites.Outros;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.fiveyoukais.alen.Alen;
import com.fiveyoukais.alen.Screens.PlayScreen;


/**
 * Created by Arthur on 17/06/2020.
 */

public class BlocoTeto extends Sprite{
    PlayScreen screen;
    World world;
    Array<TextureRegion> frames;
    Animation fireSquish;
    Animation fireAnimation;
    Animation fireDone;
    float stateTime;
    float tempoSquish = 0.2f;
    boolean destroyed;
    boolean setToDestroy;
    boolean fireRight;
    boolean squish;

    Body b2body;
    public BlocoTeto(PlayScreen screen, float x, float y, boolean fireRight){
        this.fireRight = fireRight;
        this.screen = screen;
        this.world = screen.getWorld();

        frames = new Array<TextureRegion>();

        for(int i = 0; i < 4; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("TIROALEN"), i * 6, 0, 6, 4));
        }
        fireAnimation = new Animation(0.1f, frames);

        frames.clear();

        for(int i = 0; i < 1; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("TIROALEN"), 4 * 6, 0, 6, 4));
        }
        fireSquish = new Animation(0.2f, frames);

        frames.clear();

        for(int i = 0; i < 1; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("TIROALEN"), 3 * 6, 0, 6, 4));
        }
        fireDone = new Animation(0.2f, frames);

        setRegion((TextureRegion) fireAnimation.getKeyFrame(stateTime,false));
        setBounds(x, y, 6/ Alen.PPM, 4 / Alen.PPM);
        defineBlocoTeto();
    }

    public void defineBlocoTeto(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(fireRight ? getX() + 12 /Alen.PPM : getX() - 12 /Alen.PPM, getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        if(!world.isLocked())
            b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(3 / Alen.PPM);
        fdef.filter.categoryBits = Alen.FIREBALL_BIT;
        fdef.filter.maskBits =  Alen.GROUND_BIT |
                                Alen.COIN_BIT |
                                Alen.BRICK_BIT |
                                Alen.ENEMY_BIT;

        fdef.shape = shape;
        fdef.restitution = 1;
        fdef.friction = 0;
        b2body.createFixture(fdef).setUserData(this);
        b2body.setLinearVelocity(new Vector2(fireRight ? 3 : -3, 1f));
    }

    public void update(float dt){
        stateTime += dt;
        setRegion((TextureRegion) fireDone.getKeyFrame(stateTime, false));
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if((stateTime > 3 || setToDestroy) && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        }
        if(b2body.getLinearVelocity().y > 2f)
            b2body.setLinearVelocity(b2body.getLinearVelocity().x, 2f);
        if((fireRight && b2body.getLinearVelocity().x < 0) || (!fireRight && b2body.getLinearVelocity().x > 0))
            setToDestroy();

        if(destroyed){
            b2body = null;
        }
    }

    public void setToDestroy(){
        setToDestroy = true;
    }

    public boolean isDestroyed(){
        return destroyed;
    }

    public void setSquish(){
        squish = true;
    }
}
