package ex1;

import java.util.Queue;
import java.util.Set;

public class BorderCell extends InnerCell {
    private Set<ExternalCellQueue> externalCellQueues;

    BorderCell(Integer row, Integer col, Boolean value, Queue<Cell> threadWorkQueue,
               Set<ExternalCellQueue> externalCellQueues, Integer generationsToDo, boolean[][][] results) {
        super(row,col, value, threadWorkQueue, generationsToDo, results);

        this.externalCellQueues = externalCellQueues;
    }

    @Override
    public cellUpdateResult updateValue() {
        cellUpdateResult updateResult = super.updateValue();
        if (updateResult.equals(cellUpdateResult.CELL_UPDATE_SUCCESS)) {
            produceToNeighbours();
        }
        return updateResult;
    }

    private void produceToNeighbours() {
        ExternalParams params = new ExternalParams(row, col, gen, value);
        for (ExternalCellQueue q : externalCellQueues
                ) {
            q.enqueue(params);
        }
    }
}
