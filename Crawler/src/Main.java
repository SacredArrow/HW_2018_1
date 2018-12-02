
import java.io.IOException;
import java.util.concurrent.*;

public class Main {
    static int number_of_threads = 4;
    static MyThreadPool pool;
    static int max_depth = 2;
    static ConcurrentSkipListSet<String> urls = new ConcurrentSkipListSet<>();
    public static void main(String[] args) throws IOException {
//        String addr = "https://en.wikipedia.org/wiki/Universal_Paperclips";
        String addr = "http://hwproj.me";
        urls.add(addr);
//        pool = new ThreadPoolExecutor(number_of_threads, number_of_threads, Long.MAX_VALUE, TimeUnit.NANOSECONDS, new LinkedBlockingQueue<>());
        pool = new MyThreadPool(number_of_threads, new LinkedBlockingQueue<>());
        LinkTask task = new LinkTask(addr, 1);
        pool.execute(task);
    }
}
