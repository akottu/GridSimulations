package cellsociety.model;

import cellsociety.enums.State;

class CellModel {

  private final State state;
  private final int rowNumber;
  private final int columnNumber;

  CellModel(int inRowNumber, int inColumnNumber, State inState) {
    rowNumber = inRowNumber;
    columnNumber = inColumnNumber;
    state = inState;
  }

  State getState() {
    return state;
  }

  int getRowNumber() {
    return rowNumber;
  }

  int getColumnNumber() {
    return columnNumber;
  }


}
