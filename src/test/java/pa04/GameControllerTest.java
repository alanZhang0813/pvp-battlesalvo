package pa04;

import static org.junit.jupiter.api.Assertions.*;

import cs3500.pa03.controller.GameController;
import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Board;
import cs3500.pa03.model.ManualPlayer;
import cs3500.pa03.model.Player;
import cs3500.pa03.view.PlayView;
import cs3500.pa03.view.View;
import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for the GameController class
 */
public class GameControllerTest {
  private Player manualPlayer;
  private Player aiPlayer;
  private Board consoleBoard;
  private Board aiBoard;
  private View consoleView;
  private Appendable output;
  private ByteArrayInputStream inputStream;
  private GameController gameController;

  @BeforeEach
  void setup() {
    output = new StringBuilder();
    consoleBoard = new Board();
    aiBoard = new Board();
    manualPlayer = new ManualPlayer(consoleBoard);
    aiPlayer = new AiPlayer(aiBoard);
    consoleView = new PlayView(aiBoard, consoleBoard, output);
    gameController = new GameController(manualPlayer, aiPlayer, consoleBoard, aiBoard,
        consoleView);
  }

  @Test
  void testRun() {
    String input = "10 10\n1 1 1 1\n(1,0) (2,0) (3,0) (4,0)";
    inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);
    try {
      gameController.run();
    } catch (NullPointerException e) {
      assertEquals("Cannot invoke \"String.split(String)\" because \"shots\" is null",
          e.getMessage());
    }
    assertTrue(output.toString().contains("Welcome to my BattleSalvo game for OOD!\n"
        + "Please enter a row and a column value to represent your board size:\n"
        + "*-------------------------------------------------*\n"
        + "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n"
        + "Remember, your fleet may not exceed size 8 and there must be at least one ship\n"
        + "*-------------------------------------------------*\n"));
  }

  @Test
  void testRunInvalidShipDimensions() {
    String input = "10 10\n" + "2 1 1 0\n";
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    try {
      gameController.run();
    } catch (NullPointerException e) {
      assertEquals("Cannot invoke \"String.split(String)\" because "
          + "\"shipsString\" is null", e.getMessage());
    }
    assertEquals("Welcome to my BattleSalvo game for OOD!\n"
        + "Please enter a row and a column value to represent your board size:\n"
        + "*-------------------------------------------------*\n"
        + "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n"
        + "Remember, your fleet may not exceed size 8 and there must be at least one ship\n"
        + "*-------------------------------------------------*\n"
        + "Uh Oh! You've entered invalid fleet sizes.\n"
        + "*-------------------------------------------------*\n"
        + "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n"
        + "Remember, your fleet may not exceed size 8 and there must be at least one ship\n"
        + "*-------------------------------------------------*\n", output.toString());
  }

  @Test
  void testRunInvalidDimensions() {
    String input = "10\n10";
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    try {
      gameController.run();
    } catch (NullPointerException e) {
      assertEquals("Cannot invoke \"String.split(String)\" because "
          + "\"input\" is null", e.getMessage());
    }
    assertEquals("Welcome to my BattleSalvo game for OOD!\n"
        + "Please enter a row and a column value to represent your board size:\n"
        + "*-------------------------------------------------*\n"
        + "That is an invalid number of inputs!\n"
        + "*-------------------------------------------------*\n"
        + "That is an invalid number of inputs!\n"
        + "*-------------------------------------------------*\n", output.toString());
  }
}