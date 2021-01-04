package cellsociety.view;

import cellsociety.util.ResourceUtil;
import cellsociety.util.SimulationModelUtil;
import cellsociety.view.dialog.DialogBox;
import cellsociety.view.dialog.FrequencyDialog;
import cellsociety.view.dialog.ProbabilityDialog;
import cellsociety.view.dialog.SaveConfigDialog;
import java.io.File;
import cellsociety.model.SimulationModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * This abstract class holds the contents of the entire screen for a specific display type. Its
 * subclasses share a lot of common functionalities, which will all be found here. The abstract
 * classes in this method will allow the subclasses to change the screen to fit the subclass'
 * needs.
 *
 * @author Hosam Tageldin
 */
public abstract class SimulationScreen {

  private static final String RESOURCES = "resources";
  private static final String DUKE_STYLESHEET = "/styles/duke.css";
  private static final String UNC_STYLESHEET = "/styles/unc.css";
  private static final String NC_STATE_STYLESHEET = "/styles/state.css";
  private static final String STEP_COUNTER = "StepCounter";
  private static final String UNC_CSS = "UNCCSS";
  private static final String STATE_CSS = "StateCSS";
  private static final String DUKE_CSS = "DukeCSS";
  private static final double SECOND_DELAY = 1.0;
  private static final double MAXIMUM_ANIMATION_SPEED = 8.0;
  private static final double MINIMUM_ANIMATION_SPEED = 1.0 / 2;
  private static final double SPEED_INCREASE_RATE = 1.5;
  private static final double SPEED_DECREASE_RATE = 0.5;

  private Button myResumeButton;
  private Button myPauseButton;
  private Button mySpeedUpButton;
  private Button mySlowDownButton;
  private Scene myScene;
  private Timeline animation;
  private Text stepCounterText;
  SimulationModel myModel;
  String currentStyleSheet;


  private int stepCounter;
  private boolean paused;
  private double updatedSpeed;


  public SimulationScreen(SimulationModel model) {
    myModel = model;
    updatedSpeed = SECOND_DELAY;
    currentStyleSheet = DUKE_STYLESHEET;
  }

  /**
   * Called every time the newest update of the model is in order. Can be called either through the
   * animation timer or through the user inputted "Next" button.
   */
  void step() {
    stepCounter++;
    updateView();
    stepCounterText.setText(ResourceUtil.getResourceValue(STEP_COUNTER) + stepCounter);
  }

  /**
   * For every step of the simulation, the updateView is called to update the specific simulation
   * view in the appropriate manner
   */
  protected abstract void updateView();

  private Node createHeader() {
    VBox header = new VBox();
    header.getChildren().addAll(createTitle(), createHeaderButtons());
    return header;
  }

  private Node createTitle() {
    HBox titleNode = new HBox();
    Text Title = new Text(myModel.getTitle());
    Title.setId("SimulationTitle");
    stepCounterText = new Text(ResourceUtil.getResourceValue(STEP_COUNTER) + stepCounter);
    titleNode.getChildren().addAll(Title, stepCounterText);
    return titleNode;
  }

  /**
   * Incorporates all the header buttons involved in a specific simulation into the the header of
   * the screen.
   *
   * @return the Header Buttons included in the specific subclass of the simulation screen
   */
  protected abstract Node createHeaderButtons();

  /**
   * Incorporates all of the styling options for the display and includes the MenuItems into the
   * MenuButton in the parameter list
   *
   * @param changeStyle the MenuButton to allow users to change CSS of the display
   */
  protected void populateStyleDropDown(MenuButton changeStyle) {
    MenuItem changeToUnc = new MenuItem(ResourceUtil.getResourceValue(UNC_CSS));
    changeToUnc.setOnAction(e -> changeToUnc());
    MenuItem changeToDuke = new MenuItem(ResourceUtil.getResourceValue(DUKE_CSS));
    changeToDuke.setOnAction(e -> changeToDuke());
    MenuItem changeToState = new MenuItem(ResourceUtil.getResourceValue(STATE_CSS));
    changeToState.setOnAction(e -> changeToState());
    changeStyle.getItems().addAll(changeToUnc, changeToState, changeToDuke);
  }

  private void changeToUnc() {
    myScene.getStylesheets().clear();
    currentStyleSheet = UNC_STYLESHEET;
    myScene.getStylesheets().add(getClass().getResource(UNC_STYLESHEET).toExternalForm());
  }

  private void changeToDuke() {
    myScene.getStylesheets().clear();
    currentStyleSheet = DUKE_STYLESHEET;
    myScene.getStylesheets().add(getClass().getResource(DUKE_STYLESHEET).toExternalForm());
  }

  private void changeToState() {
    myScene.getStylesheets().clear();
    currentStyleSheet = NC_STATE_STYLESHEET;
    myScene.getStylesheets().add(getClass().getResource(NC_STATE_STYLESHEET).toExternalForm());
  }

  private Node makeButtonPanel() {
    VBox buttons = new VBox();
    buttons.getChildren().addAll(makeTopRowButtonPanel(), makeBottomRowButtonPanel());
    enableButtons();
    return buttons;

  }

  private Node makeTopRowButtonPanel() {
    HBox topRowButtons = new HBox();
    mySlowDownButton = makeButton("SlowDownCommand", event -> setNewRate(SPEED_DECREASE_RATE));
    myResumeButton = makeButton("ResumeCommand", event -> resume());
    myPauseButton = makeButton("PauseCommand", event -> pause());
    Button myNextButton = makeButton("NextCommand", event -> step());
    mySpeedUpButton = makeButton("SpeedUpCommand", event -> setNewRate(SPEED_INCREASE_RATE));
    topRowButtons.getChildren()
        .addAll(mySlowDownButton, myResumeButton, myPauseButton, myNextButton, mySpeedUpButton);
    return topRowButtons;
  }

  private Node makeBottomRowButtonPanel() {
    HBox bottomRowButtons = new HBox();
    Button myAddButton = makeButton("AddCommand", event -> addSim());
    Button myUploadButton = makeButton("UploadCommand", event -> replaceSim());
    Button mySaveStateButton = makeButton("SaveStateCommand", event -> saveState());
    Button myFrequencyConfig = makeButton("CreateConfigFrequency",
        event -> createFrequencyConfig());
    myFrequencyConfig.setId("FrequencyButton");
    Button myProbabilityConfig = makeButton("CreateConfigProbability",
        event -> createProbabilityConfig());
    myProbabilityConfig.setId("ProbabilityButton");
    bottomRowButtons.getChildren()
        .addAll(myAddButton, myUploadButton,
            mySaveStateButton, myProbabilityConfig, myFrequencyConfig);
    return bottomRowButtons;
  }

  /**
   * Pauses the simulation run
   */
  protected void pause() {
    animation.pause();
    paused = true;
    enableButtons();
  }

  /**
   * Activated when simulation is paused, allows the simulation to run again
   */
  protected void resume() {
    animation.play();
    paused = false;
    enableButtons();
  }

  private void setNewRate(double rateChange) {
    updatedSpeed *= rateChange;
    animation.setRate(updatedSpeed);
    enableButtons();
  }

  private void addSim() {
    String pathName = upload();
    if (pathName.length() > 0) {
      SimulationModelUtil.newSimulation(pathName, new Stage());
    }
    resume();
  }

  private void replaceSim() {
    String pathName = upload();
    if (pathName.length() > 0) {
      changeSimulation(pathName);
    }
  }

  private String upload() {
    if (!paused) {
      pause();
    }
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File(RESOURCES));
    File chosen = fileChooser.showOpenDialog(null);
    if (chosen != null) {
      return chosen.getPath();
    } else {
      resume();
    }
    return "";
  }

  private void saveState() {
    pause();
    DialogBox saveStateBox = new SaveConfigDialog(myModel);
    saveStateBox.createNewScreen(currentStyleSheet);
    resume();
  }


  private void createFrequencyConfig() {
    pause();
    DialogBox frequencyDialog = new FrequencyDialog(myModel);
    frequencyDialog.createNewScreen(currentStyleSheet);
    resume();
  }

  private void createProbabilityConfig() {
    pause();
    DialogBox probabilityDialog = new ProbabilityDialog(myModel);
    probabilityDialog.createNewScreen(currentStyleSheet);
    resume();
  }

  /**
   * Creates a button with the specified label and handler
   *
   * @param property the label of the button
   * @param handler  the method to run once button is clicked on
   * @return the Button that holds the property and handler
   */
  protected Button makeButton(String property, EventHandler<ActionEvent> handler) {
    Button result = new Button();
    String label = ResourceUtil.getResourceValue(property);
    result.setText(label);
    result.setOnAction(handler);
    result.setId(property);
    return result;
  }


  private void enableButtons() {
    myResumeButton.setDisable(!paused);
    myPauseButton.setDisable(paused);
    mySpeedUpButton.setDisable(updatedSpeed > MAXIMUM_ANIMATION_SPEED);
    mySlowDownButton.setDisable(updatedSpeed < MINIMUM_ANIMATION_SPEED);
  }

  /**
   * For testing purposes, to show the pause button controls the state of the game
   *
   * @return a boolean regarding whether or not the game is paused
   */
  public boolean isPaused() {
    return paused;
  }

  /**
   * For testing purposes, to show that the step counter gets properly incremented with time and
   * using the "Next" button
   *
   * @return the int representing the current step count of the simulation run
   */
  public int getStepCounter() {
    return stepCounter;
  }

  /**
   * Initializes the display, including the header, the visualizer and the bottom panel of buttons.
   */
  protected void createRoot() {
    BorderPane root = new BorderPane();
    stepCounter = 0;
    root.setTop(createHeader());
    root.setCenter(createContent());
    root.setBottom(makeButtonPanel());
    myScene.setRoot(root);
  }

  /**
   * Creates the portion of the screen that represents the changing states of the simulation
   *
   * @return the Node representing the visualization of the screen
   */
  protected abstract Node createContent();

  /**
   * Displays the exception messages to the user
   *
   * @param e the specific exception to display the correct message
   */
  protected void createExceptionRoot(Exception e) {
    BorderPane root = new BorderPane();
    stepCounter = 0;
    Text errorMessage = new Text(e.getMessage());
    errorMessage.setId("error");
    root.setCenter(errorMessage);
    root.setBottom(makeButtonPanel());
    myScene.getStylesheets().add(getClass().getResource(DUKE_STYLESHEET).toExternalForm());
    myScene.setRoot(root);
  }

  /**
   * Initializes the animation and all the features of the game
   *
   * @param stage the stage to populate once the simulation begins
   */
  public void startSimulation(Stage stage) {
    myScene = new Scene(new Pane());
    createRoot();
    myScene.getStylesheets().add(getClass().getResource(DUKE_STYLESHEET).toExternalForm());
    stage.setTitle(ResourceUtil.getResourceValue("SimulationTitle"));
    stage.setScene(myScene);
    stage.show();
    KeyFrame frame = new KeyFrame(Duration.seconds(updatedSpeed), e -> step());
    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  /**
   * Changes the simulation to the same type of current simulation
   *
   * @param pathName represents property file of new simulation
   */
  protected abstract void changeSimulation(String pathName);

}


