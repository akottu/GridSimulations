package cellsociety.model;

public abstract class EdgePolicy {

  int numRows;
  int numColumns;

  EdgePolicy(int inNumRows, int inNumColumns) {
    numRows = inNumRows;
    numColumns = inNumColumns;
  }

  CellModel validateCoordinates(int row, int column, GridModel simGrid) {
    if (validateRows(row) && validateColumns(column)) {
      return simGrid.getCell(row, column);
    }
    return handleEdges(row, column, simGrid);
  }

  private boolean validateRows(int row) {
    return row >= 0 && row < numRows;
  }

  private boolean validateColumns(int column) {
    return column >= 0 && column < numColumns;
  }

  abstract CellModel handleEdges(int row, int column, GridModel simGrid);
}
