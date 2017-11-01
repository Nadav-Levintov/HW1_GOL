package ex1;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    Integer x,y,gen;
    Boolean value, old_value;
    List<Cell> neighbors = new ArrayList<>();
}
