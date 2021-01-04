package cellsociety.model;

public class KleinEdgePolicy extends EdgePolicy {

  /**
   * Creates a KleinEdgePolicy object that allows for wrap around on the columns, but flips the
   * rows
   *
   * @param numRows    the number of rows in the grid
   * @param numColumns the number of columns in the grid
   */
  public KleinEdgePolicy(int numRows, int numColumns) {
    super(numRows, numColumns);
  }

  @Override
  CellModel handleEdges(int row, int column, GridModel simGrid) {
    if (checkOffSide(column)) {
      row = numRows - 1 - row;
      column = handleColumnEdge(column);
    } else {
      row = handleRowEdge(row);
    }
    return simGrid.getCell(row, column);
  }

  private int handleColumnEdge(int column) {
    if (column < 0) {
      return numColumns - 1;
    } else if (column >= numColumns) {
      return 0;
    }
    return column;
  }

  private boolean checkOffSide(int column) {
    return (column < 0 || column >= numColumns);
  }

  private int handleRowEdge(int row) {
    if (row < 0) {
      row = numRows - 1;
    } else if (row >= numRows) {
      row = 0;
    }
    return row;
  }
}
