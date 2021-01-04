package cellsociety.enums;

/**
 * Enum for RockPaperScissors Simulation Type
 */
public enum RockPaperScissorsState implements State {
  ROCK,
  PAPER,
  SCISSORS;

  private static final RockPaperScissorsState[] allValues = values();

  /**
   * Converts from int to Enum
   *
   * @param input the index of the Enum
   * @return the RockPaperScissorsState itself based on the input
   */
  public static RockPaperScissorsState fromInt(Integer input) {
    return allValues[input];
  }

}

