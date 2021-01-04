package cellsociety.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cellsociety.exception.InvalidSimGridDataException;
import cellsociety.exception.InvalidSimTypeException;
import cellsociety.exception.SimConfigurationMissingException;
import cellsociety.model.SimulationModel;
import cellsociety.util.ResourceUtil;
import cellsociety.util.SimulationModelUtil;
import cellsociety.view.GridScreen;
import cellsociety.view.SimulationScreen;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


/**
 * Tests for the SimulationView class.
 *
 * @author Hosam Tageldin
 */
class PortugueseTest extends DukeApplicationTest {
  private static final String SIMULATION1 = "resources/Conway1.sim";
  private static final String PORTUGUESE_PATH = "properties/Portuguese";
  private static final String UPLOAD_COMMAND = "#UploadCommand";

  private Button myUploadButton;




  @Override
  public void start (Stage stage)
      throws IOException, InvalidSimGridDataException, SimConfigurationMissingException,
      InvalidSimTypeException {
    ResourceUtil.setLanguage(PORTUGUESE_PATH);
    SimulationModel mySim = SimulationModelUtil.createModelFromPath(SIMULATION1);
    SimulationScreen display = new GridScreen(mySim);
    display.startSimulation(stage);

    myUploadButton = lookup(UPLOAD_COMMAND).query();

  }

  @Test
  void testUploadButtonIsPortuguese () {
    assertEquals("Carregar Novo",myUploadButton.getText());
  }

}
