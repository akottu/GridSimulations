package cellsociety.enums;

/**
 * Enum for Conway Simulation Type
 */
public enum ConwayState implements State {
  DEAD,
  ALIVE;

  private static final ConwayState[] allValues = values();

  /**
   * Converts from int to Enum
   *
   * @param input the index of the Enum
   * @return the ConwayState itself based on the input
   */
  public static ConwayState fromInt(Integer input) {
    return allValues[input];
  }
}
