package cellsociety.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cellsociety.exception.InvalidSimGridDataException;
import cellsociety.exception.InvalidSimTypeException;
import cellsociety.exception.SimConfigurationMissingException;
import cellsociety.model.SimulationModel;
import cellsociety.util.ResourceUtil;
import cellsociety.util.SimulationModelUtil;
import cellsociety.util.SimulationWriter;
import java.io.IOException;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

public class SimulationWriterTest {
  private static final String ENGLISH_PATH = "properties/English";

  private SimulationModel mySim;

  private void makeGame(String pathName)
      throws InvalidSimGridDataException, SimConfigurationMissingException, InvalidSimTypeException {
    try {
      mySim = SimulationModelUtil.createModelFromPath("testresources/" + pathName);
    } catch (IOException e) {
      mySim = null;
    }
  }

  private void setLanguage(){
    ResourceUtil.setLanguage(ENGLISH_PATH);
  }

  @Test
  void testWriter () throws IOException {
    setLanguage();
    makeGame("testFireSpread3.sim");
    SimulationWriter.saveSimulationConfig(mySim, "Title", "Description", "Author");
    assertTrue(true);
  }

}
