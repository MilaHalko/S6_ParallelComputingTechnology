import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.RecursiveTask;

class WordsLengthsTask extends RecursiveTask<List<Integer>> {
    private final File file;

    WordsLengthsTask(File file) {
        this.file = file;
    }

    @Override
    protected List<Integer> compute() {
        if (!file.isDirectory()) {
            return getAllLengthsInFile(file);
        }

        ArrayList<Integer> wordsLength = new ArrayList<>();
        List<RecursiveTask<List<Integer>>> tasks = new LinkedList<>();

        for (File entry : Objects.requireNonNull(file.listFiles())) {
            WordsLengthsTask task = new WordsLengthsTask(entry);
            tasks.add(task);
            task.fork();
        }

        for (RecursiveTask<List<Integer>> task : tasks) {
            wordsLength.addAll(task.join());
        }

        return wordsLength;
    }

    private static List<Integer> getAllLengthsInFile(File file) {
        List<Integer> wordLengths = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                for (String word : getAllWordsInLine(line)) {
                    wordLengths.add(word.length());
                }
                line = reader.readLine();
            }
        } catch (Exception ignored) {
        }

        return wordLengths;
    }

    private static String[] getAllWordsInLine(String line) {
        return line.trim().split("(\\s|\\p{Punct})+");
    }
}