package com.pennypop.project;

public class GameSetting {
	
	private static GameSetting gamesetting;
	private int players;
	private int row;
	private int col;
	private boolean ai = true;
	private int winCondition;
	
	public static GameSetting get(){
		if(gamesetting==null)
			gamesetting = new GameSetting();
		return gamesetting;
	}
	
	public int getPlayers() {
		return players;
	}

	public void setPlayers(int players) {
		this.players = players;
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

	public boolean isAi() {
		return ai;
	}

	public void setAi(boolean ai) {
		this.ai = ai;
	}

	public int getWinCondition() {
		return winCondition;
	}

	public void setWinCondition(int winCondition) {
		this.winCondition = winCondition;
	}

	public GameSetting(){
		
		this.players = 2;
		this.row = 6;
		this.col = 7;
		this.ai = false;
		this.winCondition = 4;
		
	}
}
