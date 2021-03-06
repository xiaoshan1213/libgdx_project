package com.pennypop.project;

import java.util.Random;

import com.pennypop.project.Cell;
import com.pennypop.project.GameSetting;

/* AI class that implements how the AI would move.
 * 
 * Algorithm: shrink version of minimax algorithm, just two rounds check
 * 
 * Algorithm is divided into two parts:
 * First round checks the AI's disc matches and sums a score + if any would result in a win
 * or current 3-in-a-row for opponent.
 * 
 * Second round checks for opponent's next turn moves based on the possible moves from the AI.
 * It sums the points based on the opponent's matches and subtracts it from the current sum
 * from the first round.
 * 
 * AI is PLAYER_2
 */

public class AI {
	private double[] score;
	Cell[][] board;
	GameSetting settings;
	
	public AI(Cell[][] board) {
		settings = GameSetting.get();
		score = new double[settings.getCol()];
		this.board = board;
		clearScores();
	}
	
	public void clearScores() {
		for (int i = 0; i < settings.getCol(); i++) {
			score[i] = 0;
		}
	}
	
	public int findBestMove() {
		double max;
		int column;
		
		//Go through first round and find all the sums
		firstRound();
		for (int i = 0; i < settings.getCol(); i++ ) {
			//if there is a 3-in-a-row currently that can end in victory, return it
			if (score[i] >= Math.pow(10, settings.getWinCondition())) {
				return i;
			}
		}
		
		//Second round to sum all the points from opponent moves based on yours
		//initial max
		score[0] += secondRound(0);
		max = score[0];
		column = 0;
		
		//loop through to find max
		for (int i = 1; i < settings.getCol(); i++ ) {
			//full, so not a choice
			if (score[i] == -1) {
				continue;
			}
		
			score[i] += secondRound(i);
			
			if (score[i] > max) {
				max = score[i];
				column = i;
			} else {
				if (score[i] == max) {
					//if the number generated is even
					//best move is now the new number
					Random rand = new Random();
					if (rand.nextInt(2) == 0) {
						max = score[i];
						column = i;
					}
				}
			}
		}
		
		clearScores();
		return column;
	}
	
	//Sums up all matches - find the maximum
	public void firstRound() {
		int row, col;
		
		for (col = 0; col < settings.getCol(); col++) {
			//find empty row
			for (row = 0; row < settings.getRow(); row++) {
				if (board[row][col].getPlayer() == 0) {
					break;
				}
			}
			
			//if no empty slot at this column, give it -1
			if (row == settings.getRow()) {
				score[col] = -1;
			} else {
				score[col] = checkVerticalMatch(row, col, GameCore.PLAYER_2)
						+ checkHorizontalMatch(row, col, GameCore.PLAYER_2)
						+ checkDiagonalRightMatch(row, col, GameCore.PLAYER_2)
						+ checkDiagonalLeftMatch(row, col, GameCore.PLAYER_2);
			}
		}
	}
	
	//Subtracts all the opponent matches based on AI's move - minimize lost
	public double secondRound(int choice) {
		int col, row;
		double sum = 0;
		
		for (col = 0; col < settings.getCol(); col++) {
			//if there's no more room, move on to the next one
			if (score[col] == -1) {
				continue;
			}
			
			for (row = 0; row < settings.getRow(); row++) {
				if (board[row][col].getPlayer() == 0) {
					break;
				}
				
			}
			
			//If it's currently the row the the AI chose, put the piece one row higher
			//because the AI's disc is currently there
			if (col == choice && row+1 < settings.getRow()) {
				row++;
			} else {
				if (row+1 > settings.getRow()){
					continue;
				}
			}
			
			//Sum up everything to subtract from the current score
			sum -= checkVerticalMatch(row, col, GameCore.PLAYER_1);
			sum	-= checkHorizontalMatch(row, col, GameCore.PLAYER_1);
			sum	-= checkDiagonalRightMatch(row, col, GameCore.PLAYER_1);
			sum -= checkDiagonalLeftMatch(row, col, GameCore.PLAYER_1);
					
		}
		
		return sum;
	}
	
	//Sum matches from top to bottom: '|'
	private double checkVerticalMatch(int row, int col, int player) {
		int count = 1;
			
		for (int i = row - 1; i >= 0; i--) {
			if (board[i][col].getPlayer() != player) {
				break;
			}
				
			count++;
		}
		
		//If no matches
		if (count < 2) {
			return 0;
		} else {
			return Math.pow(10, count);
		}
		
	}
		
	//Sum matches from left to right: '-'
	private double checkHorizontalMatch(int row, int col, int player) {
		int count = 1;
			
		//Check left half
		for (int i = col - 1; i >= 0; i--) {
			if (board[row][i].getPlayer() != player) {
				break;
			}
				
			count++;
		}
			
		//Check right half
		for (int i = col + 1; i < settings.getCol(); i++) {
			if (board[row][i].getPlayer() != player) {
				break;
			}
				
			count++;
				
		}
		
		//If no matches
		if (count < 2) {
			return 0;
		} else {
			return Math.pow(10, count);
		}
	}
		
	//Sum matches diagonally to the right: '/'
	private double checkDiagonalRightMatch(int row, int col, int player) {
		//Check top half
		int count = 1;
		for (int i = 1; row+i < settings.getRow() && col+i < settings.getCol(); i++) {
			if (board[row + i][col + i].getPlayer() != player) {
				break;
			}
				
			count++;
		}
			
		//Check bottom half
		for (int i = 1; row-i >= 0 && col-i >= 0; i++) {
			if (board[row - i][col - i].getPlayer() != player) {
				break;
			}
			
			count++;
		}
		
		//If no matches
		if (count < 2) {
			return 0;
		} else {
			return Math.pow(10, count);
		}
	}
		
	//Sum matches diagonally to the left: '\'
	private double checkDiagonalLeftMatch(int row, int col, int player) {
		//Check top half
		int count = 1;
		for (int i = 1; row+i < settings.getRow() && col-i >= 0; i++) {
			if (board[row + i][col - i].getPlayer() != player) {
				break;
			}
					
			count++;
		}
				
		//Check bottom half
		for (int i = 1; row-i >= 0 && col+i < settings.getCol(); i++) {
			if (board[row - i][col + i].getPlayer() != player) {
				break;
			}
				
			count++;
		}
			
		//If no matches
		if (count < 2) {
			return 0;
		} else {
			return Math.pow(10, count);
		}
	}
	
	public void printScore() {
		for (int i = 0; i < settings.getCol(); i++) {
			System.out.print(score[i] + " ");
		}
		
		System.out.println("");
	}
	
}
