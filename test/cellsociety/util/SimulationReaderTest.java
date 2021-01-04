package cellsociety.util;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cellsociety.exception.GridDataNotFoundException;
import cellsociety.exception.InvalidSimGridDataException;
import cellsociety.exception.InvalidSimTypeException;
import cellsociety.exception.NewFileNotCorrectTypeException;
import cellsociety.exception.SimConfigurationMissingException;
import org.junit.jupiter.api.Test;

public class SimulationReaderTest {
  private static final String ENGLISH_PATH = "properties/English";

  private void setLanguage(){
    ResourceUtil.setLanguage(ENGLISH_PATH);
  }
  @Test
  void testValidateCellStateValues () {
    setLanguage();
    Exception exception = assertThrows(InvalidSimGridDataException.class,
        () -> SimulationReader.read("testresources"
            + "/testSegregationInvalidStateValues.csv","Segregation"));
    assertTrue(exception.getMessage().equals(ResourceUtil.getResourceValue(
        "GridDataUnexpectedCellStateValues")));
  }

  @Test
  void testValidateNumberOfColumns () {
    setLanguage();
    Exception exception = assertThrows(InvalidSimGridDataException.class,
        () -> SimulationReader.read("testresources"
        + "/testSegregationInvalidNumberOfColumns.csv","Segregation"));
    assertTrue(exception.getMessage().equals(ResourceUtil.getResourceValue(
        "GridDataUnexpectedColumns")));
  }

  @Test
  void testValidateNumberOfRows () {
    setLanguage();
    Exception exception = assertThrows(InvalidSimGridDataException.class,
        () -> SimulationReader.read("testresources"
            + "/testSegregationInvalidNumberOfRows.csv","Segregation"));
    assertTrue(exception.getMessage().equals(ResourceUtil.getResourceValue(
        "GridDataUnexpectedRows")));
  }

  @Test
  void testValidateSimConfigurationMissing () {
    setLanguage();
    Exception exception = assertThrows(SimConfigurationMissingException.class,
        () -> SimulationModelUtil.readFile("testresources"
            + "/testSegregationInvalidNumberOfRows.csv","", "Conway"));
    assertTrue(exception.getMessage().equals(ResourceUtil.getResourceValue(
        "SimConfigMissingRequiredFields")));
  }

  @Test
  void testValidateGridDataNotFound () {
    setLanguage();
    Exception exception = assertThrows(GridDataNotFoundException.class,
        () -> SimulationReader.read("testresources"
            + "/testSegregationNonExistentFile.csv","Segregation"));
    assertTrue(exception.getMessage().equals(ResourceUtil.getResourceValue(
        "GridDataNotFound")));
  }

  @Test
  void testValidateInvalidSimType () {
    setLanguage();
    Exception exception = assertThrows(InvalidSimTypeException.class,
        () -> SimulationModelUtil.createModelFromPath("testresources"
            + "/testSegregationInvalidSimType.sim"));
    assertTrue(exception.getMessage().equals(ResourceUtil.getResourceValue(
        "SimTypeNotValid")));
  }

  @Test
  void testValidateInvalidFile () {
    setLanguage();
    Exception exception = assertThrows(NewFileNotCorrectTypeException.class,
        () -> SimulationModelUtil.createModelFromPath("testresources"
            + "/testSegregationInvalidStateValues.csv"));
    assertTrue(exception.getMessage().equals(ResourceUtil.getResourceValue(
        "NewFileNotCorrectType")));
  }

}
