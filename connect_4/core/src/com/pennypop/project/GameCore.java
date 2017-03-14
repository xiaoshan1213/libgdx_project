package com.pennypop.project;

/*
 * This is the game core class, doing:
 * 1.set players
 * 2.play turn
 * 3.check play state
 * 4.judge win
 * 
 * created by Wenjin, Feb.19,2017
 */

public class GameCore {

	public static final int PLAYER_1 = 1;
	public static final int PLAYER_2 = 2;
	
	public static final int ONGAMING = 0;
	public static final int WIN = 1;
	public static final int DRAW = 2;
	
	private int rows;
	private int cols;
	private int numOfSlots;
	private int turn;
	private int winCondition;
	private int winner;
	private int state;
	private GameSetting gamesetting;
	private Cell[][] board;
	
	public GameCore(){
		
		gamesetting = GameSetting.get();
		rows = gamesetting.getRow();
		cols = gamesetting.getCol();
		numOfSlots = rows*cols;
		turn = PLAYER_1;
		winCondition = gamesetting.getWinCondition();
		state = ONGAMING;
		board = new Cell[rows][cols];
		for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){
				Cell cell = new Cell(rows,cols);
				board[i][j] = cell;
			}
		}
	}
	
	public void addToSlot(int player,int row,int col){
		board[row][col].setPlayer(player);
		numOfSlots--;
		if(checkWin(row,col)){
			winner = turn;
			return ;
		}
		if(checkDraw(row,col)){
			return ;
		}
		turn++;
	}
	
	public int checkTurn(){
		if(turn>gamesetting.getPlayers())
			turn = PLAYER_1;
		return turn;
	}
	
	public boolean checkWin(int row,int col){
		if(checkVertical(row,col)||
				checkHorizontal(row,col)||
				checkDiagonal(row,col)){
			state = WIN;
			return true;
		}
		return false;
	}
	
	private boolean checkVertical(int row,int col){
		int count = 1;
		for(int i=row-1;i>=0;i--){
			if(board[i][col].getPlayer()!=board[row][col].getPlayer())
				break;
			count++;
			if(count==winCondition)
				return true;
		}
		return false;
	}
	
	private boolean checkHorizontal(int row,int col){
		int count = 1;
		for(int i=col-1;i>=0;i--){
			if(board[row][i].getPlayer()!=board[row][col].getPlayer())
				break;
			count++;
			if(count==winCondition)
				return true;
		}
		for(int i=col+1;i<this.cols;i++){
			if(board[row][i].getPlayer()!=board[row][col].getPlayer())
				break;
			count++;
			if(count==winCondition)
				return true;
		}
		return false;
	}
	
	private boolean checkDiagonal(int row,int col){
		int count = 1;
		for(int i=row-1,j=col-1;i>=0&&j>=0;i--,j--){
			if(board[i][j].getPlayer()!=board[row][col].getPlayer())
				break;
			count++;
			if(count==winCondition)
				return true;
		}
		for(int i=row+1,j=col+1;i<this.rows&&j<this.cols;i++,j++){
			if(board[i][j].getPlayer()!=board[row][col].getPlayer())
				break;
			count++;
			if(count==winCondition)
				return true;
		}
		count = 1;
		for(int i=row-1,j=col+1;i>=0&&j<this.cols;i--,j++){
			if(board[i][j].getPlayer()!=board[row][col].getPlayer())
				break;
			count++;
			if(count==winCondition)
				return true;
		}
		for(int i=row+1,j=col-1;i<this.rows&&j>=0;i++,j--){
			if(board[i][j].getPlayer()!=board[row][col].getPlayer())
				break;
			count++;
			if(count==winCondition)
				return true;
		}
		return false;
	}
	
	public boolean checkDraw(int row,int col){
		if(numOfSlots==0){
			state = DRAW;
			return true;
		}
		return false;
	}
	
	public int getWinner() {
		return winner;
	}

	public int getState() {
		return state;
	}

	public Cell[][] getBoard() {
		return board;
	}
	
}
