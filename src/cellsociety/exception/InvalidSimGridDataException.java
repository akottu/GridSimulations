package cellsociety.exception;

/**
 * SimConfigurationMissingException is thrown when required configuration for simulation is missing
 */
public class InvalidSimGridDataException extends RuntimeException {

  /**
   * creates exception with an error message parameter
   *
   * @param errorMessage message to display back to the user
   */
  public InvalidSimGridDataException(String errorMessage) {
    super(errorMessage);
  }

}
