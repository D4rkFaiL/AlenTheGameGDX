package com.fiveyoukais.alen.Sprites.Inimigos;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.fiveyoukais.alen.Alen;
import com.fiveyoukais.alen.Scenes.HUD;
import com.fiveyoukais.alen.Screens.PlayScreen;


/**
 * Created by Arthur on 08/06/2020.
 */

public class Slime extends Enemy{

    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    private int tick;

    public Slime(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 3; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("SlimeAndada"),i*15,0,15,11));
        }

        frames.add(new TextureRegion(screen.getAtlas().findRegion("SlimeAndada"),1*15,0,15,11));

        walkAnimation = new Animation(0.1f,frames);
        stateTime = 0;
        setBounds(getX(),getY(),15/Alen.PPM,11/Alen.PPM);

        setToDestroy = false;
        destroyed = false;
    }

    public void update(float dt){
        stateTime += dt;
        if (setToDestroy && !destroyed){
            destroyed = true;
            stateTime = 0;

            Filter filter = new Filter();
            filter.maskBits = Alen.NOTHING_BIT;

            for(Fixture fixture: b2body.getFixtureList())
                fixture.setFilterData(filter);

            setRegion(new TextureRegion(screen.getAtlas().findRegion("SlimeAndada"),1*15,0,15,11));

            b2body.applyLinearImpulse(new Vector2(0,2f),b2body.getWorldCenter(),true);

            world.destroyBody(b2body);
        }
        else if(!destroyed){

            TextureRegion region;
            region = (TextureRegion) walkAnimation.getKeyFrame(stateTime, true);
            setRegion(region);

            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            b2body.setLinearVelocity(velocity.x/1.5f,velocity.y);

            if ((b2body.getLinearVelocity().x > 0) && !region.isFlipX()){
                region.flip(true,false);
            }
            else if((b2body.getLinearVelocity().x <  0) && region.isFlipX()){
                region.flip(true,false);
            }
        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-10,5).scl(1/Alen.PPM);
        vertice[1] = new Vector2(10,5).scl(1/Alen.PPM);
        vertice[2] = new Vector2(-10,-5).scl(1/Alen.PPM);
        vertice[3] = new Vector2(10,-5).scl(1f/Alen.PPM);
        shape.set(vertice);

        fdef.filter.categoryBits = Alen.ENEMY_BIT;
        fdef.filter.maskBits =  Alen.GROUND_BIT |
                                Alen.COIN_BIT  |
                                Alen.BRICK_BIT |
                                Alen.ENEMY_BIT |
                                Alen.ALEN_BIT  |
                                Alen.VOLTAR_BIT|
                                Alen.FIREBALL_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        //Criar o hitbox da cabeca do goomba;

        PolygonShape head = new PolygonShape();
        Vector2[] vertice2 = new Vector2[4];
        vertice2[0] = new Vector2(-7,10).scl(1/Alen.PPM);
        vertice2[1] = new Vector2(7,10).scl(1/Alen.PPM);
        vertice2[2] = new Vector2(-7,3).scl(1/Alen.PPM);
        vertice2[3] = new Vector2(7,3).scl(1f/Alen.PPM);
        head.set(vertice2);

        fdef.shape = head;
        fdef.restitution = 1f;
        fdef.filter.categoryBits = Alen.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch){
        if (!destroyed || stateTime <1) {
            super.draw(batch);
        }
    }

    @Override
    public void hitOnHead() {
        Alen.manager.get("sounds/pisao.wav", Sound.class).play();
        setToDestroy = true;
        HUD.addScore(250);
    }
}
