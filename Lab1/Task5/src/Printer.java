import java.util.ArrayList;
import java.util.List;

public class Printer {
    List<SymbolThread> threads;
    List<SyncSymbolThread> syncThreads;

    public Printer() {
        threads = new ArrayList<>();
        syncThreads = new ArrayList<>();
    }

    public void addSymbolThread(SymbolThread symbolThread) {
        threads.add(symbolThread);
    }

    public void addSyncSymbolThread(SyncSymbolThread symbolThread) {
        syncThreads.add(symbolThread);
    }

    public void printThreads() {
        for (SymbolThread thread : threads) {
            thread.start();
        }
    }

    public void printSyncThreads() {
        for (SyncSymbolThread thread : syncThreads) {
            thread.start();
        }
    }
}
