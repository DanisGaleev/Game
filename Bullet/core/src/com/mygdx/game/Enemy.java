package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Enemy {
    private Body enemy;

    private Sprite enemySprite;

    Enemy(World world, float x, float y) {

        enemySprite = new Sprite(new Texture(Gdx.files.internal("circle.png")));
        enemySprite.setSize(1, 1);
        enemySprite.setPosition(x, y);

        BodyDef enemyBodyDef = new BodyDef();
        enemyBodyDef.type = BodyDef.BodyType.DynamicBody;
        enemyBodyDef.position.set(x, y);

        enemy = world.createBody(enemyBodyDef);

        enemy.setUserData("enemy");
        CircleShape enemyShape = new CircleShape();
        enemyShape.setRadius(0.5f);

        FixtureDef enemyFixtureDef = new FixtureDef();
        enemyFixtureDef.density = 10;
        enemyFixtureDef.shape = enemyShape;
        enemyFixtureDef.restitution = 0.5f;

        Fixture fixture = enemy.createFixture(enemyFixtureDef);

        enemyShape.dispose();
    }

    void act() {
        //FIXME: у body позиция это центр тела, а у sprite левый нижний угол
        // поэтому это нужно учитывать при отрисовке


        enemySprite.setPosition(
                enemy.getPosition().x - enemySprite.getHeight() / 2,
                enemy.getPosition().y - (enemySprite.getHeight() / 2)
        );
    }

    void draw(SpriteBatch batch) {
        batch.begin();
        enemySprite.draw(batch);
        batch.end();
    }

    Body getEnemy() {
        return enemy;
    }
}
