package cellsociety.view.dialog;

import cellsociety.enums.State;
import cellsociety.model.SimulationModel;
import cellsociety.util.ResourceUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Controls the Image Selection dialog box for users to input their own files for images to
 * represent each cell state
 *
 * @author Hosam Tageldin
 */
public class ImageSelectorDialog extends StateSelectorDialog {

  private static final String INCOMPLETE = "Incomplete";
  private static final String SAVE_COLORS = "SaveImagesButton";

  private final List<TextField> imageList;
  private final Map<String, String> imageMappings;

  public ImageSelectorDialog(SimulationModel currentModel) {
    super(currentModel);
    saveCustomButton = new Button(ResourceUtil.getResourceValue("CustomImages"));
    saveCustomButton.setOnAction(e -> checkEntries());
    saveCustomButton.setId(SAVE_COLORS);
    dialog.setTitle(ResourceUtil.getResourceValue("ChooseImages"));
    imageMappings = new HashMap<>();
    imageList = new ArrayList<>();
  }

  /**
   * Associates each cell state with an input field for users to type in the file name
   * The file name that the user inputs must be included in the resources folder
   *
   * @param state the Specific state to change
   * @param populateBox the VBox to populate
   * @param count the count used for testing to give each component an ID
   */
  @Override
  protected void statePicker(State state, VBox populateBox, int count) {
    HBox imageRow = new HBox();
    Text stateText = new Text(state.toString());
    TextField imageURLPicker = new TextField();
    imageURLPicker.setId("imagePicker" + count);
    imageRow.getChildren().addAll(stateText, imageURLPicker);
    populateBox.getChildren().add(imageRow);
    imageList.add(imageURLPicker);
  }

  private void checkEntries() {
    for (TextField field : imageList) {
      if (field.getText().isEmpty()) {
        fillAllFields.setText(ResourceUtil.getResourceValue(INCOMPLETE));
        fillAllFields.setId("FillAllFields");
        return;
      }
    }
    stateToImageMappings();
  }

  private void stateToImageMappings() {
    for (int i = 0; i < imageList.size(); i++) {
      imageMappings.put(enumList[i].toString(), imageList.get(i).getText());
    }
    currModel.setImageMappings(imageMappings);
    dialog.hide();
  }
}
