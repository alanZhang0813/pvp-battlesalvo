package cs3500.pa03.view;

import java.util.List;

/**
 * Interface for the View methods
 */
public interface View {
  void viewBoards();

  void viewWelcomeMessage();

  void viewErrorMessage(String type);

  void promptForShots();

  void promptForShips();
}
