package cellsociety.view.dialog;

import cellsociety.enums.State;
import cellsociety.model.SimulationModel;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * This abstract class populates the stage with the appropriate input items in regards to the type
 * of change the user would like to make to the cell states (images or colors).
 *
 * @author Hosam Tageldin
 */
public abstract class StateSelectorDialog extends DialogBox {

  protected Button saveCustomButton;
  protected Text fillAllFields;
  protected State[] enumList;
  protected SimulationModel currModel;

  public StateSelectorDialog(SimulationModel currentModel) {
    currModel = currentModel;
    enumList = currentModel.getEnums();
    fillAllFields = new Text();
  }

  /**
   * Populates the stage with the state selector contents
   * @param populateBox the VBox to populate
   */
  @Override
  protected void populateStage(VBox populateBox) {
    int count = 1;
    for (State state : enumList) {
      statePicker(state, populateBox, count);
      count++;
    }
    populateBox.getChildren().add(saveCustomButton);
    populateBox.getChildren().add(fillAllFields);
  }

  /**
   *
   * @param state the Specific state to change
   * @param populateBox the VBox to populate
   * @param count the count used for testing to give each component an ID
   */
  protected abstract void statePicker(State state, VBox populateBox, int count);


}
