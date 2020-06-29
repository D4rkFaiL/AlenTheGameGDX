package com.fiveyoukais.alen.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.fiveyoukais.alen.Alen;
import com.fiveyoukais.alen.Sprites.Inimigos.Boss;
import com.fiveyoukais.alen.Sprites.Inimigos.Pedras;
import com.fiveyoukais.alen.Sprites.Outros.AlenP;
import com.fiveyoukais.alen.Sprites.Outros.Fireball;
import com.fiveyoukais.alen.Sprites.Inimigos.Enemy;
import com.fiveyoukais.alen.Sprites.Blocos.InteractiveTileObject;
import com.fiveyoukais.alen.Sprites.Items.Item;

import static com.fiveyoukais.alen.Alen.hittable;

/**
 * Created by Arthur on 13/04/2020.
 */

public class WorldContactListener implements ContactListener{
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){

            case Alen.ITEM_BIT | Alen.ALEN_BIT:
                if(fixA.getFilterData().categoryBits == Alen.ITEM_BIT)
                    ((Item)fixA.getUserData()).use((AlenP)fixB.getUserData());
                else
                    ((Item)fixB.getUserData()).use((AlenP)fixA.getUserData());
                break;

            //Fazer interacao de brick/moeda;
            case Alen.ALEN_HEAD_BIT | Alen.BRICK_BIT:
            case Alen.ALEN_HEAD_BIT | Alen.COIN_BIT:
            case (Alen.FIREBALL_BIT | Alen.BRICK_BIT):
                if (fixA.getFilterData().categoryBits == Alen.ALEN_HEAD_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit();
                else
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit();
                break;

            //Reset de pulo alen;
            case (Alen.GROUND_BIT | Alen.ALEN_FEET_BIT):
            case (Alen.BRICK_BIT| Alen.ALEN_FEET_BIT):
            case (Alen.COIN_BIT| Alen.ALEN_FEET_BIT):
                if (fixA.getFilterData().categoryBits == Alen.ALEN_FEET_BIT)
                    ((AlenP) fixA.getUserData()).setPulo(false);
                else
                    ((AlenP) fixB.getUserData()).setPulo(false);
                break;

            //Reset de matar o inimigo quando o alen pular na cabeca;
            case Alen.ENEMY_HEAD_BIT | Alen.ALEN_BIT:
                if (fixA.getFilterData().categoryBits == Alen.ENEMY_HEAD_BIT)
                    ((Enemy)fixA.getUserData()).hitOnHead();
                else
                    ((Enemy)fixB.getUserData()).hitOnHead();
                break;

            //mudar a direcao do inimigo quando bate em algo;
            case Alen.ENEMY_BIT | Alen.VOLTAR_BIT:
            case Alen.ENEMY_BIT | Alen.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == Alen.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true,false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true,false);
                break;

            //matar o alen se ele escostar no inimigo;
            case Alen.ALEN_BIT | Alen.AGUA_BIT:
                Alen.cabo = true;

            case Alen.ALEN_BIT | Alen.ENEMY_BIT:
            case Alen.ALEN_BIT | Alen.PEDRA_BIT:
                if (fixA.getFilterData().categoryBits == Alen.ALEN_BIT)
                    ((AlenP)fixA.getUserData()).hit();
                else
                    ((AlenP)fixB.getUserData()).hit();
                break;

            //fazer animacao do tiro squish;
            case (Alen.GROUND_BIT | Alen.FIREBALL_FEET_BIT):
                if (fixA.getFilterData().categoryBits == Alen.FIREBALL_FEET_BIT)
                    ((Fireball) fixA.getUserData()).setSquish();
                else
                    ((Fireball) fixB.getUserData()).setSquish();
                break;

                /*
            case Alen.FIREBALL_BIT | Alen.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == Alen.ENEMY_HEAD_BIT) {
                    ((Enemy) fixA.getUserData()).hitOnHead();
                    ((Fireball) fixA.getUserData()).setToDestroy();
                }
                else {
                    ((Enemy) fixB.getUserData()).hitOnHead();
                    ((Fireball) fixB.getUserData()).setToDestroy();
                }
                break;
                */

            case Alen.BOSS_BIT | Alen.VOLTAR_BIT:
                if(fixA.getFilterData().categoryBits == Alen.BOSS_BIT) {
                    ((Enemy) fixA.getUserData()).hitOnHead();
                    ((Enemy) fixA.getUserData()).reverseVelocity(true,false);
                }
                else {
                    ((Enemy) fixB.getUserData()).hitOnHead();
                    ((Enemy) fixB.getUserData()).reverseVelocity(true,false);
                }
                break;

            case Alen.PEDRA_BIT | Alen.GROUND_BIT:
                if(fixA.getFilterData().categoryBits == Alen.PEDRA_BIT)
                    ((Pedras) fixA.getUserData()).hitOnHead();
                else
                    ((Pedras) fixB.getUserData()).hitOnHead();
                break;

            case Alen.ALEN_BIT | Alen.BOSS_BIT:
                if(hittable){
                    if (fixA.getFilterData().categoryBits == Alen.BOSS_BIT)
                        ((Boss)fixA.getUserData()).playerHit();
                    else
                        ((Boss)fixB.getUserData()).playerHit();
                    break;
                }
                else{
                    if (fixA.getFilterData().categoryBits == Alen.ALEN_BIT)
                        ((AlenP)fixA.getUserData()).hit();
                    else
                        ((AlenP)fixB.getUserData()).hit();
                    break;
                }


        }
    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}
