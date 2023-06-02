import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Folder {
    private final List<Folder> subFolders;
    private final List<File> files;

    public Folder(List<Folder> subFolders, List<File> documents) {
        this.subFolders = subFolders;
        this.files = documents;
    }
    List<File> getFiles() {
        return this.files;
    }
    List<Folder> getSubFolders() {
        return this.subFolders;
    }

    static Folder fromDirectory(File dir) throws IOException {
        List<File> innerFiles = new LinkedList<>();
        List<Folder> subFolders = new LinkedList<>();

        for (File entry : Objects.requireNonNull(dir.listFiles())) {
            if (entry.isDirectory()) {
                subFolders.add(Folder.fromDirectory(entry));
            } else {
                innerFiles.add(File.getFromFile(entry));
            }
        }
        return new Folder(subFolders, innerFiles);
    }
}
