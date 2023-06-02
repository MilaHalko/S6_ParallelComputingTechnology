import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.RecursiveTask;

class DocumentSearchTask extends RecursiveTask<HashMap<Integer, Integer>> {
    private final File file;

    DocumentSearchTask(File file) {
        this.file = file;
    }

    @Override
    protected ArrayList<Integer> compute() {
        return TextAnalizer(file);
    }
}
