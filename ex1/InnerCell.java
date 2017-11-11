package ex1;

import java.util.Queue;

public class InnerCell extends Cell {

    InnerCell(Integer row, Integer col, Boolean value, Queue<Cell> threadWorkQueue, Integer generationsToDo,
              boolean[][][] results) {
        super(row,col, value, threadWorkQueue, generationsToDo, results);
    }

    @Override
    public cellUpdateResult updateValue() {

        /* if for some reason this cell has already achieved the generation required return */
        if (this.gen.equals(this.generationsToDo)) {
            return cellUpdateResult.CELL_UPDATE_FAIL;
        }

        /* count the number of alive neighbors */
        int liveNeighbours = 0;
        for (Cell neighbour :
                neighbors) {
            Integer neighborGen = neighbour.getGen();
            int neighbourValue = 0;
            /* if we find a neighbor that is to far behind we can't update */
            if (neighborGen > -1 && !(neighborGen.equals(this.gen) || neighborGen.equals(this.gen + 1)))
                return cellUpdateResult.CELL_UPDATE_FAIL;

            if (neighborGen.intValue() == this.gen) {
                /* if we are the same gen is our neighbor */
                if (neighbour.getValue()) {
                    neighbourValue = 1;
                }
            } else {
                if (neighbour.getOldValue()) {
                    /* if we are the one gen behind our neighbor */
                    neighbourValue = 1;
                }
            }
            liveNeighbours += neighbourValue;
        }

        /* update gen and oldValue */
        this.oldValue = value;
        this.gen++;

        /* determine new value */
        if ((liveNeighbours == 3) || (value && liveNeighbours == 2)) {
            value = Boolean.TRUE;
        } else {
            value = Boolean.FALSE;
        }

        /* add all relevant neighbors to the workQueue of the thread */
        addNeighborsToQueue();

        /* write to result matrix if we reached the correct gen */
        if (gen.equals(generationsToDo - 1)) {
            results[0][row][col] = value;
        }
        if (gen.equals(generationsToDo)) {
            results[1][row][col] = value;
        }
        return cellUpdateResult.CELL_UPDATE_SUCCESS;
    }

    @Override
    public void addToWorkQueue(Integer genOfAdderCell) {
        /* add this cell to the work queue only if it thread can work on it */
        if (genOfAdderCell.equals(gen) || genOfAdderCell.equals(gen + 1)) {
            threadWorkQueue.add(this);
        }
    }
}
