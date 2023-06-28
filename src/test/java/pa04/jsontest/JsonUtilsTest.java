package pa04.jsontest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class to test the JsonUtils class
 */
public class JsonUtilsTest {
  JsonUtils jsonUtils;
  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("void");

  @BeforeEach
  void setup() {
    jsonUtils = new JsonUtils();
  }

  @Test
  public void testSerializeRecord() {
    Record record = new MessageJson("John", VOID_RESPONSE);
    JsonNode jsonNode = JsonUtils.serializeRecord(record);
    assertNotNull(jsonNode);

    record = new MessageJson(null, null);
    try {
      JsonUtils.serializeRecord(record);
    } catch (IllegalArgumentException e) {
      assertEquals("", e.getMessage());
    }
  }
}
