package com.jarias;

import com.badlogic.gdx.Game;
import com.jarias.screens.Splash;

public class Juanman extends Game {

	@Override
	public void create () {
		setScreen(new Splash());
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
	}
}

