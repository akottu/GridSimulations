package cellsociety.model;

import cellsociety.enums.State;

class WaTorCellModel extends CellModel {

  private final int energyLeft;
  private final int turnsToBreed;
  private boolean eaten;

  WaTorCellModel(int inRowNumber, int inColumnNumber, State inState, int inEnergy,
      int inTurnsToBreed) {
    super(inRowNumber, inColumnNumber, inState);
    energyLeft = inEnergy;
    turnsToBreed = inTurnsToBreed;
    eaten = false;
  }

  boolean checkEaten() {
    return eaten;
  }

  void beEaten() {
    eaten = true;
  }

  int getEnergyLeft() {
    return energyLeft;
  }

  int getTurnsToBreed() {
    return turnsToBreed;
  }
}
