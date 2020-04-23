package task_b;

public class Client implements Runnable {
    Thread thread;
    boolean debug_on = true;
    Armchair armchair;
    volatile Queue queue;
    final int id;
    static int totalID;
    boolean chairIsMine;

    Client(Armchair armchair, Queue queue) {
        this.armchair = armchair;
        this.queue = queue;
        id = totalID++;
        chairIsMine = false;

        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        if (debug_on) System.out.println("client #" + id + "come to the hairdresser");
        synchronized (armchair) {
            if (armchair.isHairdresserSleep) {
                if (debug_on) System.out.println("client #" + id + "wakes up the hairdresser");
                armchair.notify();
            }

            if (armchair.whoIsSit == null) {
                chairIsMine = true;
                armchair.whoIsSit = this;

                try {
                    if (debug_on) System.out.println("client #" + id + "sleep in a chair.");
                    armchair.wait();
                    if (debug_on) System.out.println("client #" + id + "woke up after haircut.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (!chairIsMine) {
            try {
                synchronized (queue) {
                    if (debug_on) System.out.println("client #" + id + "sleep in queue");
                    queue.wait();
                    if (debug_on) System.out.println("client #" + id + "woke up after queue");
                }
                synchronized (armchair) {
                    armchair.whoIsSit = this;
                    if (debug_on) System.out.println("client #" + id + "sleep in a chair.");
                    armchair.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



        System.out.println("\nclient #" + id + "finished his haircut\n");
    }
}
