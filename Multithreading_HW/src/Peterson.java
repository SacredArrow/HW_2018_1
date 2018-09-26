public class Peterson {

    private static int turn;
    private static boolean[] interested = {false, false};

    void lock (int proc_id) {
        int other = 1-proc_id;
        interested[proc_id] = true;
        turn = proc_id;
        while (turn == proc_id && interested[other]){
            // waiting, huh?
        }
    }
    void unlock(int proc_id) {
        interested[proc_id]=false;
    }
}
