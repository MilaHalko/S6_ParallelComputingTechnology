package Multipliers;

import Containers.*;

import java.util.ArrayList;
import java.util.concurrent.*;

public final class TapeMultiplier implements IMatricesMultiplier {
    private int poolCapacity;

    public TapeMultiplier(int poolCapacity) {
        this.poolCapacity = poolCapacity;
    }

    @Override
    public Result multiply(Matrix matrixA, Matrix matrixB) throws ExecutionException, InterruptedException {
        int size = matrixA.getRowsNumber();
        Matrix transposedB = matrixB.getTransposedMatrix();
        Matrix resultMatrix = new Matrix(size, size, false);
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

    @Override
    public String getName() {
        return "Tape";
    }

    @Override
    public int getPoolCapacity() {
        return poolCapacity;
    }

    @Override
    public void setPoolCapacity(int capacity) {
        this.poolCapacity = capacity;
    }

    @Override
    public boolean isParallelAlgorithm() {
        return true;
    }

    private record TapeMultiplierTask(int[] row, int[] col) implements Callable<Integer> {
        @Override
        public Integer call() {
            int sum = 0;
            for (int i = 0; i < row.length; i++) {
                sum += row[i] * col[i];
            }
            return sum;
        }
    }

}