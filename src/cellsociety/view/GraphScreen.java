package cellsociety.view;

import cellsociety.exception.InvalidSimGridDataException;
import cellsociety.exception.InvalidSimTypeException;
import cellsociety.exception.SimConfigurationMissingException;
import cellsociety.model.SimulationModel;
import cellsociety.util.ResourceUtil;
import cellsociety.util.SimulationModelUtil;
import java.io.IOException;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Extends the SimulationScreen to implement the specific differences incorporated in the screen
 * that holds the Graph View of the simulation
 *
 * @author Hosam Tageldin
 */
public class GraphScreen extends SimulationScreen {

  private static final String CREATE_GRID = "CreateGrid";
  private static final String STYLE_CHANGE = "StyleChange";
  private GraphVisualizer myGraph;

  public GraphScreen(SimulationModel model) {
    super(model);
    myGraph = new GraphVisualizer(model);
  }

  /**
   * Initializes the buttons in the header of the GraphScreen
   *
   * @return the Node containing the specific header buttons found in the GraphScreen
   */
  @Override
  protected Node createHeaderButtons() {
    HBox headerButtons = new HBox();
    Button displayGrid = makeButton(CREATE_GRID, event -> displayGrid());
    displayGrid.setId("Grid");
    MenuButton changeStyle = new MenuButton(ResourceUtil.getResourceValue(STYLE_CHANGE));
    populateStyleDropDown(changeStyle);
    headerButtons.getChildren().addAll(displayGrid, changeStyle);
    return headerButtons;
  }

  private void displayGrid() {
    SimulationModelUtil.newGridSimulation(myModel, new Stage());
  }

  /**
   * Updates the view of the graph
   */
  @Override
  protected void updateView() {
    myGraph.updateView();
  }

  /**
   * Creates the content of the GraphVisualizer and returns it to be added into the scene
   *
   * @return the Node containing the visual of the graph
   */
  @Override
  protected Node createContent() {
    return myGraph.createContent();
  }

  /**
   * Changes the simulation to the data file that the user selects
   *
   * @param pathName represents property file of new simulation
   */
  @Override
  public void changeSimulation(String pathName) {
    try {
      myModel = SimulationModelUtil.createModelFromPath(pathName);
      myGraph = new GraphVisualizer(myModel);
      createRoot();
    } catch (InvalidSimGridDataException | SimConfigurationMissingException | InvalidSimTypeException | IOException exception) {
      createExceptionRoot(exception);
    }
    resume();
  }

  /**
   * For testing purposes, returns the data of the graph for all the data points leading up to the
   * specific state of the simulation
   *
   * @return
   */
  public List<Series<Number, Number>> getGraphData() {
    return myGraph.getData();
  }


}
