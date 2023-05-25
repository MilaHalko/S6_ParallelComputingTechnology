package Multipliers;

import Containers.*;

public class StandardMultiplier {
    public static Result multiply(Matrix matrixA, Matrix matrixB) {
        int rowsA = matrixA.getRowsNumber();
        int colsB = matrixB.getColumnsNumber();
        Result result = new Result(rowsA, colsB);
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                int sum = 0;
                for (int k = 0; k < matrixB.getRowsNumber(); k++) {
                    sum += matrixA.getValue(i, k) * matrixB.getValue(k, j);
                }
                result.setValue(i, j, sum);
            }
        }
        return result;
    }
}
