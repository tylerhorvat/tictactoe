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
	  
      int [] moves = minimax(newBoard, 'O');
      
      return new Point(moves[1], moves[2]);
  }
  
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