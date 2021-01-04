package cellsociety.exception;

/**
 * NewFileNotCorrectTypeException is thrown when a file is uploaded that is not a .sim file
 */
public class NewFileNotCorrectTypeException extends RuntimeException {

  /**
   * creates exception with an error message parameter
   *
   * @param errorMessage message to display back to the user
   */
  public NewFileNotCorrectTypeException(String errorMessage) {
    super(errorMessage);
  }

}
