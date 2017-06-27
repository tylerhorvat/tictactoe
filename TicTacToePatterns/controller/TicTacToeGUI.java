/*
 * Updated by: Tyler Horvat
 * CSC 335 Summer 2017 6/27/17
 * Changes include the addition for a TextView
 * menu option
 */

package controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.RandomAI;
import model.StopperAI;
import model.TicTacToeGame;
import view.ButtonView;
import view.TextAreaView;

/**
 * Play TicTacToe the computer that can have different AIs to beat you. 
 * Select the Options menus to begin a new game, switch strategies for 
 * the computer player (BOT or AI), and to switch between the two views.
 * 
 * This class represents an event-driven program with a graphical user 
 * interface as a controller between the view and the model. It has 
 * event handlers to mediate between the view and the model.
 * 
 * This controller employs the Observer design pattern that updates two 
 * views every time the state of the tic tac toe game changes:
 * 
 *    1) whenever you make a move by clicking a button or an area of either view
 *    2) whenever the computer AI makes a move
 *    3) whenever there is a win or a tie
 *    
 * You can also select two different strategies to play against from the menus
 * 
 * @author mercer
 */
public class TicTacToeGUI extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  private TicTacToeGame theGame;

  private MenuBar menuBar;

  private OurObserver currentView;
  private OurObserver buttonView;
  private OurObserver textAreaView;
  private BorderPane window;
  public static final int width = 254;
  public static final int height = 360;

  public void start(Stage stage) {
    stage.setTitle("Tic Tac Toe");
    window = new BorderPane();
    Scene scene = new Scene(window, width, height);

    setupMenus();
    window.setTop(menuBar);
    initializeGameForTheFirstTime();
    
    // Set up the views
    buttonView = new ButtonView(theGame);
    textAreaView = new TextAreaView(theGame);
    theGame.addObserver(buttonView);
    theGame.addObserver(textAreaView);
    
    setViewTo(buttonView);
    stage.setScene(scene);

    stage.show();
  }

  public void initializeGameForTheFirstTime() {
    theGame = new TicTacToeGame();
    // This event driven program will always have
    // a computer player who takes the second turn
    theGame.setComputerPlayerStrategy(new RandomAI());
  }

  private void setupMenus() {
    MenuItem beginner = new MenuItem("RandomAI");
    MenuItem intermediate = new MenuItem("StopperAI");
    Menu strategies = new Menu("Stategies");
    strategies.getItems().addAll(beginner, intermediate);

    MenuItem buttonV = new MenuItem("Button");
    MenuItem textV = new MenuItem("TextArea");
    Menu views = new Menu("Views");
    views.getItems().addAll(buttonV, textV);

    MenuItem newGame = new MenuItem("New Game");
    Menu options = new Menu("Options");
    options.getItems().addAll(newGame, strategies, views);

    menuBar = new MenuBar();
    menuBar.getMenus().addAll(options);

    // Add the same listener to all menu items requiring action
    MenuItemListener menuListener = new MenuItemListener();
    newGame.setOnAction(menuListener);
    beginner.setOnAction(menuListener);
    buttonV.setOnAction(menuListener);
    textV.setOnAction(menuListener);
    intermediate.setOnAction(menuListener);
  }

  private void setViewTo(OurObserver newView) {
    window.setCenter(null);
    currentView = newView;
    window.setCenter((Node) currentView);
  }

  private class MenuItemListener implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent e) {
      // Find out the text of the JMenuItem that was just clicked
      String text = ((MenuItem) e.getSource()).getText();
      if (text.equals("Button"))
        setViewTo(buttonView);
      else if (text.equals("TextArea"))
        setViewTo(textAreaView);
      else if (text.equals("New Game"))
        theGame.startNewGame(); // The computer player has been set and should not change.
      else if (text.equals("StopperAI"))
        theGame.setComputerPlayerStrategy(new StopperAI());
      else if (text.equals("RandomAI"))
        theGame.setComputerPlayerStrategy(new RandomAI());
    }
  }
}
