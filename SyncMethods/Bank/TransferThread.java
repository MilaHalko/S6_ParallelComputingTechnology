public class TransferThread extends Thread {
    private Bank bank;
    private int fromAccount;
    private int maxAmount;
    private static final int REPS = 1000;

    public TransferThread(Bank b, int from, int maxAmount) {
        bank = b;
        fromAccount = from;
        this.maxAmount = maxAmount;
    }

    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < REPS; i++) {
                int toAccount = (int) (bank.size() * Math.random());
                int amount = (int) (maxAmount * Math.random() / REPS);
//                bank.transfer(fromAccount, toAccount, amount);
//                bank.syncMethodTransfer(fromAccount, toAccount, amount);
//                bank.syncBlockTransfer(fromAccount, toAccount, amount);
                bank.lockTransfer(fromAccount, toAccount, amount);
            }
        }
    }
}