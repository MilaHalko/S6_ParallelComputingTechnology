import java.util.ArrayList;
import java.util.concurrent.*;

public final class FoxMultiplier implements IMatricesMultiplier {
    private final int poolCapacity;

    public FoxMultiplier(int poolCapacity) {
        this.poolCapacity = poolCapacity;
    }

    @Override
    public Result multiply(Matrix matrixA, Matrix matrixB) throws ExecutionException, InterruptedException {
        int splitSize = (int) Math.sqrt(poolCapacity - 1) + 1;
        Matrix[][] matricesA = IMatricesMultiplier.getSplitMatrices(matrixA, splitSize);
        Matrix[][] matricesB = IMatricesMultiplier.getSplitMatrices(matrixB, splitSize);
        Matrix[][] resultMatrices = new Matrix[splitSize][splitSize];
        for (int blockI = 0; blockI < splitSize; blockI++) {
            for (int blockJ = 0; blockJ < splitSize; blockJ++) {
                resultMatrices[blockI][blockJ] = new Matrix(matricesA[blockI][blockJ].getRowsNumber(), matricesB[blockI][blockJ].getColumnsNumber(), false);
            }
        }

        ExecutorService pool = Executors.newFixedThreadPool(poolCapacity);
        ArrayList<Future<Matrix>> futureResults = new ArrayList<>();

        for (int s = 0; s < splitSize; s++) {
            for (int i = 0; i < splitSize; i++) {
                for (int j = 0; j < splitSize; j++) {
                    futureResults.add(pool.submit(new FoxMultiplierTask(matricesA[i][s], matricesB[s][j])));
                }
            }
            for (int i = 0; i < splitSize; i++) {
                for (int j = 0; j < splitSize; j++) {
                    resultMatrices[i][j].addMatrix(futureResults.get(i * splitSize + j).get());
                }
            }
            futureResults.clear();
        }
        pool.shutdown();
        return new Result(IMatricesMultiplier.combineMatrices(resultMatrices));
    }

    private record FoxMultiplierTask(Matrix matrixA, Matrix matrixB) implements Callable<Matrix> {
        @Override
        public Matrix call() {
            return new StandardMultiplier().multiply(matrixA, matrixB).getMatrix();
        }
    }
}