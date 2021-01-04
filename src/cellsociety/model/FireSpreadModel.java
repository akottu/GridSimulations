package cellsociety.model;

import cellsociety.enums.FireSpreadState;
import cellsociety.enums.State;
import java.util.List;

public class FireSpreadModel extends SimulationModel {

  private final double regrowthProbability;
  private final double fireSpreadProbability;
  private final double spontaneousProbability;

  private static final int REGROWTH_CONFIG_LOCATION = 2;
  private static final int SPREAD_PROBABILITY_CONFIG_LOCATION = 3;
  private static final int SPONTANEOUS_CONFIG_LOCATION = 4;

  /**
   * Creates a model of fire spreading through a forest
   *
   * @param allEntries representation of all of the cells' initial states
   * @param title      name of the particular simulation
   * @param hoodType   type of neighborhood to be used in the simulation
   * @param edgeType   type of edge to be used in the simulation
   */
  public FireSpreadModel(List<String[]> allEntries, String title, Neighborhood hoodType,
      EdgePolicy edgeType) {
    super(allEntries, title, hoodType, edgeType);
    regrowthProbability = Double.parseDouble(allEntries.get(0)[REGROWTH_CONFIG_LOCATION]);
    fireSpreadProbability = Double
        .parseDouble(allEntries.get(0)[SPREAD_PROBABILITY_CONFIG_LOCATION]);
    spontaneousProbability = Double.parseDouble(allEntries.get(0)[SPONTANEOUS_CONFIG_LOCATION]);
  }


  @Override
  protected void setNextState(GridModel nextGrid, int row, int column) {
    burnOutFires(nextGrid, row, column);
    if (simGrid.getCellState(row, column) == FireSpreadState.TREE) {
      if (checkIfFireSpread(row, column)) {
        nextGrid.addCellModel(new CellModel(row, column, FireSpreadState.BURNING));
      } else {
        nextGrid.addCellModel(new CellModel(row, column, FireSpreadState.TREE));
      }
    } else if (Math.random() < regrowthProbability) {
      nextGrid.addCellModel(new CellModel(row, column, FireSpreadState.TREE));
    } else {
      nextGrid.addCellModel(new CellModel(row, column, FireSpreadState.EMPTY));
    }
  }

  private boolean checkIfFireSpread(int row, int column) {
    int numberBurningNeighbors = hoodType
        .countNeighborsOfType(row, column, FireSpreadState.BURNING, simGrid);
    double probabilityOfBurning = numberBurningNeighbors * fireSpreadProbability;
    return Math.random() < probabilityOfBurning || Math.random() < spontaneousProbability;
  }

  private void burnOutFires(GridModel nextGrid, int row, int column) {
    if (simGrid.getCellState(row, column) == FireSpreadState.BURNING) {
      nextGrid.addCellModel(new CellModel(row, column, FireSpreadState.EMPTY));
    }
  }

  /**
   * Provides a list of enums associated with this type of model
   *
   * @return list of each FireSpreadState enum
   */
  @Override
  public State[] getEnums() {
    return FireSpreadState.values();
  }
}