package ex1;

public class ExternalCell extends Cell {
    public ExternalCell(Integer row, Integer col, Boolean value) {
        super(row, col, value);
    }

    @Override
    public cellUpdateResult updateValue() {
        return cellUpdateResult.CELL_UPDATE_SUCCESS;
    }

    public cellUpdateResult externalUpdateValue() {
        /*TODO: use this function to update the cell when update is available from another thread */
        return cellUpdateResult.CELL_UPDATE_SUCCESS;
    }
}
