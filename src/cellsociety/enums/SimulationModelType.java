package cellsociety.enums;

/**
 * Enum for SimulationModelType
 */
public enum SimulationModelType {
  CONWAY,
  FIRESPREAD,
  PERCOLATION,
  ROCKPAPERSCISSORS,
  SEGREGATION,
  WATOR;

  /**
   * checks to see if input is a simulation model type
   *
   * @param modelName the simulation type
   * @return boolean based on if the type is a valid one based on if it is in the values() set
   */
  public static boolean isValidModel(String modelName) {
    for (SimulationModelType modelType : values()) {
      if (modelType.toString().equalsIgnoreCase(modelName)) {
        return true;
      }
    }
    return false;
  }
}
