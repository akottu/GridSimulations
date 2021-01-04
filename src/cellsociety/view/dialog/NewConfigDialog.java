package cellsociety.view.dialog;

import cellsociety.enums.State;
import cellsociety.model.SimulationModel;
import cellsociety.util.ResourceUtil;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * An abstract class to hold the commonalities for all the dialog boxes that prompt a user to create
 * a new configuration based off the subclasses parameters. Currently, there is the
 * ProbabilityDialog and FrequencyDialog but this class may be extended for different types of
 * dialog boxes.
 *
 * @author Hosam Tageldin
 */
public abstract class NewConfigDialog extends DialogBox {

  private static final String INCOMPLETE = "Incomplete";
  private static final String TITLE = "Title";
  private static final String AUTHOR = "Author";
  private static final String DESCRIPTION = "Description";
  private static final String SAVE_CONFIG_LABEL = "ConfigSaveTitle";

  protected final TextField simulationTitle = new TextField();
  protected final TextField simulationAuthor = new TextField();
  protected final TextField simulationDescription = new TextField();
  protected final TextField state1Entry = new TextField();
  protected final TextField state2Entry = new TextField();
  protected final TextField state3Entry = new TextField();
  protected final List<TextField> entryList = List.of(state1Entry, state2Entry, state3Entry);
  protected final Text errorMessage = new Text();
  protected final SimulationModel currModel;
  protected final State[] myStates;

  public NewConfigDialog(SimulationModel currentModel) {
    currModel = currentModel;
    myStates = currModel.getEnums();
  }

  /**
   * Populates the box with the input fields for the new configuration dialogs.
   *
   * @param populateBox the box to populate with the input fields
   */
  @Override
  protected void populateStage(VBox populateBox) {
    Button saveButton = new Button(ResourceUtil.getResourceValue(SAVE_CONFIG_LABEL));
    saveButton.setOnAction(e -> checkEntries());
    saveButton.setId("SaveButton");
    setDialogControls(populateBox);
    for (int i = 0; i < myStates.length; i++) {
      entryList.get(i).setPromptText(myStates[i].toString());
      entryList.get(i).setId("Entry" + i);
    }
    populateBox.getChildren()
        .addAll(state1Entry, state2Entry, state3Entry, saveButton, errorMessage);
  }

  private void setDialogControls(VBox populateBox) {
    simulationTitle.setPromptText(ResourceUtil.getResourceValue(TITLE));
    simulationTitle.setId("Title");
    simulationAuthor.setPromptText(ResourceUtil.getResourceValue(AUTHOR));
    simulationAuthor.setId("Author");
    simulationDescription.setPromptText(ResourceUtil.getResourceValue(DESCRIPTION));
    simulationDescription.setId("Description");
    populateBox.getChildren().addAll(simulationTitle, simulationAuthor, simulationDescription);
  }

  /**
   * Saves the config depending on the specific subclass and the user input
   */
  protected abstract void newConfigSaved();

  /**
   * Checks if the user inputted entries are valid
   *
   * @return boolean regarding whether or not the entries were valid
   */
  protected abstract boolean validEntries();

  private void checkEntries() {
    if (completeEntries() && validEntries()) {
      newConfigSaved();
    }
  }

  private boolean completeEntries() {
    if (emptyHeader() || emptyEntries()) {
      errorMessage.setText(ResourceUtil.getResourceValue(INCOMPLETE));
      errorMessage.setId("error");
      return false;
    } else {
      return true;
    }
  }

  private boolean emptyHeader() {
    return simulationTitle.getText().isEmpty() || simulationAuthor.getText().isEmpty()
        || simulationDescription.getText().isEmpty();
  }

  private boolean emptyEntries() {
    return state1Entry.getText().isEmpty() ||
        state2Entry.getText().isEmpty() ||
        (myStates.length > 2 && state3Entry.getText().isEmpty());
  }
}
