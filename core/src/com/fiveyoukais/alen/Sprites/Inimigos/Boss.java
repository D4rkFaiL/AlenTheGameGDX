package com.fiveyoukais.alen.Sprites.Inimigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.fiveyoukais.alen.Alen;
import com.fiveyoukais.alen.Screens.PlayScreen;
import com.fiveyoukais.alen.Screens.TransicaoScreen;

import static com.fiveyoukais.alen.Alen.hittable;


/**
 * Created by Arthur on 08/06/2020.
 */

public class Boss extends Enemy{

    private String golpe;
    private float stateTime;

    private Animation<TextureRegion> rataoFire;
    private Animation<TextureRegion> rataoRabada;
    private Animation<TextureRegion> rataoBatida;
    private Animation<TextureRegion> rataoCharge;
    private Animation<TextureRegion> rataoCorrida;
    private Animation<TextureRegion> rataoIdle;
    private Animation<TextureRegion> rataoPiscar;

    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    private boolean bossIsDead;
    private boolean runningright;
    private float tempo;
    private TextureRegion region = null;
    private int vida = 3;

    public Boss(PlayScreen screen, float x, float y) {
        super(screen, x, y);

        frames = new Array<TextureRegion>();
        for (int i = 0; i < 10; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("RataoFire"),i*192,0,192,93));
        }

        rataoFire = new Animation(0.1f,frames);
        frames.clear();

        for (int i = 0; i < 10; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("RataoRabada"),i*192,0,192,93));
        }

        rataoRabada = new Animation(0.1f,frames);
        frames.clear();

        for (int i = 0; i < 8; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("RataoBatida"),i*192,0,192,93));
        }

        rataoBatida = new Animation(0.1f,frames);
        frames.clear();

        for (int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("RataoCorrida"),i*192,0,192,93));
        }

        rataoCorrida = new Animation(0.1f,frames);
        frames.clear();

        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("RataoIdle"),i*192,0,192,93));
        }

        rataoIdle = new Animation(0.1f,frames);
        frames.clear();

        for (int i = 0; i < 8; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("RataoCharge"),i*192,0,192,93));
        }

        rataoCharge = new Animation(0.1f,frames);
        frames.clear();

        frames.add(new TextureRegion(screen.getAtlas().findRegion("RataoIdle"),0*192,0,192,93));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("RataoIdle"),0,0,0,0));
        rataoPiscar = new Animation(0.1f,frames);

        stateTime = 0;
        setBounds(getX(),getY(),192/ Alen.PPM,93/Alen.PPM);

        runningright = false;
        setToDestroy = false;
        destroyed = false;
        bossIsDead = false;
        Alen.fim = false;
        golpe = "Idle";
    }

    public void update(float dt){
        stateTime += dt;
        tempo = tempo + 0.1f;

        if(vida <= 0 != setToDestroy){
            setToDestroy = true;
            Alen.fim = true;
        }

        if (setToDestroy && !destroyed){

            destroyed = true;
            stateTime = 0;

            Filter filter = new Filter();
            filter.maskBits = Alen.NOTHING_BIT;

            for(Fixture fixture: b2body.getFixtureList())
                fixture.setFilterData(filter);

            b2body.applyLinearImpulse(new Vector2(0,3f),b2body.getWorldCenter(),true);

            world.destroyBody(b2body);
        }
        else if(!destroyed){

            if(golpe == "Charge"){
                region = (TextureRegion) rataoCharge.getKeyFrame(stateTime, true);
                hittable = false;
                if (tempo > 20){
                    golpe = "Corrida";
                    tempo = 0;
                }
            }

            if(golpe == "Batida"){
                region = (TextureRegion) rataoBatida.getKeyFrame(stateTime, true);
                if(tempo > 5){
                    region = (TextureRegion) rataoIdle.getKeyFrame(stateTime,true);
                    hittable = true;
                    if (tempo > 15){
                        if(runningright)
                            runningright = false;
                        else
                            runningright = true;
                        golpe = "Charge";
                        tempo = 0;
                    }
                }
            }

            if(golpe == "Corrida"){
                region = (TextureRegion) rataoCorrida.getKeyFrame(stateTime, true);
                if(runningright)
                    b2body.setLinearVelocity(velocity.x * 2,0);
                else
                    b2body.setLinearVelocity(velocity.x * -2,0);

            }

            if(golpe == "Idle"){
                region = (TextureRegion) rataoIdle.getKeyFrame(stateTime, true);
                if (tempo > 15){
                    golpe = "Charge";
                    tempo = 0;
                }
            }

            if(runningright && region.isFlipX()) {
                setPosition(b2body.getPosition().x - 0.5f, b2body.getPosition().y - 0.15f);
                //region.flip(true, false);
            }
            if(!runningright && !region.isFlipX()){
                setPosition(b2body.getPosition().x - 1.1f, b2body.getPosition().y -0.15f);
                //region.flip(true,false);
            }

            if ((b2body.getLinearVelocity().x > 0) && !region.isFlipX()){
                region.flip(true,false);
                runningright = true;
            }
            else if((b2body.getLinearVelocity().x <  0) && region.isFlipX()){
                region.flip(true,false);
                runningright = false;
            }

            if(golpe == "Dano"){
                region = (TextureRegion) rataoPiscar.getKeyFrame(stateTime, true);
                if(tempo > 5){
                    tempo = 0;
                    golpe = "Idle";
                }
            }
            setRegion(region);

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
        vertice[0] = new Vector2(-30,15).scl(1/Alen.PPM);
        vertice[1] = new Vector2(50,15).scl(1/Alen.PPM);
        vertice[2] = new Vector2(-30,-15).scl(1/Alen.PPM);
        vertice[3] = new Vector2(50,-15).scl(1f/Alen.PPM);
        shape.set(vertice);

        fdef.filter.categoryBits = Alen.BOSS_BIT;
        fdef.filter.maskBits =  Alen.GROUND_BIT |
                                Alen.ALEN_BIT  |
                                Alen.VOLTAR_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

    }

    public void draw(Batch batch){
        if (!destroyed || stateTime <1) {
            super.draw(batch);
        }
    }

    @Override
    public void hitOnHead() {
        tempo = 0;
        Pedras.ativado = true;
        golpe = "Batida";
        reverseVelocity(true,false);
    }

    public void playerHit() {
       vida--;
       golpe = "Dano";
       tempo = 0;
       hittable = false;
    }
}
