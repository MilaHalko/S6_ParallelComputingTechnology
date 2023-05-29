import Containers.*;
import Multipliers.FoxMultiplier;
import Multipliers.IMatricesMultiplier;
import Multipliers.StandardMultiplier;
import Multipliers.TapeMultiplier;

import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[][] dataA = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };

        int[][] dataB = {
                {1, 5, 9, 13},
                {2, 6, 10, 14},
                {3, 7, 11, 15},
                {4, 8, 12, 16}
        };
        Matrix smallA = new Matrix(dataA);
        Matrix smallB = new Matrix(dataB);
        Matrix bigA = new Matrix(2000, 2000, true);
        Matrix bigB = new Matrix(2000, 2000, true);

        //checkAlorithmsResults(smallA, smallB);
        testAlgorithm(new StandardMultiplier());
        testAlgorithm(new TapeMultiplier(1));
        testAlgorithm(new FoxMultiplier(1));
    }

    private static void testAlgorithm(IMatricesMultiplier multiplier) throws ExecutionException, InterruptedException {
        System.out.println("     " + multiplier.getName());
        for (int size = 1000; size <= 2500; size += 500) {
            System.out.printf("     MATRIX SIZE: %-5d\n", size);
            Matrix matrixA = new Matrix(size, size, true);
            Matrix matrixB = new Matrix(size, size, true);
            if (multiplier.isParallelAlgorithm()) {
                for (int capacity = 1; capacity <= 10; capacity++) {
                    System.out.printf("     THREADS: %-5d ", capacity);
                    multiplier.setPoolCapacity(capacity);
                    repeatMultiplication(multiplier, matrixA, matrixB);
                }
            } else {
                repeatMultiplication(multiplier, matrixA, matrixB);
            }
            System.out.println();
        }
    }

    private static void repeatMultiplication(IMatricesMultiplier multiplier, Matrix matrixA, Matrix matrixB) throws ExecutionException, InterruptedException {
        Clock.averageOn();
        for (int i = 0; i < 10; i++) {
            Clock.start();
            multiplier.multiply(matrixA, matrixB);
            Clock.stop();
        }
        System.out.printf("     AVERAGE TIME: %-5d\n", Clock.getAverage());
        Clock.averageOff();
    }

    private static void checkAlorithmsResults(Matrix smallA, Matrix smallB) throws ExecutionException, InterruptedException {
        Result standardResult = new StandardMultiplier().multiply(smallA, smallB);
        Result tapeResult = new TapeMultiplier(1).multiply(smallA, smallB);
        Result foxResult = new FoxMultiplier(1).multiply(smallA, smallB);
        if (Matrix.compareMatrices(standardResult.getMatrix(), tapeResult.getMatrix()) &&
                Matrix.compareMatrices(standardResult.getMatrix(), foxResult.getMatrix())) {
            System.out.println("Standard, Tape and Fox result matrices are equal");
        } else {
            System.out.println("Standard, Tape and Fox result matrices are not equal");
        }
        standardResult.print();
        tapeResult.print();
        foxResult.print();
    }
}