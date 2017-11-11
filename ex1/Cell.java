package ex1;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

abstract public class Cell {

    Integer col, row, gen, generationsToDo;
    Boolean value,oldValue;
    boolean[][][] results;
    Queue<Cell> threadWorkQueue;
    List<Cell> neighbors = new ArrayList<>();

    Boolean getOldValue() {
        return oldValue;
    }
    List<Cell> getNeighbors() {
        return neighbors;
    }
    Boolean getValue() {
        return value;
    }
    Integer getGen() {
        return gen;
    }

    Cell(Integer row, Integer col, Boolean value, Queue<Cell> threadWorkQueue, Integer generationsToDo,
         boolean[][][] results) {
        this.col = col;
        this.row = row;
        this.value = value;
        this.threadWorkQueue = threadWorkQueue;
        this.generationsToDo = generationsToDo;
        this.results = results;
        this.gen = 0;
    }

    void addNeighbor(Cell c) {
        neighbors.add(c);
    }

    abstract public cellUpdateResult updateValue();

    abstract public void addToWorkQueue(Integer genOfAdderCell);

    void addNeighborsToQueue() {
        for (Cell neighbor :
                neighbors) {
            /* add all cells that can now continue to the workQueue*/
            neighbor.addToWorkQueue(this.gen);
        }
    }

}

enum cellUpdateResult {
    CELL_UPDATE_SUCCESS,
    CELL_UPDATE_FAIL
}
