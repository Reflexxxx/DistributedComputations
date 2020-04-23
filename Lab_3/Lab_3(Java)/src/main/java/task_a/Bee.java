package task_a;

public class Bee implements Runnable {
    static int id = 0;

    int beeID = id++;
    Thread thread;
    final Pot pot;
    boolean isBeesRunning = true;

    Bee(Pot pot) {
        this.pot = pot;
        thread = new Thread(this);
        thread.start();
        System.out.println("Bee #" + beeID + " started.");
    }

    public void run() {
        while (isBeesRunning) {
            System.out.print("Bee #" + beeID + ": ");
            int state = pot.addGulpOfHoney();
            if (state == Pot.THE_LAST_GULP_OF_HONEY) {
                System.out.println("Bee #" + beeID + " put the last gulp of honey. Notify on put (should wake the bear up).");
                synchronized (pot) {
                    pot.notifyAll();
                    try {
                        pot.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else if (state == Pot.POT_IS_FULL) {
                System.out.println("Bee #" + beeID + " try to put a gulp of honey, but the pot is already full. Bee falls asleep.");
                synchronized (pot) {
                    try {
                        pot.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Bee #" + beeID + " wake up after sleep");
            }
        }
    }
}
