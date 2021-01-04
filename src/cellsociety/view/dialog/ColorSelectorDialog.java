package cellsociety.view.dialog;

import cellsociety.enums.State;
import cellsociety.model.SimulationModel;
import cellsociety.util.ResourceUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Controls the Color Selection dialog box for users to select the colors to represent each cell
 * state
 *
 * @author Hosam Tageldin
 */
public class ColorSelectorDialog extends StateSelectorDialog {

  private static final String CUSTOM_COLORS = "CustomColors";
  private static final String SAVE_COLORS = "SaveColorsButton";
  private static final String CHOOSE_COLORS = "ChooseColors";
  private static final String HEX_CODE = "#";
  private final List<ColorPicker> colorList;
  private final Map<String, String> colorMappings;

  public ColorSelectorDialog(SimulationModel currentModel) {
    super(currentModel);
    saveCustomButton = new Button(ResourceUtil.getResourceValue(CUSTOM_COLORS));
    saveCustomButton.setOnAction(e -> stateToColorMappings());
    saveCustomButton.setId(SAVE_COLORS);
    dialog.setTitle(ResourceUtil.getResourceValue(CHOOSE_COLORS));
    colorMappings = new HashMap<>();
    colorList = new ArrayList<>();
  }

  /**
   * Associates each cell state with a ColorPicker for the users to select a color to represent a
   * cell state
   *
   * @param state       the Specific state to change
   * @param populateBox the VBox to populate
   * @param count       the count used for testing to give each component an ID
   */
  @Override
  protected void statePicker(State state, VBox populateBox, int count) {
    HBox colorRow = new HBox();
    Text stateText = new Text(state.toString());
    ColorPicker colorPicker = new ColorPicker();
    colorRow.getChildren().addAll(stateText, colorPicker);
    populateBox.getChildren().add(colorRow);
    colorList.add(colorPicker);
  }

  private void stateToColorMappings() {
    for (int i = 0; i < colorList.size(); i++) {
      colorMappings.put(enumList[i].toString(), turnToHex(i));
    }
    currModel.setColorMappings(colorMappings);
    dialog.hide();
  }

  private String turnToHex(int index) {
    return HEX_CODE + colorList.get(index).getValue().toString().substring(2);
  }
}
