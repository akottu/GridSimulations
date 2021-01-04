package cellsociety.view;

import cellsociety.enums.State;
import cellsociety.model.SimulationModel;
import cellsociety.util.SimulationModelUtil;
import javafx.scene.control.Button;
import javafx.scene.paint.Paint;

/**
 * The GraphicalCell is what makes up the GridVisualizer. It extends button to implement the
 * appropriate response when the user clicks on the cell.
 *
 * @author Hosam Tageldin
 */
public class GraphicalCell extends Button {

  private static final String IMAGE = "IMAGE";
  private static final String BACKGROUND_IMAGE = "-fx-background-image: url(%s)";
  private static final String BACKGROUND_COLOR = "-fx-background-color: ";
  private static final int TOP_IMAGE_INDEX = 0;
  private State cellState;
  private final int myRow;
  private final int myColumn;
  private boolean showImages;
  private final SimulationModel myModel;

  /**
   * Creates the GraphicalCell and sets the fill depending on the parameters
   *
   * @param isState the state of the specific button
   * @param row the row that this cell is found on
   * @param column the column that this cell is found on
   * @param currModel the current model that this cell is found in
   */
  public GraphicalCell(State isState, int row, int column, SimulationModel currModel) {
    super();
    cellState = isState;
    myRow = row;
    myColumn = column;
    myModel = currModel;
    setCellFill();
  }

  /**
   * Changes state depending on what comes next after the user clicks on a cell
   *
   * @return the nextState after the user clicks on the cell
   */
  public State changeState() {
    cellState = SimulationModelUtil.determineNextState(cellState);
    setCellFill();
    return cellState;
  }

  private void setCellFill() {
    this.getStyleClass().clear();
    if (showImages) {
      this.getStyleClass().add(cellState.toString() + IMAGE);
      if (myModel.getImageMappings().containsKey(cellState.toString())) {
        this.setStyle(
            String.format(BACKGROUND_IMAGE, myModel.getImageMappings().get(cellState.toString())));
      }
    } else {
      this.getStyleClass().add(cellState.toString());
      if (myModel.getColorMappings().containsKey(cellState.toString())) {
        this.setStyle(BACKGROUND_COLOR + myModel.getColorMappings().get(cellState.toString()));
      }
    }
  }

  /**
   * For testing purposes, returns the specific color of the GraphicalCell
   * @return the Paint object of the color of the current cell state
   */
  public Paint getFill() {
    return getBackground().getFills().get(TOP_IMAGE_INDEX).getFill();
  }

  /**
   * Updates the cell based on the parameters which tell the Cell its next state
   *
   * @param newStateActive the new state of this GraphicalCell
   * @param showImage whether or not to show the images
   */
  public void updateCell(State newStateActive, boolean showImage) {
    cellState = newStateActive;
    showImages = showImage;
    setCellFill();
  }

  /**
   * @return the row of this GraphicalCell
   */
  public int getMyRow() {
    return myRow;
  }

  /**
   * @return the Column of this GraphicalCell
   */
  public int getMyColumn() {
    return myColumn;
  }
}
