public class Main {
    public static int NUMBER_OF_THREADS = 4;
    public static int NUMBER_OF_COMMANDS;
    public static Coordinates[] coordinates;
    public static volatile Coordinates[] ress;
    public static volatile Coordinates result;
    public static double[] angles;
    public static double[] prefixes;
    public static final Object sync = new Object();
    public static volatile int countedPrefixes;

    /*
    Algorithm:
    1. Count direction of turtle in every position(prefix scan on angles, then mod 360)
    2. Correct initial data(initial + prefix angle)
    3. Simple calculation relative to X0Y(t*cos(x), t*sin(x)
     */
    public static void main(String[] args) {
        Thread[] threads = new Thread[NUMBER_OF_THREADS];
        prefixes = new double[NUMBER_OF_THREADS];
        ress = new Coordinates[NUMBER_OF_THREADS];
        Command[] commands = new Command[] {new Command(45,40), new Command(30,50), new Command(105,40), new Command(90,20)};
        NUMBER_OF_COMMANDS = commands.length;
        angles = new double[NUMBER_OF_COMMANDS];
        for (int i=0; i<NUMBER_OF_COMMANDS;i++) {
            angles[i] = commands[i].angle;
        }
        int step = NUMBER_OF_COMMANDS/NUMBER_OF_THREADS;
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            if (i == NUMBER_OF_THREADS - 1) {
                threads[i] = new Thread(new RunAngles(i * step, NUMBER_OF_COMMANDS, i));
            } else {
                threads[i] = new Thread(new RunAngles(i * step, (i + 1) * step, i));
            }
            threads[i].start();
        }
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        coordinates = new Coordinates[NUMBER_OF_COMMANDS];
        for (int i = 0; i < NUMBER_OF_COMMANDS; i++) {
            coordinates[i] = new Coordinates(Math.cos(Math.toRadians(angles[i])) * commands[i].length, Math.sin(Math.toRadians(angles[i])) * commands[i].length);

        }

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            if (i == NUMBER_OF_THREADS - 1) {
                threads[i] = new Thread(new Run(i * step, NUMBER_OF_COMMANDS, i));
            } else {
                threads[i] = new Thread(new Run(i * step, (i + 1) * step, i));
            }
            threads[i].start();
        }
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Tree tree = new Tree(1, NUMBER_OF_THREADS);
        tree.create_tree(1, NUMBER_OF_THREADS);
        result = tree.upsweep();
        System.out.println(result); //Assuming that turtle started in (0,0)


    }
}
