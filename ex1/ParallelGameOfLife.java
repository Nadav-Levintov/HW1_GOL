package ex1;

public class ParallelGameOfLife implements GameOfLife {

    public boolean[][][] invoke(boolean[][] initialField, int hSplit, int vSplit,
                                int generations) {
        /* Global data - threads matrix, communication queues matrix and results */
        GameOfLifeThread[][] threadMatrix = new GameOfLifeThread[vSplit][hSplit];
        ExternalCellQueue[][] queuesMatrix = new ExternalCellQueue[vSplit][hSplit];
        boolean[][][] results = new boolean[2][initialField.length][initialField[0].length];

        /* Init all queues for all threads */
        int id = 0;
        for (int i = 0; i < vSplit; i++) {
            for (int j = 0; j < hSplit; j++) {
                queuesMatrix[i][j] = new ExternalCellQueue(id);
                id++;
            }
        }

        /* Each thread has rowStep rows and colStep columns */
        int rowStep = initialField.length / vSplit;
        int colStep = initialField[0].length / hSplit;

        /* Init and start all threads */
        for (int row = 0; row < vSplit; row++) {
            for (int col = 0; col < hSplit; col++) {
                int height = (row == vSplit - 1) ? rowStep + (initialField.length % vSplit) : rowStep;
                int width = (col == hSplit - 1) ? colStep + (initialField[0].length % hSplit) : colStep;
                /* create neighbor thread communication queue matrix */
                ExternalCellQueue[][] currThreadQueueMatrix = buildCellQueueMatrix(row, col, queuesMatrix);

                threadMatrix[row][col] = new GameOfLifeThread(height, width, row * rowStep,
                        col * colStep, currThreadQueueMatrix, generations, initialField, results);

                threadMatrix[row][col].start();
            }
        }

        /* wait for all threads to finish */
        for (int row = 0; row < vSplit; row++) {
            for (int col = 0; col < hSplit; col++) {
                try {
                    threadMatrix[row][col].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        return results;
    }

    /* Build the queues matrix for each thread
     * If thread A has a neighbor to the left, thread B then the producer-consumer queue of thread B will
     * be in newMatrix[1][0], if A is above be them the queue will be in newMatrix[2][1] etc... */
    private ExternalCellQueue[][] buildCellQueueMatrix(int currRow, int currCol, ExternalCellQueue[][] allQueuesMatrix) {
        final int matrixSize = 3;
        ExternalCellQueue[][] newMatrix = new ExternalCellQueue[matrixSize][matrixSize];

        for (int row = 0; row < matrixSize; row++) {
            if ((currRow == 0 && row == 0) || (currRow >= allQueuesMatrix.length - 1 && row >= matrixSize - 1))
                continue;
            for (int col = 0; col < matrixSize; col++) {
                if ((currCol == 0 && col == 0) || (currCol >= allQueuesMatrix[0].length - 1 && col >= matrixSize - 1))
                    continue;
                int rowInBigMatrix = currRow + row - 1;
                int colInBigMatrix = currCol + col - 1;
                newMatrix[row][col] = allQueuesMatrix[rowInBigMatrix][colInBigMatrix];
            }
        }

        return newMatrix;
    }
}
