package cellsociety.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cellsociety.exception.InvalidSimGridDataException;
import cellsociety.exception.InvalidSimTypeException;
import cellsociety.exception.SimConfigurationMissingException;
import cellsociety.model.SimulationModel;
import cellsociety.util.ResourceUtil;
import cellsociety.util.SimulationModelUtil;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


/**
 * Tests for the SimulationView class.
 *
 * @author Hosam Tageldin
 */
class GraphViewTest extends DukeApplicationTest {

  private static final String SIMULATION = "testresources/centerBeacon.sim";
  private static final String ENGLISH_PATH = "properties/English";
  private static final String NEXT_COMMAND = "#NextCommand";

  private GraphScreen display;
  private Button myNextButton;

  @Override
  public void start(Stage stage)
      throws IOException, InvalidSimGridDataException, SimConfigurationMissingException,
      InvalidSimTypeException {
    ResourceUtil.setLanguage(ENGLISH_PATH);
    SimulationModel mySim = SimulationModelUtil.createModelFromPath(SIMULATION);
    display = new GraphScreen(mySim);
    display.startSimulation(stage);

    myNextButton = lookup(NEXT_COMMAND).query();
  }

  @Test
  public void testCounts(){
    assertEquals(0,display.getGraphData().get(0).getData().get(0).getXValue());
    assertEquals(30,display.getGraphData().get(0).getData().get(0).getYValue());
    assertEquals(0,display.getGraphData().get(1).getData().get(0).getXValue());
    assertEquals(6,display.getGraphData().get(1).getData().get(0).getYValue());
  }

  @Test
  public void testCountsAfterOneStep(){
    assertEquals(0,display.getGraphData().get(0).getData().get(0).getXValue());
    assertEquals(30,display.getGraphData().get(0).getData().get(0).getYValue());
    assertEquals(0,display.getGraphData().get(1).getData().get(0).getXValue());
    assertEquals(6,display.getGraphData().get(1).getData().get(0).getYValue());
    clickOn(myNextButton);
    assertEquals(1,display.getGraphData().get(0).getData().get(1).getXValue());
    assertEquals(28,display.getGraphData().get(0).getData().get(1).getYValue());
    assertEquals(1,display.getGraphData().get(1).getData().get(1).getXValue());
    assertEquals(8,display.getGraphData().get(1).getData().get(1).getYValue());
  }

  @Test
  public void testCountsGraphAndGridAgree(){
    Number x1 = display.getGraphData().get(0).getData().get(0).getXValue();
    Number x2 = display.getGraphData().get(0).getData().get(0).getYValue();
    Number x3 = display.getGraphData().get(1).getData().get(0).getXValue();
    Number x4 = display.getGraphData().get(1).getData().get(0).getYValue();
    clickOn(lookup("#Grid").query());
    clickOn(myNextButton);
    clickOn(lookup("#Graph").query());
    clickOn(myNextButton);
    assertEquals(0,x1);
    assertEquals(30,x2);
    assertEquals(0,x3);
    assertEquals(6,x4);
  }



}