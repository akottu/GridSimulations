package cellsociety.util;

import cellsociety.enums.State;
import cellsociety.exception.NewFileNotCorrectTypeException;
import cellsociety.model.SimulationModel;
import com.opencsv.CSVWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Util class used to write new files in based on given parameters
 */
public final class SimulationWriter {

  private static final String SIMULATION_TYPE = "SimulationType";
  private static final String TITLE = "Title";
  private static final String AUTHORS = "Authors";
  private static final String DESCRIPTION = "Description";
  private static final String FILE_NAME = "Filename";
  private static final String IMAGES_USED = "ImagesUsed";
  private static final String COLORS_USED = "ColorsUsed";
  private static final String EDGE_POLICY = "EdgePolicy";
  private static final String NEIGHBORHOOD_TYPE = "NeighborhoodType";
  private static final String RESOURCES_PACKAGE = "resources/";
  private static final String FILE_EXTENSION = ".sim";
  private static final String CSV_EXTENSION = ".csv";

  private SimulationWriter() {
    //not called but need a private constructor since it is a util class
  }

  /**
   * Saves a config based on a model passed in
   *
   * @param simModel    model being saved
   * @param title       title of simulation
   * @param description description of simulation
   * @param author      author of simulation
   */
  public static void saveSimulationConfig(SimulationModel simModel, String title,
      String description, String author) {
    String modelName = SimulationModelUtil.getSimModelName(simModel.getClass().getName());
    writeSimulationConfigFile(simModel, modelName, title, description, author);
    writeSimGridDataFile(simModel, title);
  }

  private static void writeSimulationConfigFile(SimulationModel simModel, String modelName,
      String title, String description, String author) {
    try (OutputStream output = new FileOutputStream(RESOURCES_PACKAGE + title + FILE_EXTENSION)) {
      Properties prop = new Properties();
      prop.setProperty(TITLE, title);
      prop.setProperty(AUTHORS, author);
      prop.setProperty(DESCRIPTION, description);
      prop.setProperty(SIMULATION_TYPE, modelName);
      prop.setProperty(FILE_NAME, RESOURCES_PACKAGE + title + CSV_EXTENSION);
      prop.setProperty(IMAGES_USED, mapToString(simModel, true));
      prop.setProperty(COLORS_USED, mapToString(simModel, false));
      prop.setProperty(EDGE_POLICY, "Finite");
      prop.setProperty(NEIGHBORHOOD_TYPE, "Complete");
      prop.store(output, null);
    } catch (IOException e) {
      throw new NewFileNotCorrectTypeException("Error");
    }
  }

  private static String mapToString(SimulationModel simModel, boolean useImage) {
    String stringFromMap = "";
    Map<String, String> displayMap;
    if (useImage) {
      displayMap = simModel.getImageMappings();
    } else {
      displayMap = simModel.getColorMappings();
    }
    for (State state : simModel.getEnums()) {
      if (displayMap.get(state.toString()) != null) {
        stringFromMap = stringFromMap.concat(displayMap.get(state.toString()) + ",");
      }
    }
    return stringFromMap;
  }

  private static void writeSimGridDataFile(SimulationModel simulationModel, String title) {
    try (FileWriter fileWriter = new FileWriter(RESOURCES_PACKAGE + title + CSV_EXTENSION)) {
      CSVWriter csvWriter = new CSVWriter(fileWriter);
      List<String[]> simData = new ArrayList<>();
      int numberOfRows = simulationModel.getNumberOfRows();
      int numberOfColumns = simulationModel.getNumberOfColumns();
      String[] header = getSimulationFileHeader(simulationModel, numberOfRows,
          numberOfColumns);
      simData.add(header);
      populateCSVFile(simulationModel, simData);
      csvWriter.writeAll(simData, false);
    } catch (IOException e) {
      throw new NewFileNotCorrectTypeException("Error");
    }
  }

  private static void populateCSVFile(SimulationModel simModel, List<String[]> simData) {
    for (int i = 0; i < simModel.getNumberOfRows(); i++) {
      String[] rowData = new String[simModel.getNumberOfColumns()];
      for (int j = 0; j < simModel.getNumberOfColumns(); j++) {
        State cellState = simModel.getCellState(i, j);
        int k = SimulationModelUtil.getStateIndex(cellState,
            SimulationModelUtil.getSimModelName(simModel.getClass().getName()));
        rowData[j] = k + "";
      }
      simData.add(rowData);
    }
  }

  private static String[] getSimulationFileHeader(SimulationModel simulationModel,
      int numberOfRows, int numberOfColumns) {
    List<String> headerList = new ArrayList<>();
    headerList.add(numberOfRows + "");
    headerList.add(numberOfColumns + "");
    if (simulationModel.getClass().getName().contains("RockPaperScissors")) {
      headerList.add("2");
    } else if (simulationModel.getClass().getName().contains("FireSpread")) {
      headerList.addAll(List.of(".05", ".5", ".01"));
    } else if (simulationModel.getClass().getName().contains("WaTor")) {
      headerList.addAll(List.of("3", "15", "5"));
    }
    return headerList.toArray(new String[0]);
  }
}
