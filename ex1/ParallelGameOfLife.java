package ex1;

public class ParallelGameOfLife implements GameOfLife {

    public boolean[][][] invoke(boolean[][] initalField, int hSplit, int vSplit,
                                int generations) {
        GameOfLifeThread[][] threadMatrix = new GameOfLifeThread[hSplit][vSplit];
        ExternalCellQueue[][] queuesMatrix = new ExternalCellQueue[hSplit][vSplit];
        boolean[][][] results = new boolean[2][][];

        /* Init all queues for all threads */
        for (int i = 0; i < vSplit; i++) {
            for (int j = 0; j < hSplit; j++) {
                queuesMatrix[i][j] = new ExternalCellQueue();
            }
        }

        /* Init threads */
        int rowStep = initalField.length / vSplit;
        int colStep = initalField[0].length / hSplit;
        for (int i = 0; i < vSplit; i++) {
            if (i == rowStep - 1) {
                rowStep += initalField.length % vSplit;
            }
            for (int j = 0; j < hSplit; j++) {
                int tmpColStep = (j == colStep - 1) ? colStep + (initalField[0].length % hSplit) : colStep;
                ExternalCellQueue[][] currThreadQueueMatrix = buildCellQueueMatrix(i, j, queuesMatrix);

                threadMatrix[i][j] = new GameOfLifeThread(rowStep, tmpColStep, i * rowStep, j * colStep,
                        currThreadQueueMatrix, generations, initalField, results);

            }
        }

        return results;
    }

    /* Build the queues matrix for each thread */
    private ExternalCellQueue[][] buildCellQueueMatrix(int currI, int currJ, ExternalCellQueue[][] allQueuesMatrix) {
        final int matrixSize = 3;
        ExternalCellQueue[][] newMatrix = new ExternalCellQueue[matrixSize][matrixSize];

        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                if ((currI == 0 && i == 0) || (currI == allQueuesMatrix.length - 1 && i == matrixSize - 1))
                    continue;
                if ((currJ == 0 && j == 0) || (currJ == allQueuesMatrix.length - 1 && j == matrixSize - 1))
                    continue;
                newMatrix[i][j] = allQueuesMatrix[currI + i - 1][currJ + j - 1];
            }
        }


        return newMatrix;
    }
}
