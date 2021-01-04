package cellsociety.util;

import cellsociety.enums.EdgePolicy;
import cellsociety.enums.NeighborhoodType;
import cellsociety.enums.SimulationModelType;
import cellsociety.enums.State;
import cellsociety.exception.GridDataNotFoundException;
import cellsociety.exception.InvalidSimGridDataException;
import cellsociety.exception.InvalidSimTypeException;
import cellsociety.exception.NewFileNotCorrectTypeException;
import cellsociety.exception.SimConfigurationMissingException;
import cellsociety.model.Neighborhood;
import cellsociety.model.SimulationModel;
import cellsociety.view.GraphScreen;
import cellsociety.view.GridScreen;
import cellsociety.view.SimulationScreen;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

/**
 * Util class to help provide tools for SimulationModel and SimulationView's use
 */
public final class SimulationModelUtil {

  private static final String MODEL_PATH = "cellsociety.model.";
  private static final String ENUM_PATH = "cellsociety.enums.";
  private static final String ERROR_MESSAGE = "ErrorMessage";
  private static final String MODEL = "Model";
  private static final String STATE = "State";
  private static final String EDGE_POLICY = "EdgePolicy";
  private static final String NEIGHBORHOOD = "Neighborhood";
  private static final String FROM_INT_METHOD = "fromInt";
  private static final int MODEL_LENGTH = 5;

  private SimulationModelUtil() {
    //not called but need a private constructor since it is a util class
  }

  /**
   * creates a model based on a path to an input .sim file
   *
   * @param path .sim file
   * @return the new model
   * @throws IOException                      if there is a general I/O exception
   * @throws InvalidSimGridDataException      if the .csv is incorrectly created
   * @throws SimConfigurationMissingException if the .sim's associated .csv does not exist
   * @throws InvalidSimTypeException          if the model does not have an appropriate simulation
   *                                          type
   * @throws NewFileNotCorrectTypeException   if the model does not have a .sim extension
   */
  public static SimulationModel createModelFromPath(String path)
      throws IOException, InvalidSimGridDataException, SimConfigurationMissingException,
      InvalidSimTypeException, NewFileNotCorrectTypeException {
    if (!path.contains(".sim")) {
      throw new NewFileNotCorrectTypeException(ResourceUtil.getResourceValue(
          "NewFileNotCorrectType"));
    }
    Properties myResources = new Properties();
    myResources.load(new FileInputStream(path));
    String simulationType = myResources.getProperty("SimulationType");
    String title = myResources.getProperty("Title");
    String fileName = myResources.getProperty("Filename");
    String imagesUsed = myResources.getProperty("ImagesUsed");
    String colorsUsed = myResources.getProperty("ColorsUsed");
    String edgeType = myResources.getProperty("EdgePolicy");
    String neighborType = myResources.getProperty("NeighborhoodType");
    validateSimType(simulationType, edgeType, neighborType);
    List<String[]> allEntries = readFile(fileName, title, simulationType);
    return populateSimModel(allEntries, simulationType, title, imagesUsed,
        colorsUsed, edgeType, neighborType);
  }

  private static void validateSimType(String simulationType, String edgeType, String neighborType)
      throws InvalidSimTypeException {
    if (!SimulationModelType.isValidModel(simulationType) || !EdgePolicy.isValidModel(edgeType)
        || !NeighborhoodType.isValidModel(neighborType)) {
      throw new InvalidSimTypeException(ResourceUtil.getResourceValue("SimTypeNotValid"));
    }
  }

  /**
   * creates a data structure based on .csv file contents
   *
   * @param fileName       .csv file
   * @param title          title of the simulation
   * @param simulationType type of the simulation
   * @return a data structure that holds all cell values in the simulation
   * @throws SimConfigurationMissingException if the .csv does not exist
   */
  public static List<String[]> readFile(String fileName,
      String title, String simulationType) throws SimConfigurationMissingException {
    if (StringUtils.isEmpty(fileName) || StringUtils.isEmpty(title)) {
      throw new SimConfigurationMissingException(ResourceUtil.getResourceValue(
          "SimConfigMissingRequiredFields"));
    } else {
      return SimulationReader.read(fileName, simulationType);
    }
  }

  private static SimulationModel populateSimModel(List<String[]> allEntries, String type,
      String title, String imagesUsed, String colorsUsed,
      String edgeType, String neighborType) throws IOException {
    try {
      cellsociety.model.EdgePolicy newPolicy = createEdgePolicy(allEntries.size() - 1,
          allEntries.get(1).length,
          edgeType);
      Neighborhood newNeighborhood = createNeighborhood(newPolicy, neighborType);
      SimulationModel newSimulation = createSimulation(type, allEntries, title, newNeighborhood,
          newPolicy);
      populateCellModels(newSimulation, allEntries, type);
      newSimulation.setImageMappings(createDisplayMap(newSimulation, imagesUsed));
      newSimulation.setColorMappings(createDisplayMap(newSimulation, colorsUsed));
      return newSimulation;
    } catch (Exception e) {
      throw new IOException();
    }
  }

  private static void populateCellModels(SimulationModel newSimulation, List<String[]> allEntries,
      String type)
      throws Exception {
    Class<?> stateClass = Class.forName(ENUM_PATH + type + STATE);
    Method fromIntMethod = stateClass.getMethod(FROM_INT_METHOD, Integer.class);
    for (int row = 0; row < allEntries.size() - 1; row++) {
      for (int column = 0; column < allEntries.get(row + 1).length; column++) {
        newSimulation.addCellModel(row, column,
            (State) fromIntMethod
                .invoke(null, Integer.parseInt(allEntries.get(row + 1)[column])));
      }
    }
  }

  private static SimulationModel createSimulation(String type, List<String[]> allEntries,
      String title, Neighborhood neighborType,
      cellsociety.model.EdgePolicy edgeType)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
    Class<?> modelClass = Class.forName(MODEL_PATH + type + MODEL);
    Class<?>[] modelConstructorParameters = {List.class, String.class, Neighborhood.class,
        cellsociety.model.EdgePolicy.class};
    Constructor<?> constructors = modelClass.getConstructor(modelConstructorParameters);
    Object[] constructorObjects = {allEntries, title, neighborType, edgeType};
    return (SimulationModel) constructors
        .newInstance(constructorObjects);
  }

  private static cellsociety.model.EdgePolicy createEdgePolicy(int numRows, int numCols,
      String edgeType)
      throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException, ClassNotFoundException {
    Class<?> edgeClass = Class.forName(MODEL_PATH + edgeType + EDGE_POLICY);
    Class<?>[] edgeConstructorParameters = {int.class, int.class};
    Constructor<?> constructors = edgeClass.getConstructor(edgeConstructorParameters);
    Object[] edgeConstructorObjects = {numRows, numCols};
    return (cellsociety.model.EdgePolicy) constructors.newInstance(edgeConstructorObjects);
  }

  private static Neighborhood createNeighborhood(cellsociety.model.EdgePolicy newPolicy,
      String neighborType)
      throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    Class<?> neighborhoodClass = Class.forName(MODEL_PATH + neighborType + NEIGHBORHOOD);
    Class<?>[] neighborConstructorParams = {cellsociety.model.EdgePolicy.class};
    Constructor<?> constructors = neighborhoodClass.getConstructor(neighborConstructorParams);
    Object[] neighborConstructorObjects = {newPolicy};
    return (Neighborhood) constructors.newInstance(neighborConstructorObjects);
  }

  private static Map<String, String> createDisplayMap(SimulationModel newSimulation,
      String displayUsed) {
    Map<String, String> displayMap = new HashMap<>();
    if (!displayUsed.equals("")) {
      for (int index = 0; index < newSimulation.getEnums().length; index++) {
        displayMap.put(newSimulation.getEnums()[index].toString(), displayUsed.split(",")[index]);
      }
    }
    return displayMap;
  }

  /**
   * determines next state based on the current state
   *
   * @param currentState current state
   * @return next state
   */
  public static State determineNextState(State currentState) {
    try {
      Method nextState = currentState.getClass().getMethod("values");
      State[] allValues = ((State[]) nextState.invoke(null));
      return nextState(allValues, currentState);
    } catch (Exception e) {
      return currentState;
    }
  }

  public static String getSimModelName(String simModelPath) {
    String[] splitPath = simModelPath.split("\\.");
    String modelName = splitPath[splitPath.length - 1];
    return modelName.substring(0, modelName.length() - MODEL_LENGTH);
  }

  public static int getNumberOfStates(String type) {
    State[] states = getStates(type);
    return states.length - 1;
  }

  public static State[] getStates(String type) {
    try {
      Class<?> stateClass = Class.forName(ENUM_PATH + type + STATE);
      Method getStateIndex = stateClass.getMethod("values");
      return (State[]) getStateIndex.invoke(null);
    } catch (Exception e) {
      return new State[1];
    }
  }

  public static int getStateIndex(State state, String type) {
    State[] states = getStates(type);
    return getIndex(states, state);
  }

  private static int getIndex(State[] states, State state) {
    for (int i = 0; i < states.length; i++) {
      if (states[i] == state) {
        return i;
      }
    }
    return -1;
  }

  private static State nextState(State[] allStates, State currentState) {
    for (int i = 0; i < allStates.length; i++) {
      if (allStates[i].equals(currentState)) {
        if (i == allStates.length - 1) {
          return allStates[0];
        } else {
          return allStates[i + 1];
        }
      }
    }
    return allStates[0];
  }

  /**
   * creates a new simulation
   *
   * @param fileName .sim file for new simulation
   * @param newStage new stage to be populated
   */
  public static void newSimulation(String fileName, Stage newStage) {
    SimulationModel mySim;
    try {
      if (!fileName.contains(".sim")) {
        throw new NewFileNotCorrectTypeException(ResourceUtil.getResourceValue(
            "NewFileNotCorrectType"));
      }
      mySim = SimulationModelUtil.createModelFromPath(fileName);
      SimulationScreen display = new GridScreen(mySim);
      display.startSimulation(newStage);
    } catch (GridDataNotFoundException | InvalidSimGridDataException |
        SimConfigurationMissingException | InvalidSimTypeException | NewFileNotCorrectTypeException | IOException exception) {
      createErrorMessageDisplay(newStage, exception);
    }
  }

  /**
   * creates a new graph simulation
   *
   * @param myModel  simulation model
   * @param newStage a stage for display
   */
  public static void newGraphSimulation(SimulationModel myModel, Stage newStage) {
    SimulationScreen graphDisplay = new GraphScreen(myModel);
    graphDisplay.startSimulation(newStage);
  }

  /**
   * creates a new grid simulation
   *
   * @param myModel  simulation model
   * @param newStage a stage for display
   */
  public static void newGridSimulation(SimulationModel myModel, Stage newStage) {
    SimulationScreen gridDisplay = new GridScreen(myModel);
    gridDisplay.startSimulation(newStage);
  }

  private static void createErrorMessageDisplay(Stage stage, Exception exception) {
    Scene scene = new Scene(new Pane());
    BorderPane root = new BorderPane();
    HBox titleNode = new HBox();
    Text Title = new Text(ResourceUtil.getResourceValue(ERROR_MESSAGE));
    Title.setId("SimulationTitle");
    titleNode.getChildren().add(Title);
    root.setTop(titleNode);
    Text errorMessage = new Text(exception.getMessage());
    errorMessage.setId("error");
    root.setCenter(errorMessage);
    scene.setRoot(root);
    stage.setScene(scene);
    stage.show();
  }
}
