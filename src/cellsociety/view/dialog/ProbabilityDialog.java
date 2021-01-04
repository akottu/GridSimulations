package cellsociety.view.dialog;


import cellsociety.model.SimulationModel;
import cellsociety.util.ConfigurationCreator;
import cellsociety.util.ResourceUtil;
import cellsociety.util.SimulationModelUtil;
import javafx.stage.Stage;

/**
 * The Dialog box that pops up whenever a user requests to create a new configuration based off
 * probabilities for each cell state. Includes the input fields for the user to select the
 * cell state probabilities.
 *
 * @author Hosam Tageldin
 */
public class ProbabilityDialog extends NewConfigDialog {

  private static final String PROBABILITY_ERROR = "ProbabilityError";
  private static final String PROBABILITY_DIALOG_TITLE = "ProbabilityDialogTitle";
  private static final String ERROR = "error";
  private static final int TOTAL_PROBABILITY = 1;


  public ProbabilityDialog(SimulationModel currentModel) {
    super(currentModel);
    dialog.setTitle(ResourceUtil.getResourceValue(PROBABILITY_DIALOG_TITLE));
  }

  /**
   * Checks if the user entries are valid before attempting to create the new simulation. By valid,
   * the probabilities must add up to 1.
   *
   * @return a boolean regarding whether the entries are valid or not
   */
  @Override
  protected boolean validEntries() {
    Double probabilitySum =
        Double.parseDouble(state1Entry.getText()) + Double.parseDouble(state2Entry.getText());
    if (myStates.length > 2) {
      probabilitySum += Double.parseDouble(state3Entry.getText());
    }
    if (probabilitySum != TOTAL_PROBABILITY) {
      errorMessage.setText(ResourceUtil.getResourceValue(PROBABILITY_ERROR));
      errorMessage.setId(ERROR);
      return false;
    } else {
      return true;
    }
  }

  /**
   * Calls the control class to create a new configuration based off the chosen probabilities
   */
  @Override
  protected void newConfigSaved() {
    SimulationModel newModel = ConfigurationCreator
        .saveProbabilityConfig(currModel, toDoubleArray(), simulationTitle.getText(),
            simulationDescription.getText(),
            simulationAuthor.getText());
    SimulationModelUtil.newGridSimulation(newModel, new Stage());
    dialog.hide();
  }

  private double[] toDoubleArray() {
    double[] stateProbabilities = new double[myStates.length];
    for (int i = 0; i < stateProbabilities.length; i++) {
      stateProbabilities[i] = Double.parseDouble(entryList.get(i).getText());
    }
    return stateProbabilities;
  }
}
