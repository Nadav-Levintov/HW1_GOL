package ex1;

public class ParallelGameOfLife implements GameOfLife {

    public boolean[][][] invoke(boolean[][] initalField, int hSplit, int vSplit,
                                int generations) {
        GameOfLifeThread[][] threadMatrix = new GameOfLifeThread[hSplit][vSplit];
        ExternalCellQueue[][] queuesMatrix = new ExternalCellQueue[hSplit][vSplit];
        boolean[][][] results = new boolean[2][initalField.length][initalField[0].length];

        /* Init all queues for all threads */
        int id = 0;
        for (int i = 0; i < vSplit; i++) {
            for (int j = 0; j < hSplit; j++) {
                queuesMatrix[i][j] = new ExternalCellQueue(id);
                id++;
            }
        }

        /* Init threads */
        int rowStep = initalField.length / vSplit;
        int colStep = initalField[0].length / hSplit;

        for (int row = 0; row < vSplit; row++) {
            for (int col = 0; col < hSplit; col++) {
                int height = (row == vSplit - 1) ? rowStep + (initalField.length % vSplit) : rowStep;
                int width = (col == hSplit - 1) ? colStep + (initalField[0].length % hSplit) : colStep;
                ExternalCellQueue[][] currThreadQueueMatrix = buildCellQueueMatrix(row, col, queuesMatrix);

                threadMatrix[row][col] = new GameOfLifeThread(height, width, row * rowStep,
                        col * colStep, currThreadQueueMatrix, generations, initalField, results);
                threadMatrix[row][col].start();
            }
        }

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

    /* Build the queues matrix for each thread */
    private ExternalCellQueue[][] buildCellQueueMatrix(int currRow, int currCol, ExternalCellQueue[][] allQueuesMatrix) {
        final int matrixSize = 3;
        ExternalCellQueue[][] newMatrix = new ExternalCellQueue[matrixSize][matrixSize];

        for (int row = 0; row < matrixSize; row++) {
            for (int col = 0; col < matrixSize; col++) {
                if ((currRow == 0 && row == 0) || (currRow == allQueuesMatrix.length - 1 && row == matrixSize - 1))
                    continue;
                if ((currCol == 0 && col == 0) || (currCol == allQueuesMatrix.length - 1 && col == matrixSize - 1))
                    continue;
                newMatrix[row][col] = allQueuesMatrix[currRow + row - 1][currCol + col - 1];
            }
        }

        return newMatrix;
    }
}
