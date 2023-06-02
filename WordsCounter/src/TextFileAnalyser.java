import java.io.File;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class TextFileAnalyser {
    private final ForkJoinPool forkJoinPool;
    private File filePath;
    private List<Integer> wordsLength;
    public TextFileAnalyser(int threadsCount, File filePath) {
        forkJoinPool = new ForkJoinPool(threadsCount);
        this.filePath = filePath;
    }

    public List<Integer> getWordsLengthForkJoin() {
        return wordsLength = forkJoinPool.invoke(new SearchTask(filePath));
    }

    public Integer getMeanLength() {
        return wordsLength.stream().reduce(0, Integer::sum) / wordsLength.size();
    }

    public Integer getMaxLength() {
        return wordsLength.stream().max(Integer::compareTo).get();
    }

    public Integer getMinLength() {
        return wordsLength.stream().min(Integer::compareTo).get();
    }

    public Integer getWordsCount() {
        return wordsLength.size();
    }
}