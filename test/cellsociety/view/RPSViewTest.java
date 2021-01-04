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
class RPSViewTest extends DukeApplicationTest {

  private static final String SIMULATION1 = "testresources/testRockPaperScissors3.sim";
  private static final String ENGLISH_PATH = "properties/English";
  private static final Color ROCK = Color.RED;
  private static final Color SCISSORS = Color.GREEN;
  private static final Color PAPER = Color.BLUE;

  private GraphicalCell myRockCell;
  private GraphicalCell myPaperCell;
  private GraphicalCell myScissorsCell;


  @Override
  public void start(Stage stage)
      throws IOException, InvalidSimGridDataException, SimConfigurationMissingException,
      InvalidSimTypeException {
    ResourceUtil.setLanguage(ENGLISH_PATH);
    SimulationModel mySim = SimulationModelUtil.createModelFromPath(SIMULATION1);
    GridScreen display = new GridScreen(mySim);
    display.startSimulation(stage);
    myPaperCell = lookup("#cell1").query();
    myRockCell = lookup("#cell3").query();
    myScissorsCell = lookup("#cell7").query();
  }

  @Test
  void checkPaperFill() {
    assertEquals(PAPER, myPaperCell.getFill());
  }

  @Test
  void checkScissorsFill() {
    assertEquals(SCISSORS, myScissorsCell.getFill());
  }

  @Test
  void checkRockFill() {
    assertEquals(ROCK, myRockCell.getFill());
  }
}