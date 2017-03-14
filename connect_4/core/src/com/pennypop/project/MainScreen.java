package com.pennypop.project;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * This is where you screen code will go, any UI should be in here
 * 
 * @author Richard Taylor, Wenjin
 */
public class MainScreen implements Screen {

	private Screen currentScreen;
	private final Stage stage;
	private final SpriteBatch spriteBatch;
	private Viewport viewport;
	private OrthographicCamera camera;
	private BitmapFont font;
	private AssetManager manager;
	private WebConn webconn;
	private int row_width;
	private int col_height;
	private Texture sfxTexture, apiTexture, gameTexture;
	private Sound clickSound;
	private Game game;

	public MainScreen(Game game) {
		currentScreen = this;
		this.game = game;
		row_width = Gdx.graphics.getWidth() / 4;
		col_height = Gdx.graphics.getHeight() / 4;
		spriteBatch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		viewport = new ScreenViewport(camera);
//		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
//		false, spriteBatch);
		stage = new Stage(viewport);
		manager = Assets.get().getManager();
		font = manager.get("font.fnt",BitmapFont.class);
		sfxTexture = manager.get("sfxButton.png",Texture.class);
		apiTexture = manager.get("apiButton.png",Texture.class);
		gameTexture = manager.get("gameButton.png", Texture.class);
		clickSound = manager.get("button_click.wav",Sound.class);

		Label pennyLabel = new Label("PennyPop", new LabelStyle(font,Color.RED));
		pennyLabel.setPosition(row_width, col_height*3);
		
		//sfx button
		Button sfxBtn = bondBtnEvent(sfxTexture);
		sfxBtn.setPosition(row_width-110, col_height*2);
		
		//api button
		Button apiBtn = bondBtnEvent(apiTexture);
		apiBtn.setPosition(row_width , col_height*2);
		
		//game button
		Button gameBtn = bondBtnEvent(gameTexture);
		gameBtn.setPosition(row_width+110, col_height*2);
		
		//add all actors to stage
		stage.addActor(sfxBtn);
		stage.addActor(apiBtn);
		stage.addActor(gameBtn);
		stage.addActor(pennyLabel);
		
	}
	
	//add button listeners to 3 buttons
	public Button bondBtnEvent(final Texture texture){
		Button btn = new Button(new Image(texture).getDrawable());
		btn.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event,float x,float y){
				clickSound.play();
				if(texture == apiTexture){
					Runnable getWeather = new Runnable(){
						public void run(){
							fetchWeather();
						}
					};
					new Thread(getWeather).start();
				}else{
					if(texture == gameTexture){
						//jump to game screen
						manager.finishLoading();
						Screen gameScreen = new GameScreen(game, currentScreen);
						currentScreen.hide();
						game.setScreen(gameScreen);
					}
				}
			}
		});
		return btn;
	}
	
	//connect to weather api using webconn and fetch data
	public void fetchWeather(){
		
		webconn = new WebConn();
		webconn.getConnection("San Francisco", "US");
		webconn.getWeatherInfo();
		
		String weatherinfo = "Current Weather" + "\n"
		+ webconn.weather.getLocation() + "\n"
		+ webconn.weather.getDescription() + "\n"
		+ webconn.weather.getTemperature() + " degrees, "
		+ webconn.weather.getWind() + " mph wind";
		String location = webconn.weather.getLocation();
		String description = webconn.weather.getDescription();
		String temp_wind = webconn.weather.getTemperature() + " degrees, "
				+ webconn.weather.getWind() + " mph wind";
		//System.out.print(weatherinfo);
		Label weatherLabel = new Label(weatherinfo, new LabelStyle(font,Color.BROWN));
		GlyphLayout layout = new GlyphLayout();
		layout.setText(font,weatherinfo);
		weatherLabel.setPosition(row_width*3-layout.width/2, col_height*2-layout.height/2);
		
		stage.addActor(weatherLabel);
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
		font.dispose();
		stage.dispose();
	}

	@Override
	public void render(float delta) {
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// stage.setViewport(width, height, false);
		stage.setViewport(viewport);
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void pause() {
		// Irrelevant on desktop, ignore this
	}

	@Override
	public void resume() {
		// Irrelevant on desktop, ignore this
	}

}
