package com.jarias.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

public class R {

    public static AssetManager assets = new AssetManager();

    public static void loadResources() {
        assets.load("core/assets/atlas.pack.atlas", TextureAtlas.class);

        assets.load("core/assets/sounds/music.mp3", Music.class);
        assets.load("core/assets/sounds/bruh.mp3", Sound.class);
        assets.load("core/assets/sounds/coin.wav", Sound.class);
        assets.load("core/assets/sounds/victory.mp3", Sound.class);
    }

    public static boolean update() {
        return assets.update();
    }

    public static TextureRegion getTexture(String name) {
        return assets.get("core/assets/atlas.pack.atlas", TextureAtlas.class).findRegion(name);
    }

    public static Array<TextureAtlas.AtlasRegion> getAnimation(String name) {
        return assets.get("core/assets/atlas.pack.atlas", TextureAtlas.class).findRegions(name);
    }

    public static Skin getSkin() {
        return new Skin(Gdx.files.internal("core/assets/ui/neon-ui.json"));
    }

    public static Sound getSound(String name) {
        return assets.get(name, Sound.class);
    }

    public static Music getMusic(String name) {
        return assets.get(name, Music.class);
    }
}
