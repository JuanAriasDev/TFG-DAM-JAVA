package com.jarias.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jarias.managers.R;
import com.jarias.utils.Utilities;

public class Player extends Character {

    public enum State {
        RIGHT, LEFT, IDLE
    }

    public final static float JUMP_SPEED = 5.3f;
    public boolean isJumping;
    public final static int SALTOS = 2;

    private Animation<TextureAtlas.AtlasRegion> rightAnimation;
    private Animation<TextureAtlas.AtlasRegion> leftAnimation;
    public State state;
    public float time;
    public Rectangle rectSuelo;
    public int saltos;

    public Player(TextureRegion image) {
        super(image);
        speed = new Vector2(0, 0);
        rectangle = new Rectangle(position.x, position.y, size.x, size.y);
        rectSuelo = new Rectangle(position.x, position.y + size.y, size.x, size.y - (1/4*size.y));
        state = State.IDLE;
        isJumping = false;
        saltos = 0;

        rightAnimation = new Animation<TextureAtlas.AtlasRegion>(0.05f, R.getAnimation("robot_run_right"));
        leftAnimation = new Animation<TextureAtlas.AtlasRegion>(0.05f, R.getAnimation("robot_run_left"));
    }

    public void update(float dt) {
        super.update(dt);
        time += dt;

        switch (state) {
            case RIGHT:
                if (!isJumping) {
                    if (Utilities.equipada == "robot_idle")
                        rightAnimation = new Animation<TextureAtlas.AtlasRegion>(0.05f, R.getAnimation("robot_run_right"));

                    if (Utilities.equipada == "megaman_idle")
                        rightAnimation = new Animation<TextureAtlas.AtlasRegion>(0.05f, R.getAnimation("megaman_run_right"));

                    if (Utilities.equipada == "ash_idle_down")
                        rightAnimation = new Animation<TextureAtlas.AtlasRegion>(0.05f, R.getAnimation("ash_run_right"));

                    image = rightAnimation.getKeyFrame(time, true);
                } else {
                    if (Utilities.equipada == "robot_idle")
                        image = R.getTexture("jump_right");

                    if (Utilities.equipada == "megaman_idle")
                        image = R.getTexture("megaman_run_right");

                    if (Utilities.equipada == "ash_idle_down")
                        image = R.getTexture("ash_run_right");
                }
                break;
            case LEFT:
                if (!isJumping) {
                    if (Utilities.equipada == "robot_idle")
                        leftAnimation = new Animation<TextureAtlas.AtlasRegion>(0.05f, R.getAnimation("robot_run_left"));

                    if (Utilities.equipada == "megaman_idle")
                        leftAnimation = new Animation<TextureAtlas.AtlasRegion>(0.05f, R.getAnimation("megaman_run_left"));

                    if (Utilities.equipada == "ash_idle_down")
                        leftAnimation = new Animation<TextureAtlas.AtlasRegion>(0.05f, R.getAnimation("ash_run_left"));

                    image = leftAnimation.getKeyFrame(time, true);
                } else {
                    if (Utilities.equipada == "robot_idle")
                        image = R.getTexture("jump_left");

                    if (Utilities.equipada == "megaman_idle")
                        image = R.getTexture("megaman_run_left");

                    if (Utilities.equipada == "ash_idle_down")
                        image = R.getTexture("ash_run_left");
                }
                break;
            case IDLE:
                if (Utilities.equipada == "robot_idle")
                    image = R.getTexture("robot_idle");

                if (Utilities.equipada == "megaman_idle")
                    image = R.getTexture("megaman_idle");

                if (Utilities.equipada == "ash_idle_down")
                    image = R.getTexture("ash_idle_down");
                break;
        }
    }

    @Override
    public void move(Vector2 movement) {
        super.move(movement);

        rectSuelo.x = position.x;
        rectSuelo.y = position.y;
    }

    public void jump() {
        speedY = JUMP_SPEED;
    }
}
