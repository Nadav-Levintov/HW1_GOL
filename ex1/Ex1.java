package ex1;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class Ex1 {
    private static void printUsage() {
        System.out.println("<Initial pattern filename> <#horizonal splits> " +
                "<#vertical splits> <#generations> " +
                "<fieldSizeX> <fieldSizeY>");
        System.exit(-1);
    }

    public static void main(String[] argv) {
        if (argv.length != 6) {
            printUsage();
        }

        String filename = argv[0];
        int hSplit = Integer.parseInt(argv[1]);
        int vSplit = Integer.parseInt(argv[2]);
        int nGenerations = Integer.parseInt(argv[3]);
        int xSize = Integer.parseInt(argv[4]);
        int ySize = Integer.parseInt(argv[5]);

        GameFieldReader g = new GameFieldReader(filename, xSize, ySize);
        boolean[][] field = null;
        try {
            field = g.parseData();
        } catch (Exception e) {
            System.err.println("Error while initializing the field:" + e.getMessage());
            //e.printStackTrace();
            System.exit(-1);
        }
        //printArray(field);
        System.out.println(":END of inital array:");

        if (vSplit > field.length || hSplit > field[0].length) {
            System.err.println("Wrong number of splits specified");
            System.exit(-1);
        }
        GameOfLife sGol = new SerialGameOfLife();
        GameOfLife pGol = new ParallelGameOfLife();

        long tStart = System.currentTimeMillis();

        boolean[][][] resultSerial = sGol.invoke(field, hSplit, vSplit, nGenerations);

        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;
        Double serialElapsedSeconds = tDelta / 1000.0;
        System.out.println("Serial time: ".concat(serialElapsedSeconds.toString()));

        //printArray(resultSerial);
        try {
            printArray(resultSerial[0], "output.txt");
            printArray(resultSerial[1], "output1.txt");
        } catch (Exception e) {
            System.err.println("Failed to write the output to file");
            e.printStackTrace();
            System.exit(-1);
        }
        tStart = System.currentTimeMillis();

        boolean[][][] resultParallel = pGol.invoke(field, hSplit, vSplit, nGenerations);

        tEnd = System.currentTimeMillis();
        tDelta = tEnd - tStart;
        Double parallelElapsedSeconds = tDelta / 1000.0;
        System.out.println("Parallel time: ".concat(parallelElapsedSeconds.toString()));
        Double speedup = serialElapsedSeconds/parallelElapsedSeconds;
        System.out.println("Speedup: ".concat(speedup.toString()));

        if (compareArrays(resultParallel[0], resultSerial[0]) && compareArrays(resultParallel[1], resultSerial[1])) {
            System.out.println("Success!");
        } else {
            System.out.println("Parallel version results do not match!!");
            System.exit(-1);
        }
        System.exit(0);
    }

    //This funcion prints the output in a standard way to allow
    //coherent output comparison.
    //The arr parameter should contain a matrix of zero and ones.
    // This in no way should be considered a hint re/
    // internal data structure.

    public static void printArray(boolean[][] arr) {
        for (boolean[] row : arr) {
            for (boolean cell : row) {
                System.out.print((cell ? '1' : '0'));
            }
            System.out.println();
        }
    }

    public static void printArray(boolean[][] arr, String filename) throws Exception {
        BufferedWriter output = new BufferedWriter(new FileWriter(filename));

        for (boolean[] row : arr) {
            for (boolean cell : row) {
                output.write((cell ? '1' : '0'));
            }
            output.newLine();
        }
        output.close();
    }

    public static boolean compareArrays(boolean[][] arr1, boolean[][] arr2) {
        if (arr1 == null || arr2 == null) {
            return false;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i].length != arr2[i].length) {
                return false;
            }
            for (int j = 0; j < arr1[i].length; j++) {
                if (arr1[i][j] != arr2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

}
