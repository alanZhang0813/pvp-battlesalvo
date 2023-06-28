package pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.Driver;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for the Driver of PA04
 */
public class Pa4DriverTest {
  private Driver pa4Driver;
  private String[] args;

  @BeforeEach
  void setup() {
    pa4Driver = new Driver();
    args = new String[] {};
    System.setIn(System.in);
    System.setOut(System.out);
  }

  @Test
  void testRunClient() {
    args = new String[] {"0.0.0.0", "35001"};
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    String expectedOutput = "";

    Driver.main(args);

    assertEquals(expectedOutput, outputStream.toString());
  }

  @Test
  void testMainPortException() {
    args = new String[] {"0.0.0.0.0", "not a port"};
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    String expectedOutput = "You must enter an integer for the port\n";

    Driver.main(args);

    assertEquals(expectedOutput, outputStream.toString());
  }

  @Test
  void testRunConsole() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    String expectedOutput = "Welcome to my BattleSalvo game for OOD!\n"
        + "Please enter a row and a column value to represent your board size:\n"
        + "*-------------------------------------------------*\n";

    try {
      Driver.main(args);
    } catch (NullPointerException e) {
      assertEquals("Cannot invoke \"String.split(String)\" because \"input\" is null",
          e.getMessage());
    }

    // Assert that the console output matches the expected output
    assertEquals(expectedOutput, outputStream.toString());
  }
}
