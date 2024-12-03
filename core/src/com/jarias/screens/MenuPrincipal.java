package com.jarias.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jarias.managers.R;
import com.jarias.utils.Utilities;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisImage;
import com.kotcrab.vis.ui.widget.VisTable;

import javax.rmi.CORBA.Util;

public class MenuPrincipal implements Screen {

    private Stage stage;
    private Juego juego;

    public MenuPrincipal(){}

    public MenuPrincipal(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        if (!VisUI.isLoaded())
            VisUI.load();

        stage = new Stage();

        Utilities.music.setLooping(true);
        Utilities.music.setVolume(0.15f);
        if (Utilities.musicActive)
            Utilities.music.play();

        VisTable tabla = new VisTable();
        VisTable background = new VisTable();
        background.setFillParent(true);
        tabla.setFillParent(true);
        stage.addActor(background);
        stage.addActor(tabla);

        TextButton nuevaPartida = new TextButton("Nueva Partida", R.getSkin());
        nuevaPartida.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Utilities.currentLevel = 1;
                Utilities.muertes = 0;
                Utilities.monedas = 0;
                Utilities.puntos = 0;
                Utilities.megaman.comprado = false;
                Utilities.ash.comprado = false;
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Juego());
                dispose();
            }
        });

        TextButton tienda = new TextButton("Tienda", R.getSkin());
        tienda.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Tienda(juego));
                dispose();
            }
        });

        TextButton continuar = new TextButton("Continuar", R.getSkin());
        continuar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(juego != null) {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(juego);
                    dispose();
                }
            }
        });

        TextButton btnOpciones = new TextButton("Opciones", R.getSkin());
        btnOpciones.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuConfiguracion(juego));
                dispose();
            }
        });

        TextButton btnSalir = new TextButton("Salir", R.getSkin());
        btnSalir.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                System.exit(0);
            }
        });

        VisImage image = new VisImage();
        image.setDrawable(new Texture("core/assets/ui/background.png"));

        VisImage logo = new VisImage();
        logo.setDrawable(new Texture("core/assets/ui/logo.png"));

        background.add(image);
        tabla.row();
        tabla.add(logo);
        tabla.row();
        tabla.add(continuar).center().width(200).height(50).pad(5);
        tabla.row();
        tabla.add(nuevaPartida).center().width(200).height(50).pad(5);
        tabla.row();
        tabla.add(tienda).center().width(200).height(50).pad(5);
        tabla.row();
        tabla.add(btnOpciones).center().width(200).height(50).pad(5);
        tabla.row();
        tabla.add(btnSalir).center().width(200).height(50).pad(5);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0, 0, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(dt);
        stage.draw();
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
