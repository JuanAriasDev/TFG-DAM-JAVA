package com.jarias.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jarias.managers.R;
import com.jarias.utils.Utilities;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisImage;
import com.kotcrab.vis.ui.widget.VisTable;

public class EndGame implements Screen {

    private Stage stage;
    private Juego juego;

    public EndGame(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        menu();
    }

    public void menu() {
        if (!VisUI.isLoaded())
            VisUI.load();

        stage = new Stage();

        VisTable tabla = new VisTable();
        VisTable background = new VisTable();
        background.setFillParent(true);
        tabla.setFillParent(true);
        stage.addActor(background);
        stage.addActor(tabla);

        TextButton newGame = new TextButton("Nueva Partida", R.getSkin());
        newGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Utilities.muertes = 0;
                Utilities.monedas = 0;
                Utilities.puntos = 0;
                Utilities.megaman.comprado = false;
                Utilities.ash.comprado = false;
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Juego());
                dispose();
            }
        });

        TextButton exit = new TextButton("Salir", R.getSkin());
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.exit(0);
            }
        });

        TextButton menuPrincipal = new TextButton("Menu Principal", R.getSkin());
        menuPrincipal.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuPrincipal());
                dispose();
            }
        });

        VisImage image = new VisImage();
        image.setDrawable(new Texture("core/assets/ui/background.png"));

        background.add(image);
        tabla.row();
        tabla.add(newGame).center().width(200).height(50).pad(5);
        tabla.row();
        tabla.add(menuPrincipal).center().width(200).height(50).pad(5);
        tabla.row();
        tabla.add(exit).center().width(200).height(50).pad(5);

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

    }
}
