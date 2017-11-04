package ex1;

import java.util.Queue;

public class ExternalCell extends Cell {

    ExternalCell(Integer row, Integer col, Boolean value, Queue<Cell> threadWorkQueue, Integer generationsToDo,
                 boolean[][][] results) {
        super(row,col, value, threadWorkQueue,generationsToDo,results);
    }

    @Override
    public cellUpdateResult updateValue() {
        return cellUpdateResult.CELL_UPDATE_FAIL;
    }

    public void externalUpdateValue(int gen, Boolean value) {
        this.oldValue = this.value;
        this.value=value;
        this.gen = gen;

        addNeighborsToQueue();
    }
}
