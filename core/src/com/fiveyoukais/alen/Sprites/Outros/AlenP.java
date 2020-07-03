package com.fiveyoukais.alen.Sprites.Outros;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.fiveyoukais.alen.Alen;
import com.fiveyoukais.alen.Screens.PlayScreen;

/**
 * Created by Arthur on 04/04/2020.
 */

public class AlenP extends Sprite{

    public enum State{Falling,Jumping,Standing,Running,Dead};
    public State currentstate;
    public State previousstate;

    public World world;

    public Body b2body;

    private TextureRegion alenStand;
    private TextureRegion alenJump;
    private TextureRegion alenFalling;
    private TextureRegion alenDead;
    private Animation<TextureRegion> alenRun;
    private Animation<TextureRegion> alenIdle;

    private TextureRegion armaJump;
    private TextureRegion armaFalling;
    private Animation<TextureRegion> armaIdle;
    private Animation<TextureRegion> armaRun;

    private PlayScreen screen;

    private boolean runningright;
    private boolean alenHasGun;
    private boolean alenGunAnim;
    private boolean alenIsDead;
    private boolean pulou;
    private boolean atirou;

    private float statetimer;
    private float fireRate = 0;
    private float fireRateValue = 12f;
    private float tempo;
    public static float vida = 0f;

    private Array<Fireball> fireballs;


    public AlenP(PlayScreen screen){
        this.screen = screen;
        this.world = screen.getWorld();
        currentstate = State.Standing;
        previousstate = State.Standing;
        statetimer = 0;
        runningright = true;
        vida = 0;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 1; i < 6; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("AlenSpriteSheet"),i*21,0,21,28));
        }

        alenRun = new Animation<TextureRegion>(0.1f,frames);
        frames.clear();

        for (int i = 1; i < 6; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("AlenAndadaArma"),i*29,0,29,29));
        }
        armaRun = new Animation<TextureRegion>(0.1f,frames);
        frames.clear();


        alenJump = new TextureRegion(screen.getAtlas().findRegion("AlenPulandinho"),28,0,23,29);
        armaJump = new TextureRegion(screen.getAtlas().findRegion("AlenPulandinho"),28*3,0,23,29);
        alenFalling = new TextureRegion(screen.getAtlas().findRegion("AlenPulandinho"),0,0,23,29);
        armaFalling = new TextureRegion(screen.getAtlas().findRegion("AlenPulandinho"),28*2,0,23,29);
        alenDead = new TextureRegion(screen.getAtlas().findRegion("AlenPulandinho"),0,0,23,29);

        for (int i = 1; i < 6; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("AlenIdleFINAL"),i*23,0,23,27));
        }
        alenIdle = new Animation<TextureRegion>(0.1f,frames);
        frames.clear();

        for (int i = 1; i < 6; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("AlenIdleArma"),i*29,0,29,27));
        }
        armaIdle = new Animation<TextureRegion>(0.1f,frames);

        alenStand = new TextureRegion(screen.getAtlas().findRegion("AlenIdleArma"),0,0,29,29);
        setBounds(0,0,23/Alen.PPM,28/Alen.PPM);
        setRegion(alenStand);

        fireballs = new Array<Fireball>();

        defineAlen();
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth()/2,b2body.getPosition().y - getHeight()/2);
        setRegion(getFrame(dt));

        for(Fireball  ball : fireballs) {
            ball.update(dt);
            if (ball.isDestroyed())
                fireballs.removeValue(ball, true);
        }

        tempo = tempo + 0.1f;
    }

    public TextureRegion getFrame(float dt){
        currentstate = getState();

        TextureRegion region;
        switch (currentstate){
            case Dead:
                region = alenDead;
                break;
            case Jumping:
                region = alenHasGun ? armaJump : alenJump;
                break;
            case Running:
                region = alenHasGun ? armaRun.getKeyFrame(statetimer,true):
                                      alenRun.getKeyFrame(statetimer,true);
                break;
            case Falling:
                region = alenHasGun ? armaFalling : alenFalling;
                break;
            case Standing:
            default:
                region = alenHasGun ? armaIdle.getKeyFrame(statetimer,true):
                                      alenIdle.getKeyFrame(statetimer,true);
                break;
        }

        if ((b2body.getLinearVelocity().x < 0 || !runningright) && !region.isFlipX()){
            region.flip(true,false);
            runningright = false;

        }
        else if((b2body.getLinearVelocity().x > 0 || runningright) && region.isFlipX()){
            region.flip(true,false);
            runningright = true;
        }

        statetimer = currentstate == previousstate ? statetimer + dt : 0;
        previousstate = currentstate;
        return region;
    }

    public State getState(){
        if(alenIsDead)
            return State.Dead;
        else if(b2body.getLinearVelocity().y > 0.0 || (b2body.getLinearVelocity().y < 0.0 && previousstate == State.Jumping))
            return State.Jumping;
        else if(b2body.getLinearVelocity().y < 0)
                return State.Falling;
        else if(b2body.getLinearVelocity().x != 0)
            return State.Running;
        else
            return State.Standing;
    }

    public void defineAlen(){

        BodyDef bdef = new BodyDef();
        bdef.position.set(1.3f,7.7f);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-8,12).scl(1/Alen.PPM);
        vertice[1] = new Vector2(8,12).scl(1/Alen.PPM);
        vertice[2] = new Vector2(-8,-12).scl(1/Alen.PPM);
        vertice[3] = new Vector2(8,-12).scl(1f/Alen.PPM);
        shape.set(vertice);


        fdef.filter.categoryBits = Alen.ALEN_BIT;
        fdef.filter.maskBits = Alen.GROUND_BIT |
                               Alen.COIN_BIT |
                               Alen.BRICK_BIT |
                               Alen.ENEMY_BIT |
                               Alen.ENEMY_HEAD_BIT |
                               Alen.ITEM_BIT|
                               Alen.AGUA_BIT|
                               Alen.BOSS_BIT|
                               Alen.PEDRA_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2((-6 + bdef.position.x)/ Alen.PPM,(8 + bdef.position.y)/Alen.PPM),
                 new Vector2((4 + bdef.position.x) / Alen.PPM,(8 + bdef.position.y)/Alen.PPM));
        fdef.filter.categoryBits = Alen.ALEN_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);

        EdgeShape feet = new EdgeShape();
        feet.set(new Vector2((-10 + bdef.position.x)/ Alen.PPM,(-24 + bdef.position.y)/Alen.PPM),
                 new Vector2((8 + bdef.position.x) / Alen.PPM,(-24 + bdef.position.y)/Alen.PPM));
        fdef.filter.categoryBits = Alen.ALEN_FEET_BIT;
        fdef.shape = feet;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
    }

    public void grow(){
        //alenGunAnim = true;
        alenHasGun = true;
        setBounds(getX(),getY(),28/Alen.PPM,getHeight() );
    }

    public void hit(){
        if (alenHasGun){
            setBounds(0,0,23/Alen.PPM,28/Alen.PPM);
            b2body.applyLinearImpulse(new Vector2(0,2f),b2body.getWorldCenter(),true);
            alenHasGun = false;
        }
        else{

            vida = vida - 0.115f;
            Gdx.app.log("vida","vida: " + vida);

            if(vida <= -0.574) {
                alenIsDead = true;
                Filter filter = new Filter();
                filter.maskBits = Alen.NOTHING_BIT;
                for (Fixture fixture : b2body.getFixtureList())
                    fixture.setFilterData(filter);
                b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
            }
        }
    }

    public void fire(float dt){
        if(tempo > fireRate){
            fireballs.add(new Fireball(screen, b2body.getPosition().x, b2body.getPosition().y, runningright ? true : false));
            fireRate = tempo + fireRateValue;
        }
    }

    public void draw(Batch batch){
        super.draw(batch);
        for(Fireball ball : fireballs)
            ball.draw(batch);
    }

    public boolean isDead(){
        return alenIsDead;
    }

    public float getStatetimer(){
        return statetimer;
    }

    public boolean getPulo(){return pulou;}

    public void setPulo(boolean pulou){this.pulou = pulou;}

    public boolean getGun(){ return alenHasGun;}
}
