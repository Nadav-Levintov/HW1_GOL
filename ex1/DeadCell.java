package ex1;

public class DeadCell extends ExternalCell {
    public DeadCell(Integer row, Integer col) {
        super(row, col, Boolean.FALSE);
        this.old_value = Boolean.FALSE;
        this.gen = -1; // always dead cell
    }
}
