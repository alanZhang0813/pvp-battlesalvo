package cs3500.pa03.view;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enum to represent each possible GameResult
 */
public enum GameResult {
  @JsonProperty("WIN") WIN, @JsonProperty("DRAW") DRAW, @JsonProperty("LOSE") LOSE;
}
