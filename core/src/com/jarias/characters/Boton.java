package com.jarias.characters;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jarias.managers.R;

public class Boton {

    public enum State {
        ON, OFF
    }

    public Vector2 position;
    public Vector2 size;
    public Rectangle rectangle;
    public TextureRegion image;
    public State state;
    public float time;

    public Boton(TextureRegion image) {
        this.image = image;
        position = new Vector2(0, 0);
        size = new Vector2(image.getRegionWidth(), image.getRegionHeight());
        rectangle = new Rectangle(position.x, position.y, size.x, size.y);
        state = State.OFF;
    }

    public void update(float dt) {
        time += dt;

        switch (state) {
            case ON:
                image = R.getTexture("btn_on");
                break;
            case OFF:
                image = R.getTexture("btn_off");
                break;
        }
    }

    public void draw(Batch batch) {
        batch.draw(image, position.x, position.y);
    }
}
