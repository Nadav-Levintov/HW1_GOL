package ex1;

import java.util.ArrayList;
import java.util.List;

abstract public class Cell {
    Integer col;
    Integer row;
    Integer gen;
    Boolean value;

    public Boolean getOldValue() {
        return oldValue;
    }

    Boolean oldValue;
    List<Cell> neighbors = new ArrayList<>();

    public Boolean getValue() {
        return value;
    }

    public Integer getGen() {
        return gen;
    }



    public Cell(Integer row, Integer col, Boolean value) {
        this.col = col;
        this.row = row;
        this.value = value;
        this.gen = 0;
    }

    public void addNeighbor(Cell c) {
        neighbors.add(c);
    }

    abstract public cellUpdateResult updateValue();
}

enum cellUpdateResult {
    CELL_UPDATE_SUCCESS,
    CELL_UPDATE_FAIL;
}
