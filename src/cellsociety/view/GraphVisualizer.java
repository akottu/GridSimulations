package cellsociety.view;

import cellsociety.model.SimulationModel;
import cellsociety.util.ResourceUtil;
import java.util.Arrays;
import java.util.List;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * Displays the Graph View of the simulation which shows the counts of the cell states at each step
 * of the simulation
 *
 * @author Hosam Tageldin
 */
public class GraphVisualizer extends SimulationVisualizer {

  private static final String STEP_COUNT = "StepCount";
  private static final String CELL_COUNT = "CellCount";
  private static final int FIRST_STATE_INDEX = 0;
  private static final int SECOND_STATE_INDEX = 1;
  private static final int THIRD_STATE_INDEX = 2;

  private final Series<Number, Number> state1Data = new Series<>();
  private final Series<Number, Number> state2Data = new Series<>();
  private final Series<Number, Number> state3Data = new Series<>();
  private final int[] stateCounter;
  private int stepCounter;

  public GraphVisualizer(SimulationModel model) {
    super(model);
    stepCounter = 0;
    stateCounter = new int[model.getEnums().length];
  }

  private void initializeCounters() {
    Arrays.fill(stateCounter, 0);
  }

  /**
   * Initializes the line graph and updates it with the current states of the first step
   *
   * @return Pane holding the graph
   */
  @Override
  protected Pane createContent() {
    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel(ResourceUtil.getResourceValue(STEP_COUNT));
    yAxis.setLabel(ResourceUtil.getResourceValue(CELL_COUNT));
    LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
    countStates();
    state1Data.setName(myModel.getEnums()[0].toString());
    state2Data.setName(myModel.getEnums()[1].toString());
    lineChart.getData().add(state1Data);
    lineChart.getData().add(state2Data);
    if (myModel.getEnums().length > 2) {
      state3Data.setName(myModel.getEnums()[2].toString());
      lineChart.getData().add(state3Data);
    }
    return new StackPane(lineChart);
  }

  private void countStates() {
    initializeCounters();
    for (int row = 0; row < numberOfCellsInColumn; row++) {
      for (int column = 0; column < numberOfCellsInRow; column++) {
        int incrementIndex = findIndex(row, column);
        stateCounter[incrementIndex]++;
      }
    }
    addDataToGraph();
  }

  private int findIndex(int row, int col) {
    for (int i = 0; i < myModel.getEnums().length; i++) {
      if (myModel.getEnums()[i] == myModel.getCellState(row, col)) {
        return i;
      }
    }
    return -1;
  }

  private void addDataToGraph() {
    state1Data.getData().add(new XYChart.Data<>(stepCounter, stateCounter[FIRST_STATE_INDEX]));
    state2Data.getData().add(new XYChart.Data<>(stepCounter, stateCounter[SECOND_STATE_INDEX]));
    if (stateCounter.length > 2) {
      state3Data.getData().add(new XYChart.Data<>(stepCounter, stateCounter[THIRD_STATE_INDEX]));
    }
  }


  /**
   * Updates the View of the graph at each step of the simulation
   */
  @Override
  protected void updateView() {
    myModel.nextStep();
    stepCounter++;
    countStates();
    addDataToGraph();
  }

  /**
   * Returns the data of the graph for testing purposes
   *
   * @return the Data of the graph
   */
  protected List<Series<Number, Number>> getData() {
    return List.of(state1Data, state2Data, state3Data);
  }
}
