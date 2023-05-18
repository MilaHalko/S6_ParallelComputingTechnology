public class Sync {
    private boolean permission = true;
    private boolean finished = false;
    private int num = 0;

    public synchronized boolean isFinished() {
        return finished;
    }

    public synchronized void waitAndChange(boolean controlValue, char symbol) {
        while (this.permission != controlValue) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.print(symbol);
        this.permission = !permission;
        num++;
        if (num % 100 == 0) System.out.println();
        if (num == 1000) finished = true;
        notifyAll();
    }
}
