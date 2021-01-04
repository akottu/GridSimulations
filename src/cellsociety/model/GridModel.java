package cellsociety.model;

import cellsociety.enums.GridState;
import cellsociety.enums.State;
import java.util.HashMap;
import java.util.Map;

/**
 * Holds the data of the simulation grid
 */
class GridModel {

  private static final State INVALID_KEY = GridState.INVALID;
  final Map<String, CellModel> CELL_MODELS;
  final int NUMBER_OF_ROWS;
  final int NUMBER_OF_COLUMNS;

  GridModel(int inNumberOfRows, int inNumberOfColumns) {
    CELL_MODELS = new HashMap<>();
    NUMBER_OF_ROWS = inNumberOfRows;
    NUMBER_OF_COLUMNS = inNumberOfColumns;
  }

  void addCellModel(CellModel inCell) {
    String cellModelKey = inCell.getRowNumber() + "." + inCell.getColumnNumber();
    CELL_MODELS.put(cellModelKey, inCell);
  }

  State getCellState(int rowNumber, int columnNumber) {
    return getCell(rowNumber, columnNumber).getState();
  }

  CellModel getCell(int rowNumber, int columnNumber) {
    if (CELL_MODELS.containsKey(rowNumber + "." + columnNumber)) {
      return CELL_MODELS.get(rowNumber + "." + columnNumber);
    }
    return new CellModel(rowNumber, columnNumber, INVALID_KEY);
  }
}