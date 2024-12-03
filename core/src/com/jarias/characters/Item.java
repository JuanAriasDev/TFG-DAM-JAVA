package com.jarias.characters;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class Item implements Disposable {

    public Vector2 position;
    public Vector2 size;
    public TextureRegion image;
    public Rectangle rectangle;
    public float speedY;

    public Item(TextureRegion image) {
        this.image = image;

        size = new Vector2(image.getRegionWidth(), image.getRegionHeight());
        position = new Vector2(0, 0);
        rectangle = new Rectangle(position.x, position.y, size.x, size.y);
    }

    public void update(float dt) {
        speedY -= 10f * dt;
        if (speedY < -7.81f) {
            speedY = -7.81f;
        }

        position.y += speedY;
        rectangle.y = position.y;
    }

    public void draw(Batch batch) {
        batch.draw(image, position.x, position.y);
    }


    @Override
    public void dispose() {

    }
}
