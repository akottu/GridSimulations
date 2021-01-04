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
class WaTorViewTest extends DukeApplicationTest {

  private static final String SIMULATION1 = "testresources/testWaTorColors.sim";
  private static final String ENGLISH_PATH = "properties/English";
  private static final Color FISH = Color.GREEN;
  private static final Color SHARK = Color.YELLOW;
  private static final Color WATER = Color.BLUE;

  private GraphicalCell myFishCell;
  private GraphicalCell mySharkCell;
  private GraphicalCell myWaterCell;


  @Override
  public void start(Stage stage)
      throws IOException, InvalidSimGridDataException, SimConfigurationMissingException,
      InvalidSimTypeException {
    ResourceUtil.setLanguage(ENGLISH_PATH);
    SimulationModel mySim = SimulationModelUtil.createModelFromPath(SIMULATION1);
    GridScreen display = new GridScreen(mySim);
    display.startSimulation(stage);
    myWaterCell = lookup("#cell1").query();
    myFishCell = lookup("#cell2").query();
    mySharkCell = lookup("#cell3").query();
  }

  @Test
  void checkFishFill() {
    assertEquals(FISH, myFishCell.getFill());
  }

  @Test
  void checkWaterFill() {
    assertEquals(WATER, myWaterCell.getFill());
  }

  @Test
  void checkSharkFill() {
    assertEquals(SHARK, mySharkCell.getFill());
  }
}
