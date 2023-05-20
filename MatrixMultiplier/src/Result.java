class Result {
    private int[][] data;

    public Result(int rows, int columns) {
        this.data = new int[rows][columns];
    }

    public int[][] getData() {
        return data;
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

    public void setValue(int row, int col, int value) {
        data[row][col] = value;
    }
}