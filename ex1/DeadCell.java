package ex1;

public class DeadCell extends ExternalCell {
    public DeadCell(Integer row, Integer col) {
        super(row, col, Boolean.FALSE,null,null,null);
        this.oldValue = Boolean.FALSE;
        this.gen = -1; // always dead cell
    }

    @Override
    public void externalUpdateValue(int gen, Boolean value) {
        /* does nothing */
    }
}
