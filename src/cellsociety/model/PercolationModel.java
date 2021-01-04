package cellsociety.model;

import cellsociety.enums.PercolationState;
import cellsociety.enums.State;
import java.util.List;

public class PercolationModel extends SimulationModel {

  /**
   * Creates a Model for Percolation simulation
   *
   * @param allEntries representation of all of the cells' initial states
   * @param title      name of the particular simulation
   * @param hoodType   type of neighborhood to be used in the simulation
   * @param edgeType   type of edge to be used in the simulation
   */
  public PercolationModel(List<String[]> allEntries, String title, Neighborhood hoodType,
      EdgePolicy edgeType) {
    super(allEntries, title, hoodType, edgeType);
  }

  @Override
  protected void setNextState(GridModel nextGrid, int row, int column) {
    keepWalls(nextGrid, row, column);
    keepFullCells(nextGrid, row, column);
    doPercolation(nextGrid, row, column);
    fillTopCells(nextGrid, row, column);
  }

  private void fillTopCells(GridModel nextGrid, int row, int column) {
    if (simGrid.getCellState(row, column) == PercolationState.EMPTY && row == 0) {
      nextGrid.addCellModel(new CellModel(row, column, PercolationState.FULL));
    }
  }

  private void doPercolation(GridModel nextGrid, int row, int column) {
    if (simGrid.getCellState(row, column) == PercolationState.EMPTY) {
      if (checkNeighborsFilled(row, column)) {
        nextGrid.addCellModel(new CellModel(row, column, PercolationState.FULL));
      } else {
        nextGrid.addCellModel(new CellModel(row, column, PercolationState.EMPTY));
      }
    }
  }

  private void keepFullCells(GridModel nextGrid, int row, int column) {
    if (simGrid.getCellState(row, column) == PercolationState.FULL) {
      nextGrid.addCellModel(new CellModel(row, column, PercolationState.FULL));
    }
  }

  private void keepWalls(GridModel nextGrid, int row, int column) {
    if (simGrid.getCellState(row, column) == PercolationState.WALL) {
      nextGrid.addCellModel(new CellModel(row, column, PercolationState.WALL));
    }
  }

  private boolean checkNeighborsFilled(int row, int column) {
    List<CellModel> adjacentNeighbors = hoodType.getNeighbors(row, column, simGrid);
    for (CellModel neighbor : adjacentNeighbors) {
      if (neighbor.getState() == PercolationState.FULL) {
        return true;
      }
    }
    return false;
  }

  /**
   * Provides a list of enums associated with this type of model
   *
   * @return list of each PercolationState enum
   */
  @Override
  public State[] getEnums() {
    return PercolationState.values();
  }

}
