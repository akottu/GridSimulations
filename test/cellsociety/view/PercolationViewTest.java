package cellsociety.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cellsociety.exception.InvalidSimGridDataException;
import cellsociety.exception.InvalidSimTypeException;
import cellsociety.exception.SimConfigurationMissingException;
import cellsociety.model.SimulationModel;
import cellsociety.util.ResourceUtil;
import cellsociety.util.SimulationModelUtil;
import cellsociety.view.GraphicalCell;
import cellsociety.view.GridScreen;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


/**
 * Tests for the SimulationView class.
 *
 * @author Hosam Tageldin
 */
class PercolationViewTest extends DukeApplicationTest {

  private static final String SIMULATION1 = "testresources/testPercolation.sim";
  private static final String ENGLISH_PATH = "properties/English";
  private static final String NEXT_COMMAND = "#NextCommand";
  private static final Color WALL = Color.GREY;
  private static final Color EMPTY = Color.WHITE;
  private static final Color FULL = Color.BLUE;

  private Button myNextButton;
  private GraphicalCell myEmptyCell;
  private GraphicalCell myWallCell;


  @Override
  public void start(Stage stage)
      throws IOException, InvalidSimGridDataException, SimConfigurationMissingException,
      InvalidSimTypeException {
    ResourceUtil.setLanguage(ENGLISH_PATH);
    SimulationModel mySim = SimulationModelUtil.createModelFromPath(SIMULATION1);
    GridScreen display = new GridScreen(mySim);
    display.startSimulation(stage);
    myNextButton = lookup(NEXT_COMMAND).query();
    myEmptyCell = lookup("#cell4").query();
    myWallCell = lookup("#cell7").query();
  }

  @Test
  void checkEmptyFill() {
    assertEquals(EMPTY, myEmptyCell.getFill());
  }

  @Test
  void checkWallFill() {
    assertEquals(WALL, myWallCell.getFill());
  }

  @Test
  void checkEmptyFillTurnsToFullFill() {
    for (int i = 0; i < 2; i++) {
      clickOn(myNextButton);
    }
    assertEquals(FULL, myEmptyCell.getFill());
  }
}
