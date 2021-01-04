package cellsociety.model;

public class ToroidEdgePolicy extends EdgePolicy {

  /**
   * Creates a ToroidEdgePolicyObject that allows wrap-around on rows and columns Creates a
   * ToroidEdgePolicyObject that allows wrap-around on rows and columns
   *
   * @param numRows
   * @param numCols
   */
  public ToroidEdgePolicy(int numRows, int numCols) {
    super(numRows, numCols);
  }

  @Override
  CellModel handleEdges(int row, int column, GridModel simGrid) {
    row = handleRowEdges(row);
    column = handleColumnEdges(column);
    return simGrid.getCell(row, column);
  }

  private int handleColumnEdges(int column) {
    if (column < 0) {
      return numColumns - 1;
    } else if (column >= numColumns) {
      return 0;
    }
    return column;
  }

  private int handleRowEdges(int row) {
    if (row < 0) {
      return numRows - 1;
    } else if (row >= numRows) {
      return 0;
    }
    return row;
  }

}
