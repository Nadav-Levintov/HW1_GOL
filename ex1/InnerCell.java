package ex1;

import java.util.Queue;

public class InnerCell extends Cell {

    public InnerCell(Integer row, Integer col, Boolean value, Queue<Cell> threadWorkQueue, Integer generationsToDo,
                     boolean[][][] results) {
        super(row, col, value, threadWorkQueue, generationsToDo, results);
    }

    @Override
    public cellUpdateResult updateValue() {

        if (this.gen == this.generationsToDo - 1) {
            return cellUpdateResult.CELL_UPDATE_FAIL;
        }

        int liveNeighbours = 0;
        for (Cell neighbour :
                neighbors) {
            Integer neighbourGen = neighbour.getGen();
            int neighbourValue = 0;
            /*check that neighbor is not DeadCell and that is in relevant gen */
            if (neighbourGen > -1 && !(neighbourGen == this.gen || neighbourGen == this.gen + 1))
                return cellUpdateResult.CELL_UPDATE_FAIL;

            if (neighbourGen.intValue() == this.gen) {
                if (neighbour.getValue()) {
                    neighbourValue = 1;
                }
            } else {
                if (neighbour.getOldValue()) {
                    neighbourValue = 1;
                }
            }
            liveNeighbours += neighbourValue;
        }

        this.oldValue = value;
        this.gen++;
        if ((liveNeighbours == 3) || (value && liveNeighbours == 2)) {
            value = Boolean.TRUE;
        } else {
            value = Boolean.FALSE;
        }

        threadWorkQueue.addAll(this.getNeighbors());

        if (gen == generationsToDo - 1) {
            results[0][row][col] = value;
        }
        if (gen == generationsToDo) {
            results[1][row][col] = value;
        }
        return cellUpdateResult.CELL_UPDATE_SUCCESS;
    }
}
