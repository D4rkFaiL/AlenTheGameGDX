package com.fiveyoukais.alen.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fiveyoukais.alen.Alen;
import com.fiveyoukais.alen.Scenes.HUD;
import com.fiveyoukais.alen.Sprites.Outros.AlenP;
import com.fiveyoukais.alen.Sprites.Inimigos.Enemy;
import com.fiveyoukais.alen.Sprites.Items.Item;
import com.fiveyoukais.alen.Sprites.Items.ItemDef;
import com.fiveyoukais.alen.Sprites.Items.Mushroom;
import com.fiveyoukais.alen.Tools.B2WorldCreator;
import com.fiveyoukais.alen.Tools.WorldContactListener;

import java.util.concurrent.LinkedBlockingDeque;

import static com.fiveyoukais.alen.Alen.fase;
import static com.fiveyoukais.alen.Alen.fim;


/**
 * Created by Arthur on 04/04/2020.
 */

public class PlayScreen implements Screen{

    private Alen game;
    private OrthographicCamera gamecam;
    private Viewport gameport;
    private HUD hud;

    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //box2d
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    private AlenP player;

    private TextureAtlas atlas;

    private Music music;

    private Array<Item> items;
    private LinkedBlockingDeque<ItemDef> itemsToSpawn;

    private float FOV = 1;

    private Texture texture;
    private Sprite sprite;

    public PlayScreen(Alen game){

        Alen.cabo = false;
        atlas = new TextureAtlas("Alenzada.pack");

        this.game = game;

        gamecam = new OrthographicCamera();

        //FOV
        gameport = new FitViewport(Alen.V_WIDTH/ (Alen.PPM/FOV) ,Alen.V_HEIGHT /(Alen.PPM/FOV),gamecam );

        hud = new HUD(game.batch);

        maploader = new TmxMapLoader();
        map = maploader.load("level"+fase+".tmx");
        renderer = new OrthogonalTiledMapRenderer(map,1/Alen.PPM);

        gamecam.position.set(gameport.getWorldWidth()/2,gameport.getWorldHeight()/2,0);

        world = new World(new Vector2(0,-10),true);
        b2dr = new Box2DDebugRenderer();

        player = new AlenP(this);

        creator = new B2WorldCreator(this);

        world.setContactListener(new WorldContactListener());

        music = Alen.manager.get("music/Esgoto3.wav",Music.class);
        music.setLooping(true);
        music.setVolume(0.15f);
        music.play();

        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingDeque<ItemDef>();

    }

    public void spawnItem(ItemDef idef){
        itemsToSpawn.add(idef);
    }

    public void hadleSpawningItems(){
        if(!itemsToSpawn.isEmpty()){
            ItemDef idef = itemsToSpawn.poll();
            if(idef.type == Mushroom.class){
                items.add(new Mushroom(this,idef.position.x,idef.position.y));
            }
        }
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {

    }

    public void update(float dt){


        handleInput(dt);
        hadleSpawningItems();

        world.step(1/60f,6,2);

        player.update(dt);

        for(Enemy enemy: creator.getGoombas()) {
            enemy.update(dt);
            if(enemy.getX() < player.getX() + 224/Alen.PPM)
                enemy.b2body.setActive(true);
        }

        for(Enemy enemy: creator.getRatao()) {
            enemy.update(dt);
            if(enemy.getX() < player.getX() + 224/Alen.PPM)
                enemy.b2body.setActive(true);
        }

        for(Enemy enemy: creator.getSlime()) {
            enemy.update(dt);
            if(enemy.getX() < player.getX() + 224/Alen.PPM)
                enemy.b2body.setActive(true);
        }

        for(Enemy enemy: creator.getBoss()) {
            enemy.update(dt);
            if(enemy.getX() < player.getX() + 224/Alen.PPM)
                enemy.b2body.setActive(true);
        }

        for(Enemy enemy: creator.getPedras()) {
            enemy.update(dt);
            if(enemy.getX() < player.getX() + 224/Alen.PPM)
                enemy.b2body.setActive(true);
        }

        for(Item item: items)
            item.update(dt);

        hud.update(dt);

        if(player.currentstate != AlenP.State.Dead) {
            gamecam.position.x = player.b2body.getPosition().x;
            gamecam.position.y = player.b2body.getPosition().y;
        }

        gamecam.update();
        renderer.setView(gamecam);
        Gdx.graphics.setTitle("FPS: "+ Gdx.graphics.getFramesPerSecond());
    }

    public void handleInput(float dt){
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

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        //HITBOX VISIVEL 300K% ATUALZIADO
        //b2dr.render(world,gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();

        texture = new Texture(Gdx.files.internal("Hud.png"));
        sprite = new Sprite(texture,207,29);
        sprite.setPosition(gamecam.position.x - 150/Alen.PPM,gamecam.position.y + 0.6f);
        sprite.setSize(1.5f * 2,0.2f * 2);
        sprite.draw(game.batch);


        texture = new Texture(Gdx.files.internal("bloco.png"));
        sprite = new Sprite(texture,2,8);
        sprite.setPosition(gamecam.position.x + 42/Alen.PPM,gamecam.position.y + 0.845f);
        sprite.setSize(player.vida,0.1f);
        sprite.draw(game.batch);


        player.draw(game.batch);
        for(Enemy enemy: creator.getGoombas())
            enemy.draw(game.batch);

        for(Enemy enemy: creator.getRatao())
            enemy.draw(game.batch);

        for(Enemy enemy: creator.getSlime())
            enemy.draw(game.batch);

        for(Enemy enemy: creator.getBoss())
            enemy.draw(game.batch);

        for(Enemy enemy: creator.getPedras())
            enemy.draw(game.batch);

        for(Item item: items)
            item.draw(game.batch);

        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        if (gameOver() || player.b2body.getPosition().x >= 35 || fim){
            if(fim){
                game.setScreen(new MenuScreen(game));
            }
            else if(player.b2body.getPosition().x >= 35){
                fase++;
                game.setScreen(new TransicaoScreen(game));
            }
            else if(gameOver())
                game.setScreen(new GameOverScreen(game));

            music.stop();
            dispose();
        }
    }

    public boolean gameOver(){
        if((player.currentstate == AlenP.State.Dead && player.getStatetimer()>1.5f) || Alen.cabo){
            music.stop();
            return true;
        }
        return false;
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }

    @Override
    public void resize(int width, int height) {
        gameport.update(width,height);
    }

    public TiledMap getMap(){
        return map;
    }

    public World getWorld(){
        return world;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }


}
