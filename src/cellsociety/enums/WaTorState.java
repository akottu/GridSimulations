package cellsociety.enums;

/**
 * Enum for WaTor Simulation Type
 */
public enum WaTorState implements State {
  WATER,
  FISH,
  SHARK;

  private static final WaTorState[] allValues = values();

  /**
   * Converts from int to Enum
   *
   * @param input the index of the Enum
   * @return the WaTorState itself based on the input
   */
  public static WaTorState fromInt(Integer input) {
    return allValues[input];
  }
}
