public class SyncSymbolThread extends Thread {
    private final char symbol;
    private final Sync sync;
    private final boolean controlValue;

    public SyncSymbolThread(char s, Sync sync, boolean controlValue) {
        this.symbol = s;
        this.sync = sync;
        this.controlValue = controlValue;
    }

    @Override
    public void run() {
        while (true) {
            sync.waitAndChange(controlValue, symbol);
            if (sync.isFinished()) {
                return;
            }
        }
    }
}
