package cellsociety.model;

import java.util.ArrayList;
import java.util.List;

public class CornerNeighborhood extends Neighborhood {

  /**
   * Creates a CornerNeighborhood object that finds neighbors in the diagonal directions
   *
   * @param edge the edgeType for this simulation
   */
  public CornerNeighborhood(EdgePolicy edge) {
    super(edge);
  }

  List<CellModel> getNeighbors(int row, int column, GridModel simGrid) {
    List<CellModel> listOfCorners = new ArrayList<>();
    for (int neighborRow = row - 1; neighborRow <= row + 1; neighborRow += 2) {
      for (int neighborColumn = column - 1; neighborColumn <= column + 1; neighborColumn += 2) {
        addIfValid(listOfCorners, edge.validateCoordinates(neighborRow, neighborColumn, simGrid),
            row, column);
      }
    }
    return listOfCorners;
  }
}
