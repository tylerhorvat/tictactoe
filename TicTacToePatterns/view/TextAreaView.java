package view;

import controller.OurObservable;
import controller.OurObserver;
import javafx.scene.layout.BorderPane;
import model.TicTacToeGame;

public class TextAreaView extends BorderPane implements OurObserver {

  private TicTacToeGame theGame;

  public TextAreaView(TicTacToeGame TicTacToeGame) {
    theGame = TicTacToeGame;  // Need to show the board before a move that calls update
  }

  // This method is called by OurObservable's notifyObservers()
  @Override
  public void update(OurObservable obs) {
    theGame = (TicTacToeGame) obs;
  }

}