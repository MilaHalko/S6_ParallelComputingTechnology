package Multipliers;

import Containers.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TapeMultiplier {
    public static Result multiply(Matrix matrixA, Matrix matrixB, int poolCapacity) throws ExecutionException, InterruptedException {
        int size = matrixA.getRowsNumber();
        Matrix transposedB = matrixB.getTransposedMatrix();
        Matrix resultMatrix = new Matrix(size, size);
        ExecutorService pool = Executors.newFixedThreadPool(poolCapacity);
        ArrayList<TapeMultiplierTask> tasks = new ArrayList<>();
        ArrayList<Future<Integer>> results = new ArrayList<>();

        for (int d = 0; d < matrixA.getColumnsNumber(); d++) {
            for (int i = 0; i < size; i++) {
                tasks.add(new TapeMultiplierTask(matrixA.getRow(i), transposedB.getRow((d + i) % size)));
                results.add(pool.submit(tasks.get(tasks.size() - 1)));
            }
        }
        pool.shutdown();
        for (int d = 0; d < size; d++) {
            for (int i = 0; i < size; i++) {
                resultMatrix.setValue(i, (d + i) % size, results.get(d * size + i).get());
            }
        }
        return new Result(resultMatrix);
    }
}