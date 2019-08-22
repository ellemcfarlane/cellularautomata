# Cellular Automata GUI with Coarse-Graining Visualization
This GUI allows 2D visualization of 1D, elementary [cellular automaton](https://en.wikipedia.org/wiki/Cellular_automaton) with cell states in the x-axis and time in the y-axis. It also allows coarse-graining of a cellular-automaton rule and animated growth.

## Features
* Visualization of:
  * static cellular automaton
  * growing cellular automaton
  * coarse-grained cellular automaton (via majority rule)
* Access to cellular automaton data across time (2D) for research purposes

## Basic Usage
```
import com.elle.cellularautomata.*;

public static void main(String[] args) {
    CellAuto2D ca2d = new CellAuto2D(110, 100, 100);
    ca2d.display;
    ca2d.displayInfo();
    ca2d.getCells();
    ca2d.coarseGrain();
    ca2d.multiGrains(2);
    ca2d.getCellHistory(3);
    
    CellAuto1D ca1d = new CellAuto1D(110, 100, 100); // rule, gens, cells
    ca1d.display;
    ca1d.displayInfo();
    ca1d.quickGen(10);
}
```

### GUI
For pure interface usage, download the cellularautomata.jar file or run the CAGUI.java. See all other files for source code and access to cellular automata state data.

The GUI is preloaded with rule 110 and looks like this:

![Alt text](https://github.com/ellesummer/CellularAutomata/blob/master/screenshots/Rule110default.png?raw=true)

After pressing the coarse-grain button just once, it looks like this:
![Alt text](https://github.com/ellesummer/CellularAutomata/blob/master/screenshots/Rule110coarsegrain.png?raw=true)

## Tests
Run the TestCA1D.java and TestCA2D.java files to run tests.

### Acknowledgements
Created with guide from Daniel Shiffman's [The Nature of Code, Chapter 7](https://natureofcode.com/book/chapter-7-cellular-automata/).
