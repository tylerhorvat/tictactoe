/*
 * Written by: Tyler Horvat
 * CSC 335 Summer 2017 6/27/17
 * This class attempts to win as many tic tac toe
 * games as possible by implementing the minimax algorithm
 */
package model;
import java.awt.Point;
import java.util.ArrayList;

/**
 * This TTT strategy look to win first. If it can not, it looks to block
 * the human player from winning. If none found, it randomly
 * picks a move from the available spaces. 
 * 
 * @throws IGotNowhereToGoException whenever asked for a move that is impossible to deliver
 * 
 * @author FirstName LastName
 */
public class StopperAI implements TicTacToeStrategy {

  @Override
  public Point desiredMove(TicTacToeGame theGame)
      throws IGotNowhereToGoException {
	  
	  if(theGame.getMoveNumber() > 8)
		  throw new IGotNowhereToGoException(null);
	  
	  char [][] board = theGame.getTicTacToeBoard();
	  
	  char [][] newBoard;
	  
	  newBoard = board.clone();
	  int [] checkWin = lookForWin(newBoard);
	  
	  if(checkWin[0] == 1) {
		  System.out.println("Check win: " + checkWin[0]);
		  return new Point(checkWin[1], checkWin[2]);
	  }
	  
      int [] moves = minimax(newBoard, 'O');
      
      return new Point(moves[1], moves[2]);
  }
  
  //this method implements the minimax algorithm, which attempts to find the 
  //best possible move
  
  private int[] minimax(char [][] board, char player) {
	  
	  //available spots
	  ArrayList<Point> availableSpots = new ArrayList<>();
	  availableSpots.addAll(emptyMoves(board));
	  
	  if(findLeader(board, 'X')) {
		  return new int[] {-10};
	  }
	  else if(findLeader(board, 'O')) {
		  return new int[] {10};
	  }
	  else if(availableSpots.size() == 0) {
		  return new int[]{0};
	  }
	  
	  ArrayList<Moves> moves = new ArrayList<>();
	  
	  for(int i = 0; i <availableSpots.size(); i++) {
		  
		  Moves move = new Moves(availableSpots.get(i));
		  int r = (int) availableSpots.get(i).getX(), c = (int) availableSpots.get(i).getY();
		  
		  board[r][c] = player;
		  
		  if(player == 'O') {
			  int result[] = minimax(board, 'X');
			  move.setScore(result[0]);
		  }
		  else {
			  int result[] = minimax(board, 'O');
			  move.setScore(result[0]);
		  }
		  
		  board[(int) availableSpots.get(i).getX()][(int) availableSpots.get(i).getY()] = '_';
		  
		  moves.add(move);
	  }
	  
	  
	  int bestMove = -1;
	  
	  if(player == 'O') {
		  int bestScore = Integer.MIN_VALUE;
		  for(int i = 0; i < moves.size(); i++) {
			  if(moves.get(i).getScore() > bestScore) {
				  bestScore = moves.get(i).getScore();
				  bestMove = i;
			  }
		  }
	  }
	  else {
		  int bestScore = Integer.MAX_VALUE;
		  for(int i = 0; i < moves.size(); i++) {
			  if(moves.get(i).getScore() < bestScore) {
				  bestScore = moves.get(i).getScore();
				  bestMove = i;
			  }
		  }
	  }
	  
	  return new int[]{moves.get(bestMove).getScore(), (int) moves.get(bestMove).getPoint().getX(), (int) moves.get(bestMove).getPoint().getY()};
  }
  
  //this method looks for all possible moves
  private ArrayList<Point> emptyMoves(char [][] board) {
	  
	  ArrayList<Point> empty = new ArrayList<>();
	  
	  for(int r = 0; r < 3; r++){
		  for(int c = 0; c < 3; c++) {
			  if(board[r][c] == '_') {
				  empty.add(new Point(r, c));
			  }
		  }
	  }
	  return empty;	  
  }
  
  //this method is a helper method for minimax, and looks for 
  //a winner
  private boolean findLeader(char [][] board, char player) {
	  
	  if((board[0][0]  == player && board[0][1] == player && board[0][2] == player) ||
		 (board[1][0]  == player && board[1][1] == player && board[1][2] == player) ||
		 (board[2][0]  == player && board[2][1] == player && board[2][2] == player) ||
		 (board[0][0]  == player && board[1][0] == player && board[2][0] == player) ||
		 (board[0][1]  == player && board[1][1] == player && board[2][1] == player) ||
		 (board[0][2]  == player && board[1][2] == player && board[2][2] == player) ||
		 (board[0][0]  == player && board[1][1] == player && board[2][2] == player) ||
		 (board[2][0]  == player && board[1][1] == player && board[0][2] == player)  ) {
		 
		  	return true;
	  }
	  
	  return false;
  }



private int[] lookForWin(char [][] board) {
	
	if((board[0][0] == 'O' && board[0][1] == 'O') || (board[1][2] == 'O' && board[2][2] == 'O') || (board[1][1] == 'O' && board[2][0] == 'O'))
		return new int[]{1, 0, 2};
	else if((board[1][0] == 'O' && board[1][1] == 'O') || (board[0][2] == 'O' && board[2][2] == 'O'))
		return new int[]{1, 1, 2};
	else if((board[2][0] == 'O' && board[2][1] == 'O') || (board[0][2] == 'O' && board[1][2] == 'O') || (board[0][0] == 'O' && board[1][1] == 'O'))
		return new int[]{1, 2, 2};
	else if((board[0][0] == 'O' && board[0][2] == 'O') || (board[2][1] == 'O' && board[1][1] == 'O'))
		return new int[]{1, 0, 1};
	else if((board[0][0] == 'O' && board[2][2] == 'O') || (board[2][0] == 'O' && board[0][2] == 'O') ||
	        (board[0][1] == 'O' && board[2][1] == 'O') || (board[1][0] == 'O' && board[1][2] == 'O'))
		return new int[]{1, 1, 1};
	else if((board[2][0] == 'O' && board[2][2] == 'O') || (board[0][1] == 'O' && board[1][1] == 'O'))
		return new int[]{1, 2, 1};
	else if((board[0][1] == 'O' && board[0][2] == 'O') || (board[1][0] == 'O' && board[2][0] == 'O') || (board[1][1] == 'O' && board[2][2] == 'O'))
		return new int[]{1, 0, 0}; 
	else if((board[1][1] == 'O' && board[1][2] == 'O') || (board[0][0] == 'O' && board[2][0] == 'O'))
		return new int[]{1, 1, 0};
	else if((board[0][0] == 'O' && board[1][0] == 'O') || (board[2][1] == 'O' && board[2][2] == 'O') || (board[1][1] == 'O' && board[0][2] == 'O'))
		return new int[]{1, 2, 0};
		
	else
	    return new int[]{-1};
}

}

class Moves {
	
	public Point getPoint() {
		return point;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	Point point = new Point();
	int score;
	
	Moves(Point point){
		this.point = point;
		this.score = 0;
	}
}
