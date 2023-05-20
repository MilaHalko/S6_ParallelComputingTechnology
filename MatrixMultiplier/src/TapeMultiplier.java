import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TapeMultiplier {
    private final int[][] matrixA;
    private final int[][] matrixB;
    private final Result result;
    private final ExecutorService executor;
    public TapeMultiplier(Matrix matrixA, Matrix matrixB, int threads) {
        this.matrixA = matrixA.getData();
        this.matrixB = matrixB.getData();
        this.result = new Result(matrixA.getRows(), matrixB.getColumns());
        this.executor = Executors.newFixedThreadPool(threads);
    }

    public Result multiply() {
        int rowsA = matrixA.length;
        int colsB = matrixB[0].length;

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                int finalI = i;
                int finalJ = j;
                executor.execute(() -> multiplyCell(finalI, finalJ));
            }
        }

        executor.shutdown();
        return result;
    }

    private void multiplyCell(int row, int col) {
        int sum = 0;
        for (int k = 0; k < matrixB.length; k++) {
            sum += matrixA[row][k] * matrixB[k][col];
        }
        result.setValue(row, col, sum);
    }
}
