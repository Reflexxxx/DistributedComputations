package task_b;

import java.util.Queue;

public class Petrov implements Runnable {
    private Thread thread;
    private volatile Queue<Integer> buffer;
    private Necheporchuk necheporchuk;
    private volatile Truck truck;
    private volatile boolean isRunning;
    private volatile Switcher switcher;

    Petrov(Queue<Integer> buffer, Truck truck, Switcher switcher, Necheporchuk necheporchuk) {
        this.buffer = buffer;
        this.truck = truck;
        this.switcher = switcher;
        this.necheporchuk = necheporchuk;
        thread = new Thread(this, "Petrov");
        isRunning = true;
        thread.start();
    }

    public void run() {
        while (isRunning) {
            synchronized (buffer) {
                if (!buffer.isEmpty() && switcher.getIsLastElementCounted()) {
                    truck.addElement(buffer.poll());
                    switcher.setIsLastElementCounted(false);
                }
            }
        }
        int elementsLeft;
        synchronized (buffer) {
            elementsLeft = buffer.size();
        }
        while (elementsLeft > 0) {
            if (switcher.getIsLastElementCounted()) {
                synchronized (buffer) {
                    truck.addElement(buffer.poll());
                    elementsLeft = buffer.size();
                }
                switcher.setIsLastElementCounted(false);
            }

        }
        necheporchuk.stop();
    }

    void stop() {
        isRunning = false;
    }
}
