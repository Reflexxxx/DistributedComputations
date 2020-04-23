package task_a;

public class MainClass {
    final static int NUMBER_OF_BEES = 5;
    final static int POT_CAPACITY = 1000;


    public static void main(String[] args) {
        final Pot pot = new Pot(POT_CAPACITY);
        Bear bear = new Bear(pot);
        Bee[] bees = new Bee[NUMBER_OF_BEES];
        for (int i = 0; i < NUMBER_OF_BEES; i++) {
            bees[i] = new Bee(pot);
        }

        try {
            Thread.sleep(1500000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        bear.thread.interrupt();
        for (int i = 0; i < bees.length; i++) {
            bees[i].thread.interrupt();
        }
    }
}
