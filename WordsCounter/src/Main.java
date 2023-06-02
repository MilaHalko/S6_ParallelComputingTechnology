import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("Texts");
        Folder folder = Folder.fromDirectory(file);
        TextAnalizer textAnalizer = new TextAnalizer();

        ArrayList<Integer> wordsLength = textAnalizer.getWordsLengthParallel(folder);
//        long startTime = System.currentTimeMillis();
//        long time = System.currentTimeMillis() - startTime;

        double totalLength = 0;
        double count = 0;
        for (var item: wordsLength.entrySet()){
            totalLength += (item.getValue() * item.getKey());
            count += item.getValue();
        }
        double meanLength = totalLength / count;

        totalLength = 0;
        for (var item: wordsLength.entrySet()){
            totalLength += (Math.pow(item.getKey(),2) * item.getValue());
        }
        double D = (totalLength/count) - Math.pow(meanLength,2);
        double G = Math.sqrt(D);

        System.out.println("Count: " + count);
        System.out.println("Mean length: " + meanLength);
        System.out.println("Dispersion: " + D);
        System.out.println("Mean square deviation: " + G);
        System.out.println("\nTime: " + time);

    }
}