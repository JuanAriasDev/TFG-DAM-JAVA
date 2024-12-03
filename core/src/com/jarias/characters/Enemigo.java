package com.jarias.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jarias.managers.R;

public class Enemigo extends Character {

    public Direction direction;
    private Animation<TextureAtlas.AtlasRegion> rightAnimation;
    private Animation<TextureAtlas.AtlasRegion> leftAnimation;
    private float time;

    public enum Direction {
        RIGHT, LEFT
    }

    public Enemigo(TextureRegion image) {
        super(image);

        rightAnimation = new Animation<TextureAtlas.AtlasRegion>(0.05f, R.getAnimation("right_enemy"));
        leftAnimation = new Animation<TextureAtlas.AtlasRegion>(0.05f, R.getAnimation("left_enemy"));

        direction = Direction.LEFT;
    }

    @Override
    public void update(float dt) {
        time += dt;

        switch (direction) {
            case LEFT:
                image = leftAnimation.getKeyFrame(time, true);
                break;
            case RIGHT:
                image = rightAnimation.getKeyFrame(time, true);
                break;
        }
    }
}
