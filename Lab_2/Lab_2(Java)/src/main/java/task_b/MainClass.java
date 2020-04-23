package task_b;

import java.util.LinkedList;
import java.util.Queue;

public class MainClass {

    private Truck truck;
    private Stock stock;
    private volatile Queue<Integer> buffer;
    private Ivanov ivanov;
    private Necheporchuk necheporchuk;
    private Petrov petrov;
    private volatile Switcher switcher;
    private final int numberOfThings = 5;

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();

        mainClass.start();
    }

    void start() {
        truck = new Truck();
        stock = new Stock(numberOfThings);
        buffer = new LinkedList<>();
        switcher = new Switcher();
        necheporchuk = new Necheporchuk(buffer, switcher, this);
        petrov = new Petrov(buffer, truck, switcher, necheporchuk);
        ivanov = new Ivanov(stock, buffer, petrov);

        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(truck);
    }
}
