package ex1;

import java.util.AbstractMap;
import java.util.Map;

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


    Map.Entry getCoordination() {
        return new AbstractMap.SimpleEntry(row,col);
    }

    private Integer row, col, gen;
    private Boolean value;

    ExternalParams(Integer row, Integer col, Integer gen, Boolean value) {
        this.row = row;
        this.col = col;
        this.gen = gen;
        this.value = value;
    }
}
