import java.util.Random;

class Matrix {
    private final int[][] data;
    private final int rows;
    private final int columns;

    public Matrix(int[][] data) {
        this.data = data;
        this.rows = data.length;
        this.columns = data[0].length;
    }

    public Matrix(int rows, int columns) {
        this.data = generateMatrix(rows, columns);
        this.rows = rows;
        this.columns = columns;
    }

    public int[][] getData() {return data;}
    public int getRows() {return rows;}
    public int getColumns() {return columns;}

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

    public void print() {
        for (int[] row : data) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int[][] makeCopy() {
        int[][] copy = new int[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                copy[row][col] = data[row][col];
            }
        }
        return copy;
    }
}
