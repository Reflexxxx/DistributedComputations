package task_b;

import java.util.LinkedList;
import java.util.Random;

public class Stock {
    private Random random;
    private LinkedList<Integer> stuff;

    Stock(final int numberOfThings) {
        random = new Random();
        stuff = new LinkedList<>();
        for (int i = 0; i < numberOfThings; i++) {
            stuff.add(random.nextInt(5000) + 300);
        }
    }

    synchronized boolean isEmpty() {
        return stuff.isEmpty();
    }

    synchronized Integer getStuff() {
        return stuff.remove();
    }
}
