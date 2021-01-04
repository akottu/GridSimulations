package cellsociety.model;

import cellsociety.enums.State;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This represents the heart of the simulation Loads and plays the simulation
 */
public abstract class SimulationModel implements Cloneable {

  GridModel simGrid;
  String title;
  private Map<String, String> colorMappings;
  private Map<String, String> imageMappings;
  Neighborhood hoodType;
  EdgePolicy edgeType;

  /**
   * Creates a Model for an unspecified simulation
   *
   * @param allEntries representation of all of the cells' initial states
   * @param title      name of the particular simulation
   * @param hoodType   type of neighborhood to be used in the simulation
   * @param edgeRules  type of edge to be used in the simulation
   */
  public SimulationModel(List<String[]> allEntries, String title, Neighborhood hoodType,
      EdgePolicy edgeRules) {
    simGrid = new GridModel(Integer.parseInt(allEntries.get(0)[0]),
        Integer.parseInt(allEntries.get(0)[1]));
    this.title = title;
    colorMappings = new HashMap<>();
    imageMappings = new HashMap<>();
    this.hoodType = hoodType;
    edgeType = edgeRules;
  }

  /**
   * Updates the grid by one step of the simulation
   */
  public void nextStep() {
    GridModel nextGrid = new GridModel(simGrid.NUMBER_OF_ROWS, simGrid.NUMBER_OF_COLUMNS);
    for (int column = 0; column < simGrid.NUMBER_OF_COLUMNS; column++) {
      for (int row = 0; row < simGrid.NUMBER_OF_ROWS; row++) {
        setNextState(nextGrid, row, column);
      }
    }
    simGrid = nextGrid;
  }

  protected abstract void setNextState(GridModel nextGrid, int row, int column);

  /**
   * Provides a list of enums associated with this type of model
   *
   * @return a list of enums for this model
   */
  public abstract State[] getEnums();

  /**
   * Returns the number of rows in the simulation
   *
   * @return integer representation of the row count
   */
  public int getNumberOfRows() {
    return simGrid.NUMBER_OF_ROWS;
  }

  /**
   * Returns the number of columns in the simulation
   *
   * @return integer representation of the column count
   */
  public int getNumberOfColumns() {
    return simGrid.NUMBER_OF_COLUMNS;
  }

  /**
   * Adds a cell to the simulation
   *
   * @param rowNumber    which row the cell is in
   * @param columnNumber which column the cell is in
   * @param state        what state the cell has
   */
  public void addCellModel(int rowNumber, int columnNumber, State state) {
    simGrid.addCellModel(new CellModel(rowNumber, columnNumber, state));
  }

  /**
   * Returns the state of the cell
   *
   * @param rowNumber    which row the cell is in
   * @param columnNumber which column the cell is in
   * @return the state associated with that cell
   */
  public State getCellState(int rowNumber, int columnNumber) {
    return simGrid.getCellState(rowNumber, columnNumber);
  }

  /**
   * Sets the colors associated with each state
   *
   * @param colorMap a map that associates states and colors
   */
  public void setColorMappings(Map<String, String> colorMap) {
    colorMappings = colorMap;
  }

  /**
   * Sets the images associated with each state
   *
   * @param imageMap a map that associates states and images
   */
  public void setImageMappings(Map<String, String> imageMap) {
    imageMappings = imageMap;
  }

  /**
   * Provides a mapping from states to display colors
   *
   * @return a map that associates states and colors
   */
  public Map<String, String> getColorMappings() {
    return colorMappings;
  }

  /**
   * Provides a mapping from states to display images
   *
   * @return a map that associates states and images
   */
  public Map<String, String> getImageMappings() {
    return imageMappings;
  }

  /**
   * Fetches the name of the simulation
   *
   * @return the name of the simulation
   */
  public String getTitle() {
    return title;
  }

  /**
   * Modifies the name of the simulation
   *
   * @param title the new name for the simulation
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Creates a copy of the simulation
   *
   * @return a new SimulationModel with the same data
   * @throws CloneNotSupportedException if the object can't be cloned
   */
  public SimulationModel copy() throws
      CloneNotSupportedException {
    SimulationModel clone = (SimulationModel) super.clone();
    clone.simGrid = new GridModel(getNumberOfRows(), getNumberOfColumns());
    return clone;
  }
}
