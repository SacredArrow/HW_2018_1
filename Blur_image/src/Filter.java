import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Filter {

    private BufferedImage src;
    private BufferedImage target;
    private volatile int lineCounter;
    private int maxIndex;
    private final int RADIUS = 5;

    public Filter(@NotNull BufferedImage src) {
        this.src = src;
        this.target = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
    }

    public void process(int threadsNumber, Mode mode) throws InterruptedException {
        lineCounter = 0;
        Runnable run = new Runnable() {
            @Override
            public void run() {
                while (lineCounter < maxIndex) {
                    processLine(mode, lineCounter++);
                }
            }
        };

        if (mode == Mode.HORIZONTAL_PROCESSING) {
            maxIndex = src.getHeight();
        } else {
            maxIndex = src.getWidth();
        }

        Thread[] threads = new Thread[threadsNumber];
        for (int i = 0; i < threadsNumber; i++) {
            threads[i] = new Thread(run);
            threads[i].start();
        }
        for (int i = 0; i < threadsNumber; i++) {
            threads[i].join();
        }

    }

    private void processLine(Mode mode, int index) {
        if (mode == Mode.HORIZONTAL_PROCESSING) {
            for (int i = 0; i < src.getWidth(); i++) {
                processPixel(i, index);
            }
        } else {
            for (int i = 0; i < src.getHeight(); i++) {
                processPixel(index, i);
            }
        }
    }

    private void processPixel(int x, int y) {
        int pixel_cntr = 0;
        int[] colors = new int[3];
        for (int i = - RADIUS; i < RADIUS; i++) {
            for (int j = - RADIUS; j < RADIUS; j++) {
                if (x + i < src.getWidth() && x + i >= 0 && y + j < src.getHeight() && y + j >= 0) {
                    pixel_cntr++;
                    Color color = new Color(src.getRGB(x + i, y + j));
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    colors[0] += red;
                    colors[1] += green;
                    colors[2] += blue;
                }
            }
        }
        int new_red = colors[0] / pixel_cntr;
        int new_green = colors[1] / pixel_cntr;
        int new_blue = colors[2] / pixel_cntr;
        int new_color = getIntFromColor(new_red, new_green, new_blue);
        target.setRGB(x, y, new_color);

    }

    public int getIntFromColor(int red, int green, int blue) {
        red = (red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        green = (green << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
        blue = blue & 0x000000FF; //Mask out anything not blue.

        return 0xFF000000 | red | green | blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }

    public BufferedImage getTarget() {
        return target;
    }
}
