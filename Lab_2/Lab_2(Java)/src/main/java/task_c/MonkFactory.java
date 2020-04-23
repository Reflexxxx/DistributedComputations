package task_c;

import java.util.Random;

public class MonkFactory {
    private Random random;

    MonkFactory() {
        random = new Random();
    }

    Monk getNext() {
        return new Monk(random.nextInt(2) % 2 == 0 ? Monk.Monastery.Guan_Yin : Monk.Monastery.Guan_Yan, random.nextInt(450) + 50);
    }
}
