package cellsociety.model;

import cellsociety.enums.ConwayState;
import cellsociety.enums.State;
import java.util.List;

public class ConwayModel extends SimulationModel {

  private static final int NUM_NEIGHBORS_TO_TURN_ALIVE = 3;
  private static final int NUM_NEIGHBORS_TO_STAY_ALIVE = 2;

  /**
   * Creates a Model for Conway's Game of Life
   *
   * @param allEntries representation of all of the cells' initial states
   * @param title      name of the particular simulation
   * @param hoodType   type of neighborhood to be used in the simulation
   * @param edgeType   type of edge to be used in the simulation
   */
  public ConwayModel(List<String[]> allEntries, String title,
      Neighborhood hoodType, EdgePolicy edgeType) {
    super(allEntries, title, hoodType, edgeType);
  }

  @Override
  protected void setNextState(GridModel nextGrid, int row, int column) {
    int numLiveNeighbors = hoodType
        .countNeighborsOfType(row, column, ConwayState.ALIVE, simGrid);
    if (numLiveNeighbors == NUM_NEIGHBORS_TO_TURN_ALIVE) {
      nextGrid.addCellModel(new CellModel(row, column, ConwayState.ALIVE));
    } else if (numLiveNeighbors == NUM_NEIGHBORS_TO_STAY_ALIVE
        && getCellState(row, column) == ConwayState.ALIVE) {
      nextGrid.addCellModel(new CellModel(row, column, ConwayState.ALIVE));
    } else {
      nextGrid.addCellModel(new CellModel(row, column, ConwayState.DEAD));
    }
  }

  /**
   * Provides a list of enums associated with this type of model
   *
   * @return list of each ConwayState enum
   */
  @Override
  public State[] getEnums() {
    return ConwayState.values();
  }
}