# Simulation Lab Discussion
## Anish Kottu ak313, Hosam Tageldin ht120, James Arnold jra55


## Rock Paper Scissors

### High Level Design Ideas

Weapon class with String name and List beats that shows what a specific type of weapon beats, Player
 parent class (Userand Computer subclasses), FileReader class to read from a config file.

### CRC Card Classes

```java
public class FileReader {
    public readFile(File f) { }
}
```

```java
public class Weapon {
    private String name;
    private List<String> beats;
    public Weapon getWinner(Weapon opponentWeapon) { }
}
```

```java
public class Player {
    private int score;
    private Weapon weapon;
    public void chooseWeapon() { }
}
```

```java
public class Main {
    List<Player> players;
    
    public void getWinner() {
        for(Player p: players) {
            for(Player q: players) {
                if(!p == q) {
                  p.getWeapon().getWinner(q);
                }
            } 
        }
    }
}
```

### Use Cases

 * A new game is started with five players, their scores are reset to 0.
 ```java
Player p1 = new Player();
// 4 more times
 ```

 * A player chooses his RPS "weapon" with which he wants to play for this round.
 ```java
  p1.chooseWeapon();
 ```

 * Given three players' choices, one player wins the round, and their scores are updated.
 ```java
    getWinner();
 ```

 * A new choice is added to an existing game and its relationship to all the other choices is updated.
 ```java
 Something thing = new Something();
 Value v = thing.getValue();
 v.update(13);
 ```

 * A new game is added to the system, with its own relationships for its all its "weapons".
 ```java
 Something thing = new Something();
 Value v = thing.getValue();
 v.update(13);
 ```


## Cell Society

### High Level Design Ideas


### CRC Card Classes

This class's purpose or value is to manage something:
```java
public class Something {
    public int getTotal (Collection<Integer> data)
    public Value getValue ()
}
```

This class's purpose or value is to be useful:
```java
public class Value {
    public void update (int data)
}
```

### Use Cases

* Apply the rules to a cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all of its neighbors)
```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```

* Move to the next generation: update all cells in a simulation from their current state to their next state
```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```

* Switch simulations: load a new simulation from a data file, replacing the current running simulation with the newly loaded one
```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```