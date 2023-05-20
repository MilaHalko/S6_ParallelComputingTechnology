import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FoxMultiplier {
    private final int[][] matrixA;
    private final int[][] matrixB;
    private final Result result;
    private final int blockSize;
    private final ExecutorService executor;

    public FoxMultiplier(Matrix matrixA, Matrix matrixB, int threads) {
        this.matrixA = matrixA.makeCopy();
        this.matrixB = matrixB.makeCopy();
        this.result = new Result(matrixA.getRows(), matrixB.getColumns());
        int size = (int) Math.sqrt((double) (matrixA.getRows() * matrixA.getRows()) / Runtime.getRuntime().availableProcessors());
        blockSize = size == 0 ? 1 : size;
        this.executor = Executors.newFixedThreadPool(threads);
    }

    public Result multiply() {
        int gridSize = matrixA.length / blockSize;

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                int finalI = i;
                int finalJ = j;
                executor.execute(() -> multiplyBlock(finalI, finalJ));
            }
        }
        executor.shutdown();
        return result;
    }

    private void multiplyBlock(int blockRow, int blockCol) {
        int startRow = blockRow * blockSize;
        int startCol = blockCol * blockSize;

        for (int k = 0; k < matrixA.length; k++) {
            for (int i = startRow; i < startRow + blockSize; i++) {
                int sum = 0;
                for (int j = startCol; j < startCol + blockSize; j++) {
                    sum += matrixA[i][j] * matrixB[j][k];
                }
                result.setValue(i, k, sum);
            }
        }
    }
}
