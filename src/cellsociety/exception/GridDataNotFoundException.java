package cellsociety.exception;

/**
 * GridDataNotFoundException is thrown when simumalation's CSV file is not found
 */
public class GridDataNotFoundException extends RuntimeException {

  /**
   * creates exception with an error message parameter
   *
   * @param errorMessage message to display back to the user
   */
  public GridDataNotFoundException(String errorMessage) {
    super(errorMessage);
  }

}
