package cellsociety.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import cellsociety.enums.ConwayState;
import cellsociety.enums.FireSpreadState;
import cellsociety.enums.PercolationState;
import cellsociety.enums.RockPaperScissorsState;
import cellsociety.enums.SegregationState;
import cellsociety.enums.WaTorState;
import cellsociety.exception.InvalidSimGridDataException;
import cellsociety.exception.InvalidSimTypeException;
import cellsociety.exception.SimConfigurationMissingException;
import cellsociety.model.SimulationModel;
import cellsociety.util.SimulationModelUtil;
import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * Tests for Model class.
 *
 * @author Robert C Duvall
 */
class ModelTest {

  private SimulationModel mySim;

  private void makeGame(String pathName)
      throws InvalidSimGridDataException, SimConfigurationMissingException, InvalidSimTypeException {
    try {
      mySim = SimulationModelUtil.createModelFromPath("testresources/" + pathName);
    } catch (IOException e) {
      mySim = null;
    }
  }

  @Test
  void testBasicSetup()
      throws InvalidSimGridDataException, SimConfigurationMissingException, InvalidSimTypeException {
    makeGame("centerBlock.sim");
    assertEquals(4, mySim.getNumberOfColumns());
    assertEquals(4, mySim.getNumberOfRows());
    for (int row = 0; row < 4; row++) {
      for (int column = 0; column < 4; column++) {
        if ((row == 1 || row == 2) && (column == 1 || column == 2)) {
          assertEquals(ConwayState.ALIVE, mySim.getCellState(row, column));
        } else {
          assertEquals(ConwayState.DEAD, mySim.getCellState(row, column));
        }
      }
    }
  }

  @Test
  void testBasicStepBlock()
      throws InvalidSimGridDataException, SimConfigurationMissingException, InvalidSimTypeException {
    makeGame("centerBlock.sim");
    mySim.nextStep();
    for (int row = 0; row < 4; row++) {
      for (int column = 0; column < 4; column++) {
        if ((row == 1 || row == 2) && (column == 1 || column == 2)) {
          assertEquals(ConwayState.ALIVE, mySim.getCellState(row, column));
        } else {
          assertEquals(ConwayState.DEAD, mySim.getCellState(row, column));
        }
      }
    }
  }

  @Test
  void testBasicStepBlinker()
      throws InvalidSimGridDataException, SimConfigurationMissingException, InvalidSimTypeException {
    makeGame("centerBlinker.sim");
    mySim.nextStep();
    for (int row = 0; row < 5; row++) {
      for (int column = 0; column < 5; column++) {
        if ((column == 1 || column == 2 || column == 3) && (row == 2)) {
          assertEquals(ConwayState.ALIVE, mySim.getCellState(row, column));
        } else {
          assertEquals(ConwayState.DEAD, mySim.getCellState(row, column));
        }
      }
    }
    mySim.nextStep();
    for (int row = 0; row < 5; row++) {
      for (int column = 0; column < 5; column++) {
        if ((row == 1 || row == 2 || row == 3) && (column == 2)) {
          assertEquals(ConwayState.ALIVE, mySim.getCellState(row, column));
        } else {
          assertEquals(ConwayState.DEAD, mySim.getCellState(row, column));
        }
      }
    }
  }

  @Test
  void testBlinkerOnEdge()
      throws InvalidSimGridDataException, SimConfigurationMissingException, InvalidSimTypeException {
    makeGame("edgeBlinker.sim");
    mySim.nextStep();
    assertEquals(ConwayState.ALIVE, mySim.getCellState(2, 0));
    assertEquals(ConwayState.ALIVE, mySim.getCellState(2, 1));
    assertEquals(ConwayState.ALIVE, mySim.getCellState(2, 2));
    mySim.nextStep();
    assertEquals(ConwayState.ALIVE, mySim.getCellState(1, 1));
    assertEquals(ConwayState.ALIVE, mySim.getCellState(2, 1));
    assertEquals(ConwayState.ALIVE, mySim.getCellState(3, 1));
  }

  @Test
  void testBlinkerCutOff()
      throws InvalidSimGridDataException, SimConfigurationMissingException, InvalidSimTypeException {
    makeGame("cutOffBlinker.sim");
    mySim.nextStep();
    assertEquals(ConwayState.ALIVE, mySim.getCellState(0, 1));
    assertEquals(ConwayState.ALIVE, mySim.getCellState(1, 1));
    mySim.nextStep();
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        assertEquals(ConwayState.DEAD, mySim.getCellState(row, col));
      }
    }
  }

  @Test
  void testBasicStepToad()
      throws InvalidSimGridDataException, SimConfigurationMissingException, InvalidSimTypeException {
    makeGame("centerToad.sim");
    mySim.nextStep();
    for (int row = 0; row < 6; row++) {
      for (int column = 0; column < 6; column++) {
        if (row == 1 && column == 3) {
          assertEquals(ConwayState.ALIVE, mySim.getCellState(row, column));
        } else if ((row == 2 || row == 3) && (column == 1 || column == 4)) {
          assertEquals(ConwayState.ALIVE, mySim.getCellState(row, column));
        } else if (row == 4 && column == 2) {
          assertEquals(ConwayState.ALIVE, mySim.getCellState(row, column));
        } else {
          assertEquals(ConwayState.DEAD, mySim.getCellState(row, column));
        }
      }
    }
  }

  @Test
  void testBasicBeacon()
      throws InvalidSimGridDataException, SimConfigurationMissingException, InvalidSimTypeException {
    makeGame("centerBeacon.sim");
    mySim.nextStep();
    assertEquals(ConwayState.ALIVE, mySim.getCellState(2, 2));
    assertEquals(ConwayState.ALIVE, mySim.getCellState(3, 3));
    mySim.nextStep();
    assertEquals(ConwayState.DEAD, mySim.getCellState(2, 2));
    assertEquals(ConwayState.DEAD, mySim.getCellState(3, 3));
  }

  @Test
  void testBasicTub()
      throws InvalidSimGridDataException, SimConfigurationMissingException, InvalidSimTypeException {
    makeGame("centerTub.sim");
    mySim.nextStep();
    assertEquals(ConwayState.ALIVE, mySim.getCellState(1, 2));
    mySim.nextStep();
    assertEquals(ConwayState.ALIVE, mySim.getCellState(1, 2));
    mySim.nextStep();
    assertEquals(ConwayState.ALIVE, mySim.getCellState(1, 2));
  }

  @Test
  void testEdgeBlock()
      throws InvalidSimGridDataException, SimConfigurationMissingException, InvalidSimTypeException {
    makeGame("edgeBlock.sim");
    mySim.nextStep();
    assertEquals(ConwayState.ALIVE, mySim.getCellState(1, 0));
    mySim.nextStep();
    assertEquals(ConwayState.ALIVE, mySim.getCellState(1, 0));
  }

  @Test
  void testPercolationAdjacent()
      throws InvalidSimGridDataException, SimConfigurationMissingException, InvalidSimTypeException {
    makeGame("testPercolation.sim");
    mySim.nextStep();
    assertEquals(PercolationState.FULL, mySim.getCellState(0, 0));
    assertEquals(PercolationState.FULL, mySim.getCellState(0, 2));
    assertEquals(PercolationState.FULL, mySim.getCellState(1, 1));
  }

  @Test
  void testPercolationNotDiagonal()
      throws InvalidSimGridDataException, SimConfigurationMissingException, InvalidSimTypeException {
    makeGame("testPercolation2.sim");
    mySim.nextStep();
    assertEquals(PercolationState.EMPTY, mySim.getCellState(1, 0));
    mySim.nextStep();
    assertEquals(PercolationState.EMPTY, mySim.getCellState(1, 0));
  }

  @Test
  void testPercolationOnlyStepOnce()
      throws InvalidSimGridDataException, SimConfigurationMissingException, InvalidSimTypeException {
    makeGame("testPercolation3.sim");
    mySim.nextStep();
    assertEquals(PercolationState.FULL, mySim.getCellState(1, 1));
    assertEquals(PercolationState.EMPTY, mySim.getCellState(2, 1));
    mySim.nextStep();
    assertEquals(PercolationState.FULL, mySim.getCellState(1, 1));
    assertEquals(PercolationState.FULL, mySim.getCellState(2, 1));
  }

  @Test
  void testRPSBasic()
      throws InvalidSimGridDataException, SimConfigurationMissingException, InvalidSimTypeException {
    makeGame("testRockPaperScissors.sim");
    mySim.nextStep();
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        assertEquals(RockPaperScissorsState.ROCK, mySim.getCellState(row, col));
      }
    }
  }

  @Test
  void testRPSThreshold()
      throws InvalidSimGridDataException, SimConfigurationMissingException, InvalidSimTypeException {
    makeGame("testRockPaperScissors2.sim");
    mySim.nextStep();
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        if (row != 1 || col != 1) {
          assertEquals(RockPaperScissorsState.ROCK, mySim.getCellState(row, col));
        } else {
          assertEquals(RockPaperScissorsState.PAPER, mySim.getCellState(row, col));
        }
      }
    }
  }

  @Test
  void testRPSCorrectWinConditions()
      throws InvalidSimGridDataException, SimConfigurationMissingException, InvalidSimTypeException {
    makeGame("testRockPaperScissors3.sim");
    mySim.nextStep();
    assertEquals(RockPaperScissorsState.PAPER, mySim.getCellState(1, 0));
    assertEquals(RockPaperScissorsState.SCISSORS, mySim.getCellState(4, 0));
    assertEquals(RockPaperScissorsState.ROCK, mySim.getCellState(7, 0));
    assertEquals(RockPaperScissorsState.PAPER, mySim.getCellState(10, 0));
    assertEquals(RockPaperScissorsState.SCISSORS, mySim.getCellState(13, 0));
    assertEquals(RockPaperScissorsState.ROCK, mySim.getCellState(16, 0));
  }

  @Test
  void testFireBasic()
      throws InvalidSimGridDataException, SimConfigurationMissingException, InvalidSimTypeException {
    makeGame("testFireSpread.sim");
    mySim.nextStep();
    assertEquals(FireSpreadState.BURNING, mySim.getCellState(3, 3));
    assertEquals(FireSpreadState.BURNING, mySim.getCellState(3, 4));
    assertEquals(FireSpreadState.BURNING, mySim.getCellState(3, 5));
    assertEquals(FireSpreadState.BURNING, mySim.getCellState(4, 3));
    assertEquals(FireSpreadState.EMPTY, mySim.getCellState(4, 4));
    assertEquals(FireSpreadState.BURNING, mySim.getCellState(4, 5));
    assertEquals(FireSpreadState.BURNING, mySim.getCellState(5, 3));
    assertEquals(FireSpreadState.BURNING, mySim.getCellState(5, 4));
    assertEquals(FireSpreadState.BURNING, mySim.getCellState(5, 5));
  }

  @Test
  void testFireReGrowth()
      throws InvalidSimGridDataException, SimConfigurationMissingException, InvalidSimTypeException {
    makeGame("testFireSpread2.sim");
    mySim.nextStep();
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        assertEquals(FireSpreadState.TREE, mySim.getCellState(i, j));
      }
    }
  }

  @Test
  void testFireSpontaneousSpread()
      throws InvalidSimGridDataException, SimConfigurationMissingException, InvalidSimTypeException {
    makeGame("testFireSpread3.sim");
    mySim.nextStep();
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        assertEquals(FireSpreadState.BURNING, mySim.getCellState(i, j));
      }
    }
  }

  @Test
  void testSegregationAlwaysSatisfied()
      throws InvalidSimGridDataException, SimConfigurationMissingException, InvalidSimTypeException {
    makeGame("testSegregation.sim");
    mySim.nextStep();
    assertEquals(SegregationState.X, mySim.getCellState(0, 1));
    assertEquals(SegregationState.O, mySim.getCellState(4, 1));
  }

  @Test
  void testSegregationNeverSatisfied()
      throws InvalidSimGridDataException, SimConfigurationMissingException, InvalidSimTypeException {
    makeGame("testSegregation2.sim");
    mySim.nextStep();
    assertNotEquals(SegregationState.X, mySim.getCellState(0, 1));
  }

  @Test
  void testSegregationThreshold()
      throws InvalidSimGridDataException, SimConfigurationMissingException, InvalidSimTypeException {
    makeGame("testSegregation3.sim");
    mySim.nextStep();
    assertEquals(SegregationState.EMPTY, mySim.getCellState(1, 1));
  }

  @Test
  void testWaTorSharkPreferFish()
      throws InvalidSimGridDataException, SimConfigurationMissingException, InvalidSimTypeException {
    makeGame("testWaTor.sim");
    mySim.nextStep();
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        if (row == 0 && col == 1) {
          assertEquals(WaTorState.SHARK, mySim.getCellState(row, col));
        } else {
          assertEquals(WaTorState.WATER, mySim.getCellState(row, col));
        }
      }
    }
  }

  @Test
  void testWaTorSharkAlwaysMove()
      throws InvalidSimGridDataException, SimConfigurationMissingException, InvalidSimTypeException {
    makeGame("testWaTor2.sim");
    mySim.nextStep();
    int sharkCount = 0;
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        if (row == 1 && col == 1) {
          assertEquals(WaTorState.WATER, mySim.getCellState(row, col));
        } else if (mySim.getCellState(row, col) == WaTorState.SHARK) {
          sharkCount += 1;
        }
      }
    }
    assertEquals(1, sharkCount);
  }

  @Test
  void testWaTorSharkStarve()
      throws InvalidSimGridDataException, SimConfigurationMissingException, InvalidSimTypeException {
    makeGame("testWaTor2.sim");
    mySim.nextStep();
    mySim.nextStep();
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        assertEquals(WaTorState.WATER, mySim.getCellState(row, col));
      }
    }
  }

  @Test
  void testFishBreed()
      throws InvalidSimGridDataException, SimConfigurationMissingException, InvalidSimTypeException {
    makeGame("testWaTor3.sim");
    mySim.nextStep();
    int fishCount = 0;
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        if (mySim.getCellState(row, col) == WaTorState.FISH) {
          fishCount += 1;
        }
      }
    }
    assertEquals(2, fishCount);
  }

  @Test
  void testSharkBreed()
      throws InvalidSimGridDataException, SimConfigurationMissingException, InvalidSimTypeException {
    makeGame("testWaTor4.sim");
    mySim.nextStep();
    int sharkCount = 0;
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        if (mySim.getCellState(row, col) == WaTorState.SHARK) {
          sharkCount += 1;
        }
      }
    }
    assertEquals(2, sharkCount);
  }

  @Test
  void testCardinalNeighborsConway()
      throws InvalidSimTypeException, SimConfigurationMissingException, InvalidSimGridDataException {
    makeGame("testCardinal1.sim");
    mySim.nextStep();
    assertEquals(ConwayState.ALIVE, mySim.getCellState(1,1));
  }

  @Test
  void testCardinalNeighborsShark()
      throws InvalidSimTypeException, SimConfigurationMissingException, InvalidSimGridDataException {
    makeGame("testCardinal2.sim");
    mySim.nextStep();
    boolean sharkFound=false;
    for (int i =0;i<3;i++) {
      for (int j=0; j<3;j++) {
        if ((i==1 && j==0)||(i==0&&j==1)||(i==2&&j==1)||(j==2&&i==1)) {
          if(mySim.getCellState(i,j)==WaTorState.SHARK) {
            sharkFound=true;
          }
        }
      }
    }
    assertEquals(true, sharkFound);
  }

  @Test
  void testCompleteNeighborsConwayBeacon()
      throws InvalidSimTypeException, SimConfigurationMissingException, InvalidSimGridDataException {
    makeGame("centerBeacon.sim");
    mySim.nextStep();
    assertEquals(ConwayState.ALIVE, mySim.getCellState(2, 2));
    assertEquals(ConwayState.ALIVE, mySim.getCellState(3, 3));
    mySim.nextStep();
    assertEquals(ConwayState.DEAD, mySim.getCellState(2, 2));
    assertEquals(ConwayState.DEAD, mySim.getCellState(3, 3));
  }

  @Test
  void testCompleteNeighborsRPS2()
      throws InvalidSimTypeException, SimConfigurationMissingException, InvalidSimGridDataException {
    makeGame("testRockPaperScissors2.sim");
    mySim.nextStep();
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        if (row != 1 || col != 1) {
          assertEquals(RockPaperScissorsState.ROCK, mySim.getCellState(row, col));
        } else {
          assertEquals(RockPaperScissorsState.PAPER, mySim.getCellState(row, col));
        }
      }
    }
  }

  @Test
  void testCornerNeighbors()
      throws InvalidSimTypeException, SimConfigurationMissingException, InvalidSimGridDataException {
    makeGame("testCorner1.sim");
    mySim.nextStep();
    assertEquals(ConwayState.ALIVE, mySim.getCellState(1,1));
  }

  @Test
  void testCornerNeighborsShark()
      throws InvalidSimTypeException, SimConfigurationMissingException, InvalidSimGridDataException {
    makeGame("testCorner2.sim");
    mySim.nextStep();
    assertEquals(WaTorState.SHARK, mySim.getCellState(2,2));
  }

  @Test
  void testFiniteNotOppositeWall()
      throws InvalidSimTypeException, SimConfigurationMissingException, InvalidSimGridDataException {
    makeGame("testFinite1.sim");
    mySim.nextStep();
    int numAlive = 0;
    for(int i= 0; i<3; i++) {
      for (int j=0; j<3; j++) {
        if (mySim.getCellState(i,j)==ConwayState.ALIVE) {
          numAlive+=1;
        }
      }
    }
    assertEquals(0, numAlive);
  }

  @Test
  void testFiniteNotOppositeCorners()
      throws InvalidSimTypeException, SimConfigurationMissingException, InvalidSimGridDataException {
    makeGame("testFinite2.sim");
    mySim.nextStep();
    int numAlive = 0;
    for(int i= 0; i<3; i++) {
      for (int j=0; j<3; j++) {
        if (mySim.getCellState(i,j)==ConwayState.ALIVE) {
          numAlive+=1;
        }
      }
    }
    assertEquals(0, numAlive);
  }

  @Test
  void testToroidOppositeWall()
      throws InvalidSimTypeException, SimConfigurationMissingException, InvalidSimGridDataException {
    makeGame("testToroid1.sim");
    mySim.nextStep();
    int numAlive = 0;
    for(int i= 0; i<3; i++) {
      for (int j=0; j<4; j++) {
        if (mySim.getCellState(i,j)==ConwayState.ALIVE) {
          numAlive+=1;
        }
      }
    }
    assertEquals(4, numAlive);
  }

  @Test
  void testToroidOppositeCorners()
      throws InvalidSimTypeException, SimConfigurationMissingException, InvalidSimGridDataException {
    makeGame("testToroid2.sim");
    mySim.nextStep();
    int numAlive=0;
    for(int i = 0; i<5; i++) {
      for (int j=0; j<3; j++) {
        if(mySim.getCellState(i,j)==ConwayState.ALIVE) {
          numAlive+=1;
        }
      }
    }
    assertEquals(3, numAlive);
  }

  @Test
  void testKleinOppositeWall()
      throws InvalidSimTypeException, SimConfigurationMissingException, InvalidSimGridDataException {
    makeGame("testKlein1.sim");
    mySim.nextStep();
    int numAlive=0;
    for(int i=0; i<5; i++) {
      for (int j=0; j<5; j++) {
        if (mySim.getCellState(i, j) == ConwayState.ALIVE) {
          numAlive+=1;
        }
      }
    }
    assertEquals(3, numAlive);
  }

  @Test
  void testKleinOppositeCorner()
      throws InvalidSimTypeException, SimConfigurationMissingException, InvalidSimGridDataException {
    makeGame("testKlein2.sim");
    mySim.nextStep();
    int numAlive=0;
    for(int i=0; i<5; i++) {
      for (int j=0; j<5; j++) {
        if (mySim.getCellState(i, j) == ConwayState.ALIVE) {
          numAlive+=1;
        }
      }
    }
    assertEquals(4, numAlive);
  }
}
