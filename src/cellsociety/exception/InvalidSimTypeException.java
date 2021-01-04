package cellsociety.exception;

/**
 * InvalidSimTypeException is thrown when required simulation type for simulation is non-existent
 */
public class InvalidSimTypeException extends RuntimeException {

  /**
   * creates exception with an error message parameter
   *
   * @param errorMessage message to display back to the user
   */
  public InvalidSimTypeException(String errorMessage) {
    super(errorMessage);
  }

}
