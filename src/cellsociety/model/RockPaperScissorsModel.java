package cellsociety.model;

import cellsociety.enums.RockPaperScissorsState;
import cellsociety.enums.State;
import cellsociety.util.SimulationModelUtil;
import java.util.List;


public class RockPaperScissorsModel extends SimulationModel {

  private final int threshold;

  /**
   * Creates a Model for Rock Paper Scissors bacteria simulation
   *
   * @param allEntries representation of all of the cells' initial states
   * @param title      name of the particular simulation
   * @param hoodType   type of neighborhood to be used in the simulation
   * @param edgeType   type of edge to be used in the simulation
   */
  public RockPaperScissorsModel(List<String[]> allEntries, String title, Neighborhood hoodType,
      EdgePolicy edgeType) {
    super(allEntries, title, hoodType, edgeType);
    threshold = Integer.parseInt(allEntries.get(0)[2]);
  }

  @Override
  protected void setNextState(GridModel nextGrid, int row, int column) {
    for (State type : getEnums()) {
      if (hoodType.countNeighborsOfType(row, column, type, simGrid) > threshold
          && SimulationModelUtil.determineNextState(simGrid.getCellState(row, column)) == type) {
        nextGrid.addCellModel(new CellModel(row, column, type));
        return;
      }
    }
    nextGrid.addCellModel(new CellModel(row, column, getCellState(row, column)));
  }

  /**
   * Provides a list of enums associated with this type of model
   *
   * @return list of each RockPaperScissorsState enum
   */
  @Override
  public State[] getEnums() {
    return RockPaperScissorsState.values();
  }
}
