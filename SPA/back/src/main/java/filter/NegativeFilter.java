package filter;


import servlets.EmbeddedAsyncServlet;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class NegativeFilter implements Filter {
    File file;

    public NegativeFilter(File file) {
        this.file = file;
    }

    public void process() {
        int cntr = 0;
        BufferedImage img = null;
        //read image
        try {
            img = ImageIO.read(file);
        } catch (
                IOException e) {
            System.out.println(e);
        }

        //get image width and height
        int width = img.getWidth();
        int height = img.getHeight();
        //convert to negative
        for (
                int y = 0;
                y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = img.getRGB(x, y);
                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;
                //subtract RGB from 255
                r = 255 - r;
                g = 255 - g;
                b = 255 - b;
                //set new RGB value
                p = (a << 24) | (r << 16) | (g << 8) | b;
                img.setRGB(x, y, p);
                cntr++;
                if (cntr == width * height / 100) {
                    cntr = 0;
                    EmbeddedAsyncServlet.progress++;
                }
            }
        }
        //write image
        try {
            ImageIO.write(img, EmbeddedAsyncServlet.format, file);
        } catch (
                IOException e) {
            System.out.println(e);
        }
    }
}