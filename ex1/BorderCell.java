package ex1;

import java.util.Queue;
import java.util.Set;

public class BorderCell extends InnerCell {
    private Set<ExternalCellQueue> neighborCommunicationQueues;

    BorderCell(Integer row, Integer col, Boolean value, Queue<Cell> threadWorkQueue,
               Set<ExternalCellQueue> neighborCommunicationQueues, Integer generationsToDo, boolean[][][] results) {
        super(row, col, value, threadWorkQueue, generationsToDo, results);

        this.neighborCommunicationQueues = neighborCommunicationQueues;
    }

    @Override
    public cellUpdateResult updateValue() {
        /* use inner cell update to update the cell and neighbors*/
        cellUpdateResult updateResult = super.updateValue();
        if (updateResult.equals(cellUpdateResult.CELL_UPDATE_SUCCESS)) {
            /* if updated successfully produce the new value to neighbor threads */
            produceToNeighbours();
        }
        return updateResult;
    }

    private void produceToNeighbours() {
        ExternalParams params = new ExternalParams(row, col, gen, value);
        for (ExternalCellQueue q : neighborCommunicationQueues
                ) {
            q.enqueue(params);
        }
    }
}
