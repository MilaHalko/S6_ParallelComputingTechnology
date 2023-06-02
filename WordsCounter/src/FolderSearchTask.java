import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

class FolderSearchTask extends RecursiveTask<HashMap<Integer, Integer>> {
    private final Folder folder;

    FolderSearchTask(Folder folder) {
        this.folder = folder;
    }

    @Override
    protected ArrayList<Integer> compute() {
        ArrayList<Integer> wordsLength = new ArrayList<>();
        List<RecursiveTask<ArrayList<Integer>>> tasks = new LinkedList<>();

        for (Folder subFolder : folder.getSubFolders()) {
            FolderSearchTask task = new FolderSearchTask(subFolder);
            tasks.add(task);
            task.fork();
        }

        for (File file : folder.getFiles()) {
            DocumentSearchTask task = new DocumentSearchTask(file);
            tasks.add(task);
            task.fork();
        }

        for (RecursiveTask<ArrayList<Integer>> task : tasks) {
            wordsLength = mergeMaps(wordsLength, task.join());
        }
        return wordsLength;
    }
}
