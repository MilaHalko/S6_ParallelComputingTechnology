package Multipliers;

import Containers.*;

import java.util.ArrayList;
import java.util.concurrent.*;

public class FoxMultiplier {
    public static Result multiply(Matrix matrixA, Matrix matrixB, int poolCapacity, int splitSize) throws ExecutionException, InterruptedException {
        Matrix[][] matricesA = getSplitMatrices(matrixA, splitSize);
        Matrix[][] matricesB = getSplitMatrices(matrixB, splitSize);
        Matrix finalResult = new Matrix(matrixA.getRowsNumber(), matrixB.getColumnsNumber());

        Matrix[][] resultMatrices = new Matrix[splitSize][splitSize];
        for (int blockI = 0; blockI < splitSize; blockI++) {
            for (int blockJ = 0; blockJ < splitSize; blockJ++) {
                resultMatrices[blockI][blockJ] = new Matrix(matricesA[blockI][blockJ].getRowsNumber(), matricesB[blockI][blockJ].getColumnsNumber());
            }
        }

        ExecutorService pool = Executors.newFixedThreadPool(poolCapacity);
        ArrayList<Future<Matrix>> futureResults = new ArrayList<>();

        for (int d = 0; d < splitSize; d++) {
            for (int i = 0; i < splitSize; i++) {
                for (int j = 0; j < splitSize; j++) {
                    futureResults.add(pool.submit(new FoxMultiplierTask(matricesA[i][(i + d) % splitSize], matricesB[(i + d) % splitSize][j])));
                }
            }
            int resultIndex = 0;
            for (int i = 0; i < splitSize; i++) {
                for (int j = 0; j < splitSize; j++) {
                    resultMatrices[i][j] = futureResults.get(resultIndex).get();
                    resultIndex++;
                }
            }
        }
        pool.shutdown();
        return new Result(combineMatrices(resultMatrices));
    }

    public static Matrix[][] getSplitMatrices(Matrix matrix, int splitNumber) {
        int splitSize = (matrix.getColumnsNumber() - 1) / splitNumber + 1;
        Matrix[][] splitMatrices = new Matrix[splitNumber][splitNumber];

        for (int matrixI = 0; matrixI < splitNumber; matrixI++) {
            for (int matrixJ = 0; matrixJ < splitNumber; matrixJ++) {
                splitMatrices[matrixI][matrixJ] = new Matrix(splitSize, splitSize);
                for (int i = 0; i < splitSize; i++) {
                    for (int j = 0; j < splitSize; j++) {
                        if (matrixI * splitSize + i >= matrix.getRowsNumber() || matrixJ * splitSize + j >= matrix.getColumnsNumber()) {
                            splitMatrices[matrixI][matrixJ].setValue(i, j, 0);
                            continue;
                        }
                        splitMatrices[matrixI][matrixJ].setValue(i, j, matrix.getValue(matrixI * splitSize + i, matrixJ * splitSize + j));
                    }
                }
            }
        }
        return splitMatrices;
    }

    public static Matrix combineMatrices(Matrix[][] resultMatrices) {
        int splitSize = resultMatrices.length;
        int fullSizeI = resultMatrices[0][0].getRowsNumber();
        int fullSizeJ = resultMatrices[0][0].getColumnsNumber();
        Matrix resultMatrix = new Matrix(splitSize * fullSizeI, splitSize * fullSizeJ);

        for (int matrixI = 0; matrixI < splitSize; matrixI++) {
            for (int matrixJ = 0; matrixJ < splitSize; matrixJ++) {
                for (int i = 0; i < fullSizeI; i++) {
                    for (int j = 0; j < fullSizeJ; j++) {
                        resultMatrix.setValue(matrixI * fullSizeI + i, matrixJ * fullSizeJ + j, resultMatrices[matrixI][matrixJ].getValue(i, j));
                    }
                }
            }
        }
        return resultMatrix;
    }

    private static class FoxMultiplierTask implements Callable<Matrix> {
        private Matrix matrixA;
        private Matrix matrixB;

        public FoxMultiplierTask(Matrix matrixA, Matrix matrixB) {
            this.matrixA = matrixA;
            this.matrixB = matrixB;
        }

        @Override
        public Matrix call() throws Exception {
            return StandardMultiplier.multiply(matrixA, matrixB).getMatrix();
        }
    }
}
