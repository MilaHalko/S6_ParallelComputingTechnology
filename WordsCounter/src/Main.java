import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        File theGreatGatsbyPath = new File("Texts");
        int[] threadsCount = {2, 4, 6, 8, 9};
        for (var count : threadsCount) {
            System.out.println("----------------------------------------------------------");
            System.out.println(theGreatGatsbyPath.getAbsolutePath());
            System.out.println("Threads count: " + count);
            testResult(count, theGreatGatsbyPath);
        }
    }

    private static void testResult(int threadsCount, File path) throws IOException {
        TextFileAnalyser theGreatGatsbyAnalyser = new TextFileAnalyser(threadsCount, path);

        long startTime = System.currentTimeMillis();
        theGreatGatsbyAnalyser.getWordsLengthForkJoin();
        long totalTime = System.currentTimeMillis() - startTime;

        System.out.println("Count: " + theGreatGatsbyAnalyser.getWordsCount());
        System.out.println("Mean length: " + theGreatGatsbyAnalyser.getMeanLength());
        System.out.println("Min length: " + theGreatGatsbyAnalyser.getMinLength());
        System.out.println("Max length: " + theGreatGatsbyAnalyser.getMaxLength());
        System.out.println("\nTime: " + totalTime + " ms");
    }
}