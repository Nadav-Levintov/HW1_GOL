package ex1;

import javafx.util.Pair;

public class ExternalParams {
    public Integer getRow() {
        return row;
    }

    public Integer getCol() {
        return col;
    }

    Integer getGen() {
        return gen;
    }

    Boolean getValue() {
        return value;
    }

    Pair<Integer, Integer> getCoordination() {
        return new Pair<>(row,col);
    }

    private Integer row,col,gen;
    private Boolean value;

    ExternalParams(Integer row, Integer col, Integer gen, Boolean value) {
        this.row = row;
        this.col = col;
        this.gen = gen;
        this.value = value;
    }
}
