package cs3500.pa03.model;

import cs3500.pa03.view.Coord;
import cs3500.pa03.view.GameResult;
import cs3500.pa03.view.Ship;
import cs3500.pa03.view.ShipType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Abstract class for efficient management of the setup method
 */
public abstract class AbstractPlayer implements Player {
  public abstract String name();

  /**
   * The method that sets up the board as well as gives each ship random placement
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return List of Ships
   */
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    List<Ship> resultShips = new ArrayList<>();
    for (int i = 0; i < specifications.get(ShipType.CARRIER); i++) {
      resultShips.add(new Ship(ShipType.CARRIER));
    }
    for (int i = 0; i < specifications.get(ShipType.BATTLESHIP); i++) {
      resultShips.add(new Ship(ShipType.BATTLESHIP));
    }
    for (int i = 0; i < specifications.get(ShipType.DESTROYER); i++) {
      resultShips.add(new Ship(ShipType.DESTROYER));
    }
    for (int i = 0; i < specifications.get(ShipType.SUBMARINE); i++) {
      resultShips.add(new Ship(ShipType.SUBMARINE));
    }
    return resultShips;
  }

  public abstract List<Coord> takeShots();

  public abstract List<Coord> reportDamage(List<Coord> opponentShotsOnBoard);

  public abstract void successfulHits(List<Coord> shotsThatHitOpponentShips);

  /**
   * Method that delegates when the game ends, and for what reason
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  public void endGame(GameResult result, String reason) {
    if (result.equals(GameResult.DRAW)) {
      System.out.println("The game was a draw, because " + reason);
    } else if (result.equals(GameResult.LOSE)) {
      System.out.println("You lost the game, because " + reason);
    } else if (result.equals(GameResult.WIN)) {
      System.out.println("You won the game, because " + reason);
    }
  }
}
