package ex1;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class GameOfLifeThread extends Thread {
    Integer height, width, x, y, generationsToDo, currGen;
    Cell[][] threadField;
    Queue<Cell> workQueue = new LinkedList<>();
    ExternalCellQueue consumerQueue;
    ExternalCellQueue[][] producerQueses;
    Map<Pair<Integer, Integer>, ExternalCell> externalCellMap = new TreeMap<>();
    boolean[][][] results;


    public GameOfLifeThread(Integer height, Integer width, Integer x, Integer y, ExternalCellQueue[][] producerQueses,
                            Integer gen, boolean[][] initalField,boolean[][][] results) {
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
        this.producerQueses = producerQueses;
        this.generationsToDo = gen;
        this.currGen = 0;
        this.results = results;



    }
}
