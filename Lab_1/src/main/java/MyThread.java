public class MyThread implements Runnable {
    private int v;
    private MainClass mainClass;
    private boolean isRunning = true;
//    private boolean outRunning = true;
    private Thread thread;


    public void setRunning(boolean running) {
        isRunning = running;
    }

//    public void setOutRunning(boolean running) {
//        outRunning = running;
//    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public void run() {
//        while (outRunning) {
            while (isRunning) {
                mainClass.setSliderValue(v);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                System.out.println("thread #" + v);
            }
//            System.out.println("out thread #" + v);
//        }
    }

    public void setMaxPriority() {
        thread.setPriority(Thread.MAX_PRIORITY);
    }

    public void setMinPriority() {
        thread.setPriority(Thread.MIN_PRIORITY);
    }

    public MyThread(int v, MainClass mainClass) {
        this.v = v;
        this.mainClass = mainClass;
        thread = new Thread(this);
        thread.start();
    }

    public void increasePriority() {
        int priority = thread.getPriority();
        if (priority + 1 <= Thread.MAX_PRIORITY) {
            thread.setPriority(priority + 1);
            System.out.println("thread #" + v + " set priority to: " + (priority + 1));
        }
    }

    public void decreasePriority() {
        int priority = thread.getPriority();
        if (priority - 1 >= Thread.MIN_PRIORITY) {
            thread.setPriority(priority - 1);
            System.out.println("thread #" + v + " set priority to: " + (priority - 1));
        }
    }

}
