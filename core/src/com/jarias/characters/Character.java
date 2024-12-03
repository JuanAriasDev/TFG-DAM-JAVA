package com.jarias.characters;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public abstract class Character implements Disposable {

    public Vector2 position;
    public Vector2 speed;
    public Vector2 size;
    public TextureRegion image;
    public Rectangle rectangle;
    public float speedY;

    public Character(TextureRegion image) {
        this.image = image;

        size = new Vector2(image.getRegionWidth(), image.getRegionHeight());
        position = new Vector2(0, 0);
        rectangle = new Rectangle(position.x, position.y, size.x, size.y);
    }

    public void update(float dt) {
        speedY -= 10f * dt;
        if (speedY < -5.81f) {
            speedY = -5.81f;
        }

        position.y += speedY;
        rectangle.y = position.y;
    }

    public void move() {
        move(speed);
    }

    public void move(Vector2 movement) {
        position.add(movement);
        rectangle.x = position.x;
        rectangle.y = position.y;
    }

    public void draw(Batch batch) {
        batch.draw(image, position.x, position.y);
    }

    @Override
    public void dispose() {

    }
}
