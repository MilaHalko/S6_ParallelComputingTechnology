import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class FoxMultiplierTask extends RecursiveTask<Matrix> {
    private final Matrix matrixA;
    private final Matrix matrixB;

    public FoxMultiplierTask(Matrix matrixA, Matrix matrixB) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
    }

    @Override
    public Matrix compute() {
        int matrixSizeLimit = 100;
        if (matrixA.getColumnsNumber() <= matrixSizeLimit) {
            return matrixA.multiply(matrixB);
        }
        int splitSize = 2;
        Matrix[][] matricesA = IMatricesMultiplier.getSplitMatrices(matrixA, splitSize);
        Matrix[][] matricesB = IMatricesMultiplier.getSplitMatrices(matrixB, splitSize);

        Matrix[][] resultMatrices = new Matrix[splitSize][splitSize];
        for (int blockI = 0; blockI < splitSize; blockI++) {
            for (int blockJ = 0; blockJ < splitSize; blockJ++) {
                resultMatrices[blockI][blockJ] = new Matrix(matricesA[blockI][blockJ].getRowsNumber(), matricesB[blockI][blockJ].getColumnsNumber(), false);
            }
        }


        for (int s = 0; s < splitSize; s++) {
            List<FoxMultiplierTask> tasks = new ArrayList<>();
            List<Matrix> calculatedSubBlocks = new ArrayList<>();

            for (int i = 0; i < splitSize; i++) {
                for (int j = 0; j < splitSize; j++) {
                    var task = new FoxMultiplierTask(
                            matricesA[i][s],
                            matricesB[s][j]);

                    tasks.add(task);
                    task.fork();
                }
            }

            for (var task : tasks) {
                var subMatrix = task.join();
                calculatedSubBlocks.add(subMatrix);
            }

            for (int i = 0; i < splitSize; i++) {
                for (int j = 0; j < splitSize; j++) {
                    resultMatrices[i][j].addMatrix(calculatedSubBlocks.get(i * splitSize + j));
                }
            }
        }

        return IMatricesMultiplier.combineMatrices(resultMatrices);
    }
}
