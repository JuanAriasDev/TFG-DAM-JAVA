package com.jarias.utils;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.jarias.characters.Skin;
import com.jarias.managers.R;

public class Utilities {
    public static int currentLevel = 1;
    public static final int LEVELS = 7;
    public static boolean reanudar = false;
    public static int skin = 0;
    public static String equipada = "robot_idle";
    public static Vector2 start = new Vector2(0,0);
    public static int monedas = 0;
    public static int puntos = 0;
    public static int muertes = 0;
    public static final int NUM_SKINS = 2;
    public static Skin megaman = new Skin(false, "megaman_idle", 50);
    public static Skin ash = new Skin(false, "ash_idle_down", 50);
    public static boolean energy = false;
    public static boolean enMapaSecreto = false;
    public static final float VELOCIDAD_PERSONAJE = 3.2f;
    public static final float VELOCIDAD_PERSONAJE_PLUS = 5;
    public static final float ENEMIES_SPEED = 2.2f;
    public static boolean musicActive = true;
    public static Music music = R.getMusic("core/assets/sounds/music.mp3");
    public static boolean inGameMenu = false;
}
