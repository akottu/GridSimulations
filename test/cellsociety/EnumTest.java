package cellsociety;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cellsociety.enums.ConwayState;
import cellsociety.enums.FireSpreadState;
import cellsociety.enums.PercolationState;
import cellsociety.util.ResourceUtil;
import cellsociety.util.SimulationModelUtil;
import org.junit.jupiter.api.Test;

public class EnumTest {

  private static final String ENGLISH_PATH = "properties/English";

  private void setLanguage(){
    ResourceUtil.setLanguage(ENGLISH_PATH);
  }

  @Test
  void testNextStateConway () {
    setLanguage();
    assertEquals(SimulationModelUtil.determineNextState(ConwayState.DEAD), ConwayState.ALIVE);
  }

  @Test
  void testNextStateFireSpread () {
    setLanguage();
    assertEquals(SimulationModelUtil.determineNextState(FireSpreadState.EMPTY), FireSpreadState.TREE);
  }

  @Test
  void testNextStatePercolationState () {
    setLanguage();
    assertEquals(SimulationModelUtil.determineNextState(PercolationState.EMPTY),
        PercolationState.FULL);
  }
}
