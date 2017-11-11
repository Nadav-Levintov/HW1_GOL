package ex1;

import java.util.Queue;

public class ExternalCell extends Cell {

    ExternalCell(Integer row, Integer col, Boolean value, Queue<Cell> threadWorkQueue, Integer generationsToDo,
                 boolean[][][] results) {
        super(row,col, value, threadWorkQueue, generationsToDo, results);
    }

    @Override
    public cellUpdateResult updateValue() {
        /* external cells are updated externally, the thread does not work on them */
        return cellUpdateResult.CELL_UPDATE_FAIL;
    }

    @Override
    public void addToWorkQueue(Integer genOfAdderCell) {
        /* The thread does not work on external cells so we don't add them to the queue*/
    }

    public void externalUpdateValue(int gen, Boolean value) {
        /* updated externally from ExternalParams received from other thread through the P-C queue */
        this.oldValue = this.value;
        this.value=value;
        this.gen = gen;

        /* add all relevent cells (border cells) to the thread work queue */
        addNeighborsToQueue();
    }
}
