package task_b;

public class Hairdresser implements Runnable {
    Thread thread;
    boolean debug_on = true;
    volatile Armchair armchair;
    volatile Queue queue;

    Hairdresser(Armchair armchair, Queue queue) {
        this.armchair = armchair;
        this.queue = queue;
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        while (!thread.isInterrupted()) {
            synchronized (queue) {
                if (debug_on) System.out.println("П: кто там в очереди следующий?");
                queue.notify();
            }
            if (debug_on) System.out.println("П: жду, пока клиет сядет в кресло.");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // gets here, when interrupt from main and should finish work
                break;
//                e.printStackTrace();
            }
            if (debug_on) System.out.println("П: ну что, кто то сел на стул?");
            synchronized (armchair) {
                if (armchair.whoIsSit == null) {
                    if (debug_on) System.out.println("П: никто не сел? Тогда я посплю.");
                    armchair.isHairdresserSleep = true;

                    try {
                        armchair.wait();
                        if (debug_on) System.out.println("П: Кто меня разбудил? Кого тут подстричь?");
                    } catch (InterruptedException e) {
                        System.out.println("Catch an exception");
                    }
                }
            }

            synchronized (armchair) {
                if (armchair.whoIsSit != null) {
                    System.out.println("\nП: Стригу клиента №" + ((Client) (armchair.whoIsSit)).id + "\n");
                    if (debug_on)
                        System.out.println("П: бужу клиента №" + ((Client) (armchair.whoIsSit)).id + " и провожу его со стула.");
                    armchair.notify();
                    armchair.whoIsSit = null;
                } else {
                    if (debug_on) System.out.println("В кресле пусто.");
                }
            }
        }
    }
}
