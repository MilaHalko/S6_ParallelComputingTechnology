package Multipliers;

import Containers.*;

import java.util.ArrayList;
import java.util.concurrent.*;

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

    private static class TapeMultiplierTask implements Callable<Integer> {
        private final int[] row;
        private final int[] col;
        public TapeMultiplierTask(int[] row, int[] col) {
            this.row = row;
            this.col = col;
        }
        @Override
        public Integer call() throws Exception {
            int sum = 0;
            for (int i = 0; i < row.length; i++) {
                sum += row[i] * col[i];
            }
            return sum;
        }
    }

}