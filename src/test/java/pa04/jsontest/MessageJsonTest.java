package pa04.jsontest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.MessageJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class to test the MessageJson record
 */
public class MessageJsonTest {
  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("void");
  MessageJson messageJson;

  @BeforeEach
  void setup() {
    messageJson = new MessageJson("messageJson", VOID_RESPONSE);
  }

  @Test
  void testMessageJson() {
    assertEquals("messageJson", messageJson.methodName());
    assertEquals(VOID_RESPONSE, messageJson.arguments());
  }
}
