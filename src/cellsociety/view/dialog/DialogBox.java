package cellsociety.view.dialog;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * DialogBox is the top level superclass for any of the pop up boxes that appear in the simulation.
 * The popup boxes extend this superclass and Override populateStage with the necessary components
 * for that pop up box.
 */
public abstract class DialogBox extends Stage {

  private static final String POP_UP_BOX = "PopUpBox";
  protected Stage dialog;

  public DialogBox() {
    dialog = new Stage();
  }

  /**
   * Creates a new popup box of the same style and does not allow the user to click off of it until
   * the dialog box is complete and submitted or canceled.
   *
   * @param currentStyleSheet the current stylesheet of the program
   */
  public void createNewScreen(String currentStyleSheet) {
    VBox populateBox = new VBox();
    populateBox.setId(POP_UP_BOX);
    populateStage(populateBox);
    Scene scene = new Scene(populateBox);
    scene.getStylesheets().add(getClass().getResource(currentStyleSheet).toExternalForm());
    dialog.setScene(scene);
    dialog.initModality(Modality.APPLICATION_MODAL);
    dialog.showAndWait();
  }

  /**
   * Each class that extends this class will override this method to add all the necessary
   * components to the stage.
   *
   * @param populateBox the box to populate
   */
  protected abstract void populateStage(VBox populateBox);
}