package cs3500.pa03.controller;

import cs3500.pa03.model.Board;
import cs3500.pa03.model.Player;
import cs3500.pa03.view.Coord;
import cs3500.pa03.view.ShipType;
import cs3500.pa03.view.View;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for the Controller of the entire program
 */
public class GameController implements Controller {
  private final Player manualPlayer;
  private final Player aiPlayer;
  private final Board consoleBoard;
  private final Board aiBoard;
  private final View consoleView;

  /**
   * Constructor that holds every field
   *
   * @param manualPlayer The console player
   * @param aiPlayer The AI player
   * @param consoleBoard The console's board
   * @param aiBoard The AI's board
   * @param consoleView The View object that displays to the console
   */
  public GameController(Player manualPlayer, Player aiPlayer, Board consoleBoard, Board aiBoard,
                        View consoleView) {
    this.manualPlayer = manualPlayer;
    this.aiPlayer = aiPlayer;
    this.consoleBoard = consoleBoard;
    this.aiBoard = aiBoard;
    this.consoleView = consoleView;
  }

  /**
   * Runs the program and delegates responsibility
   */
  public void run() {
    BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
    consoleView.viewWelcomeMessage();
    String[] inputs = getDimensions(inputReader);
    while (inputs.length != 2) {
      consoleView.viewErrorMessage("input_length");
      inputs = getDimensions(inputReader);
    }
    while (!isAppropriate(inputs)) {
      inputs = getDimensions(inputReader);
    }

    int rowSize = Integer.parseInt(inputs[0]);
    int colSize = Integer.parseInt(inputs[1]);
    consoleBoard.initBoard(rowSize, colSize);
    aiBoard.initBoard(rowSize, colSize);
    Map<ShipType, Integer> shipsMap = this.getShips(inputReader);

    //add all the player's ships and place them randomly
    manualPlayer.setup(rowSize, colSize, shipsMap);

    //add all the AI's ships and place them randomly
    aiPlayer.setup(rowSize, colSize, shipsMap);

    boolean isGameOver = false;
    while (!isGameOver) {
      //outputting the view
      consoleView.viewBoards();
      consoleView.promptForShots();

      //shoot each other
      List<Coord> playerShotCoords = manualPlayer.takeShots();
      List<Coord> aiShotCoords = aiPlayer.takeShots();

      //report damage
      List<Coord> consoleHits = manualPlayer.reportDamage(aiShotCoords);
      List<Coord> aiHits = aiPlayer.reportDamage(playerShotCoords);

      //successful hits
      //displays the shots (from the console) that hit the AI ships
      manualPlayer.successfulHits(consoleHits);
      //displays which AI shots hit the player's ships
      aiPlayer.successfulHits(aiHits);

      //repeat?
      if (consoleBoard.getListOfShips().isEmpty() || aiBoard.getListOfShips().isEmpty()) {
        isGameOver = true;
      }
    }
  }

  private String[] getDimensions(BufferedReader inputReader) {
    try {
      String input = inputReader.readLine();
      return input.split(" ");
    } catch (IOException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  private boolean isAppropriate(String[] inputs) {
    if (inputs.length != 2) {
      return false;
    }
    String input1 = inputs[0];
    String input2 = inputs[1];
    int input1Int = Integer.parseInt(input1);
    int input2Int = Integer.parseInt(input2);

    if (input1Int < 6 || input1Int > 15 || input2Int < 6 || input2Int > 15) {
      consoleView.viewErrorMessage("board_size");
      return false;
    }
    return true;
  }

  private Map<ShipType, Integer> getShips(BufferedReader inputReader) {
    Map<ShipType, Integer> shipValues = new HashMap<>();
    String shipsInput = "";
    boolean isValid = false;

    while (!isValid) {
      try {
        consoleView.promptForShips();
        shipsInput = inputReader.readLine();
      } catch (IOException e) {
        e.printStackTrace();
      }
      if (isValidFleetSize(shipsInput)) {
        isValid = true;
      } else {
        consoleView.viewErrorMessage("fleet_size");
      }
    }
    String[] ships = shipsInput.split(" ");
    int carriers = Integer.parseInt(ships[0]);
    int battleships = Integer.parseInt(ships[1]);
    int destroyers = Integer.parseInt(ships[2]);
    int submarines = Integer.parseInt(ships[3]);

    shipValues.put(ShipType.CARRIER, carriers);
    shipValues.put(ShipType.BATTLESHIP, battleships);
    shipValues.put(ShipType.DESTROYER, destroyers);
    shipValues.put(ShipType.SUBMARINE, submarines);

    return shipValues;
  }

  private boolean isValidFleetSize(String shipsString) {
    String[] ships = shipsString.split(" ");
    if (ships.length != 4) {
      return false;
    }
    int total = 0;
    for (String ship : ships) {
      try {
        int numShips = Integer.parseInt(ship);
        if (numShips == 0) {
          return false;
        }
        total += numShips;
      } catch (NumberFormatException e) {
        return false;
      }
    }
    return !(total > 8 || total <= 0);
  }
}