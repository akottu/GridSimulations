package cellsociety.model;

import java.util.List;

public class CardinalNeighborhood extends Neighborhood {

  /**
   * Creates a CardinalNeighborhood object that finds neighbors in the cardinal directions
   *
   * @param edge the edgeType for this simulation
   */
  public CardinalNeighborhood(EdgePolicy edge) {
    super(edge);
  }

  List<CellModel> getNeighbors(int row, int column, GridModel simGrid) {
    CompleteNeighborhood complete = new CompleteNeighborhood(edge);
    List<CellModel> allNeighbors = complete.getNeighbors(row, column, simGrid);
    CornerNeighborhood corner = new CornerNeighborhood(edge);
    allNeighbors.removeAll(corner.getNeighbors(row, column, simGrid));
    return allNeighbors;
  }

}
