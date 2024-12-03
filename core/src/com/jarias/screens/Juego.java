package com.jarias.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.jarias.characters.*;
import com.jarias.managers.R;
import com.jarias.utils.Utilities;

import java.awt.image.SampleModel;

public class Juego implements Screen {

    private Batch batch;
    public Player player;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private Array<Puerta> puertas;
    private Array<Pared> paredes;
    private Array<Boton> botones;
    private Array<Barril> barriles;
    private Array<Pincho> pinchos;
    private Array<Laser> lasers;
    private Array<Moneda> monedas;
    private Array<Enemigo> enemigos;
    private MapLayer lDoors, lFloor, lTraps, lCoins, lEnemies;
    private boolean onoff;
    public boolean paused = false;
    private BitmapFont bitmapFont;

    @Override
    public void show() {
        bitmapFont = new BitmapFont();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 32 * 32, 16 * 32);
        camera.update();
        if (!Utilities.reanudar || !Utilities.enMapaSecreto) {
            monedas = new Array<Moneda>();
            loadCurrentMap();
            loadCoins();
        }
    }

    @Override
    public void render(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            paused = !paused;
            Utilities.inGameMenu = true;
            ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuInGame(this));
        }
        if (!paused) {
            mapRenderer.render(new int[]{0, 1});
            player.update(dt);
            handleBorders();
            handleCollisions();
            handleKeyboard();
            enemiesMovement();

            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_CLEAR_VALUE);

            batch.begin();
            for (Puerta puerta : puertas)
                puerta.draw(batch);
            if (!onoff) {
                for (Pared pared : paredes)
                    pared.draw(batch);
            } else {
                paredes.clear();
            }
            for (Boton boton : botones) {
                boton.update(dt);
                boton.draw(batch);
            }
            for (Barril barril : barriles) {
                barril.update(dt);
                barril.draw(batch);
            }
            for (Pincho pincho : pinchos) {
                pincho.draw(batch);
            }
            for (Laser laser : lasers) {
                laser.update(dt);
                laser.draw(batch);
            }
            for (Moneda moneda : monedas) {
                moneda.draw(batch);
            }
            for (Enemigo enemigo : enemigos) {
                enemigo.update(dt);
                enemigo.draw(batch);
            }
            player.draw(batch);
            bitmapFont.draw(batch, "Monedas: " + Utilities.monedas, 30, Gdx.graphics.getHeight() - 5);
            bitmapFont.draw(batch, "Muertes: " + Utilities.muertes, 130, Gdx.graphics.getHeight() - 5);
            bitmapFont.draw(batch, "Puntos: " + Utilities.puntos, 230, Gdx.graphics.getHeight() - 5);
            batch.end();
        }
    }

    private void loadCurrentMap() {
        onoff = false;
        map = new TmxMapLoader().load("core/assets/levels/mapa_" + Utilities.currentLevel + ".tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        batch = mapRenderer.getBatch();
        mapRenderer.setView(camera);
        loadDoors();
        loadTraps();
        loadEnemies();
    }

    private void loadSecreta() {
        map = new TmxMapLoader().load("core/assets/levels/mapa_secreto.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        batch = mapRenderer.getBatch();
        mapRenderer.setView(camera);
        loadDoors();
        loadTraps();
        loadEnemies();
    }

    private void handleCollisions() {
        // Colisiones con el suelo, paredes y techo
        lFloor = map.getLayers().get("suelo");
        for (MapObject object : lFloor.getObjects()) {
            if (object.getProperties().containsKey("suelo")) {
                RectangleMapObject objectRect = (RectangleMapObject) object;
                Rectangle rect = objectRect.getRectangle();

                if (rect.overlaps(player.rectangle)) {
                    if(player.isJumping) {
                        player.isJumping = false;
                    }
                    player.saltos = 0;
                    player.position.y = rect.y + rect.getHeight();
                    player.rectangle.y = player.position.y;
                }

                for (Enemigo enemigo : enemigos) {
                    if (rect.overlaps(enemigo.rectangle)) {
                        enemigo.position.y = rect.y + rect.getHeight();
                        enemigo.rectangle.y = enemigo.position.y;
                    }
                }

                for(Barril barril : barriles) {
                    if(rect.overlaps(barril.rectangle)) {
                        barriles.removeValue(barril, true);
                    }
                }
            }
            if (object.getProperties().containsKey("techo")) {
                RectangleMapObject objectRect = (RectangleMapObject) object;
                Rectangle rect = objectRect.getRectangle();

                if (player.rectangle.overlaps(rect)) {
                    player.speedY = -player.speedY/2;
                    player.position.y = rect.y - player.size.y;
                    player.rectangle.y = player.position.y;
                }
            }
            if (object.getProperties().containsKey("pared_i")) {
                RectangleMapObject objectRect = (RectangleMapObject) object;
                Rectangle rect = objectRect.getRectangle();

                if (player.rectangle.overlaps(rect)) {
                    player.position.x = rect.x + rect.getWidth();
                    player.rectangle.x = player.position.x;
                }
            }
            if (object.getProperties().containsKey("pared_d")) {
                RectangleMapObject objectRect = (RectangleMapObject) object;
                Rectangle rect = objectRect.getRectangle();

                if (player.rectangle.overlaps(rect)) {
                    player.position.x = rect.x - player.size.x;
                    player.rectangle.x = player.position.x;
                }
            }
        }
        // Colisiones con las puertas
        for (Puerta puerta : puertas) {
            puerta.rectangle.set(puerta.position.x, puerta.position.y, puerta.size.x, puerta.size.y);
            if (puerta.end) {
                if (player.rectangle.overlaps(puerta.rectangle)) {
                    Utilities.currentLevel++;
                    if (Utilities.currentLevel > Utilities.LEVELS) {
                        R.getSound("core/assets/sounds/victory.mp3").play();
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new EndGame(this));
                        Utilities.currentLevel = 1;
                    }
                    else {
                        loadCurrentMap();
                        monedas = new Array<Moneda>();
                        loadCoins();
                    }
                }
            }
            if (puerta.secreta) {
                if (player.rectangle.overlaps(puerta.rectangle)) {
                    Utilities.enMapaSecreto = true;
                    loadSecreta();
                    monedas = new Array<Moneda>();
                    loadCoins();
                }
            }
        }
        // Colisiones con pared que desaparece
        for (Pared pared : paredes) {
            pared.rectangle.set(pared.position.x, pared.position.y, pared.size.x, pared.size.y);
            if (pared.derecha) {
                if (player.rectangle.overlaps(pared.rectangle)) {
                    player.position.x = pared.rectangle.x - player.size.x;
                    player.rectangle.x = player.position.x;
                }
            } else {
                if (player.rectangle.overlaps(pared.rectangle)) {
                    player.position.x = pared.rectangle.x + pared.rectangle.width;
                    player.rectangle.x = player.position.x;
                }
            }
        }
        // Colisiones con botones que abren puertas
        for (Boton boton : botones) {
            boton.rectangle.set(boton.position.x, boton.position.y, boton.size.x, boton.size.y);
            if (player.rectangle.overlaps(boton.rectangle)) {
                boton.state = Boton.State.ON;
                onoff = true;
            }
        }
        // Colisiones con monedas
        for (Moneda moneda : monedas) {
            moneda.rectangle.set(moneda.position.x, moneda.position.y, moneda.size.x, moneda.size.y);
            if (moneda.energy) {
                if (player.rectangle.overlaps(moneda.rectangle)) {
                    monedas.removeValue(moneda, true);
                    Utilities.energy = true;
                    Utilities.enMapaSecreto = false;
                    loadCurrentMap();
                }
            } else if (player.rectangle.overlaps(moneda.rectangle)) {
                monedas.removeValue(moneda, true);
                R.getSound("core/assets/sounds/coin.wav").play();
                Utilities.monedas++;
                Utilities.puntos += 100;
            }
        }
        // Colisiones para activar la caida de los barriles
        lTraps = map.getLayers().get("trampas");
        for (MapObject object : lTraps.getObjects()) {
            if(object.getProperties().containsKey("activa_barril")) {
                RectangleMapObject objectRect = (RectangleMapObject) object;
                Rectangle rectangle = objectRect.getRectangle();
                for(int i=0; i < barriles.size; i++) {
                    for(Barril barril : barriles) {
                        if(object.getProperties().containsKey("a_" + barril.number)) {
                            if(rectangle.overlaps(player.rectangle)) {
                                barril.active = true;
                            }
                        }
                    }
                }
            }
        }
        // Colisiones con los barriles
        for (Barril barril : barriles) {
            barril.rectangle.set(barril.position.x, barril.position.y, barril.size.x, barril.size.y);
            if (player.rectangle.overlaps(barril.rectangle)) {
                R.getSound("core/assets/sounds/bruh.mp3").play();
                Utilities.muertes++;
                if (Utilities.enMapaSecreto)
                    loadSecreta();
                else
                    loadCurrentMap();
            }
        }
        // Colisiones con pinchos
        for (Pincho pincho : pinchos) {
            pincho.rectangle.set(pincho.position.x, pincho.position.y, pincho.size.x, pincho.size.y);
            if (player.rectangle.overlaps(pincho.rectangle)) {
                R.getSound("core/assets/sounds/bruh.mp3").play();
                Utilities.muertes++;
                loadCurrentMap();
            }
        }
        // Colisiones con lasers
        for (Laser laser : lasers) {
            laser.rectangle.set(laser.position.x, laser.position.y, laser.size.x, laser.size.y);
            if (player.rectangle.overlaps(laser.rectangle)) {
                if (laser.state) {
                    R.getSound("core/assets/sounds/bruh.mp3").play();
                    Utilities.muertes++;
                    loadCurrentMap();
                }
            }
        }
        //Colisiones de los enemigos
        lEnemies = map.getLayers().get("enemies");
        for (MapObject object : lEnemies.getObjects()) {
            if(object.getProperties().containsKey("pared_a_i")) {
                RectangleMapObject objectRect = (RectangleMapObject) object;
                Rectangle rectangle = objectRect.getRectangle();
                for (Enemigo enemigo : enemigos) {
                    enemigo.rectangle.set(enemigo.position.x, enemigo.position.y, enemigo.size.x, enemigo.size.y);
                    if (rectangle.overlaps(enemigo.rectangle)) {
                        enemigo.direction = Enemigo.Direction.LEFT;
                    }
                }
            } else if(object.getProperties().containsKey("pared_a_d")) {
                RectangleMapObject objectRect = (RectangleMapObject) object;
                Rectangle rectangle = objectRect.getRectangle();
                for (Enemigo enemigo : enemigos) {
                    enemigo.rectangle.set(enemigo.position.x, enemigo.position.y, enemigo.size.x, enemigo.size.y);
                    if (rectangle.overlaps(enemigo.rectangle)) {
                        enemigo.direction = Enemigo.Direction.RIGHT;
                    }
                }
            }
        }
        // Colisiones de los enemigos con el personaje
        for (Enemigo enemigo : enemigos) {
            if (enemigo.rectangle.overlaps(player.rectangle)) {
                R.getSound("core/assets/sounds/bruh.mp3").play();
                Utilities.muertes++;
                loadCurrentMap();
            }
        }
    }

    public void loadCoins() {
        lCoins = map.getLayers().get("monedas");
        for (MapObject object : lCoins.getObjects()) {
            TiledMapTileMapObject  tileObject = (TiledMapTileMapObject) object;
            if (tileObject.getProperties().containsKey("moneda")) {
                Moneda moneda = null;
                if (tileObject.getProperties().containsKey("energy")) {
                    moneda = new Moneda(R.getTexture("energy"));
                    moneda.position.set(tileObject.getX(), tileObject.getY());
                    moneda.energy = true;
                } else {
                    moneda = new Moneda(R.getTexture("moneda"));
                    moneda.position.set(tileObject.getX(), tileObject.getY());
                }
                monedas.add(moneda);
            }
        }
    }

    public void loadDoors() {
        puertas = new Array<Puerta>();
        botones = new Array<Boton>();
        paredes = new Array<Pared>();
        lDoors = map.getLayers().get("puertas");
        for (MapObject object : lDoors.getObjects()) {
            TiledMapTileMapObject  tileObject = (TiledMapTileMapObject) object;
            if (tileObject.getProperties().containsKey("door")) {
                Puerta puerta;
                if (tileObject.getProperties().containsKey("secreta")) {
                    puerta = new Puerta(R.getTexture("secreta"));
                    puerta.position.set(tileObject.getX(), tileObject.getY());
                    puerta.secreta = true;
                } else {
                    puerta = new Puerta(R.getTexture("door"));
                    puerta.position.set(tileObject.getX(), tileObject.getY());
                }

                if (tileObject.getProperties().containsKey("start")) {
                    Utilities.start.set(puerta.position.x, puerta.position.y);
                    player = new Player(R.getTexture("robot_idle"));
                    player.position.set(Utilities.start.x, Utilities.start.y);
                    player.rectangle.set(Utilities.start.x, Utilities.start.y, player.size.x, player.size.y);
                }
                if (tileObject.getProperties().containsKey("end")) {
                    puerta.end = true;
                }
                puertas.add(puerta);
            }
            if (tileObject.getProperties().containsKey("pared_i")) {
                Pared pared = new Pared(R.getTexture("pared"));
                pared.derecha = false;
                pared.position.set(tileObject.getX(), tileObject.getY());
                paredes.add(pared);
            }
            if (tileObject.getProperties().containsKey("pared_d")) {
                Pared pared = new Pared(R.getTexture("pared"));
                pared.derecha = true;
                pared.position.set(tileObject.getX(), tileObject.getY());
                paredes.add(pared);
            }
            if (tileObject.getProperties().containsKey("boton")) {
                Boton boton = new Boton(R.getTexture("btn_off"));
                boton.position.set(tileObject.getX(), tileObject.getY());
                boton.state = Boton.State.OFF;
                botones.add(boton);
            }
            if (tileObject.getProperties().containsKey("secreta")) {
                Puerta puerta = new Puerta(R.getTexture("secreta"));
                puerta.position.set(tileObject.getX(), tileObject.getY());
                puertas.add(puerta);
            }
        }
    }

    public void loadTraps() {
        barriles = new Array<Barril>();
        pinchos = new Array<Pincho>();
        lasers = new Array<Laser>();
        lTraps = map.getLayers().get("trampas");
        for (MapObject object : lTraps.getObjects()) {
            if(!object.getProperties().containsKey("activa_barril")) {
                TiledMapTileMapObject tileObject = (TiledMapTileMapObject) object;
                if (tileObject.getProperties().containsKey("barril")) {
                    Barril barril = new Barril(R.getTexture("barrel_fire"));
                    barril.position.set(tileObject.getX(), tileObject.getY());
                    barril.number = (Integer) tileObject.getProperties().get("numero");
                    barriles.add(barril);
                }
                if (tileObject.getProperties().containsKey("pinchos")) {
                    Pincho pincho = new Pincho(R.getTexture("pinchos"));
                    pincho.position.set(tileObject.getX(), tileObject.getY());
                    pinchos.add(pincho);
                }
                if (tileObject.getProperties().containsKey("laser")) {
                    Laser laser = new Laser(R.getTexture("laser"));
                    laser.position.set(tileObject.getX(), tileObject.getY());
                    lasers.add(laser);
                }
            }
        }
    }

    public void loadEnemies() {
        enemigos = new Array<Enemigo>();
        lEnemies = map.getLayers().get("enemies");
        for (MapObject object : lEnemies.getObjects()) {
            if (!object.getProperties().containsKey("pared_a_d") && !object.getProperties().containsKey("pared_a_i")) {
                TiledMapTileMapObject tileObject = (TiledMapTileMapObject) object;
                if (tileObject.getProperties().containsKey("enemy")) {
                    Enemigo enemigo = new Enemigo(R.getTexture("left_enemy"));
                    enemigo.position.set(tileObject.getX(), tileObject.getY());
                    enemigos.add(enemigo);
                }
            }
        }
    }

    public void enemiesMovement() {
        for (Enemigo enemigo : enemigos) {
            if (enemigo.direction == Enemigo.Direction.LEFT)
                enemigo.move(new Vector2(-Utilities.ENEMIES_SPEED, 0));
            else if (enemigo.direction == Enemigo.Direction.RIGHT)
                enemigo.move(new Vector2(Utilities.ENEMIES_SPEED, 0));
        }
    }

    public void handleKeyboard() {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (Utilities.energy)
                player.move(new Vector2(Utilities.VELOCIDAD_PERSONAJE_PLUS, 0));
            else
                player.move(new Vector2(Utilities.VELOCIDAD_PERSONAJE, 0));
            player.state = Player.State.RIGHT;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (Utilities.energy)
                player.move(new Vector2(-Utilities.VELOCIDAD_PERSONAJE_PLUS, 0));
            else
                player.move(new Vector2(-Utilities.VELOCIDAD_PERSONAJE, 0));
            player.state = Player.State.LEFT;
        } else {
            player.state = Player.State.IDLE;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            if (player.saltos != player.SALTOS) {
                player.jump();
                player.saltos++;
                player.isJumping = true;
            }
        }
    }

    public void handleBorders() {
        if (player.position.x < 0)
            player.position.x = 0;

        if ((player.position.x + player.size.x) > Gdx.graphics.getWidth())
            player.position.x = Gdx.graphics.getWidth() - player.size.x;

        if (player.position.y < 0)
            player.position.y = 0;

        if ((player.position.y + player.size.y) > Gdx.graphics.getHeight())
            player.position.y = Gdx.graphics.getHeight() - player.size.y;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
