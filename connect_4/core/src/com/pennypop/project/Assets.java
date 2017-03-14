package com.pennypop.project;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Assets {
	
	private static Assets assets;
	private AssetManager manager;
	
	public static Assets get(){
		if(assets==null)
			assets = new Assets();
		return assets;
	}
	
	public Assets(){
		manager = new AssetManager();
	}
	
	public AssetManager getManager(){
		return manager;
	}
	
	public void loadMainScreen(){
		manager.load("font.fnt",BitmapFont.class);
		manager.load("apiButton.png",Texture.class);
		manager.load("sfxButton.png",Texture.class);
		manager.load("gameButton.png", Texture.class);
		manager.load("button_click.wav", Sound.class);
	}
	
	public void loadGameScreen(){
		manager.load("yellow.png",Texture.class);
		manager.load("red.png",Texture.class);
		manager.load("cell.png",Texture.class);
	}
}
