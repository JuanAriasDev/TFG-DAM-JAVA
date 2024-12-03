package com.jarias.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jarias.managers.R;

public class Laser extends Trampa {

    private Animation<TextureAtlas.AtlasRegion> animacionLaser;

    public Laser(TextureRegion image) {
        super(image);
        animacionLaser = new Animation<TextureAtlas.AtlasRegion>(0.115f, R.getAnimation("laser"));
    }

    public void update(float dt) {
        tiempo += dt;
        image = animacionLaser.getKeyFrame(tiempo, true);
        if (((TextureAtlas.AtlasRegion) image).index == 4 || ((TextureAtlas.AtlasRegion) image).index == 5) {
            state = true;
        } else {
            state = false;
        }
    }
}
