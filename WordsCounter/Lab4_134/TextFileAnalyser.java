import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class TextFileAnalyser {
    private final ForkJoinPool forkJoinPool;
    private final File filePath;
    private List<Integer> wordsLength = new ArrayList<>();
    private HashMap<String, List<String>> keyWordsInFiles = new HashMap<>();
    private List<String> keyWords = new ArrayList<>();
    public TextFileAnalyser(int threadsCount, File filePath) {
        forkJoinPool = new ForkJoinPool(threadsCount);
        this.filePath = filePath;
    }

    public void wordsLengthForkJoin() {
        wordsLength = forkJoinPool.invoke(new WordsLengthsTask(filePath));
    }

    public HashSet<String> getCommonWordsForkJoin() {
        return forkJoinPool.invoke(new CommonWordsTask(filePath));
    }

    public HashMap<String, List<String>> getKeyWordsInFiles(List<String> keyWords) {
        this.keyWords = keyWords;
        List<String> keyWordsLowerCase = new ArrayList<>();
        for (String word : keyWords) {
            keyWordsLowerCase.add(word.toLowerCase());
        }
        return keyWordsInFiles = forkJoinPool.invoke(new KeyWordsInFileTask(filePath, keyWordsLowerCase));
    }

    public Integer getMeanLength() {
        if (wordsLength.size() == 0) {
            return 0;
        }
        return wordsLength.stream().reduce(0, Integer::sum) / wordsLength.size();
    }

    public Integer getMaxLength() {
        if (wordsLength.size() == 0) {
            return 0;
        }
        return wordsLength.stream().reduce(0, Integer::max);
    }

    public Integer getWordsCount() {
        return wordsLength.size();
    }

    public int getCountNoKeyWords() {
        int count = 0;
        for (var file : keyWordsInFiles.entrySet()) {
            if (file.getValue().size() == 0) {
                count++;
            }
        }
        return count;
    }

    public int getCountAllKeyWords() {
        int count = 0;
        for (var file : keyWordsInFiles.entrySet()) {
            if (file.getValue().size() == keyWords.size()) {
                count++;
            }
        }
        return count;
    }

    public int getCountNotAllKeyWords() {
        int count = 0;
        for (var file : keyWordsInFiles.entrySet()) {
            if (file.getValue().size() < keyWords.size() && file.getValue().size() != 0) {
                count++;
            }
        }
        return count;
    }

}