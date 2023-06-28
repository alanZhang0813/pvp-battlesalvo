package cs3500.pa04;

import cs3500.pa03.controller.Controller;
import cs3500.pa03.controller.GameController;
import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Board;
import cs3500.pa03.model.ManualPlayer;
import cs3500.pa03.model.Player;
import cs3500.pa03.view.PlayView;
import cs3500.pa03.view.View;
import cs3500.pa04.proxy.ProxyController;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 * This is the main driver of this project.
 */
public class Driver {
  private static void runClient(String host, int port) throws IOException {
    Socket socket = new Socket(host, port);

    Board aiBoard = new Board();

    Player aiPlayer = new AiPlayer(aiBoard);

    ProxyController proxyController = new ProxyController(socket, aiPlayer);
    proxyController.run();
  }

  private static void runConsole() {
    Board playerBoard = new Board();
    Board aiBoard = new Board();

    Player consolePlayer = new ManualPlayer(playerBoard);
    Player aiPlayer = new AiPlayer(aiBoard);

    Appendable output = new PrintStream(System.out);
    View consoleView = new PlayView(aiBoard, playerBoard, output);
    Controller gameController = new GameController(consolePlayer, aiPlayer, playerBoard, aiBoard,
        consoleView);
    gameController.run();
  }

  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      Driver.runConsole();
    } else if (args.length == 2) {
      String host = args[0];
      try {
        int port = Integer.parseInt(args[1]);
        try {
          Driver.runClient(host, port);
        } catch (IOException e) {
          e.printStackTrace();
        }
      } catch (NumberFormatException f) {
        System.out.println("You must enter an integer for the port");
      }
    }
  }
}