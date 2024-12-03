package com.jarias.characters;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class Moneda implements Disposable {
    public Vector2 position;
    public Vector2 size;
    public TextureRegion image;
    public Rectangle rectangle;
    public boolean energy;

    public Moneda(TextureRegion image) {
        this.image = image;

        energy = false;
        size = new Vector2(image.getRegionWidth(), image.getRegionHeight());
        position = new Vector2(0, 0);
        rectangle = new Rectangle(position.x, position.y, size.x, size.y);
    }

    public void draw(Batch batch) {
        batch.draw(image, position.x, position.y);
    }


    @Override
    public void dispose() {

    }
}
