import org.jsoup.nodes.Document;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SaveTask implements Runnable {
    Document doc;

    public SaveTask(Document doc) {
        this.doc = doc;
    }

    @Override
    public void run() {
        System.out.println("Saving " + doc.location());
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("res/" + doc.location().replace('/', ' ').replace(':', ' ') + ".html"));
            writer.write(doc.toString());

        } catch (IOException e) {
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
