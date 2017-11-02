package ex1;

import javafx.util.Pair;

import java.util.*;

public class GameOfLifeThread extends Thread {
    private Integer height, width, initialCol, initialRow, generationsToDo, currGen;
    private Cell[][] threadField;
    private Queue<Cell> workQueue = new LinkedList<>();
    private ExternalCellQueue consumerQueue;
    private ExternalCellQueue[][] producerQueues;
    private Map<Pair<Integer, Integer>, ExternalCell> externalCellMap = new TreeMap<>();
    private  boolean[][][] results;
    private boolean[][] initalField;


    GameOfLifeThread(Integer height, Integer width, Integer initialCol, Integer initialRow,
                     ExternalCellQueue[][] consumerProducerQueues, Integer gen,
                     boolean[][] initalField, boolean[][][] results) {
        this.height = height + 1;
        this.width = width + 1;
        this.initialCol = initialCol;
        this.initialRow = initialRow;
        this.producerQueues = consumerProducerQueues;
        this.consumerQueue = consumerProducerQueues[1][1];
        this.generationsToDo = gen;
        this.initalField = initalField;
        this.currGen = 0;
        this.results = results;
    }

    /* Build the correct cell type for the thread field based on location in the thread field, location in the initial
        field and the initial value
     */
    private Cell buildCell(int row, int col) {
        int currentRow, currentCol;
        currentRow = initialRow + row - 1;
        currentCol = initialCol + col - 1;

        if (currentRow < 0 || currentCol < 0 || currentRow > initalField.length - 1 ||
                currentCol > initalField[0].length - 1) {
            /* Out of bounds of original field */
            return new DeadCell(currentRow, currentCol);
        }


        if (row == 0 || col == 0 || row == height - 1 || col == width - 1) {
            ExternalCell externalCell = new ExternalCell(currentRow, currentCol, initalField[currentRow][currentCol]);
            externalCellMap.put(new Pair<>(currentRow, currentCol), externalCell);
            return externalCell;

        }

        if (row == 1 || col == 1 || row == height - 2 || row == width - 2) {
            List<ExternalCellQueue> neighboursQueues = new ArrayList<>();
            /* TODO: build neighboursQueues based on row,col & producerQueues */
            return new BorderCell(currentRow, currentCol, initalField[currentRow][currentCol], neighboursQueues);
        }

        return new InnerCell(currentRow, currentCol, initalField[currentRow][currentCol]);

    }

    @Override
    public void run() {
        /* build thread data */
        for (int row = 0; row < this.height; row++) {
            for (int col = 0; col < this.width; col++) {
                threadField[row][col] = buildCell(row, col);
            }
        }

        for (int row = 1; row < this.height - 1; row++) {
            for (int col = 1; col < this.width - 1; col++) {
                generateNeighbourList(row, col);
            }
        }

    }

    private void generateNeighbourList(int row, int col) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i != 1 && j != 1) {
                    threadField[row][col].addNeighbor(threadField[row + i - 1][col + j - 1]);
                }
            }
        }
    }
}
