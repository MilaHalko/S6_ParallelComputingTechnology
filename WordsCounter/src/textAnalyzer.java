import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class textAnalyzer {
    private final ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

    public ArrayList<Integer> analyzeWordsLength(File file) throws IOException {
        Text text = Text.getFromFile(file);
        WordsLengthTask task = new WordsLengthTask(text);
        return forkJoinPool.invoke(task);
    }

    class WordsLengthTask extends RecursiveTask<ArrayList<Integer>> {
        private final Text text;
        ArrayList<Integer> wordsLength = new ArrayList<>();

        public WordsLengthTask(Text text) {
            this.text = text;
        }

        @Override
        protected ArrayList<Integer> compute() {
            if (text.getLines().size() <= 1) {
                for (String line : text.getLines()) {
                    String[] words = line.trim().split("(\\s|\\p{Punct})+");
                    for (String word : words) {
                        int length = word.length();
                        wordsLength.add(length);
                    }
                }
                return wordsLength;
            } else {
                int mid = text.getLines().size() / 2;
                List<String> firstHalf = text.getLines().subList(0, mid);
                List<String> secondHalf = text.getLines().subList(mid, text.getLines().size());

                WordsLengthTask firstHalfTask = new WordsLengthTask(new Text(firstHalf));
                WordsLengthTask secondHalfTask = new WordsLengthTask(new Text(secondHalf));

                firstHalfTask.fork();
                secondHalfTask.fork();

                ArrayList<Integer> result = firstHalfTask.join();
                ArrayList<Integer> secondHalfResultList = secondHalfTask.join();

                result.addAll(secondHalfResultList);
                return result;
            }
        }
    }
}
