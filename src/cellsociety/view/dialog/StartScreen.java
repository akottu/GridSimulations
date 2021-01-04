package cellsociety.view.dialog;

import cellsociety.util.ResourceUtil;
import java.io.File;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 * The StartScreen is the first screen to pop up when the user runs the program. It is a language
 * select screen for the language to be saved for the rest of the game.
 *
 * @author Hosam Tageldin
 */
public class StartScreen extends DialogBox {

  private static final String PROPERTIES = "properties/";
  private static final String START_LANGUAGE = "English";
  private static final String RESOURCES = "resources";
  private final String[] supportedLanguages = new String[]{"English", "Spanish", "Portuguese"};
  private final ResourceBundle resourceBundle = ResourceBundle
      .getBundle(PROPERTIES + START_LANGUAGE);
  private String chosenFileName;

  public StartScreen() {
    dialog.setTitle(resourceBundle.getString("SelectLanguage"));
  }

  /**
   * populates the stage with all of the language options
   *
   * @param allLanguages the Box to populate with all of the language options
   */
  @Override
  protected void populateStage(VBox allLanguages) {
    for (String language : supportedLanguages) {
      addLanguageButton(language, allLanguages);
    }
  }

  private void addLanguageButton(String language, VBox allStates) {
    Button languageButton = new Button();
    String label = resourceBundle.getString(language);
    languageButton.setText(label);
    languageButton.setOnAction(e -> setLanguage(PROPERTIES + language));
    allStates.getChildren().add(languageButton);
  }

  private void setLanguage(String chosenLanguage) {
    ResourceUtil.setLanguage(chosenLanguage);
    dialog.hide();
    openFileChooser();
  }

  private void openFileChooser() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File(RESOURCES));
    File chosen = fileChooser.showOpenDialog(null);
    if (chosen != null) {
      chosenFileName = chosen.getPath();
    }
  }

  /**
   * @return the fileName chosen by the user for the simulation
   */
  public String getFileName() {
    return chosenFileName;
  }


}
