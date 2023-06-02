import Containers.*;
import java.util.concurrent.ForkJoinPool;

public class FoxAlgorithmForkJoin implements IMatricesMultiplier {
    private final ForkJoinPool forkJoinPool;
    private int poolCapacity = 1;

    public FoxAlgorithmForkJoin(int poolCapacity) {
        forkJoinPool = new ForkJoinPool(poolCapacity);
    }
    @Override
    public Result multiply(Matrix matrixA, Matrix matrixB) {
        long startTime = System.currentTimeMillis();

        return new Result(forkJoinPool.invoke(new FoxAlgorithmTask(matrixA, matrixB)),
                System.currentTimeMillis() - startTime);
    }

    @Override
    public int getPoolCapacity() {
        return poolCapacity;
    }

    @Override
    public void setPoolCapacity(int capacity) {
        poolCapacity = capacity;
    }

    @Override
    public boolean isParallelAlgorithm() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }
}