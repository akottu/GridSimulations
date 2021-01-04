package cellsociety.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


/**
 * Tests for the SimulationView class.
 *
 * @author Hosam Tageldin
 */
class FireSpreadViewTest extends DukeApplicationTest {

  private static final String SIMULATION1 = "testresources/testFireSpread.sim";
  private static final String ENGLISH_PATH = "properties/English";
  private static final String NEXT_COMMAND = "#NextCommand";
  private static final Color TREE = Color.GREEN;
  private static final Color EMPTY = Color.WHITE;


  private Button myNextButton;
  private GraphicalCell myTreeCell;


  @Override
  public void start(Stage stage)
      throws IOException, InvalidSimGridDataException, SimConfigurationMissingException,
      InvalidSimTypeException {
    ResourceUtil.setLanguage(ENGLISH_PATH);
    SimulationModel mySim = SimulationModelUtil.createModelFromPath(SIMULATION1);
    GridScreen display = new GridScreen(mySim);
    display.startSimulation(stage);
    myNextButton = lookup(NEXT_COMMAND).query();
    myTreeCell = lookup("#cell4").query();
  }

  @Test
  void checkTreeFill() {
    assertEquals(TREE, myTreeCell.getFill());
  }

  @Test
  void checkFireFillTurnsToEmptyFill() {
    for(int i = 0; i< 10; i++){
      clickOn(myNextButton);
    }
    assertEquals(EMPTY, myTreeCell.getFill());
  }

  @Test
  void checkFrequencyDialogIncomplete() {
    clickOn(lookup("#FrequencyButton").query());
    clickOn(lookup("#SaveButton").query());
    Text myFillAllFields = lookup("#error").query();
    assertEquals("Fill All Fields!", myFillAllFields.getText());
  }

  @Test
  void checkProbabilityDialogIncomplete() {
    clickOn(lookup("#ProbabilityButton").query());
    clickOn(lookup("#SaveButton").query());
    Text myFillAllFields = lookup("#error").query();
    assertEquals("Fill All Fields!", myFillAllFields.getText());
  }

  @Test
  void checkFrequencyDialogNotSumToTotal() {
    clickOn(lookup("#FrequencyButton").query());
    write(lookup("#Title").query(), "1");
    write(lookup("#Author").query(), "1");
    write(lookup("#Description").query(), "1");
    write(lookup("#Entry0").query(), "1");
    write(lookup("#Entry1").query(), "1");
    write(lookup("#Entry2").query(), "1");
    clickOn(lookup("#SaveButton").query());
    Text myFillAllFields = lookup("#error").query();
    assertEquals("Frequencies do not total size of grid", myFillAllFields.getText());
  }

  @Test
  void checkProbabilityDialogNotSumToOne() {
    clickOn(lookup("#ProbabilityButton").query());
    write(lookup("#Title").query(), "1");
    write(lookup("#Author").query(), "1");
    write(lookup("#Description").query(), "1");
    write(lookup("#Entry0").query(), "1");
    write(lookup("#Entry1").query(), "1");
    write(lookup("#Entry2").query(), "1");
    clickOn(lookup("#SaveButton").query());
    clickOn(lookup("#SaveButton").query());
    Text myFillAllFields = lookup("#error").query();
    assertEquals("Probabilities do not add to 1", myFillAllFields.getText());
  }

  @Test
  void checkFrequencyDialogDoesSumToTotalAndWritesToFile() {
    clickOn(lookup("#FrequencyButton").query());
    write(lookup("#Title").query(), "ViewFreqTest");
    write(lookup("#Author").query(), "1");
    write(lookup("#Description").query(), "1");
    write(lookup("#Entry0").query(), "25");
    write(lookup("#Entry1").query(), "25");
    write(lookup("#Entry2").query(), "50");
    clickOn(lookup("#SaveButton").query());
    assertTrue(true);
  }

  @Test
  void checkProbabilityDialogDoesSumToOneAndWritesToFile() {
    clickOn(lookup("#ProbabilityButton").query());
    write(lookup("#Title").query(), "ViewProbTest");
    write(lookup("#Author").query(), "1");
    write(lookup("#Description").query(), "1");
    write(lookup("#Entry0").query(), ".1");
    write(lookup("#Entry1").query(), ".1");
    write(lookup("#Entry2").query(), ".8");
    clickOn(lookup("#SaveButton").query());
    assertTrue(true);
  }

}

