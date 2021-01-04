package cellsociety.enums;

/**
 * Enum for Segregation Simulation Type
 */
public enum SegregationState implements State {
  X,
  O,
  EMPTY;

  private static final SegregationState[] allValues = values();

  /**
   * Converts from int to Enum
   *
   * @param input the index of the Enum
   * @return the SegregationState itself based on the input
   */
  public static SegregationState fromInt(Integer input) {
    return allValues[input];
  }

}
