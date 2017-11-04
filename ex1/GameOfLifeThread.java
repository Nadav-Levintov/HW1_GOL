package ex1;

import java.util.*;

public class GameOfLifeThread extends Thread {
    private final boolean[][][] results;
    private Integer height, width, initialCol, initialRow, generationsToDo;
    private Cell[][] threadField;
    private Queue<Cell> workQueue = new LinkedList<>();
    private ExternalCellQueue consumerQueue;
    private ExternalCellQueue[][] producerQueues;
    private Map<Map.Entry<Integer, Integer>, ExternalCell> externalCellMap = new HashMap<>();
    private boolean[][] initialField;
    private Integer updatesToDo, updatesDone;
    private List<InnerCell> innerCells = new ArrayList<>();
    private List<BorderCell> borderCells = new ArrayList<>();

    GameOfLifeThread(Integer height, Integer width, Integer initialRow, Integer initialCol,
                     ExternalCellQueue[][] consumerProducerQueues, Integer gen,
                     boolean[][] initialField, boolean[][][] results) {
        this.height = height + 2;
        this.width = width + 2;
        this.initialCol = initialCol;
        this.initialRow = initialRow;
        this.producerQueues = consumerProducerQueues;
        this.consumerQueue = consumerProducerQueues[1][1];
        this.generationsToDo = gen;
        this.initialField = initialField;
        this.results = results;
        this.updatesToDo = generationsToDo * width * height;
        this.updatesDone = 0;
        this.threadField = new Cell[this.height][this.width];
    }

    /* Build the correct cell type for the thread field based on location in the thread field, location in the initial
        field and the initial value
     */
    private Cell buildCell(int row, int col) {

        int rowInOriginalField, colInOriginalField;
        rowInOriginalField = initialRow + row - 1;
        colInOriginalField = initialCol + col - 1;

        if (rowInOriginalField < 0 || colInOriginalField < 0 || rowInOriginalField > initialField.length - 1 ||
                colInOriginalField > initialField[0].length - 1) {
            /* Out of bounds of original field */
            return new DeadCell(rowInOriginalField, colInOriginalField);
        }


        if (row == 0 || col == 0 || row == height - 1 || col == width - 1) {
            ExternalCell externalCell = new ExternalCell(rowInOriginalField, colInOriginalField,
                    initialField[rowInOriginalField][colInOriginalField], workQueue, generationsToDo, results);
            externalCellMap.put(new AbstractMap.SimpleEntry<>(rowInOriginalField, colInOriginalField), externalCell);
            return externalCell;

        }

        if (row == 1 || col == 1 || row == height - 2 || col == width - 2) {
            Set<ExternalCellQueue> neighboursQueues = createExternalCellQueues(row, col);
            BorderCell borderCell = new BorderCell(rowInOriginalField, colInOriginalField,
                    initialField[rowInOriginalField][colInOriginalField], workQueue, neighboursQueues, generationsToDo, results);
            borderCells.add(borderCell);
            return borderCell;
        }
        InnerCell innerCell = new InnerCell(rowInOriginalField, colInOriginalField,
                initialField[rowInOriginalField][colInOriginalField], workQueue, generationsToDo, results);
        innerCells.add(innerCell);
        return innerCell;

    }

    private Set<ExternalCellQueue> createExternalCellQueues(int row, int col) {
        Set<ExternalCellQueue> neighboursQueues = new TreeSet<>();
        if (row == 1 && col == 1 && this.producerQueues[0][0] != null)
            neighboursQueues.add(this.producerQueues[0][0]);

        if (row == 1 && this.producerQueues[0][1] != null)
            neighboursQueues.add(this.producerQueues[0][1]);

        if (row == 1 && col == width - 2 && this.producerQueues[0][2] != null)
            neighboursQueues.add(this.producerQueues[0][2]);

        if (col == 1 && this.producerQueues[1][0] != null)
            neighboursQueues.add(this.producerQueues[1][0]);

        if (col == width - 2 && this.producerQueues[1][2] != null)
            neighboursQueues.add(this.producerQueues[1][2]);

        if (row == height - 2 && col == 1 && this.producerQueues[2][0] != null)
            neighboursQueues.add(this.producerQueues[2][0]);

        if (row == height - 2 && this.producerQueues[2][1] != null)
            neighboursQueues.add(this.producerQueues[2][1]);

        if (row == height - 2 && col == width - 2 && this.producerQueues[2][2] != null)
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

        for (int row = 0; row < this.height; row++) {
            for (int col = 0; col < this.width; col++) {
                generateNeighbourList(row, col);
            }
        }

        //workQueue.addAll(borderCells);
        //workQueue.addAll(innerCells);
        workQueue.add(threadField[1][1]);

        /*Main work loop */
        //TODO: remove.
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
            List<ExternalParams> params = consumerQueue.dequeue();
            for (ExternalParams externalParams :
                    params) {
                Map.Entry coordination = externalParams.getCoordination();
                ExternalCell externalCell = externalCellMap.get(coordination);
                externalCell.externalUpdateValue(externalParams.getGen(), externalParams.getValue());
            }

        }
    }

    private void generateNeighbourList(int row, int col) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 1 && j == 1) {
                    continue;
                }
                if (row + i - 1 < 0 || row + i - 1 >= height) {
                    continue;
                }
                if (col + j - 1 < 0 || col + j - 1 >= width) {
                    continue;
                }
                threadField[row][col].addNeighbor(threadField[row + i - 1][col + j - 1]);
            }
        }
    }
}
