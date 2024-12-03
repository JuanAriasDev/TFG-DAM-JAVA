package com.jarias.characters;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class Trampa implements Disposable {

    public Vector2 position, size;
    public TextureRegion image;
    public Rectangle rectangle;
    public boolean state;
    public float tiempo;

    public Trampa(TextureRegion image) {
        this.image = image;

        size = new Vector2(image.getRegionWidth(), image.getRegionHeight());
        position = new Vector2(0, 0);
        rectangle = new Rectangle(position.x, position.y, size.x, size.y);
        state = false;
        tiempo = 0;
    }

    public void draw(Batch batch) {
        batch.draw(image, position.x, position.y);
    }

    @Override
    public void dispose() {

    }
}
