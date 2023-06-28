package cs3500.pa03.view;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enum used for the Direction a ship is facing
 */
public enum Direction {
  @JsonProperty("HORIZONTAL") HORIZONTAL, @JsonProperty("VERTICAL") VERTICAL
}
