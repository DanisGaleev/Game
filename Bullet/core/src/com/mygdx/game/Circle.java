package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Circle {
    private Body bullet;
    private Fixture fixture;

    public Circle(World world, float x, float y) {
        BodyDef bulletDef = new BodyDef();
        bulletDef.type = BodyDef.BodyType.DynamicBody;
        bulletDef.position.set(x, y);

        bullet = world.createBody(bulletDef);
        bullet.setUserData("bullet");
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(1.5f);
        // FIXME: Метод setPosition для circleShape вообще не нужен, и работает он не так как ты ожидал
//        circleShape.setPosition(new Vector2(x, y));
        FixtureDef bulletFixture = new FixtureDef();
        bulletFixture.density = 100;
        bulletFixture.restitution = 1.5f;
        bulletFixture.shape = circleShape;
        fixture = bullet.createFixture(bulletFixture);
        circleShape.dispose();
    }

    public Fixture getFixture() {
        return fixture;
    }

    Body getBullet() {
        return bullet;
    }

    //FIXME: у либы есть пакет scene2d, в нем используется подобный подход в классах Actor
    // можно в класс Bullet добавить отдельный метод act() для выполнения операций с обьектом
    // а draw() использовать непосредственно для отрисовки
    void act() {
        //FIXME: у body позиция это центр тела, а у sprite левый нижний угол
        // поэтому это нужно учитывать при отрисовке

    }

    void draw(SpriteBatch batch) {
        batch.begin();
        batch.end();
    }

}
