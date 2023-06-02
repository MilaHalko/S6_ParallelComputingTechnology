import java.util.concurrent.ForkJoinPool;

public class FoxForkJoinMultiplier implements IMatricesMultiplier {
    private final ForkJoinPool forkJoinPool;

    public FoxForkJoinMultiplier(int countThreads) {
        forkJoinPool = new ForkJoinPool(countThreads);
    }

    @Override
    public Result multiply(Matrix matrixA, Matrix matrixB) {
        return new Result(forkJoinPool.invoke(new FoxMultiplierTask(matrixA, matrixB)));
    }
}
