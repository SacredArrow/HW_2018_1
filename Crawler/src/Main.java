import java.io.IOException;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    static int number_of_threads = 4;
    static MyThreadPool pool;
    static int max_depth = 2;
    static MyConcurrentQueue<String> urls = new MyConcurrentQueue<>();

    public static void main(String[] args) throws IOException {
//        String addr = "https://en.wikipedia.org/wiki/Universal_Paperclips";
        String addr = "http://hwproj.me";
        urls.offer(addr);
//        pool = new ThreadPoolExecutor(number_of_threads, number_of_threads, Long.MAX_VALUE, TimeUnit.NANOSECONDS, new LinkedBlockingQueue<>());
        pool = new MyThreadPool(number_of_threads, new MyConcurrentQueue<>());
        LinkTask task = new LinkTask(addr, 1);
        pool.execute(task);

    }
}
