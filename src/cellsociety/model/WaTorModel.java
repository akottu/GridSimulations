package cellsociety.model;

import cellsociety.enums.State;
import cellsociety.enums.WaTorState;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class WaTorModel extends SimulationModel {

  private final int SHARK_STARTING_ENERGY;
  private final int FISH_REPRODUCTION_TIME;
  private final int SHARK_REPRODUCTION_TIME;
  private boolean sharkStep;

  private static final int SHARK_ENERGY_CONFIG_LOCATION = 2;
  private static final int SHARK_BREED_CONFIG_LOCATION = 3;
  private static final int FISH_BREED_CONFIG_LOCATION = 4;

  /**
   * Creates a Model for WaTor
   *
   * @param allEntries representation of all of the cells' initial states
   * @param title      name of the particular simulation
   * @param hoodType   type of neighborhood to be used in the simulation
   * @param edgeType   type of edge to be used in the simulation
   */
  public WaTorModel(List<String[]> allEntries, String title,
      Neighborhood hoodType, EdgePolicy edgeType) {
    super(allEntries, title, hoodType, edgeType);
    SHARK_STARTING_ENERGY = Integer.parseInt(allEntries.get(0)[SHARK_ENERGY_CONFIG_LOCATION]);
    SHARK_REPRODUCTION_TIME = Integer.parseInt(allEntries.get(0)[SHARK_BREED_CONFIG_LOCATION]);
    FISH_REPRODUCTION_TIME = Integer.parseInt(allEntries.get(0)[FISH_BREED_CONFIG_LOCATION]);
  }

  /**
   * Updates the grid by one step of the simulation
   */
  @Override
  public void nextStep() {
    sharkStep = true;
    super.nextStep();
    sharkStep = false;
    super.nextStep();
  }

  /**
   * Adds a cell to the simulation
   *
   * @param row    which row the cell is in
   * @param column which column the cell is in
   * @param state  what state the cell has
   */
  @Override
  public void addCellModel(int row, int column, State state) {
    if (state == WaTorState.SHARK) {
      simGrid.addCellModel(
          new WaTorCellModel(row, column, state, SHARK_STARTING_ENERGY, SHARK_REPRODUCTION_TIME));
    } else {
      simGrid.addCellModel(
          new WaTorCellModel(row, column, state, SHARK_STARTING_ENERGY, FISH_REPRODUCTION_TIME));
    }
  }

  @Override
  protected void setNextState(GridModel nextGrid, int row, int column) {
    State currentState = simGrid.getCellState(row, column);
    if (checkIfSharkGoes(sharkStep, currentState)) {
      doSharkTurn(nextGrid, row, column);
    } else if (checkIfFishGoes(sharkStep, currentState)) {
      doFishTurn(nextGrid, row, column);
    } else if (checkIfCellAddedYet(row, column, nextGrid)) {
      nextGrid.addCellModel(simGrid.getCell(row, column));
    }
  }

  private boolean checkIfSharkGoes(boolean sharkStep, State currentState) {
    return sharkStep && currentState == WaTorState.SHARK;
  }

  private boolean checkIfFishGoes(boolean sharkStep, State currentState) {
    return !sharkStep && currentState == WaTorState.FISH;
  }

  private boolean checkIfCellAddedYet(int row, int column, GridModel nextGrid) {
    return !nextGrid.CELL_MODELS.containsKey(row + "." + column);
  }

  private void doSharkTurn(GridModel nextGrid, int row, int column) {
    int currentEnergy = ((WaTorCellModel) simGrid.getCell(row, column)).getEnergyLeft();
    int turnsToBreed = ((WaTorCellModel) simGrid.getCell(row, column)).getTurnsToBreed();
    Point nextLocation = getNextSharkLocation(nextGrid, row, column);
    if (simGrid.getCellState(nextLocation.x, nextLocation.y) == WaTorState.FISH) {
      nextGrid
          .addCellModel(new WaTorCellModel(nextLocation.x, nextLocation.y, WaTorState.SHARK,
              SHARK_STARTING_ENERGY, turnsToBreed - 1));
      ((WaTorCellModel) simGrid.getCell(nextLocation.x, nextLocation.y)).beEaten();
    } else {
      nextGrid.addCellModel(
          new WaTorCellModel(nextLocation.x, nextLocation.y, WaTorState.SHARK,
              currentEnergy - 1,
              turnsToBreed - 1));
      checkDead(nextGrid, nextLocation.x, nextLocation.y);
    }
    breed(nextGrid, row, column, WaTorState.SHARK);
  }

  private void checkDead(GridModel nextGrid, int row, int column) {
    if (((WaTorCellModel) nextGrid.getCell(row, column)).getEnergyLeft() == 0) {
      nextGrid.addCellModel(
          new WaTorCellModel(row, column, WaTorState.WATER, SHARK_STARTING_ENERGY, 0));
    }
  }

  private Point getNextSharkLocation(GridModel nextGrid, int row, int column) {
    List<WaTorCellModel> fishLocations = getUneatenFishNeighbors(nextGrid, row, column);
    int numFishNeighbors = fishLocations.size();
    if (numFishNeighbors > 0) {
      WaTorCellModel newCell = fishLocations.get((int) (numFishNeighbors * Math.random()));
      return new Point(newCell.getRowNumber(), newCell.getColumnNumber());
    }
    List<CellModel> otherNeighbors = getOtherNeighbors(nextGrid, row, column);
    if (otherNeighbors.size() == 0) {
      return new Point(row, column);
    }
    CellModel newCell = otherNeighbors.get((int) (otherNeighbors.size() * Math.random()));
    return new Point(newCell.getRowNumber(), newCell.getColumnNumber());
  }

  private List<CellModel> getOtherNeighbors(GridModel nextGrid, int row, int column) {
    List<CellModel> otherNeighbors = hoodType.getNeighbors(row, column, simGrid);
    otherNeighbors
        .removeAll(hoodType.getNeighborsOfType(row, column, WaTorState.SHARK, nextGrid));
    otherNeighbors
        .removeAll(hoodType.getNeighborsOfType(row, column, WaTorState.SHARK, simGrid));
    otherNeighbors
        .removeAll(hoodType.getNeighborsOfType(row, column, WaTorState.FISH, simGrid));
    return otherNeighbors;
  }

  private List<WaTorCellModel> getUneatenFishNeighbors(GridModel nextGrid, int row, int column) {
    List<CellModel> fishNeighbors = hoodType
        .getNeighborsOfType(row, column, WaTorState.FISH, simGrid);
    fishNeighbors.retainAll(hoodType.getNeighbors(row, column, simGrid));
    List<WaTorCellModel> uneatenFishNeighbors = new ArrayList<>();
    for (CellModel adjacentFish : fishNeighbors) {
      if (nextGrid.getCellState(adjacentFish.getRowNumber(), adjacentFish.getColumnNumber())
          != WaTorState.SHARK) {
        uneatenFishNeighbors.add((WaTorCellModel) adjacentFish);
      }
    }
    return uneatenFishNeighbors;
  }


  private void breed(GridModel nextGrid, int row, int column, WaTorState type) {
    if (((WaTorCellModel) simGrid.getCell(row, column)).getTurnsToBreed() - 1 <= 0) {
      addToGrid(nextGrid, row, column, type);
    } else {
      addToGrid(nextGrid, row, column, WaTorState.WATER);
    }
  }


  private void addToGrid(GridModel grid, int row, int column, WaTorState state) {
    if (state == WaTorState.FISH) {
      grid.addCellModel(
          new WaTorCellModel(row, column, state, SHARK_STARTING_ENERGY, FISH_REPRODUCTION_TIME));
    } else {
      grid.addCellModel(
          new WaTorCellModel(row, column, state, SHARK_STARTING_ENERGY, SHARK_REPRODUCTION_TIME));
    }
  }

  private void doFishTurn(GridModel nextGrid, int row, int column) {
    if (nextGrid.getCell(row, column).getState() != WaTorState.SHARK) {
      List<CellModel> otherNeighbors = getOtherNeighbors(nextGrid, row, column);
      int turnsToBreed = ((WaTorCellModel) simGrid.getCell(row, column)).getTurnsToBreed() - 1;
      int newRow = row;
      int newColumn = column;
      if (otherNeighbors.size() != 0) {
        CellModel newCell = otherNeighbors.get((int) (otherNeighbors.size() * Math.random()));
        newRow = newCell.getRowNumber();
        newColumn = newCell.getColumnNumber();
      }
      nextGrid
          .addCellModel(
              new WaTorCellModel(newRow, newColumn, WaTorState.FISH, SHARK_STARTING_ENERGY,
                  turnsToBreed));
      breed(nextGrid, row, column, WaTorState.FISH);
    }
  }

  /**
   * Provides a list of enums associated with this type of model
   *
   * @return list of each WaTorState enum
   */
  @Override
  public State[] getEnums() {
    return WaTorState.values();
  }
}