package task_a;

public class Pot {
    final int capacity;
    int honey;
    final static int HAS_MORE_SPACE = 1;
    final static int THE_LAST_GULP_OF_HONEY = 2;
    final static int POT_IS_FULL = 3;
    int state = HAS_MORE_SPACE;


    public Pot(int capacity) {
        this.capacity = capacity;
        honey = 0;
    }

    /**
     *
     * @return true - if pot is full, the bee should wake up the bear
     */
    synchronized int addGulpOfHoney() {
        System.out.println("Honey = " + honey);
        if (state == HAS_MORE_SPACE) {
            if (honey < capacity) {
                honey++;
            }
            if (honey == capacity) {
                state = THE_LAST_GULP_OF_HONEY;
            }
        } else {
            state = POT_IS_FULL;
        }

        return state;
    }

    public synchronized void clearHoney() {
        honey = 0;
        state = HAS_MORE_SPACE;
    }
}
