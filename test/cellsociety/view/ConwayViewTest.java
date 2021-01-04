package cellsociety.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cellsociety.exception.InvalidSimGridDataException;
import cellsociety.exception.InvalidSimTypeException;
import cellsociety.exception.SimConfigurationMissingException;
import cellsociety.model.SimulationModel;
import cellsociety.util.ResourceUtil;
import cellsociety.util.SimulationModelUtil;
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
class ConwayViewTest extends DukeApplicationTest {
  private static final String SIMULATION1 = "resources/Conway1.sim";
  private static final String ENGLISH_PATH = "properties/English";
  private static final String NEXT_COMMAND = "#NextCommand";
  private static final String PAUSE_COMMAND = "#PauseCommand";
  private static final String RESUME_COMMAND = "#ResumeCommand";
  private static final String SHOW_IMAGES_COMMAND = "#ShowImagesCommand";
  private static final String EXPECTED_DEAD_STATE = "skull.png";
  private static final String CELL_3 = "#cell3";
  private static final String CELL_4 = "#cell4";
  private static final String CELL_5 = "#cell5";
  private static final Color ACTIVE = Color.DARKBLUE;
  private static final Color INACTIVE = Color.LIGHTGREY;


  private GridScreen display;
  private Button myResumeButton;
  private Button myPauseButton;
  private Button myNextButton;
  private Text myFillAllFields;
  private Button myChangeToImagesButton;
  private GraphicalCell myActiveCell;
  private GraphicalCell myInactiveCell;
  private GraphicalCell myChangingStateCell;




  @Override
  public void start (Stage stage)
      throws IOException, InvalidSimGridDataException, SimConfigurationMissingException,
      InvalidSimTypeException {
    ResourceUtil.setLanguage(ENGLISH_PATH);
    SimulationModel mySim = SimulationModelUtil.createModelFromPath(SIMULATION1);
    display = new GridScreen(mySim);
    display.startSimulation(stage);

    myResumeButton = lookup(RESUME_COMMAND).query();
    myPauseButton = lookup(PAUSE_COMMAND).query();
    myNextButton = lookup(NEXT_COMMAND).query();
    myChangeToImagesButton = lookup(SHOW_IMAGES_COMMAND).query();
    myInactiveCell = lookup(CELL_3).query();
    myActiveCell = lookup(CELL_4).query();
    myChangingStateCell = lookup(CELL_5).query();
  }

  @Test
  void testPauseButton () {
    assertFalse(myPauseButton.isDisabled());
    clickOn(myPauseButton);
    assertTrue(myPauseButton.isDisabled());
    assertTrue(display.isPaused());
  }

  @Test
  void testResumeButton () {
    assertTrue(myResumeButton.isDisabled());
    clickOn(myPauseButton);
    assertFalse(myResumeButton.isDisabled());
    clickOn(myResumeButton);
    assertTrue(myResumeButton.isDisabled());
    assertFalse(display.isPaused());
  }

  @Test
  void testStepButton () {
    clickOn(myPauseButton);
    int currentStep = display.getStepCounter();
    int expectedStep = currentStep + 1;
    clickOn(myNextButton);
    assertEquals(expectedStep, display.getStepCounter());
  }

  @Test
  void checkActiveFill () {
    assertEquals(ACTIVE, myActiveCell.getFill());
  }

  @Test
  void checkInactiveFill () {
    assertEquals(INACTIVE, myInactiveCell.getFill());
  }

  @Test
  void checkCellChangesColor () {
    clickOn(myPauseButton);
    assertEquals(ACTIVE,myChangingStateCell.getFill());
    clickOn(myNextButton);
    assertEquals(INACTIVE,myChangingStateCell.getFill());
  }

  @Test
  void checkFillChangeOnClick () {
    clickOn(myPauseButton);
    assertEquals(INACTIVE, myInactiveCell.getFill());
    clickOn(myInactiveCell);
    assertEquals(ACTIVE, myInactiveCell.getFill());
  }


  @Test
  void checkChangeToImages () {
    clickOn(myChangeToImagesButton);
    clickOn(myNextButton);
    String[] filePath = myActiveCell.getBackground().getImages().get(0).getImage().getUrl().split("/");
    String actualFileName = filePath[filePath.length-1];
    assertEquals(EXPECTED_DEAD_STATE,actualFileName);
  }

  @Test
  void checkChangeCellColors () {
    clickOn(lookup("#ChooseColors").query());
    clickOn(lookup("#SaveColorsButton").query());
    clickOn(myNextButton);
    assertEquals(Color.WHITE, myActiveCell.getFill());
  }

  @Test
  void textChangeCellImages() {
    clickOn(lookup("#ChooseImages").query());
    write(lookup("#imagePicker1").query(), "water.jpg");
    write(lookup("#imagePicker2").query(), "water.jpg");
    clickOn(lookup("#SaveImagesButton").query());
    clickOn(myChangeToImagesButton);
    clickOn(myPauseButton);
    clickOn(myNextButton);
    clickOn(myNextButton);
    String[] filePath = myActiveCell.getBackground().getImages().get(0).getImage().getUrl().split("/");
    String actualFileName = filePath[filePath.length-1];
    assertEquals("water.jpg",actualFileName);

  }

  @Test
  void testIncompleteChangeCellImages(){
    clickOn(lookup("#ChooseImages").query());
    write(lookup("#imagePicker1").query(), "water.jpg");
    clickOn(lookup("#SaveImagesButton").query());
    myFillAllFields = lookup("#FillAllFields").query();
    assertEquals("Fill All Fields!", myFillAllFields.getText());
  }

  @Test
  void saveIncompleteNewConfiguration () {
    clickOn(lookup("#SaveStateCommand").query());
    write(lookup("#simTitle").query(), "test");
    write(lookup("#simAuthor").query(), "test");
    clickOn(lookup("#saveNewConfig").query());
    myFillAllFields = lookup("#checkFields").query();
    assertEquals("Fill All Fields!", myFillAllFields.getText());
  }

  @Test
  void saveNewConfiguration () {
    clickOn(lookup("#SaveStateCommand").query());
    write(lookup("#simTitle").query(), "test");
    write(lookup("#simAuthor").query(), "test");
    write(lookup("#simDesc").query(), "test");
    clickOn(lookup("#saveNewConfig").query());
    assertTrue(true);
  }
}
