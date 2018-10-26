public class Run implements Runnable {
    int from;
    int to;
    int number;

    public Run(int from, int to, int number) {
        this.from = from;
        this.to = to;
        this.number = number;
    }

    public static Coordinates sumPairs(Coordinates p1, Coordinates p2) {
        p1.x = p1.x + p2.x;
        p1.y=p1.y+p2.y;
        return p1;
    }

    @Override
    public void run() {
        Coordinates tmp = Main.coordinates[from];

        for (int i = from + 1; i < to; i++) {
            tmp = sumPairs(tmp, Main.coordinates[i]);
        }
        Main.ress[number] = tmp;

    }
}
