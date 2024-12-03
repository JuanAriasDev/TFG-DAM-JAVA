package com.jarias.characters;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Barril {

    public Vector2 position;
    public Vector2 size;
    public TextureRegion image;
    public Rectangle rectangle;
    public float speedY;
    public boolean active;
    public int number;

    public Barril(TextureRegion image) {
        this.image = image;

        number = 0;
        active = false;
        size = new Vector2(image.getRegionWidth(), image.getRegionHeight());
        position = new Vector2(0, 0);
        rectangle = new Rectangle(position.x, position.y, size.x, size.y);
    }

    public void update(float dt) {
        if (active) {
            speedY -= 20f * dt;
            if (speedY < -10.81f) {
                speedY = -10.81f;
            }
            position.y += speedY;
            rectangle.y = position.y;
        }
    }

    public void draw(Batch batch) {
        batch.draw(image, position.x, position.y);
    }

}
