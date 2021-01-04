# Simulation Design Plan
### Team 10
### Names Anish Kottu (ak313), Hosam Tageldin (ht120), James Arnold (jra55)


## Design Overview
* FileReader -- class that reads from file
* Cell -- a “block” in a vast “grid”, can be dead or alive
* Grid -- contains cells in a data structure instance variable
* Game -- has implementation of game rules
* Scene -- has the appearance of the game on the frontend


## Design Details
* FileReader creates Cell objects with a specific variable to determine position in grid
* For displaying active cells, Scene needs to receive instruction from Game in regard to which cells are active and which are inactive
* Game stores the Cell objects created by FileReader and handles them

## Design Considerations
1. We didn’t know whether to use a Grid or a Cell class, but we decided to use both
    * The alternative was to use just a Grid class, or just a Cell class with an array of Cells in the Game class
2. We needed to figure out how to pass the Grid data to the Scene class
    * We decided to pass this data with a “getCellAt()” method that might get the value of a particular cell and use that to pass values to the Scene class

## User Interface

Here is our amazing UI:

![User Interface](UserInterface.PNG "Picture of potential User Interface")

* Users can press buttons to pause/resume the simulation.
* While the sim is paused, users can click step to step through the frames one at a time
* The step counter will be updated by one each frame


## Team Responsibilities

 * Team Member #1- Anish:
    * Primary -- Configuration section, providing files to test for other group members
    * Secondary -- Simulation section, in case James needs help later on


 * Team Member #2- Hosam:
    * Primary - Visualization section/ setting up the UI
    * Secondary -- Simulation section
    
 * Team Member #3- James:
    * Primary - Simulation section, creating test for simulation
    * Secondary - more simulation

