public class SymbolThread extends Thread {
    char symbol;
    public SymbolThread(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public void run() {
        for (int j = 0; j < 1000; j++) {
            for (int i = 0; i < 100; i++) {
                System.out.print(symbol);
            }
            System.out.println();
        }
    }
}
