package cellsociety.model;

import cellsociety.enums.SegregationState;
import cellsociety.enums.State;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

public class SegregationModel extends SimulationModel {

  private final double threshold;

  private final List<Pair<Point, State>> satisfiedAgents = new ArrayList<>();
  private final List<Pair<Point, State>> dissatisfiedAgents = new ArrayList<>();

  /**
   * Creates a Model for Schellling's model of segregation
   *
   * @param allEntries representation of all of the cells' initial states
   * @param title      name of the particular simulation
   * @param hoodType   type of neighborhood to be used in the simulation
   * @param edgeType   type of edge to be used in the simulation
   */
  public SegregationModel(List<String[]> allEntries, String title, Neighborhood hoodType,
      EdgePolicy edgeType) {
    super(allEntries, title, hoodType, edgeType);
    threshold = Double.parseDouble(allEntries.get(0)[2]);
  }

  @Override
  public void nextStep() {
    satisfiedAgents.clear();
    dissatisfiedAgents.clear();
    super.nextStep();
    GridModel nextGrid = new GridModel(simGrid.NUMBER_OF_ROWS, simGrid.NUMBER_OF_COLUMNS);
    putSatisfiedAgents(nextGrid);
    moveDissatisfiedAgents(nextGrid);
    simGrid = nextGrid;
  }

  private void moveDissatisfiedAgents(GridModel nextGrid) {
    for (Pair<Point, State> agent : dissatisfiedAgents) {
      int oldRow = agent.getKey().x;
      int oldColumn = agent.getKey().y;
      State agentState = agent.getValue();
      Point randomOpenSpace = findRandomOpenSpace(nextGrid, oldRow, oldColumn);
      int newRow = randomOpenSpace.x;
      int newColumn = randomOpenSpace.y;
      nextGrid.addCellModel(new CellModel(oldRow, oldColumn, SegregationState.EMPTY));
      nextGrid.addCellModel(new CellModel(newRow, newColumn, agentState));
    }
  }

  private Point findRandomOpenSpace(GridModel nextGrid, int oldRow, int oldColumn) {
    boolean emptyFound = false;
    int newRow = -1;
    int newColumn = -1;
    while (!emptyFound) {
      newRow = (int) (nextGrid.NUMBER_OF_ROWS * Math.random());
      newColumn = (int) (nextGrid.NUMBER_OF_COLUMNS * Math.random());
      checkFreeLocation(nextGrid, newRow, newColumn, oldRow, oldColumn);
      if (checkFreeLocation(nextGrid, newRow, newColumn, oldRow, oldColumn)) {
        emptyFound = true;
      }
    }
    return new Point(newRow, newColumn);
  }

  private boolean checkFreeLocation(GridModel nextGrid, int row, int col, int oldRow, int oldCol) {
    return nextGrid.getCellState(row, col) == SegregationState.EMPTY && !(row == oldRow
        && col == oldCol);
  }

  private void putSatisfiedAgents(GridModel nextGrid) {
    for (Pair<Point, State> agent : satisfiedAgents) {
      int row = agent.getKey().x;
      int column = agent.getKey().y;
      State state = agent.getValue();
      nextGrid.addCellModel(new CellModel(row, column, state));
    }
  }

  private State getOppositeState(State inState) {
    if (inState == SegregationState.O) {
      return SegregationState.X;
    }
    return SegregationState.O;
  }

  @Override
  protected void setNextState(GridModel nextGrid, int row, int column) {
    State agentState = getCellState(row, column);
    int sameNeighbors = hoodType.countNeighborsOfType(row, column, agentState, simGrid);
    int diffNeighbors = hoodType
        .countNeighborsOfType(row, column, getOppositeState(agentState), simGrid);
    int totalNeighbors = sameNeighbors + diffNeighbors;
    if (totalNeighbors != 0 && (double) sameNeighbors / (double) totalNeighbors >= threshold
        || getCellState(row, column) == SegregationState.EMPTY) {
      satisfiedAgents.add(new Pair<>(new Point(row, column), agentState));
    } else {
      dissatisfiedAgents.add(new Pair<>(new Point(row, column), agentState));
    }
  }

  /**
   * Provides a list of enums associated with this type of model
   *
   * @return list of each SegregationState enum
   */
  @Override
  public State[] getEnums() {
    return SegregationState.values();
  }
}