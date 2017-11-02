package ex1;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    Integer col, row,gen;
    Boolean value, old_value;
    List<Cell> neighbors = new ArrayList<>();


    public Cell( Integer row,Integer col, Boolean value) {
        this.col = col;
        this.row = row;
        this.value = value;
        this.gen = 0;
    }
}
