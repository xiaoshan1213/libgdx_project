package com.pennypop.project;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/*
 * This is the connect-4 game screen
 * all UI setup and event listeners
 * 
 * -- created by Wenjin
 */
public class GameScreen implements Screen {

	private Game game;
	private Screen parent;
	private Stage stage;
	private SpriteBatch spriteBatch;
	private OrthographicCamera camera;
	private Viewport viewport;
	private AssetManager manager;
	private Texture yellowTexture, redTexture, cellTexture;
	private BitmapFont font;
	private AI ai;
	
	public GameScreen(Game game, Screen parent){
		
		this.game = game;
		this.parent = parent;
		spriteBatch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		viewport = new ScreenViewport(camera);
		stage = new Stage(viewport, spriteBatch);
		manager = Assets.get().getManager();
		
		//get all assets from assetmanager
		yellowTexture = manager.get("yellow.png", Texture.class);
		redTexture = manager.get("red.png",Texture.class);
		cellTexture = manager.get("cell.png", Texture.class);
		font = manager.get("font.fnt",BitmapFont.class);
		
		setupBoard();
		
	}
	
	//set up the game board
	public void setupBoard(){
		final GameSetting gamesetting = GameSetting.get();
		final GameCore gamecore = new GameCore();
		ai = new AI(gamecore.getBoard());
		
		for(int i=0;i<gamesetting.getRow();i++){
			for(int j=0;j<gamesetting.getCol();j++){
				Image cell = new Image(cellTexture);
				cell.setSize(cell.getWidth()*1.2f, cell.getHeight()*1.2f);
				
				float x = Gdx.graphics.getWidth()/2 - cell.getWidth()/2
						- (cell.getWidth() * (gamesetting.getCol()/2))
						+ (cell.getWidth()*j);
				float y = cell.getHeight() * i +85;
				cell.setPosition(x, y);
				gamecore.getBoard()[i][j].setXY(x, y);
				stage.addActor(cell);
			}
		}
		
		//add click listener to the stage
		stage.addListener(new ClickListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				if(gamecore.getState() == GameCore.ONGAMING){
					Chess chess;
					switch(gamecore.checkTurn()){
						case GameCore.PLAYER_1:
							chess = new Chess(redTexture);
							break;
						default:
							chess = new Chess(yellowTexture);
							break;
					}
					
					if(chess.setPosition(x, gamecore.getBoard())){
						//if empty cell, add the chess
						gamecore.addToSlot(gamecore.checkTurn(), chess.getRow(), chess.getCol());
						stage.addActor(chess.getChess());
						
						//add AI here to match player1's move
//						if (gamecore.checkTurn() == GameCore.PLAYER_2) {
//							chess = new Chess(yellowTexture);
//							chess.setPosition(ai.findBestMove(), gamecore.getBoard());
//							gamecore.addToSlot(GameCore.PLAYER_2, chess.getRow(), chess.getCol());
//							stage.addActor(chess.getChess());
//						}
						
						if(gamecore.getState() != GameCore.ONGAMING){
							String status = new String();
							if(gamecore.getState() == GameCore.WIN){
								status = "Player "+gamecore.getWinner()+" wins\n"+
										"click to continue";
							}else{
								status = "Draw\nClick to continue";
							}
							LabelStyle style = new LabelStyle(font, Color.RED);
							Label statusLabel = new Label(status, style);
							statusLabel.setAlignment(Align.center);
							GlyphLayout layout = new GlyphLayout();
							layout.setText(font, status);
							statusLabel.setPosition(Gdx.graphics.getWidth()/2 - layout.width/2, 
								Gdx.graphics.getHeight() - 3 * statusLabel.getHeight()/2);

							stage.addActor(statusLabel);
						}
						
					}
				}else{
					game.setScreen(parent);
				}
			}
		});
		
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		//stage.setViewport(width, height, false);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		spriteBatch.dispose();
		stage.dispose();
	}

}
