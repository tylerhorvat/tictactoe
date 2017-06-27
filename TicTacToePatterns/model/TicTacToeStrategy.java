/*
 * Written by: Tyler Horvat
 * CSC 335 Summer 2017 6/27/17
 * This class picks a move in tic tac toe at random
 */

package model;

// Use Java's Point class to store two ints: an x and a y

// @author mercer
import java.awt.Point;

public interface TicTacToeStrategy {

  // The ComputerPlayer has access to "seeing" anything about 
  // the game when it is given the game as an argument.
  public Point desiredMove(TicTacToeGame theGame);
}