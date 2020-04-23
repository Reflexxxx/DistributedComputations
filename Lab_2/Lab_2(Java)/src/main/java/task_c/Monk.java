package task_c;

public class Monk {
    enum Monastery {Guan_Yin, Guan_Yan}

    Monastery monastery;
    int qiEnergy;

    Monk(Monastery monastery, int qiEnergy) {
        this.monastery = monastery;
        this.qiEnergy = qiEnergy;
    }

    @Override
    public String toString() {
        return "\nMonk{" +
                "monastery=" + monastery +
                ", qiEnergy=" + qiEnergy +
                "}";
    }
}
