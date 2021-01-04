package cellsociety.view;

import cellsociety.enums.State;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.GridPane;
import cellsociety.model.SimulationModel;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * Displays the Grid View of the simulation which is the regular 2D grid where each grid cell
 * represents a cell state
 *
 * @author Hosam Tageldin
 */
public class GridVisualizer extends SimulationVisualizer {

  private static final String CELL = "cell";

  private final List<GraphicalCell> cellList;
  private boolean showImages;


  public GridVisualizer(SimulationModel model) {
    super(model);
    cellList = new ArrayList<>();
  }

  /**
   * Adds the GraphicalCells to the GridPane to represent the states of the models
   *
   * @return the Pane containing the contents of the GridVisualizer
   */
  @Override
  protected Pane createContent() {
    StackPane pane = new StackPane();
    GridPane root = new GridPane();
    addCellsToRoot(root, pane);
    pane.getChildren().add(root);
    return pane;
  }

  private void addCellsToRoot(GridPane root, StackPane pane) {
    int i = 1;
    for (int row = 0; row < numberOfCellsInColumn; row++) {
      for (int column = 0; column < numberOfCellsInRow; column++) {
        GraphicalCell newCell = new GraphicalCell(isActive(row, column), row, column, myModel);
        newCell.setOnAction(event -> changeState(newCell));
        newCell.setId(CELL + i);
        i++;
        newCell.prefHeightProperty().bind(pane.heightProperty());
        newCell.prefWidthProperty().bind(pane.widthProperty());
        root.add(newCell, column, row);
        cellList.add(newCell);
      }
    }
  }

  private void changeState(GraphicalCell cell) {
    myModel.addCellModel(cell.getMyRow(), cell.getMyColumn(), cell.changeState());
  }

  /**
   * Updates the view of the the cell state depending on the specific state of the cell and whether
   * or not the user requesting to use images instead of colors
   */
  @Override
  protected void updateView() {
    for (GraphicalCell cell : cellList) {
      cell.updateCell(isActive(cell.getMyRow(), cell.getMyColumn()), showImages);
    }
  }


  private State isActive(int x, int y) {
    return myModel.getCellState(x, y);
  }

  /**
   * Called whenever the user chooses to display images rather than colors and will be updated
   * within each GraphicalCell
   */
  public void toggleCellDisplay() {
    showImages = !showImages;
  }
}
