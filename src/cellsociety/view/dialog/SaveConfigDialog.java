package cellsociety.view.dialog;

import cellsociety.model.SimulationModel;
import cellsociety.util.ResourceUtil;
import cellsociety.util.SimulationWriter;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * The Dialog box that pops up whenever a user requests to save the current state of the simulation.
 * Prompts the user to input a title, author, and description and communicates with the controller
 * to save the simulation.
 *
 * @author Hosam Tageldin
 */
public class SaveConfigDialog extends DialogBox {

  private static final String TITLE = "Title";
  private static final String AUTHOR = "Author";
  private static final String DESCRIPTION = "Description";
  private static final String INCOMPLETE = "Incomplete";
  private static final String SAVE_BUTTON = "SaveButton";
  private static final String SAVE_CONFIG_LABEL = "ConfigSaveTitle";

  private final TextField simulationTitle = new TextField();
  private final TextField simulationAuthor = new TextField();
  private final TextField simulationDescription = new TextField();
  private final Text fillAllFields = new Text();
  private final Button saveButton;
  private final SimulationModel currModel;

  public SaveConfigDialog(SimulationModel currentModel) {
    currModel = currentModel;
    saveButton = new Button(ResourceUtil.getResourceValue(SAVE_BUTTON));
    saveButton.setOnAction(e -> newConfigSaved());
    saveButton.setId("saveNewConfig");
    dialog.setTitle(ResourceUtil.getResourceValue(SAVE_CONFIG_LABEL));
  }

  /**
   * Populates the box with the input fields of Simulation Title, Author and description
   *
   * @param populateBox the box to populate with the prompts
   */
  @Override
  protected void populateStage(VBox populateBox) {
    simulationTitle.setPromptText(ResourceUtil.getResourceValue(TITLE));
    simulationTitle.setId("simTitle");
    simulationAuthor.setPromptText(ResourceUtil.getResourceValue(AUTHOR));
    simulationAuthor.setId("simAuthor");
    simulationDescription.setPromptText(ResourceUtil.getResourceValue(DESCRIPTION));
    simulationDescription.setId("simDesc");
    populateBox.getChildren()
        .addAll(simulationTitle, simulationAuthor, simulationDescription, saveButton,
            fillAllFields);
  }

  private void newConfigSaved() {
    if (checkEntries()) {
      SimulationWriter
          .saveSimulationConfig(currModel, simulationTitle.getText(),
              simulationDescription.getText(),
              simulationAuthor.getText());
      dialog.hide();
    }
  }

  private boolean checkEntries() {
    if (simulationTitle.getText().isEmpty() || simulationAuthor.getText().isEmpty()
        || simulationDescription.getText().isEmpty()) {
      fillAllFields.setText(ResourceUtil.getResourceValue(INCOMPLETE));
      fillAllFields.setId("checkFields");
      return false;
    } else {
      return true;
    }
  }

}
