package com.jarias.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.jarias.managers.R;
import com.jarias.utils.Utilities;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisImage;
import com.kotcrab.vis.ui.widget.VisTable;

public class Tienda implements Screen {

    private Stage stage;
    private Juego juego;
    private TextureRegion imgPersonaje;
    private String[] personajes;
    private final int PRECIO_SKINS = 75;
    private VisImage image;
    private BitmapFont bitmapFont;
    private SpriteBatch batch;

    public Tienda(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        menu();
        bitmapFont = new BitmapFont();
    }

    public void menu() {
        if (!VisUI.isLoaded())
            VisUI.load();

        stage = new Stage();
        personajes = new String[]{"robot_idle", "megaman_idle", "ash_idle_down"};

        VisTable tabla = new VisTable();
        VisTable background = new VisTable();
        final VisTable personaje = new VisTable();
        background.setFillParent(true);
        tabla.setFillParent(true);
        personaje.setFillParent(true);
        stage.addActor(background);
        stage.addActor(tabla);
        stage.addActor(personaje);

        final TextButton btnSiguiente = new TextButton("Siguiente", R.getSkin());
        btnSiguiente.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Utilities.skin != Utilities.NUM_SKINS) {
                    Utilities.skin++;
                } else {
                    Utilities.skin = 0;
                }
                imgPersonaje = new TextureRegion(R.getTexture(personajes[Utilities.skin]));
                image.setDrawable(new TextureRegionDrawable(imgPersonaje));
            }
        });

        final TextButton btnComprar = new TextButton("Comprar", R.getSkin());
        btnComprar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (personajes[Utilities.skin] == Utilities.megaman.nombre && Utilities.monedas >= Utilities.megaman.precio && !Utilities.megaman.comprado) {
                    Utilities.megaman.comprado = true;
                    Utilities.monedas = Utilities.monedas - Utilities.megaman.precio;
                } else if (personajes[Utilities.skin] == Utilities.ash.nombre && Utilities.monedas >= Utilities.ash.precio && !Utilities.ash.comprado) {
                    Utilities.ash.comprado = true;
                    Utilities.monedas = Utilities.monedas - Utilities.ash.precio;
                }
            }
        });

        final TextButton btnEquipar = new TextButton("Equipar", R.getSkin());
        btnEquipar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (juego != null) {
                    if (personajes[Utilities.skin] == Utilities.megaman.nombre && Utilities.megaman.comprado) {
                        juego.player.image = imgPersonaje;
                        Utilities.equipada = personajes[Utilities.skin];
                    } else if (personajes[Utilities.skin] == Utilities.ash.nombre && Utilities.ash.comprado) {
                        juego.player.image = imgPersonaje;
                        Utilities.equipada = personajes[Utilities.skin];
                    } else {
                        Utilities.equipada = personajes[0];
                    }
                }
            }
        });

        final TextButton btnAnterior = new TextButton("Anterior", R.getSkin());
        btnAnterior.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Utilities.skin != 0) {
                    Utilities.skin--;
                } else {
                    Utilities.skin = Utilities.NUM_SKINS;
                }
                System.out.println(Utilities.skin);
                imgPersonaje = new TextureRegion(R.getTexture(personajes[Utilities.skin]));
                image.setDrawable(new TextureRegionDrawable(imgPersonaje));
            }
        });

        TextButton btnAtras = new TextButton("Atras", R.getSkin());
        btnAtras.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuPrincipal(juego));
                dispose();
            }
        });

        VisImage back = new VisImage();
        back.setDrawable(new Texture("core/assets/ui/background.png"));

        image = new VisImage();
        if(juego != null) {
            image.setDrawable(new TextureRegionDrawable(R.getTexture(juego.player.image.toString())));
        } else {
            image.setDrawable(new TextureRegionDrawable(R.getTexture(Utilities.equipada)));
        }

        background.add(back);
        tabla.row();
        tabla.add().left().height(50).pad(5);
        tabla.row();
        tabla.add(btnAnterior).left().height(50).pad(5);
        tabla.add(image).center();
        tabla.add(btnSiguiente).right().height(50).pad(5);
        tabla.row();
        tabla.add(btnComprar).left().height(50).pad(5);
        tabla.add().center().width(image.getWidth());
        tabla.add(btnEquipar).right().width(btnComprar.getWidth()).height(50).pad(5);
        tabla.row();
        tabla.add().left().height(50).pad(5);
        tabla.row();
        tabla.add().left();
        tabla.add(btnAtras).center().height(50).pad(5);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0, 0, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(dt);
        stage.draw();
        batch.begin();
        bitmapFont.draw(batch, "Monedas: " + Utilities.monedas, 5, Gdx.graphics.getHeight() - 10);
        batch.end();
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
        stage.dispose();
    }
}
