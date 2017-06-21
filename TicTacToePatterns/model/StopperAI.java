package model;

import java.awt.Point;

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
  public Point desiredMove(TicTacToeGame theGame) {

    // First have the ComputerPlayer look for a win 

    // If the AI can not win, look to block the human player

    // If no block or win is possible, pick any move from those still available

    return null;
  }

}