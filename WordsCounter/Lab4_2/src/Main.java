import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[] matrixSizes = {500, 1000, 1500, 2000, 2500, 3000};
        int[] threadsCounts = {2, 4, 8, 9};

        testAlgorithmsSpeed(matrixSizes, threadsCounts);
    }

    private static void testAlgorithmsSpeed(int[] matrixSizes, int[] threadsCounts) throws ExecutionException, InterruptedException {
        for (int matrixSize : matrixSizes) {
            Matrix matrixA = new Matrix(matrixSize, matrixSize, true);
            Matrix matrixB = new Matrix(matrixSize, matrixSize, true);
            System.out.println("-------------------------");
            System.out.println("Matrix size: " + matrixSize);
            for (int thread : threadsCounts) {
                FoxForkJoinMultiplier foxAlgorithmForkJoin = new FoxForkJoinMultiplier(thread);
                long foxSpeed = checkAlgorithmSpeed(new FoxMultiplier(thread), matrixA, matrixB, 5);
                long ffjSpeed = checkAlgorithmSpeed(foxAlgorithmForkJoin, matrixA, matrixB, 5);
                System.out.println("Threads: " + thread);
                System.out.println("StandardFox time: " + foxSpeed + " ms");
                System.out.println("FoxForkJoin time: " + ffjSpeed + " ms");
                System.out.println("Speedup: " + (double) foxSpeed / ffjSpeed);
                System.out.println();
            }
        }
    }

    static long checkAlgorithmSpeed(IMatricesMultiplier multiplier, Matrix matrixA, Matrix matrixB, int iterations) throws ExecutionException, InterruptedException {
        long sum = 0;
        for (int i = 0; i < iterations; i++) {
            long startTime = System.currentTimeMillis();
            multiplier.multiply(matrixA, matrixB);
            long endTime = System.currentTimeMillis();
            sum += endTime - startTime;
        }
        return sum / iterations;
    }
}