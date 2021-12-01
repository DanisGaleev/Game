package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen extends ApplicationAdapter {
    private World world;
    Touchpad touchpad;
    Skin skin;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;
    Stage stage;
    BodyDef bodyDef;
    Body body1;
    Body body2;
    //private Bullet bullet;
    //   private Ground ground;
    //   private Enemy enemy;

    @Override

    public void create() {
        batch = new SpriteBatch();

        stage = new Stage(new ScreenViewport());
        world = new World(new Vector2(0, -9.8f), true);

        bodyDef = new BodyDef();

        body1 = world.createBody(bodyDef);
        bodyDef.position.set(10, 20);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        CircleShape circle = new CircleShape();
        circle.setRadius(1f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit

        Fixture fixture = body1.createFixture(fixtureDef);

        circle.dispose();



        body2 = world.createBody(bodyDef);

        bodyDef.position.set(10, 15);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        CircleShape circle1 = new CircleShape();
        circle.setRadius(1f);

        FixtureDef fixtureDef1 = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit

        Fixture fixture1 = body1.createFixture(fixtureDef);

        circle.dispose();

        skin = new Skin();
        Sprite texture = new Sprite(new Texture("kn.png"));
        texture.setSize(60, 60);
        Sprite texture1 = new Sprite(new Texture("back.png"));
        texture1.setSize(240, 240);
        skin.add("k", texture);
        skin.add("b", texture1);

        Touchpad.TouchpadStyle touchpadStyle = new Touchpad.TouchpadStyle();

        touchpadStyle.knob = skin.getDrawable("k");
        touchpadStyle.background = skin.getDrawable("b");

        touchpad = new Touchpad(10f, touchpadStyle);
        touchpad.setPosition(40, 40);
        stage.addActor(touchpad);

        Gdx.input.setInputProcessor(stage);
        camera = new OrthographicCamera(32, 18);
        camera.setToOrtho(false, 32, 18);

        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();
        debugRenderer.
        // ground = new Ground(world, 0, 0);
        // ground.act();

        //bullet = new Bullet(world, 20, 10);

        //enemy = new Enemy(world, 30, 1);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        //FIXME: логику по началу отрисовки и окончанию можно перенести в Bullet класс
        //if (bullet != null) {
        //    bullet.act();
        //     bullet.draw(batch);
        // }
        stage.act();
        stage.draw();
        //  enemy.act();
        //  enemy.draw(batch);
        //ground.draw(batch);

        System.out.println(touchpad.getKnobPercentX() + "  " + touchpad.getKnobPercentY());

        //   if (Gdx.input.justTouched()) {
        //       bullet.getBullet().applyLinearImpulse(0, 20, bullet.getBullet().getPosition().x, bullet.getBullet().getPosition().y, true);
        //  }
     /*   world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Body body = null;
                System.out.println(contact.getFixtureA().toString() + "   " + contact.getFixtureB().toString());
                if (contact.getFixtureA().getBody().getUserData() != null && contact.getFixtureA().getBody().getUserData().equals("bullet"))
                    body = contact.getFixtureA().getBody();
                if (contact.getFixtureB().getBody().getUserData() != null && contact.getFixtureB().getBody().getUserData().equals("bullet"))
                    body = contact.getFixtureB().getBody();
                if (body != null) {

                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

      */
        // enemy.getEnemy().setLinearVelocity(-2, 0);
        debugRenderer.render(world, camera.combined);
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        //FIXME: логика перенесена в метод act() класса Bullet
        //FIXME: применение силы чтобы шар катился чтоб убедиться что все правильно отрисовывается

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
