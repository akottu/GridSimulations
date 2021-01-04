simulation
====

This project implements a cellular automata simulator.

Names: Anish Kottu (ak313), James Arnold (jra55), Hosam Tagel-Din (ht120)

### Timeline

Start Date: Oct 2

Finish Date: Oct 19

Hours Spent:
James - 50
Anish - 45
Hosam - 50

### Primary Roles
Hosam - Visualization (view package)
Anish - Configuration (util package) and Exceptions
James - Simulation (model package)
Each person created tests for these packages as well.


### Resources Used
Used the Duke Application Test created by Dr. Duvall in testing the JavaFx components of our program. Also used the lab browser created by Dr. Duvall in giving us a framework to work off of. 

### Running the Program

Main class: Main

Data files needed: default.css, language.properties, at least one .sim/.csv pair

Features implemented:
Grid view of each simulation
Graph view of each simulation
Multiple windows to run multiple graph/grid simulations at once
User-defined generation of new sims (probability, total number of states, etc)
Multiple neighborhoods (Complete,Cardinal,Corner)
Multiple edge types (Finite,Klein,Toroid)


### Notes/Assumptions

Assumptions or Simplifications:
When a user writes in a file for using their own image, it must be within the resource file and the input must contain the file name and extension, for example "water.png".

Interesting data files:
rps1.sim has an interesting spiral look as the simulation progresses.
WaTorBasic.sim is as close to a never ending WaTor simulation as we could get.

Known Bugs:
If a user inputs a image name that does not exist for "Choose Images", then the cells with the invalid image name will appear as black cells. 

Extra credit:
Our config files contain the kind of grid to use in regards to edges and neighbors. Colors and names of the image files for the cell states are also included in config files. We used color selectors and file browsers to aid the user in selecting valid files/ colors. We also allow the users
to run multiple simulations (Grids and Graphs) at once if they desire.

### Impressions

