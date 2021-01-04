package cellsociety.enums;

/**
 * Enum for Percolation Simulation Type
 */
public enum PercolationState implements State {
  WALL,
  EMPTY,
  FULL;

  private static final PercolationState[] allValues = values();

  /**
   * Converts from int to Enum
   *
   * @param input the index of the Enum
   * @return the PercolationState itself based on the input
   */
  public static PercolationState fromInt(Integer input) {
    return allValues[input];
  }

}
