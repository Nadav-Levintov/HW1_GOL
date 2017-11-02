package ex1;

import javafx.util.Pair;

import java.util.*;

public class GameOfLifeThread extends Thread {
    Integer height, width, initialCol, initialRow, generationsToDo, currGen;
    Cell[][] threadField;
    Queue<Cell> workQueue = new LinkedList<>();
    ExternalCellQueue consumerQueue;
    ExternalCellQueue[][] producerQueues;
    Map<Pair<Integer, Integer>, ExternalCell> externalCellMap = new TreeMap<>();
    boolean[][][] results;
    boolean[][] initalField;


    public GameOfLifeThread(Integer height, Integer width, Integer initialCol, Integer initialRow,
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
            return null;
        }


        if (row == 0 || col == 0 || row == height - 1 || col == width - 1) {
            ExternalCell externalCell = new ExternalCell(currentRow, currentCol, initalField[currentRow][currentCol]);
            externalCellMap.put(new Pair<Integer, Integer>(currentRow, currentCol), externalCell);
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
    }
}
