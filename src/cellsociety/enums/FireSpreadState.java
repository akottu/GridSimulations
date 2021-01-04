package cellsociety.enums;

/**
 * Enum for FireSpread Simulation Type
 */
public enum FireSpreadState implements State {
  EMPTY,
  TREE,
  BURNING;

  private static final FireSpreadState[] allValues = values();

  /**
   * Converts from int to Enum
   *
   * @param input the index of the Enum
   * @return the FireSpreadState itself based on the input
   */
  public static FireSpreadState fromInt(Integer input) {
    return allValues[input];
  }
}
