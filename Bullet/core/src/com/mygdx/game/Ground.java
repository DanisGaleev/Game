package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

class Ground {
    private Body ground;

    private Sprite groundSprite;

    Ground(World world, float x, float y) {

        groundSprite = new Sprite(new Texture(Gdx.files.internal("ground.png")));
        groundSprite.setSize(32, 2);
        groundSprite.setPosition(x, y);

        BodyDef groundBodyDef = new BodyDef();

        groundBodyDef.position.set(x, y);

        ground = world.createBody(groundBodyDef);


        PolygonShape grounShape = new PolygonShape();
        grounShape.setAsBox(32, 1);

        ground.createFixture(grounShape, 0);

        grounShape.dispose();
    }

    void act() {
        //FIXME: у body позиция это центр тела, а у sprite левый нижний угол
        // поэтому это нужно учитывать при отрисовке
        groundSprite.setPosition(
                ground.getPosition().x,
                ground.getPosition().y - (groundSprite.getHeight() / 2)
        );
    }

    void draw(SpriteBatch batch) {
        batch.begin();
        groundSprite.draw(batch);
        batch.end();
    }
}
