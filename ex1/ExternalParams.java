package ex1;

import javafx.util.Pair;

public class ExternalParams {
    public Integer getRow() {
        return row;
    }

    public Integer getCol() {
        return col;
    }

    public Integer getGen() {
        return gen;
    }

    public Boolean getValue() {
        return value;
    }

    public Pair<Integer, Integer> getCoordination() {
        return new Pair<>(row,col);
    }

    Integer row,col,gen;
    Boolean value;

    public ExternalParams(Integer row, Integer col, Integer gen, Boolean value) {
        this.row = row;
        this.col = col;
        this.gen = gen;
        this.value = value;
    }
}
