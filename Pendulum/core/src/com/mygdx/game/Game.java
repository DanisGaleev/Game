package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Game extends ApplicationAdapter {
	private World world;
	Touchpad touchpad;
	Skin skin;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Box2DDebugRenderer debugRenderer;
	Stage stage;
	private Circle circle;

	@Override

	public void create() {
		batch = new SpriteBatch();

		stage = new Stage(new ScreenViewport());


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
	}

	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 1, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		//FIXME: логику по началу отрисовки и окончанию можно перенести в Bullet класс

		stage.act();
		stage.draw();


		System.out.println(touchpad.getKnobPercentX() + "  " + touchpad.getKnobPercentY());


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
