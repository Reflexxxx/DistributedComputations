package task_a;

public class Bear implements Runnable {
    Thread thread;
    final Pot pot;
    boolean isBearRunning = true;

    Bear(Pot pot) {
        this.pot = pot;
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        while (isBearRunning) {
            synchronized (pot) {
                try {
                    pot.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Beer was waken up");
            pot.clearHoney();

            synchronized (pot) {
                pot.notifyAll();
            }

        }
    }
}
