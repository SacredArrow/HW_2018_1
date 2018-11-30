package filter;

import servlets.EmbeddedAsyncServlet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BlackWhiteFilter implements Filter{
    private File file;

    public BlackWhiteFilter(File file) {
        this.file = file;
    }

    public void process() {
        try {
            BufferedImage image = ImageIO.read(file);

            BufferedImage result = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_BYTE_BINARY);
            EmbeddedAsyncServlet.progress = 50;

            Graphics2D graphic = result.createGraphics();
            graphic.drawImage(image, 0, 0, Color.WHITE, null);
            graphic.dispose();
            EmbeddedAsyncServlet.progress = 100;
            ImageIO.write(result, EmbeddedAsyncServlet.format, file);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
