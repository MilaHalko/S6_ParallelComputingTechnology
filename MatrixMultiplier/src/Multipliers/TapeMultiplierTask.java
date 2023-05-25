package Multipliers;

import java.util.concurrent.Callable;

public class TapeMultiplierTask implements Callable<Integer> {
    private final int[] row;
    private final int[] col;
    public TapeMultiplierTask(int[] row, int[] col) {
        this.row = row;
        this.col = col;
    }
    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i < row.length; i++) {
            sum += row[i] * col[i];
        }
        return sum;
    }
}
