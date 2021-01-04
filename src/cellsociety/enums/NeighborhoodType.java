package cellsociety.enums;

/**
 * Enum for Edge Policy
 */
public enum NeighborhoodType {
  CARDINAL,
  CORNER,
  COMPLETE;

  /**
   * checks to see if input is a valid edge policy
   *
   * @param modelName the neighborhood
   * @return boolean based on if the policy is a valid one based on if it is in the values() set
   */
  public static boolean isValidModel(String modelName) {
    for (NeighborhoodType neighborhoodType : values()) {
      if (neighborhoodType.toString().equalsIgnoreCase(modelName)) {
        return true;
      }
    }
    return false;
  }
}