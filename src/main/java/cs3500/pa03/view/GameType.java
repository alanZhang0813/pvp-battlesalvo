package cs3500.pa03.view;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Enum GameType that is used to indicate which kind of game this is
 */
public enum GameType {
  @JsonProperty("SINGLE") SINGLE, @JsonProperty("MULTI") MULTI
}
