package ex1;

import javafx.util.Pair;

import java.util.*;

public class GameOfLifeThread extends Thread {
    private final boolean[][][] results;
    private Integer height, width, initialCol, initialRow, generationsToDo;
    private Cell[][] threadField;
    private Queue<Cell> workQueue = new LinkedList<>();
    private ExternalCellQueue consumerQueue;
    private ExternalCellQueue[][] producerQueues;
    private Map<Pair<Integer, Integer>, ExternalCell> externalCellMap = new HashMap<>();
    private boolean[][] initalField;
    private Integer updatesToDo, updatesDone;


    GameOfLifeThread(Integer height, Integer width, Integer initialCol, Integer initialRow,
                     ExternalCellQueue[][] consumerProducerQueues, Integer gen,
                     boolean[][] initalField, boolean[][][] results) {
        this.height = height + 2;
        this.width = width + 2;
        this.initialCol = initialCol;
        this.initialRow = initialRow;
        this.producerQueues = consumerProducerQueues;
        this.consumerQueue = consumerProducerQueues[1][1];
        this.generationsToDo = gen;
        this.initalField = initalField;
        this.results = results;
        this.updatesToDo = generationsToDo * width * height;
        this.updatesDone = 0;
        this.threadField = new Cell[this.height][this.width];
    }

    /* Build the correct cell type for the thread field based on location in the thread field, location in the initial
        field and the initial value
     */
    Cell buildCell(int row, int col) {

        int rowInOriginalField, colInOriginalField;
        rowInOriginalField = initialRow + row - 1;
        colInOriginalField = initialCol + col - 1;

        if (rowInOriginalField < 0 || colInOriginalField < 0 || rowInOriginalField > initalField.length - 1 ||
                colInOriginalField > initalField[0].length - 1) {
            /* Out of bounds of original field */
            return new DeadCell(rowInOriginalField, colInOriginalField);
        }


        if (row == 0 || col == 0 || row == height - 1 || col == width - 1) {
            ExternalCell externalCell = new ExternalCell(rowInOriginalField, colInOriginalField,
                    initalField[rowInOriginalField][colInOriginalField], workQueue, generationsToDo, results);
            externalCellMap.put(new Pair<>(rowInOriginalField, colInOriginalField), externalCell);
            return externalCell;

        }

        if (row == 1 || col == 1 || row == height - 2 || row == width - 2) {
            Set<ExternalCellQueue> neighboursQueues = createExternalCellQueues(row, col);
            return new BorderCell(rowInOriginalField, colInOriginalField,
                    initalField[rowInOriginalField][colInOriginalField], workQueue, neighboursQueues, generationsToDo, results);
        }

        return new InnerCell(rowInOriginalField, colInOriginalField,
                initalField[rowInOriginalField][colInOriginalField], workQueue, generationsToDo, results);

    }

    private Set<ExternalCellQueue> createExternalCellQueues(int row, int col) {
        Set<ExternalCellQueue> neighboursQueues = new TreeSet<>();
        if (row == 1 && col == 1 && this.producerQueues[0][0] != null)
            neighboursQueues.add(this.producerQueues[0][0]);

        if (row == 1 && this.producerQueues[0][1] != null)
            neighboursQueues.add(this.producerQueues[0][1]);

        if (row == 1 && col == height - 2 && this.producerQueues[0][2] != null)
            neighboursQueues.add(this.producerQueues[0][2]);

        if (col == 1 && this.producerQueues[1][0] != null)
            neighboursQueues.add(this.producerQueues[1][0]);

        if (col == height - 2 && this.producerQueues[1][2] != null)
            neighboursQueues.add(this.producerQueues[1][2]);

        if (row == height - 2 && col == height - 2 && this.producerQueues[2][0] != null)
            neighboursQueues.add(this.producerQueues[2][0]);

        if (row == height - 2 && this.producerQueues[2][1] != null)
            neighboursQueues.add(this.producerQueues[2][1]);

        if (row == height - 2 && col == height - 2 && this.producerQueues[2][2] != null)
            neighboursQueues.add(this.producerQueues[2][2]);

        return neighboursQueues;
    }

    @Override
    public void run() {
        /* build thread data */
        for (int row = 0; row < this.height; row++) {
            for (int col = 0; col < this.width; col++) {
                threadField[row][col] = buildCell(row, col);
            }
        }
        /* creating neighbor list for inner cells only */
        int tmp=0;
        for (int row = 1; row < this.height - 1; row++) {
            for (int col = 1; col < this.width - 1; col++) {
                tmp++;
                generateNeighbourList(row, col);
                workQueue.add(threadField[row][col]);
            }
        }

        /*Main work loop */
        while (!updatesDone.equals(updatesToDo)) {
            while (!workQueue.isEmpty()) {
                /* work while you can */
                Cell c = workQueue.remove();
                if (c.updateValue().equals(cellUpdateResult.CELL_UPDATE_SUCCESS)) {
                    updatesDone++;
                }
            }

            if (updatesDone.equals(updatesToDo)) {
                break;
            }
            /* wait for info from other threads */
            ExternalParams params = consumerQueue.dequeue();
            externalCellMap.get(params.getCoordination()).externalUpdateValue(params.getGen(), params.getValue());
        }

    }

    private void generateNeighbourList(int row, int col) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 1 && j == 1) {
                    continue;
                }
                threadField[row][col].addNeighbor(threadField[row + i - 1][col + j - 1]);
            }
        }
    }
}
