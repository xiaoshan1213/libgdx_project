package com.pennypop.project;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;

/**
 * The {@link ApplicationListener} for this project, create(), resize() and
 * render() are the only methods that are relevant
 * 
 * @author Richard Taylor, Wenjin
 */
public class ProjectApplication extends Game implements ApplicationListener {

	private Screen screen;
	private AssetManager manager;
	private Game game;

	public static void main(String[] args) {
		new LwjglApplication(new ProjectApplication(), "PennyPop", 1280, 720, true);
	}
	
	public ProjectApplication(){
		game = this;
	}

	@Override
	public void create() {
		// Graphics.DisplayMode mode = Gdx.graphics.getDisplayMode();
		// Gdx.graphics.setFullscreenMode(mode);
		
		manager = Assets.get().getManager();
		Assets.get().loadMainScreen();
		manager.finishLoading();
		
		screen = new MainScreen(game);
		setScreen(screen);
		
		Assets.get().loadGameScreen();
		manager.update();
	}

	@Override
	public void dispose() {
		game.getScreen().hide();
		game.getScreen().dispose();
		manager.dispose();
	}

	@Override
	public void pause() {
		game.getScreen().pause();
	}

	@Override
	public void render() {
		clearWhite();
		game.getScreen().render(Gdx.graphics.getDeltaTime());
	}

	/** Clears the screen with a white color */
	private void clearWhite() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void resize(int width, int height) {
		game.getScreen().resize(width, height);
	}

	@Override
	public void resume() {
		game.getScreen().resume();
	}
}
