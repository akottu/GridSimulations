package cellsociety.enums;

/**
 * Enum for a general Grid
 */
public enum GridState implements State {
  INVALID;

  private static final GridState[] allValues = values();

  /**
   * Converts from int to Enum
   *
   * @param input the index of the Enum
   * @return the GridState itself based on the input
   */
  public static GridState fromInt(int input) {
    return allValues[input + 1];
  }
}
