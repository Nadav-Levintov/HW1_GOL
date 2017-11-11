package ex1;

import java.util.AbstractMap;
import java.util.Map;

public class ExternalParams {
    /* used as workItems for the P-C queues */
    private Integer row, col, gen;
    private Boolean value;

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


    Map.Entry<Integer, Integer> getCoordination() {
        return new AbstractMap.SimpleEntry<>(row,col);
    }

    ExternalParams(Integer row, Integer col, Integer gen, Boolean value) {
        this.row = row;
        this.col = col;
        this.gen = gen;
        this.value = value;
    }
}
