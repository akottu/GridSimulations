package cellsociety.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import cellsociety.exception.InvalidSimGridDataException;
import cellsociety.exception.InvalidSimTypeException;
import cellsociety.exception.SimConfigurationMissingException;
import cellsociety.model.SimulationModel;
import cellsociety.util.ConfigurationCreator;
import cellsociety.util.ResourceUtil;
import cellsociety.util.SimulationModelUtil;
import cellsociety.view.GridScreen;
import cellsociety.view.SimulationScreen;
import java.io.IOException;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class ConfigCreatorTest extends DukeApplicationTest {

  private static final String ENGLISH_PATH = "properties/English";
  private static final String SIMULATION1_PROPERTIES = "resources/Conway1.sim";

  private SimulationModel mySim;
  private SimulationScreen display;


  @Override
  public void start (Stage stage)
      throws IOException, InvalidSimGridDataException, SimConfigurationMissingException,
      InvalidSimTypeException {
    ResourceUtil.setLanguage(ENGLISH_PATH);
    mySim = SimulationModelUtil.createModelFromPath(SIMULATION1_PROPERTIES);
    display = new GridScreen(mySim);
    display.startSimulation(stage);
  }


  @Test
  void testSaveProbabilityConfig () throws IOException {
    double[] probabilities = {0.4, 0.6};
    ConfigurationCreator.saveProbabilityConfig(mySim, probabilities, "ProbTest", "Description",
        "Author");
    assertTrue(true);
  }

  @Test
  void testSaveFrequencyConfig () throws IOException {
    int[] frequencies = {100,100};
    ConfigurationCreator.saveFrequencyConfig(mySim,frequencies,"FreqTest","Description",
        "Author");
    assertTrue(true);
  }

}
