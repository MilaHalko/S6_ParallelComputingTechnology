import Containers.*;

public class Main {
    public static void main(String[] args) {
        int[] matrixSizes = {500, 1000, 1500, 2000, 2500, 3000, 3500};
        int[] threadsCounts = {2, 4, 8, 9};

        testAlgorithmsSpeed(matrixSizes, threadsCounts);
    }
    private static void testAlgorithmsSpeed(int[] matrixSizes, int[] threadsCounts) {
        for (int matrixSize : matrixSizes) {
            Matrix matrixA = new Matrix(matrixSize, matrixSize, true);
            Matrix matrixB = new Matrix(matrixSize, matrixSize, true);
            long sequentialTime = checkAlgorithmSpeed(matrixA, matrixB, new SequentialAlgorithm(), 1);

            System.out.println("-------------------------");
            System.out.println("Matrix size: " + matrixSize);
            System.out.println("\nSequential algorithm: " + sequentialTime + " ms");

            for (int threads : threadsCounts) {
                System.out.println("\nThreads count: " + threads);

                long foxForkJoinTime = checkAlgorithmSpeed(matrixA, matrixB, new FoxMultiplierForkJoin(threads), 5);
                long foxTime = checkAlgorithmSpeed(matrixA, matrixB, new FoxAlgorithm(threads), 5);

                System.out.println("\tFox algorithm with " + threads + " threads: " + foxTime + " ms");
                System.out.println("\tFox algorithm with " + threads + " threads and ForkJoin: " + foxForkJoinTime + " ms");
            }
        }
    }
    static long checkAlgorithmSpeed(Matrix matrixA, Matrix matrixB, Multipliers.IMatricesMultiplier multiplier, int iterations) {
        long sum = 0;
        for (int i = 0; i < iterations; i++) {
            sum += multiplier.multiply(matrixA, matrixB).getTotalTime();
        }
        return sum / iterations;
    }
}