package cellsociety.model;

import cellsociety.enums.GridState;
import cellsociety.enums.State;
import java.util.ArrayList;
import java.util.List;

public abstract class Neighborhood {

  EdgePolicy edge;

  Neighborhood(EdgePolicy inEdge) {
    edge = inEdge;
  }

  abstract List<CellModel> getNeighbors(int row, int column, GridModel simGrid);

  void addIfValid(List<CellModel> listOfCorners, CellModel possibleNeighbor, int row, int column) {
    if (possibleNeighbor.getState() != GridState.INVALID && !(possibleNeighbor.getRowNumber() == row
        && possibleNeighbor.getColumnNumber() == column)) {
      listOfCorners.add(possibleNeighbor);
    }
  }

  List<CellModel> getNeighborsOfType(int row, int column, State type, GridModel simGrid) {
    List<CellModel> listOfNeighbors = getNeighbors(row, column, simGrid);
    List<CellModel> neighborsOfType = new ArrayList<>();
    for (CellModel neighborCell : listOfNeighbors) {
      if (neighborCell.getState() == type) {
        neighborsOfType.add(neighborCell);
      }
    }
    return neighborsOfType;
  }

  int countNeighborsOfType(int row, int column, State type, GridModel simGrid) {
    List<CellModel> neighborsOfType = getNeighborsOfType(row, column, type, simGrid);
    return neighborsOfType.size();
  }
}
