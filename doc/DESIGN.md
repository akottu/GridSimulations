# Simulation Design Final
### Anish Kottu (ak313), Hosam Tagel-Din (ht120), James Arnold (jra55)

## Team Roles and Responsibilities

 * Team Member #1 - Anish
    * Util package
    * exceptions package

 * Team Member #2 - Hosam
    * view package
    * Development and assistance in the util classes

 * Team Member #3 - James
    * model package
    * creating sim and csv files


## Design goals

#### What Features are Easy to Add
Implementing different types of views
Implementing/modifying types of simulations
Adding new neighborhood types and edgetypes
Adding new states


## High-level Design

#### Core Classes
SimulationModel, SimulationScreen, and SimulationModelUtil are the core classes for the MVC structure
The model handles the simulation rules and processes each "step", it receives data from util and passes data to view
The screen handles the visualization of the simulation and allows for user input to change/interact with the simulation
The Util handles the initial setup of the simulation and serves as communication between the model and the view

## Assumptions that Affect the Design

#### Features Affected by Assumptions
The original implementation assumed that cells would only have one value as their state, which made WaTor a bit messy.
Images referenced by user must exist in the resources folder and the input needs to include the filename.fileextension.
For example, "water.jpg" is a valid input for selecting images.

## Significant differences from Original Plan
Other than having different names from what we discussed in PLAN.md, the general structure of the program remains the same

## New Features HowTo

#### Easy to Add Features
Implementing different types of views - extend SimulationScreen so that the new view will have all the basic functionality UI, it should have a visualizer, which is essentially the core that handles the visual output of the simulation.
Implementing/modifying types of simulations - extend SimulationModel, write out the game rules, and add the corresponding enums
Adding new neighborhood types and edgetypes - extend the Neighborhood and EdgePolicy parent classes and add the desired implementation
Adding new states - just add the enum and update the .css file so that it can have a default view that's dislayed visually

#### Other Features not yet Done
Adding different shaped cells would require different kinds of neighborhoods, the view would have to adjust the shape of the displayed features (extend grid to have triangulargrid)
