/*
 * Written by: Tyler Horvat
 * CSC 335 Summer 2017 6/27/17
 * This class implements a GUI for tic tac toe
 * where the user inputs row and column and clicks
 * button to make a move
 */

package view;

import java.awt.Point;
import controller.OurObservable;
import controller.OurObserver;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.ComputerPlayer;
import model.TicTacToeGame;

public class TextAreaView extends BorderPane implements OurObserver {

  private TicTacToeGame theGame;
  private Label [][] textFields = null; 
  private ComputerPlayer computerPlayer;
  private GridPane textPanel;
  private GridPane gameBoard;
  private GridPane panel;
  public static String buttonLabel = "Make move";
  private Label row = new Label("row");
  private Label column = new Label("column");
  private TextField textRow = new TextField("");
  private TextField textColumn = new TextField("");
  private Button button;

  public TextAreaView(TicTacToeGame TicTacToeGame) {
    theGame = TicTacToeGame;
    computerPlayer = theGame.getComputerPlayer();
    ButtonListener handler = new ButtonListener();
    
    
    textPanel = new GridPane();
    textPanel.setHgap(10);
    textPanel.setVgap(10);
    
    textRow.setMaxWidth(40);
    textColumn.setMaxWidth(40);
    
    button = new Button();
    button.setText(buttonLabel);
    button.setOnAction(handler);
    button.setMinWidth(80);
    
    textPanel.add(textRow, 0, 0);
    textPanel.add(row, 1, 0);
    textPanel.add(textColumn, 0, 1);
    textPanel.add(column, 1, 1);
    textPanel.add(button, 0, 2, 2, 1);
    textPanel.setAlignment(Pos.CENTER);
  
    gameBoard = new GridPane();
    gameBoard.setHgap(15);
    gameBoard.setVgap(15);
    
   
    panel = new GridPane();
    
    panel.add(textPanel, 0, 0);
    panel.add(new Label(""), 0, 1);
    panel.add(gameBoard, 0, 2);
    panel.setAlignment(Pos.CENTER);

    gameBoard.setAlignment(Pos.CENTER);
    this.setCenter(panel);
    
    initializeLabelPanel();
    // Need to show the board before a move that calls update
  }

  // This method is called by OurObservable's notifyObservers()
  @Override
  public void update(OurObservable obs) {
    theGame = (TicTacToeGame) obs;
    
    updateLabelPanel();
  }
  
  // this method updates view of board
  public void updateLabelPanel() {
	    char[][] temp = theGame.getTicTacToeBoard();
	    for (int r = 0; r < 3; r++) {
	      for (int c = 0; c < 3; c++) {
	        String text = "" + temp[r][c];
	        textFields[r][c].setText(text);
	      }
	    }
	  }
  
  //this method sets up view of board
  private void initializeLabelPanel() {
	    int size = theGame.size();
	    Font myFont = new Font("Courier New", 30);
	    textFields = new Label[size][size];
	    for (int row = 0; row < size; row++) {
	      for (int col = 0; col < size; col++) {
	        textFields[row][col] = new Label();
	        textFields[row][col].setFont(myFont);
	        textFields[row][col].setTextFill(Color.web("#0076a3"));
	        textFields[row][col].setText("_");
	        gameBoard.add(textFields[row][col], col, row);
	      }
	    }
	  }
  
    // this class implements an event handler for when button is clicked 
    private class ButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent arg0) {
           
        	String rowStr = textRow.getText();
        	String columnStr = textColumn.getText();
        	
        	int row = Integer.parseInt(rowStr), column = Integer.parseInt(columnStr);
        	
        	textRow.setText("");
        	textColumn.setText("");
        	
        	if(row < 0 || row > 2 || column < 0 || column > 2) {
        		button.setText("Invalid Choice");
        		return;
        	}
        	
        	button.setText(buttonLabel);
        	
        	if(theGame.stillRunning() &&  theGame.getTicTacToeBoard()[row][column] == '_')
                theGame.choose(row, column);
            else {
            	button.setText("Can't move there");
                return; 
            }
        	
        	if (theGame.tied()) {
                button.setText("Tied");
                updateLabelPanel();
            }
            else if (theGame.didWin('X')) {
                button.setText("X wins");
                updateLabelPanel();
            }
            else {
                // If the game is not over, let the computer player choose
                // This algorithm assumes the computer player always
                // goes after the human player and is represented by 'O', not 'X'
                Point play = computerPlayer.desiredMove(theGame);
                theGame.choose(play.x, play.y);
                if (theGame.didWin('O')) {
                    button.setText("O wins");
                    updateLabelPanel();
                }
            }
        }
    }    
}



