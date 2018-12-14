public class Run implements Runnable {
    int from;
    int to;
    int number;

    public Run(int from, int to, int number) {
        this.from = from;
        this.to = to;
        this.number = number;
    }

    public static Pair sumPairs(Pair p1, Pair p2) {
        Pair tmp = new Pair(0, 0);
        tmp.a = p1.a * p2.a;
        tmp.b = p2.a * p1.b + p2.b;
        return tmp;
    }

    @Override
    public void run() {
        Pair tmp = Main.pairs[from];

        for (int i = from + 1; i < to; i++) {
            tmp = sumPairs(tmp, Main.pairs[i]);
        }
        Main.ress[number] = tmp;

    }
}
