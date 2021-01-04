package cellsociety.util;

import cellsociety.exception.GridDataNotFoundException;
import cellsociety.exception.InvalidSimGridDataException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Used to read input files
 */
public final class SimulationReader {

  private SimulationReader() {
    //not called but need a private constructor since it is a util class
  }

  /**
   * reads data from a .csv into a data structure
   *
   * @param pathName       the path to the .csv
   * @param simulationType the type of the simulation
   * @return the data structure with data to parse through for the model package
   * @throws InvalidSimGridDataException if the .csv is of an incorrect format
   */
  public static List<String[]> read(String pathName, String simulationType)
      throws InvalidSimGridDataException {
    File file = new File(pathName);
    try {
      InputStream data = new FileInputStream(file);
      List<String[]> fileContents = readAll(data);
      validate(fileContents, simulationType);
      return fileContents;
    } catch (IOException e) {
      throw new GridDataNotFoundException(ResourceUtil.getResourceValue("GridDataNotFound"));
    }
  }

  /**
   * converts an InputStream to a data structure parseable by model package
   *
   * @param data InputStream data
   * @return data structure parseable by model package
   * @throws GridDataNotFoundException if grid data is not found
   */
  public static List<String[]> readAll(InputStream data) throws GridDataNotFoundException {
    try (CSVReader csvReader = new CSVReader(new InputStreamReader(data))) {
      return csvReader.readAll();
    } catch (IOException | CsvException e) {
      throw new GridDataNotFoundException(ResourceUtil.getResourceValue("GridDataNotFound"));
    }
  }

  private static void validate(List<String[]> fileContents, String simulationType)
      throws InvalidSimGridDataException {
    validateRowsAndColumns(fileContents);
    validateCellStateValues(simulationType, fileContents);
  }

  private static void validateCellStateValues(String simulationType, List<String[]> fileContents)
      throws InvalidSimGridDataException {
    int numberOfStates = SimulationModelUtil.getNumberOfStates(simulationType);
    int numberOfRows = Integer.parseInt(fileContents.get(0)[0]);
    int numberOfColumns = Integer.parseInt(fileContents.get(0)[1]);
    for (int i = 1; i < numberOfRows; i++) {
      for (int j = 0; j < numberOfColumns; j++) {
        validateCell(i, j, fileContents, numberOfStates);
      }
    }
  }

  private static void validateCell(int row, int col, List<String[]> fileContents,
      int numberOfStates) throws InvalidSimGridDataException {
    int stateValue = Integer.parseInt(fileContents.get(row)[col]);
    if (stateValue < 0 || stateValue > numberOfStates) {
      throw new InvalidSimGridDataException(ResourceUtil.getResourceValue(
          "GridDataUnexpectedCellStateValues"));
    }
  }

  private static void validateRowsAndColumns(List<String[]> fileContents)
      throws InvalidSimGridDataException {
    int numberOfRows = Integer.parseInt(fileContents.get(0)[0]);
    if (numberOfRows != fileContents.size() - 1) {
      throw new InvalidSimGridDataException(
          ResourceUtil.getResourceValue("GridDataUnexpectedRows"));
    }
    int numberOfColumns = Integer.parseInt(fileContents.get(0)[1]);
    for (int i = 1; i < fileContents.size(); i++) {
      String[] row = fileContents.get(i);
      if (numberOfColumns != row.length) {
        throw new InvalidSimGridDataException(
            ResourceUtil.getResourceValue("GridDataUnexpectedColumns"));
      }
    }
  }
}

