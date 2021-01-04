package cellsociety.view;

import cellsociety.model.SimulationModel;
import javafx.scene.layout.Pane;

/**
 * This abstract class controls the simulation display portion of the screen, which is usually
 * displayed in the middle. It holds the common instance variables for the subclasses and the
 * abstract classes that the subclasses must implement.
 *
 * @author Hosam Tageldin
 */
public abstract class SimulationVisualizer {

  protected SimulationModel myModel;
  protected int numberOfCellsInRow;
  protected int numberOfCellsInColumn;


  public SimulationVisualizer(SimulationModel model) {
    myModel = model;
    numberOfCellsInRow = myModel.getNumberOfColumns();
    numberOfCellsInColumn = myModel.getNumberOfRows();
  }

  /**
   * This will be called once to initialize the components that are included in the visualizer
   * portion of the screen.
   *
   * @return the Pane containing the content for the visual of the simulation
   */
  protected abstract Pane createContent();

  /**
   * For every step of the simulation, the updateView will update the visualizer to match the
   * current state of the Model. The subclasses will implement this in the necessary steps to update
   * its view.
   */
  protected abstract void updateView();

}

