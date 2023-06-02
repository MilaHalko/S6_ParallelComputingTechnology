import java.io.File;
import java.util.HashSet;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        File theGreatGatsbyPath = new File("Texts");
        List<String> keyWords = List.of("Gatsby", "Daisy", "Tom", "Jordan", "Nick", "Myrtle", "George", "Wilson");
        int[] threadsCount = {2, 4, 6, 8, 9};

        System.out.println(theGreatGatsbyPath.getAbsolutePath());
        for (var count : threadsCount) {
            System.out.println("----------------------------------------------------------");
            System.out.println("Threads count: " + count);
            TextFileAnalyser theGreatGatsbyAnalyser = new TextFileAnalyser(count, theGreatGatsbyPath);
            testGetWordsLengths(theGreatGatsbyAnalyser);
            testGetCommonWords(theGreatGatsbyAnalyser);
            testKeyWordsInFiles(theGreatGatsbyAnalyser, keyWords);
        }
    }

    private static void testGetWordsLengths(TextFileAnalyser textFileAnalyser) {
        long startTime = System.currentTimeMillis();
        textFileAnalyser.wordsLengthForkJoin();
        long totalTime = System.currentTimeMillis() - startTime;

        System.out.println("Count: " + textFileAnalyser.getWordsCount());
        System.out.println("Mean length: " + textFileAnalyser.getMeanLength());
        System.out.println("Max length: " + textFileAnalyser.getMaxLength());
        System.out.println("WordsLength Time: " + totalTime + " ms");
        System.out.println();
    }

    private static void testGetCommonWords(TextFileAnalyser textFileAnalyser) {
        long startTime = System.currentTimeMillis();
        HashSet<String> commonWords = textFileAnalyser.getCommonWordsForkJoin();
        long time = System.currentTimeMillis() - startTime;

        System.out.println("Common words: " + commonWords);
        System.out.println("Count: " + commonWords.size());
        System.out.println("CommonWords Time: " + time + " ms");
        System.out.println();
    }

    private static void testKeyWordsInFiles(TextFileAnalyser textFileAnalyser, List<String> keyWords) {
        long startTime = System.currentTimeMillis();
        textFileAnalyser.getKeyWordsInFiles(keyWords);
        long time = System.currentTimeMillis() - startTime;

        System.out.println("Files and key word count: ");
        System.out.println("NO : " + textFileAnalyser.getCountNoKeyWords());
        System.out.println("MID: " + textFileAnalyser.getCountNotAllKeyWords());
        System.out.println("ALL: " + textFileAnalyser.getCountAllKeyWords());
        System.out.println("KeyWords Time: " + time + " ms");
        System.out.println();
    }
}