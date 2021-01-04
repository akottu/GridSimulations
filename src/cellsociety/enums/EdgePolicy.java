package cellsociety.enums;

/**
 * Enum for Edge Policy
 */
public enum EdgePolicy {
  FINITE,
  KLEIN,
  TOROID;

  /**
   * checks to see if input is a valid edge policy
   *
   * @param modelName the edge policy that is being tested
   * @return boolean based on if the policy is a valid one based on if it is in the values() set
   */
  public static boolean isValidModel(String modelName) {
    for (EdgePolicy edgeType : values()) {
      if (edgeType.toString().equalsIgnoreCase(modelName)) {
        return true;
      }
    }
    return false;
  }
}
