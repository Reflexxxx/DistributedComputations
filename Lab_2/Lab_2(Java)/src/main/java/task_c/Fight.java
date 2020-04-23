package task_c;

import java.util.LinkedList;

public class Fight implements Runnable {
    Thread thread;
    volatile Winner winner;
    LinkedList<Monk> monks;

    Fight(Winner winner, LinkedList<Monk> monks) throws IllegalArgumentException {

        if (!((monks.size() & (monks.size() - 1)) == 0))
            throw new IllegalArgumentException("The number of monks should be a power of two!");

        this.winner = winner;
        this.monks = monks;

        thread = new Thread(this, "Fight");
        thread.start();
    }

    private void getWinner() {

        if (monks.size() > 2) {
            Winner win1 = new Winner();
            Winner win2 = new Winner();

            LinkedList<Monk> qiEn1 = new LinkedList<>();
            LinkedList<Monk> qiEn2 = new LinkedList<>();

            int halfSize = monks.size() / 2;
            for (int i = 0; i < halfSize; i++) {
                qiEn1.add(monks.remove());
            }

            while (!monks.isEmpty()) {
                qiEn2.add(monks.remove());
            }

            new Fight(win1, qiEn1);
            new Fight(win2, qiEn2);

            while (!win1.isWinnerSet() || !win2.isWinnerSet()) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            LinkedList<Monk> list = new LinkedList<>();
            list.add(win1.getWinner());
            list.add(win2.getWinner());

            new Fight(winner, list);

        } else {
            Monk monk1 = monks.remove();
            Monk monk2 = monks.remove();

            winner.setWinner(monk1.qiEnergy > monk2.qiEnergy ? monk1 : monk2);
        }
    }

    @Override
    public void run() {
        getWinner();
    }
}
