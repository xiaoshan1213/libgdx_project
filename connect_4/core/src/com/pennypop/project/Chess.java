package com.pennypop.project;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/*
 * This is the chess players put on board
 * 
 * -- created by Wenjin
 */
public class Chess {
	
	private Image chess;
	private float x, y, height, width;
	private int row, col;
	
	public Chess(Texture texture){
		chess = new Image(texture);
		chess.setSize(chess.getWidth()*1.2f, chess.getHeight()*1.2f);
		width = chess.getWidth();
		height = chess.getHeight();
		x=y=row=col=-1;
	}
	
	public boolean setPosition(float x,Cell[][] board){
		for(int i=0;i<GameSetting.get().getCol();i++){
			if(x>board[0][i].getX() && x<board[0][i].getX()+width){
				for(int j=0;j<GameSetting.get().getRow();j++){
					if(board[j][i].getPlayer()==0){
						col = i;
						row = j;
						x = board[j][i].getX();
						y = board[j][i].getY();
						chess.setPosition(x, y);
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public Image getChess() {
		return chess;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getHeight() {
		return height;
	}

	public float getWidth() {
		return width;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	
	
}
