package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import com.badlogic.gdx.math.Rectangle;


import java.util.Iterator;

public class plane extends ApplicationAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Rectangle  frame_1, frame_2, frame_3, frame_4;
    private Sound drop;
    private Texture frame1, frame2, frame3, frame4;
    private Texture rect;
    private Music motor;
    private long lastletTime;
    private Array<Rectangle> lets;
    private Vector3 touch;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        touch = new Vector3();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        frame1 = new Texture("frame1.png");
        frame2 = new Texture("frame2.png");
        frame3 = new Texture("frame3.png");
        frame4 = new Texture("frame4.png");
        rect = new Texture("rect.png");
        frame_1 = new Rectangle();
        frame_2 = new Rectangle();
        frame_3 = new Rectangle();
        frame_4 = new Rectangle();

        frame_1.x = 30;
        frame_1.y = 20;
        frame_1.height = 30;
        frame_1.width = 21;

        frame_2.x = 51;
        frame_2.y = 20;
        frame_2.height = 23;
        frame_2.width = 56;

        frame_3.x = 107;
        frame_3.y = 14;
        frame_3.height = 34;
        frame_3.width = 4;

        frame_4.x = 76;
        frame_4.y = 10;
        frame_4.height = 12;
        frame_4.width = 20;

        drop = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
        motor = Gdx.audio.newMusic(Gdx.files.internal("propeller.mp3"));
        lets = new Array<>();
        motor.setLooping(true);
        motor.play();
        spawnLet();
    }

    private void spawnLet() {
        Rectangle let = new Rectangle();
        let.x = 900;
        let.y = MathUtils.random(0, 480 - 24);
        let.width = 64;
        let.height = 64;
        lets.add(let);
        lastletTime = TimeUtils.nanoTime();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0.95f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(frame1, 30, frame_1.y);
        batch.draw(frame2, 30 + 21, frame_2.y);
        batch.draw(frame3, 30 + 21 + 56, frame_3.y);
        batch.draw(frame4, 30 + 21 + 25, frame_4.y);
        for (Rectangle let : lets) {
            batch.draw(rect, let.x, let.y);
        }
        batch.end();

        if (Gdx.input.isTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);
            if (frame_1.y < touch.y || frame_2.y < touch.y || frame_3.y < touch.y || frame_4.y < touch.y) {
                frame_1.y += 50f * Gdx.graphics.getDeltaTime();
                frame_2.y += 50f * Gdx.graphics.getDeltaTime();
                frame_3.y += 50f * Gdx.graphics.getDeltaTime();
                frame_4.y += 50f * Gdx.graphics.getDeltaTime();
            }
            if (frame_1.y > touch.y || frame_2.y > touch.y || frame_3.y > touch.y || frame_4.y > touch.y) {
                frame_1.y -= 80f * Gdx.graphics.getDeltaTime();
                frame_2.y -= 80f * Gdx.graphics.getDeltaTime();
                frame_3.y -= 80f * Gdx.graphics.getDeltaTime();
                frame_4.y -= 80f * Gdx.graphics.getDeltaTime();
            }
        }

        if (frame_1.y < 0 || frame_2.y < 0 || frame_3.y < 0 || frame_4.y < 0) {
            frame_1.y = 0;
            frame_2.y = 0;
            frame_3.y = -4;
            frame_4.y = -10;
        }
        if (frame_1.y > 480 - 26 || frame_2.y > 480 - 26 || frame_3.y > 480 - 26 || frame_4.y > 480 - 26) {
            frame_1.y =480-26 ;
            frame_2.y = 480-26;
            frame_3.y = 480-26-6;
            frame_4.y = 480-26-10;
        }
        if (TimeUtils.nanoTime() - lastletTime > 1000000000) spawnLet();
        Iterator<Rectangle> iter = lets.iterator();
        while (iter.hasNext()) {
            Rectangle let = iter.next();
            let.x -= 200 * Gdx.graphics.getDeltaTime();
            if (let.x + 64 < 0) iter.remove();
            if (let.overlaps(frame_1) || let.overlaps(frame_2) || let.overlaps(frame_3) || let.overlaps(frame_4)) {
                frame3 = new Texture("frame3_fire.png");
                frame2 = new Texture("frame2_fire.png");
                drop.play();
                iter.remove();
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        rect.dispose();
        frame1.dispose();
        frame2.dispose();
        frame3.dispose();
        frame4.dispose();
        drop.dispose();
        motor.dispose();
        batch.dispose();
    }
}
