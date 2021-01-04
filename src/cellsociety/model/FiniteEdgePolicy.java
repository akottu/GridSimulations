package cellsociety.model;

import cellsociety.enums.GridState;

public class FiniteEdgePolicy extends EdgePolicy {

  static final int INVALID_COORDINATE = -1;

  /**
   * Creates a FiniteEdgePolicy object that does not allow wrap-around
   *
   * @param numRows    the number of rows in the grid
   * @param numColumns the number of columns in the grid
   */
  public FiniteEdgePolicy(int numRows, int numColumns) {
    super(numRows, numColumns);
  }

  @Override
  CellModel handleEdges(int row, int column, GridModel simGrid) {
    return new CellModel(INVALID_COORDINATE, INVALID_COORDINATE, GridState.INVALID);
  }


}
