package com.fiveyoukais.alen.Sprites.Inimigos;

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

public class Pedras extends Enemy{

    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    public static boolean ativado;
    public Vector2 posInicial;
    public Pedras(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        stateTime = 0;
        setBounds(getX(),getY(),15/Alen.PPM,14/Alen.PPM);

        setToDestroy = false;
        destroyed = false;
        ativado = false;
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

            setRegion(new TextureRegion(screen.getAtlas().findRegion("pedradecrack"),0,0,15,14));

            b2body.applyLinearImpulse(new Vector2(0,0f),b2body.getWorldCenter(),true);

            world.destroyBody(b2body);
        }
        else if(!destroyed){

            if(ativado){

                b2body.setActive(true);
                TextureRegion region;
                region = (new TextureRegion(screen.getAtlas().findRegion("pedradecrack"),0,0,15,14));
                setRegion(region);

                setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
                b2body.setLinearVelocity(0,-0.7f);
            }
            else {
                b2body.setActive(false);
                TextureRegion region;
                region = (new TextureRegion(screen.getAtlas().findRegion("pedradecrack"),15,0,15,14));
                setRegion(region);
                b2body.setLinearVelocity(0,0.175f);
                setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
                b2body.setTransform(posInicial.x,posInicial.y,0);
            }
        }
    }

    @Override
    protected void defineEnemy() {

        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        posInicial = new Vector2(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        if(!world.isLocked())
            b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / Alen.PPM);
        fdef.filter.categoryBits = Alen.PEDRA_BIT;
        fdef.filter.maskBits = Alen.GROUND_BIT|
                                Alen.ALEN_BIT;

        fdef.shape = shape;
        fdef.restitution = 1;
        fdef.friction = 0;
        b2body.createFixture(fdef).setUserData(this);
        posInicial = new Vector2(b2body.getPosition().x,b2body.getPosition().y);
    }

    public void draw(Batch batch){
        if (!destroyed || stateTime <1) {
            super.draw(batch);
        }
    }

    @Override
    public void hitOnHead() {
        ativado = false;
    }
}
