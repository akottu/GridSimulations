import cellsociety.util.SimulationModelUtil;
import cellsociety.view.dialog.StartScreen;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * Used to call the application run and upload the initial FileChooser for the data files to be
 * selected to run the program
 */
public class Main extends Application {

  private static final String STYLESHEET = "/styles/duke.css";

  /**
   * Start of the program.
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Creates a start screen for user to select language, then displays new simulation based off of
   * the file that the user selects
   *
   * @param stage the stage to populate with the simulation display
   */
  @Override
  public void start(Stage stage) {
    StartScreen startScreen = new StartScreen();
    startScreen.createNewScreen(STYLESHEET);
    if (startScreen.getFileName() != null) {
      SimulationModelUtil
          .newSimulation(startScreen.getFileName(), stage);
    }
  }
}
