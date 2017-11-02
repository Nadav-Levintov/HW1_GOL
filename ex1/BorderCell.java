package ex1;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BorderCell extends InnerCell {
    Set<ExternalCellQueue> externalCellQueues;
    List<InnerCell> blockNeighbors = new ArrayList<>();

    public BorderCell(Integer row, Integer col, Boolean value, Set<ExternalCellQueue> externalCellQueues) {
        super(row, col, value);

        this.externalCellQueues = externalCellQueues;
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
