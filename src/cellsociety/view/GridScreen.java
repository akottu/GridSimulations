package cellsociety.view;

import cellsociety.exception.InvalidSimGridDataException;
import cellsociety.exception.InvalidSimTypeException;
import cellsociety.exception.SimConfigurationMissingException;
import cellsociety.model.SimulationModel;
import cellsociety.util.ResourceUtil;
import cellsociety.util.SimulationModelUtil;
import cellsociety.view.dialog.ColorSelectorDialog;
import cellsociety.view.dialog.DialogBox;
import cellsociety.view.dialog.ImageSelectorDialog;
import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Extends the SimulationScreen to implement the specific differences incorporated in the screen
 * that holds the Grid View of the simulation
 *
 * @author Hosam Tageldin
 */
public class GridScreen extends SimulationScreen {

  private Button myShowColorsButton;
  private Button myShowImagesButton;
  private boolean showImage;
  private GridVisualizer myGrid;

  public GridScreen(SimulationModel model) {
    super(model);
    myGrid = new GridVisualizer(model);
  }

  /**
   * Initializes and returns the header buttons that are associated with the GridScreen
   *
   * @return the Node containing the header buttons associated with the grid screen
   */
  @Override
  protected Node createHeaderButtons() {
    HBox headerButtons = new HBox();
    Button displayGraph = makeButton("CreateGraph", event -> displayGraph());
    displayGraph.setId("Graph");
    myShowColorsButton = makeButton("ShowColorsCommand", event -> toggleCellDisplay());
    myShowImagesButton = makeButton("ShowImagesCommand", event -> toggleCellDisplay());
    Button pickColors = makeButton("ChooseColors", event -> selectCustomColors());
    Button pickImages = makeButton("ChooseImages", event -> selectCustomImages());
    enableHeaderButtons();
    MenuButton changeStyle = new MenuButton(ResourceUtil.getResourceValue("StyleChange"));
    populateStyleDropDown(changeStyle);
    headerButtons.getChildren()
        .addAll(displayGraph, myShowColorsButton, myShowImagesButton, pickColors, pickImages,
            changeStyle);
    return headerButtons;
  }


  private void displayGraph() {
    SimulationModelUtil.newGraphSimulation(myModel, new Stage());
  }

  /**
   * Updates the GridVisualizer
   */
  @Override
  protected void updateView() {
    myModel.nextStep();
    myGrid.updateView();
  }

  private void toggleCellDisplay() {
    myGrid.toggleCellDisplay();
    showImage = !showImage;
    myGrid.updateView();
    enableHeaderButtons();
  }

  private void selectCustomColors() {
    pause();
    DialogBox colorSelector = new ColorSelectorDialog(myModel);
    colorSelector.createNewScreen(currentStyleSheet);
    resume();
  }

  private void selectCustomImages() {
    pause();
    DialogBox imageSelector = new ImageSelectorDialog(myModel);
    imageSelector.createNewScreen(currentStyleSheet);
    resume();
  }

  private void enableHeaderButtons() {
    myShowColorsButton.setDisable(!showImage);
    myShowImagesButton.setDisable(showImage);
  }

  /**
   * Creates the current initial state of the Grid view
   *
   * @return the Node containing the content created in the GridVisualizer
   */
  @Override
  protected Node createContent() {
    showImage = false;
    return myGrid.createContent();
  }

  /**
   * Changes the simulation to a new Grid Screen of the user selected data file
   *
   * @param pathName represents property file of new simulation
   */
  @Override
  public void changeSimulation(String pathName) {
    try {
      myModel = SimulationModelUtil.createModelFromPath(pathName);
      myGrid = new GridVisualizer(myModel);
      createRoot();
    } catch (InvalidSimGridDataException | SimConfigurationMissingException | InvalidSimTypeException | IOException exception) {
      createExceptionRoot(exception);
    }
    resume();
  }


}
