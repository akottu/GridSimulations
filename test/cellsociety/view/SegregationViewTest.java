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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


/**
 * Tests for the SimulationView class.
 *
 * @author Hosam Tageldin
 */
class SegregationViewTest extends DukeApplicationTest {

  private static final String SIMULATION1 = "testresources/testSegregation.sim";
  private static final String ENGLISH_PATH = "properties/English";
  private static final Color EMPTY = Color.WHITE;
  private static final Color O = Color.BLUE;
  private static final Color X = Color.RED;

  private GraphicalCell myOCell;
  private GraphicalCell myXCell;
  private GraphicalCell myEmptyCell;


  @Override
  public void start(Stage stage)
      throws IOException, InvalidSimGridDataException, SimConfigurationMissingException,
      InvalidSimTypeException {
    ResourceUtil.setLanguage(ENGLISH_PATH);
    SimulationModel mySim = SimulationModelUtil.createModelFromPath(SIMULATION1);
    GridScreen display = new GridScreen(mySim);
    display.startSimulation(stage);
    myOCell = lookup("#cell1").query();
    myXCell = lookup("#cell2").query();
    myEmptyCell = lookup("#cell4").query();
  }

  @Test
  void checkXFill() {
    assertEquals(X, myXCell.getFill());
  }

  @Test
  void checkOFill() {
    assertEquals(O, myOCell.getFill());
  }

  @Test
  void checkEmptyFill() {
    assertEquals(EMPTY, myEmptyCell.getFill());
  }
}
