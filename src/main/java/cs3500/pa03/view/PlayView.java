package cs3500.pa03.view;

import cs3500.pa03.model.Board;
import java.io.IOException;

/**
 * Class used to display the game state and other info in the console
 */
public class PlayView implements View {
  private final Board aiBoard;
  private final Board playerBoard;
  private final Appendable output;

  /**
   * Constructor with each field
   *
   * @param aiBoard the AI's board
   * @param playerBoard the player's board
   * @param output the variable to keep track of the output
   */
  public PlayView(Board aiBoard, Board playerBoard, Appendable output) {
    this.aiBoard = aiBoard;
    this.playerBoard = playerBoard;
    this.output = output;
  }

  @Override
  public void viewBoards() {
    try {
      output.append("Your BattleSalvo board:\n");
      output.append(playerBoard.toString());
      output.append("\n\n");

      output.append("AI BattleSalvo board:\n");
      output.append(aiBoard.toString());
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot append message");
    }
  }

  @Override
  public void viewWelcomeMessage() {
    try {
      output.append("Welcome to my BattleSalvo game for OOD!\n");
      output.append("Please enter a row and a column value to represent your board size:\n");
      output.append("*-------------------------------------------------*\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot append message");
    }
  }

  @Override
  public void viewErrorMessage(String type) {
    try {
      if (type.equals("input_length")) {
        output.append("That is an invalid number of inputs!\n");
        output.append("*-------------------------------------------------*\n");
      } else if (type.equals("board_size")) {
        output.append("That is an invalid value for your board size!\nPlease enter between"
            + " 6 and 15 inclusive for row and column size\n");
        output.append("*-------------------------------------------------*\n");
      } else if (type.equals("fleet_size")) {
        output.append("Uh Oh! You've entered invalid fleet sizes.\n");
        output.append("*-------------------------------------------------*\n");
      } else if (type.equals("shots_length")) {
        output.append("Uh oh! You've entered a wrong length of shots!\n");
        output.append("Please enter shots again, up to the number of ships you have\n");
        output.append("*-------------------------------------------------*\n");
      } else {
        output.append("The error message type was incorrect!\n");
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot append");
    }
  }

  @Override
  public void promptForShots() {
    try {
      output.append("\n");
      output.append("Enter your shots in the form of (x,y), up to the number of ships you have");
      output.append("\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot append");
    }
  }

  @Override
  public void promptForShips() {
    try {
      output.append("Please enter your fleet in the order [Carrier, Battleship, Destroyer, "
          + "Submarine].\nRemember, your fleet may not exceed size 8 "
          + "and there must be at least one ship\n");
      output.append("*-------------------------------------------------*\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
