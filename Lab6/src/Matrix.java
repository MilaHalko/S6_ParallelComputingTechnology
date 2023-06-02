import java.util.Random;

public class Matrix {
    private final int[][] data;
    private final int rows;
    private final int columns;

    public Matrix(int[][] data) {
        this.data = data;
        this.rows = data.length;
        this.columns = data[0].length;
    }

    public Matrix(int rows, int columns, boolean generateRandom) {
        this.data = generateRandom ? generateMatrix(rows, columns) : new int[rows][columns];
        this.rows = rows;
        this.columns = columns;
    }

    public static Matrix fromIntArray(int[] resultMatrixBuffer, int i, int columnsNumber) {
        int[][] data = new int[i][columnsNumber];
        for (int row = 0; row < i; row++) {
            System.arraycopy(resultMatrixBuffer, row * columnsNumber, data[row], 0, columnsNumber);
        }
        return new Matrix(data);
    }

    public int getValue(int i, int j) {
        return data[i][j];
    }

    public void setValue(int i, int j, int value) {
        data[i][j] = value;
    }

    public int getRowsNumber() {
        return rows;
    }

    public int getColumnsNumber() {
        return columns;
    }

    public int[] getRow(int row) {
        return data[row];
    }

    public int[][] getArrayCopy() {
        int[][] copy = new int[rows][columns];
        for (int row = 0; row < rows; row++) {
            System.arraycopy(data[row], 0, copy[row], 0, columns);
        }
        return copy;
    }

    public Matrix getMatrixCopy() {
        return new Matrix(getArrayCopy());
    }

    public void print() {
        for (int[] row : data) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public Matrix getTransposedMatrix() {
        int[][] transposed = new int[columns][rows];
        for (int row = 0; row < columns; row++) {
            for (int col = 0; col < rows; col++) {
                transposed[row][col] = data[col][row];
            }
        }
        return new Matrix(transposed);
    }

    public void addMatrix(Matrix matrix) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                data[row][col] += matrix.getValue(row, col);
            }
        }
    }

    public Matrix multiplyMatrix(Matrix matrix) {
        int[][] result = new int[rows][matrix.getColumnsNumber()];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < matrix.getColumnsNumber(); col++) {
                for (int i = 0; i < columns; i++) {
                    result[row][col] += data[row][i] * matrix.getValue(i, col);
                }
            }
        }
        return new Matrix(result);
    }

    public Matrix sliceMatrix(int startRowIndex, int endRowIndex, int columnsCount)
    {
        Matrix subMatrix = new Matrix(endRowIndex - startRowIndex + 1, columnsCount, false);
        for (int i = startRowIndex; i <= endRowIndex; i++) {
            for (int j = 0; j < columnsCount; j++) {
                subMatrix.setValue(i - startRowIndex, j, data[i][j]);
            }
        }
        return subMatrix;
    }

    public void changeSlice(Matrix matrix, int indexStartRow, int indexEndRow, int countColumns)
    {
        for (int i = indexStartRow; i <= indexEndRow; i++) {
            for (int j = 0; j < countColumns; j++) {
                data[i][j] = matrix.getValue(i - indexStartRow, j);
            }
        }
    }

    public static boolean compareMatrices(Matrix m1, Matrix m2) {
        if (m1.getRowsNumber() != m2.getRowsNumber() || m1.getColumnsNumber() != m2.getColumnsNumber()) {
            return false;
        }
        for (int row = 0; row < m1.getRowsNumber(); row++) {
            for (int col = 0; col < m1.getColumnsNumber(); col++) {
                if (m1.getValue(row, col) != m2.getValue(row, col)) {
                    return false;
                }
            }
        }
        return true;
    }

    private int[][] generateMatrix(int rows, int columns) {
        int[][] matrix = new int[rows][columns];
        Random random = new Random();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = random.nextInt(10);
            }
        }
        return matrix;
    }

    public int[] toIntArray() {
        int[] array = new int[rows * columns];
        int index = 0;
        for (int[] row : data) {
            for (int value : row) {
                array[index++] = value;
            }
        }
        return array;
    }
}