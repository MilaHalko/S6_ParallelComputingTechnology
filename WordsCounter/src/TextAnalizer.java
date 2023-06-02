import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.*;

public class TextAnalyzer {
    private final ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

    public static ArrayList<Integer> getWordsLength(TextFile text) {
        ArrayList<Integer> wordsLength = new ArrayList<>();
        for (String line : text.getLines()) {
            String[] words = line.trim().split("(\\s|\\p{Punct})+");
            for (String word : words) {
                int length = word.length();
                wordsLength.add(length);
            }
        }
        return wordsLength;
    }

    public ArrayList<Integer> getWordsLengthParallel(File file) throws IOException {
        Folder folder = Folder.fromDirectory(file);
        return forkJoinPool.invoke(new FolderSearchTask(folder));
    }

}