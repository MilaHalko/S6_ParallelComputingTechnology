public class Main {
    public static void main(String[] args) {
        Printer printer = new Printer();

        printer.addSymbolThread(new SymbolThread('|'));
        printer.addSymbolThread(new SymbolThread('-'));

        Sync permission = new Sync();
        printer.addSyncSymbolThread(new SyncSymbolThread('|', permission, true));
        printer.addSyncSymbolThread(new SyncSymbolThread('-', permission, false));

        //printer.printThreads();
        printer.printSyncThreads();
    }
}