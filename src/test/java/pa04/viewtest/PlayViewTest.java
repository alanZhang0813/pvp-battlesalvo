package pa04.viewtest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.Board;
import cs3500.pa03.view.PlayView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class to test the PlayView class
 */
public class PlayViewTest {
  private Board aiBoard;
  private Board playerBoard;
  private StringBuilder sb;
  private PlayView playView;

  @BeforeEach
  void setup() {
    aiBoard = new Board();
    playerBoard = new Board();
    sb = new StringBuilder();
    playView = new PlayView(aiBoard, playerBoard, sb);
  }

  @Test
  void testViewBoards() {
    assertEquals("", sb.toString());
    playView.viewBoards();
    assertEquals("Your BattleSalvo board:\n\n\nAI BattleSalvo board:\n", sb.toString());
  }

  @Test
  void testViewErrorMessageInputLength() {
    playView.viewErrorMessage("input_length");
    assertEquals("That is an invalid number of inputs!\n"
        + "*-------------------------------------------------*\n", sb.toString());
  }

  @Test
  void testViewErrorMessageBoardSize() {
    playView.viewErrorMessage("board_size");
    assertEquals("That is an invalid value for your board size!\n"
        + "Please enter between 6 and 15 inclusive for row and column size\n"
        + "*-------------------------------------------------*\n", sb.toString());
  }

  @Test
  void testViewErrorMessageFleetSize() {
    playView.viewErrorMessage("fleet_size");
    assertEquals("Uh Oh! You've entered invalid fleet sizes.\n"
        + "*-------------------------------------------------*\n", sb.toString());
  }

  @Test
  void testViewErrorMessageShotsLength() {
    playView.viewErrorMessage("shots_length");
    assertEquals("Uh oh! You've entered a wrong length of shots!\n"
        + "Please enter shots again, up to the number of ships you have\n"
        + "*-------------------------------------------------*\n", sb.toString());
  }

  @Test
  void testViewErrorMessageElse() {
    playView.viewErrorMessage("");
    assertEquals("The error message type was incorrect!\n", sb.toString());
  }

  @Test
  void testPromptForShots() {
    playView.promptForShots();
    assertEquals("\nEnter your shots in the form of (x,y), "
        + "up to the number of ships you have\n", sb.toString());
  }

  @Test
  void testPromptForShips() {
    playView.promptForShips();
    assertEquals("Please enter your fleet in the order "
        + "[Carrier, Battleship, Destroyer, Submarine].\n"
        + "Remember, your fleet may not exceed size 8 and there must be at least one ship\n"
        + "*-------------------------------------------------*\n", sb.toString());
  }
}
