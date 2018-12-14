import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class LinkTask implements Runnable {
    String addr;
    int depth;

    public LinkTask(String addr, int depth) {
        this.addr = addr;
        this.depth = depth;
    }

    @Override
    public void run() {
        Document doc = null;
        try {
            Connection a = Jsoup.connect(addr);
            doc = a.get();
        } catch (IOException e) {
//            System.out.println("Bad myme type");
            return;
        }
        System.out.println(doc.location());
        Elements links = doc.select("a[href]");
//        LinkedBlockingQueue<String> link_q = new LinkedBlockingQueue<>();
        if (depth < Main.max_depth) {
            for (Element link : links) {
                String url = link.attr("abs:href");
//                Операция добавления в список пройденных не атомарна, так как вероятность
//                случая, когда в очередь будут добавлены несколько скачиваний, крайне мала,
//                а также не сделает ничего плохого(всего лишь одна страница будет скачана 2 раза).
//                Операция checkAndAdd, которая добавлят элемент, если его нет в списке, не логична -
//                Операция проверки на равенство двух элементов нужна только при добавлении строк(а не задач).
                if (!Main.urls.contains(url)) {
                    Main.urls.offer(url);
                    Main.pool.execute(new LinkTask(url, depth + 1));
                } else {
//                    System.out.println("Url "+url+ " already processed");
                }
            }
        }
        Main.pool.execute(new SaveTask(doc));
    }

    public String getAddr() {
        return addr;
    }
}
