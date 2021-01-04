package cellsociety.view.dialog;

import cellsociety.model.SimulationModel;
import cellsociety.util.ConfigurationCreator;
import cellsociety.util.ResourceUtil;
import cellsociety.util.SimulationModelUtil;
import javafx.stage.Stage;

/**
 * The Dialog box that pops up whenever a user requests to create a new configuration based off
 * frequencies for each cell state. Includes the input fields for the user to select the cell state
 * frequencies.
 *
 * @author Hosam Tageldin
 */
public class FrequencyDialog extends NewConfigDialog {

  private static final String FREQUENCY_ERROR = "FrequencyError";
  private static final String FREQUENCY_DIALOG_TITLE = "FrequencyDialogTitle";
  private static final String ERROR = "error";


  public FrequencyDialog(SimulationModel currentModel) {
    super(currentModel);
    dialog.setTitle(ResourceUtil.getResourceValue(FREQUENCY_DIALOG_TITLE));
  }

  /***
   * Checks if the user inputted entries add up to the number of cells in the grid to create a new
   * configuration based off frequencies
   *
   * @return boolean regarding whether the entries add up to the number of cells in the grid
   */
  @Override
  protected boolean validEntries() {
    int entrySum =
        Integer.parseInt(state1Entry.getText()) + Integer.parseInt(state2Entry.getText());
    if (myStates.length > 2) {
      entrySum += Integer.parseInt(state3Entry.getText());
    }
    int cellCount = currModel.getNumberOfRows() * currModel.getNumberOfColumns();
    if (entrySum != cellCount) {
      errorMessage.setText(ResourceUtil.getResourceValue(FREQUENCY_ERROR));
      errorMessage.setId(ERROR);
      return false;
    } else {
      return true;
    }
  }

  /**
   * Calls the control class to create a new configuration based off the chosen frequencies
   */
  @Override
  protected void newConfigSaved() {
    SimulationModel newModel = ConfigurationCreator
        .saveFrequencyConfig(currModel, toIntList(), simulationTitle.getText(),
            simulationDescription.getText(),
            simulationAuthor.getText());
    SimulationModelUtil.newGridSimulation(newModel, new Stage());
    dialog.hide();
  }

  private int[] toIntList() {
    int[] stateFrequencies = new int[myStates.length];
    for (int i = 0; i < stateFrequencies.length; i++) {
      stateFrequencies[i] = Integer.parseInt(entryList.get(i).getText());
    }
    return stateFrequencies;
  }
}
