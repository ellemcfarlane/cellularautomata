# Cellular Automata GUI with Coarse-Graining Visualization
This GUI allows 2D visualization of 1D, elementary cellular automaton with cell states in the x-axis and time in the y-axis. It also allows coarse-graining of a cellular-automaton rule and animated growth.

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

## Cellular Automata and Coarse-graining
[Cellular automata](https://en.wikipedia.org/wiki/Cellular_automaton) (CA)
were invented in the 1940s by John von Neumann to model 
complex systems in which group behavior emerges despite a lack of central
authority. Elementary CA, per Stephen Wolfram's lexicon, 
consist of three-cell neighborhoods in which each cell is either a 1 or a 0. 
A particular CA rule determines the state of a cell in the next generation
based off the cell's current state and the state of the cell to its left and right.
In other words, a CA rule is a function that maps three inputs to one output. 
Because there are 2^3 = 8 possible neighborhood states that can map to
either a 1 or 0, there are a total of 2^8 = 256 CA rules from 0-255 where
each rule number represents its output when converted to binary.
Example mapping for CA rule 110 (01101110 in binary):

{ 1,1,1 -> 0  
  &nbsp;&nbsp;1,1,0 -> 1  
  &nbsp;&nbsp;1,0,1 -> 1  
  &nbsp;&nbsp;1,0,0 -> 0  
  &nbsp;&nbsp;0,1,1 -> 1  
  &nbsp;&nbsp;0,1,0 -> 1  
  &nbsp;&nbsp;0,0,1 -> 1  
  &nbsp;&nbsp;0,0,0 -> 0 }

[Coarse-graining](https://en.wikipedia.org/wiki/Coarse-grained_modeling) involves simplifying
data while attempting to retain the essential components of the original data.
Coarse-graining space-time diagrams of cellular automata may elucidate interesting properties about the rules.

## Tests
Run the TestCA1D.java and TestCA2D.java files to run tests.

## Acknowledgements
Created with guide from Daniel Shiffman's [The Nature of Code, Chapter 7](https://natureofcode.com/book/chapter-7-cellular-automata/).
