import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.RecursiveTask;

class KeyWordsInFileTask extends RecursiveTask<HashMap<String, List<String>>> {
    private final File file;
    private final List<String> keyWords;

    KeyWordsInFileTask(File file, List<String> keyWords) {
        this.file = file;
        this.keyWords = keyWords;
    }

    @Override
    protected HashMap<String, List<String>> compute() {
        if (!file.isDirectory()) {
            return checkKeyWords(file, keyWords);
        }
        HashMap<String, List<String>> keyWordsFiles = new HashMap<>();
        List<RecursiveTask<HashMap<String, List<String>>>> tasks = new ArrayList<>();

        for (File entry : Objects.requireNonNull(file.listFiles())) {
            KeyWordsInFileTask task = new KeyWordsInFileTask(entry, keyWords);
            tasks.add(task);
            task.fork();
        }

        for (RecursiveTask<HashMap<String, List<String>>> task : tasks) {
            keyWordsFiles.putAll(task.join());
        }

        return keyWordsFiles;
    }

    private static HashMap<String, List<String>> checkKeyWords(File file, List<String> keyWords) {
        HashSet<String> uniqueWords = new HashSet<>();
        String fileName = file.getPath() + file.getName();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                String[] words = line.trim().split("(\\s|\\p{Punct})+");
                for (String word : words) {
                    uniqueWords.add(word.toLowerCase());
                }
                line = reader.readLine();
            }
        } catch (Exception ignored) {
        }

        List<String> matchedWords = new ArrayList<>(keyWords);
        matchedWords.retainAll(uniqueWords);

        HashMap<String, List<String>> fileMatches = new HashMap<>();
        fileMatches.put(file.getName(), matchedWords);

        return fileMatches;
    }
}