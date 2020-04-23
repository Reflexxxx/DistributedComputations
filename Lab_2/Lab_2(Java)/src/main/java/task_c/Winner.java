package task_c;

public class Winner {
    private Monk winner;
    private boolean isWinnerSet;

    Winner() {
        isWinnerSet = false;
    }

    synchronized void setWinner(Monk winner) {
        isWinnerSet = true;
        this.winner = winner;
    }

    synchronized boolean isWinnerSet() {
        return isWinnerSet;
    }

    synchronized Monk getWinner() {
        return winner;
    }

    @Override
    public String toString() {
        return "\nWinner{" + winner + '}';
    }
}
