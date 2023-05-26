import Containers.*;
import Multipliers.FoxMultiplier;
import Multipliers.StandardMultiplier;
import Multipliers.TapeMultiplier;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[][] dataA = {
                {1, 2},
                {3, 4}
        };

        int[][] dataB = {
                {5, 6},
                {7, 8}
        };
        Matrix smallA = new Matrix(dataA);
        Matrix smallB = new Matrix(dataB);
        Matrix matrixA = new Matrix(1000, 1000);
        Matrix matrixB = new Matrix(1000, 1000);

        Clock.start();
        Result standardResult = StandardMultiplier.multiply(smallA, smallB);
        Clock.stop("Standard");
        standardResult.print();

//        for (int capacity = 1; capacity <= 10; capacity++) {
//            Clock.averageOn();
//            for (int i = 0; i < 10; i++) {
//                Clock.start();
//                Result tapeResult = TapeMultiplier.multiply(matrixA, matrixB, capacity);
//                Clock.stop();
//            }
//            Clock.averageOff("Tape " + capacity + " threads. ");
//        }

        Result foxResult = FoxMultiplier.multiply(smallA, smallB, 1, 2);
        foxResult.print();

//        for (int capacity = 1; capacity <= 10; capacity++) {
//            Clock.averageOn();
//            for (int i = 0; i < 10; i++) {
//                Clock.start();
//                Clock.stop();
//            }
//            Clock.averageOff("Fox " + capacity + " threads. ");
//        }

//        System.out.println(Matrix.compareMatrices(standardResult.getMatrix(), tapeResult.getMatrix()));
    }
}