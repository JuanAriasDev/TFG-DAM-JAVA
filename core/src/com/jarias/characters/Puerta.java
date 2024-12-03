package com.jarias.characters;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Puerta {

    public Vector2 position;
    public Vector2 size;
    public Rectangle rectangle;
    public TextureRegion image;
    public boolean end;
    public boolean secreta;

    public Puerta(TextureRegion image) {
        this.image = image;
        secreta = false;
        position = new Vector2(0, 0);
        size = new Vector2(image.getRegionWidth(), image.getRegionHeight());
        rectangle = new Rectangle(position.x, position.y, size.x, size.y);
        end = false;
    }

    public void draw(Batch batch) {
        batch.draw(image, position.x, position.y);
    }
}
