package model;

import java.awt.Point;
import java.util.Random;

/**
 * This strategy selects the first available move at random.  It is easy to beat
 * 
 * @throws IGotNowhereToGoException whenever asked for a move that is impossible to deliver
 * 
 * @author FirstName LastName
 */

// There is an intentional compile time error.  Implement this interface
public class RandomAI implements TicTacToeStrategy {

  // Randomly find an open spot while ignoring possible wins and stops.
  // This should be easy to beat as a human. 

  @Override
  public Point desiredMove(TicTacToeGame theGame) 
  	throws IGotNowhereToGoException {
	  
	  if(theGame.getMoveNumber() > 8)
		  throw new IGotNowhereToGoException(null);
	  
	  boolean move = false;
	  Random randomNum = new Random ();
	  char[][] board = theGame.getTicTacToeBoard();
	  
	  while(!move) {
		  int r = randomNum.nextInt(3);
		  int c = randomNum.nextInt(3);
		  
		  if(board[r][c] == '_') {
			  move = true;
			  return new Point (r, c);  
		  }		  
	  }
    return null;
  }
}