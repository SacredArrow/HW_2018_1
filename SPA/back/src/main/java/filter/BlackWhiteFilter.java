package filter;

import servlets.EmbeddedAsyncServlet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BlackWhiteFilter implements Filter{
    private File file;
    private String id;
    private String format;

    public BlackWhiteFilter(File file, String id, String format) {
        this.file = file;
        this.id = id;
        this.format = format;
    }

    public void process() {
        try {
            BufferedImage image = ImageIO.read(file);

            BufferedImage result = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_BYTE_BINARY);
            EmbeddedAsyncServlet.map.put(id, 50);

            Graphics2D graphic = result.createGraphics();
            graphic.drawImage(image, 0, 0, Color.WHITE, null);
            graphic.dispose();

            ImageIO.write(result, format, file);
            EmbeddedAsyncServlet.map.put(id, 100);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
