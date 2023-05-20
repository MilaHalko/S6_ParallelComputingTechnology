public class SequentialMultiplier {
private final int[][] matrixA;
    private final int[][] matrixB;
    private final Result result;

    public SequentialMultiplier(Matrix matrixA, Matrix matrixB) {
        this.matrixA = matrixA.getData();
        this.matrixB = matrixB.getData();
        this.result = new Result(matrixA.getRows(), matrixB.getColumns());
    }

    public Result multiply() {
        int rowsA = matrixA.length;
        int colsB = matrixB[0].length;

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                multiplyCell(i, j);
            }
        }

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
