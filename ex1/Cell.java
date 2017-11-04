package ex1;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

abstract public class Cell {

    Integer col, row, gen, generationsToDo;
    Boolean value;

    boolean[][][] results;
    private Queue<Cell> threadWorkQueue;

    Boolean getOldValue() {
        return oldValue;
    }

    Boolean oldValue;

    List<Cell> getNeighbors() {
        return neighbors;
    }

    List<Cell> neighbors = new ArrayList<>();

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

    void addNeighborsToQueue() {
        for (Cell neighbor :
                neighbors) {
            /* add all cells that can now continue to the workQueue*/
            final Integer gen = neighbor.getGen();
            if (gen >=0 &&(this.gen.equals(gen) || this.gen.equals(gen + 1))) {
                threadWorkQueue.add(neighbor);
            }
        }
    }

}

enum cellUpdateResult {
    CELL_UPDATE_SUCCESS,
    CELL_UPDATE_FAIL;
}
