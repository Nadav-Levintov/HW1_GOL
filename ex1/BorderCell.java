package ex1;

import java.util.ArrayList;
import java.util.List;

public class BorderCell extends InnerCell {
    List<ExternalCellQueue> neighborBlocksQueue;
    List<InnerCell> blockNeighbors = new ArrayList<>();

    public BorderCell(Integer row, Integer col, Boolean value, List<ExternalCellQueue> neighborBlocksQueue) {
        super(row, col, value);

        this.neighborBlocksQueue = neighborBlocksQueue;
    }

    @Override
    public cellUpdateResult updateValue() {
        cellUpdateResult updateResult = super.updateValue();
        if (updateResult.equals(cellUpdateResult.CELL_UPDATE_SUCCESS)) {
            /* TODO: produce to the relevent neighbours queue */
        }
        return updateResult;
    }
}
