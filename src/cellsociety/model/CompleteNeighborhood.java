package cellsociety.model;

import java.util.ArrayList;
import java.util.List;

public class CompleteNeighborhood extends Neighborhood {

  /**
   * Creates a CompleteNeighborhood that finds neighbors in all 8 directions
   *
   * @param edge the edgeType for this simulation
   */
  public CompleteNeighborhood(EdgePolicy edge) {
    super(edge);
  }

  List<CellModel> getNeighbors(int row, int column, GridModel simGrid) {
    List<CellModel> listOfNeighbors = new ArrayList<>();
    for (int neighborRow = row - 1; neighborRow <= row + 1; neighborRow++) {
      for (int neighborColumn = column - 1; neighborColumn <= column + 1; neighborColumn++) {
        addIfValid(listOfNeighbors, edge.validateCoordinates(neighborRow, neighborColumn, simGrid),
            row, column);
      }
    }
    return listOfNeighbors;
  }
}
