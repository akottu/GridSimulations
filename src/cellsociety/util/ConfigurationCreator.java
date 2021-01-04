package cellsociety.util;

import cellsociety.enums.State;
import cellsociety.exception.NewFileNotCorrectTypeException;
import cellsociety.model.SimulationModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Helps to create a new configuration
 */
public final class ConfigurationCreator {

  private final static int FIRST_STATE_INDEX = 0;
  private final static int SECOND_STATE_INDEX = 1;
  private final static int THIRD_STATE_INDEX = 2;
  private final static int THREE_STATES = 3;

  private ConfigurationCreator() {
    //not called but need a private constructor since it is a util class
  }

  /**
   * Creates a new configuration based on probability inputs
   *
   * @param currModel     current model
   * @param probabilities probability inputs
   * @param title         title of the simulation
   * @param description   description of the simulation
   * @param author        author of the simulation
   * @return the new simulation model based on the probabilities
   */
  public static SimulationModel saveProbabilityConfig(SimulationModel currModel,
      double[] probabilities,
      String title, String description, String author) {
    try {
      SimulationModel updateModel = currModel.copy();
      updateProbModel(updateModel, probabilities);
      SimulationWriter.saveSimulationConfig(updateModel, title, description, author);
      updateModel.setTitle(title);
      return updateModel;
    } catch (CloneNotSupportedException e) {
      throw new NewFileNotCorrectTypeException("Error");
    }
  }

  private static void updateProbModel(SimulationModel updateModel, double[] probabilities) {
    State[] states =
        SimulationModelUtil
            .getStates(SimulationModelUtil.getSimModelName(updateModel.getClass().getName()));
    updateCellModels(states, updateModel, probabilities);
  }

  private static void updateCellModels(State[] states, SimulationModel updateModel,
      double[] probabilities) {
    for (int i = 0; i < updateModel.getNumberOfRows(); i++) {
      for (int j = 0; j < updateModel.getNumberOfColumns(); j++) {
        double randomNumber = Math.random();
        int stateIndex = findIndex(randomNumber, probabilities);
        updateModel.addCellModel(i, j, states[stateIndex]);
      }
    }
  }

  private static int findIndex(double randomNumber, double[] probabilities) {
    if (randomNumber <= probabilities[FIRST_STATE_INDEX]) {
      return FIRST_STATE_INDEX;
    } else if (randomNumber
        <= probabilities[FIRST_STATE_INDEX] + probabilities[SECOND_STATE_INDEX]) {
      return SECOND_STATE_INDEX;
    } else {
      return THIRD_STATE_INDEX;
    }
  }

  /**
   * Creates a new configuration based on frequency inputs
   *
   * @param currModel   current model
   * @param frequencies frequency inputs
   * @param title       title of the simulation
   * @param description description of the simulation
   * @param author      author of the simulation
   * @return the new simulation model based on the frequencies
   */
  public static SimulationModel saveFrequencyConfig(SimulationModel currModel, int[] frequencies,
      String title,
      String description, String author) {
    try {
      SimulationModel updateModel = currModel.copy();
      updateFreqModel(updateModel, frequencies);
      SimulationWriter.saveSimulationConfig(updateModel, title, description, author);
      updateModel.setTitle(title);
      return updateModel;
    } catch (CloneNotSupportedException e) {
      throw new NewFileNotCorrectTypeException("Error");
    }
  }

  private static void updateFreqModel(SimulationModel currModel, int[] frequencies) {
    State[] states =
        SimulationModelUtil
            .getStates(SimulationModelUtil.getSimModelName(currModel.getClass().getName()));
    int state3Freq = 0;
    if (frequencies.length == THREE_STATES) {
      state3Freq = frequencies[THIRD_STATE_INDEX];
    }
    List<State> stateConfig = createStateConfig(states, frequencies[FIRST_STATE_INDEX],
        frequencies[SECOND_STATE_INDEX], state3Freq);
    Collections.shuffle(stateConfig);
    updateCellModels(currModel, stateConfig);
  }

  private static void updateCellModels(SimulationModel currModel, List<State> stateConfig) {
    for (int i = 0; i < currModel.getNumberOfRows(); i++) {
      for (int j = 0; j < currModel.getNumberOfColumns(); j++) {
        currModel.addCellModel(i, j, stateConfig.get(i * currModel.getNumberOfColumns() + j));
      }
    }
  }

  private static List<State> createStateConfig(State[] states, int state1Freq, int state2Freq,
      int state3Freq) {
    List<State> stateConfig = new ArrayList<>();
    addAll(stateConfig, state1Freq, FIRST_STATE_INDEX, states);
    addAll(stateConfig, state2Freq, SECOND_STATE_INDEX, states);
    addAll(stateConfig, state3Freq, THIRD_STATE_INDEX, states);
    return stateConfig;
  }

  private static void addAll(List<State> stateConfig, int stateFreq, int stateIndex,
      State[] states) {
    for (int i = 0; i < stateFreq; i++) {
      stateConfig.add(states[stateIndex]);
    }
  }

}
