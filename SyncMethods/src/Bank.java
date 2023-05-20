import java.util.concurrent.locks.ReentrantLock;

public class Bank {
    public static final int NTEST = 10000;
    private final int[] accounts;
    private long transacts = 0;
    private final ReentrantLock bankLock = new ReentrantLock();

    public Bank(int n, int initialBalance) {
        accounts = new int[n];
        int i;
        for (i = 0; i < accounts.length; i++) accounts[i] = initialBalance;
        transacts = 0;
    }

    public void transfer(int from, int to, int amount) {
        accounts[from] -= amount;
        accounts[to] += amount;
        transacts++;
        if (transacts % NTEST == 0) test();
    }

    public void syncBlockTransfer(int from, int to, int amount) {
        synchronized (this) {
            accounts[from] -= amount;
            accounts[to] += amount;
            transacts++;
            if (transacts % NTEST == 0) test();
        }
    }

    public synchronized void syncTransfer(int from, int to, int amount) {
        transfer(from, to, amount);
    }

    public void lockTransfer(int from, int to, int amount) {
        bankLock.lock();
        try {
            accounts[from] -= amount;
            accounts[to] += amount;
            transacts++;
            if (transacts % NTEST == 0) test();
        } finally {
            bankLock.unlock();
        }
    }

    public void test() {
        int sum = 0;
        for (int account : accounts) sum += account;
        System.out.println("Transactions:" + transacts + " Sum: " + sum);
    }

    public int size() {
        return accounts.length;
    }
}
