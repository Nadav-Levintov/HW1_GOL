package ex1;

public class InnerCell extends Cell {

    public InnerCell(Integer row, Integer col, Boolean value) {
        super(row, col, value);
    }

    @Override
    public cellUpdateResult updateValue() {

        int liveNeighbours = 0;
        for (Cell neighbour :
                neighbors) {
            Integer neighbourGen = neighbour.getGen();
            int neighbourValue = 0;
            /*check that neighbor is not DeadCell and that is in relevant gen */
            if (neighbourGen > -1 && (neighbourGen < this.gen - 1 || neighbourGen > this.gen))
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

        return cellUpdateResult.CELL_UPDATE_SUCCESS;
    }
}
