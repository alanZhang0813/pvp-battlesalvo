package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.view.GameResult;

/**
 * Json Object that represents when the game ends
 *
 * @param gameResult The Enum GameResult, which is WIN, LOSE, or DRAW
 * @param reason The String reason for why the game ended, because the player won/lost or otherwise
 */
public record EndGameJson(
    @JsonProperty("result") GameResult gameResult,
    @JsonProperty("reason") String reason
) {
}
