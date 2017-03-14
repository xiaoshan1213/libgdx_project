package com.pennypop.project;

/*
 * This is the board initiation cells
 * 
 * -- created by Wenjin, Feb.19, 2017
 */
public class Cell {
	
	private int row,col,player;
	private float x,y;
	
	public Cell(int row,int col){
		this.row = row;
		this.col = col;
		player = 0;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public void setXY(float x,float y){
		this.x = x;
		this.y = y;
	}
	
}
