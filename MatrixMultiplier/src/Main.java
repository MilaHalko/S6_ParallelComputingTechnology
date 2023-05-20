public class Main {
    public static void main(String[] args) {
        int[][] dataA = {
                {1, 2},
                {3, 4}
        };

        int[][] dataB = {
                {5, 6},
                {7, 8}
        };

        Matrix matrixA = new Matrix(1000, 1000);
        Matrix matrixB = new Matrix(1000, 1000);


        long startTimeSeq = System.currentTimeMillis();
        SequentialMultiplier sequentialMultiplier = new SequentialMultiplier(matrixA, matrixB);
        sequentialMultiplier.multiply();
        long endTimeSeq = System.currentTimeMillis();
        System.out.println("Sequential: " + (endTimeSeq - startTimeSeq));

        for (int i = 0; i < 10; i++) {
            long startTime = System.currentTimeMillis();
            TapeMultiplier tapeMultiplier = new TapeMultiplier(matrixA, matrixB, i + 1);
            tapeMultiplier.multiply();
            long endTime = System.currentTimeMillis();
            System.out.println("Tape: " + (endTime - startTime));
        }
//        FoxMultiplier foxMultiplier = new FoxMultiplier(matrixA, matrixB);

        Result sequentialResult = sequentialMultiplier.multiply();
//        Result foxResult = foxMultiplier.multiply();

//        sequentialResult.print();
//        tapeResult.print();
//        foxResult.print();
    }
}