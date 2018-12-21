package filter;

import servlets.EmbeddedAsyncServlet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import org.jetbrains.annotations.NotNull;


public class BlurFilter implements Filter {
    private File file;
    private String id;
    private String format;
    public BlurFilter(File file, String id, String format) {
        this.file=file;
        this.id = id;
        this.format = format;
    }
    public enum Mode {
        HORIZONTAL_PROCESSING, VERTICAL_PROCESSING;
    }


    public class Filter {

        private BufferedImage src;
        private BufferedImage target;
        private AtomicInteger lineCounter = new AtomicInteger();
        private double progress;
        private int maxIndex;
        private final int RADIUS = 5;
        private ReentrantLock lock = new ReentrantLock();


        public Filter(@NotNull BufferedImage src) {
            this.src = src;
            this.target = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());

        }

        public void process(int threadsNumber, Mode mode) throws InterruptedException {
            lineCounter.set(0);

            Runnable run = new Runnable() {
                @Override
                public void run() {
                    int local_counter = lineCounter.getAndIncrement();
                    while (local_counter < maxIndex) {
                        processLine(mode, local_counter);
                        lock.lock();
                        progress += 100.0/maxIndex;
                        if (progress<100) {
                            EmbeddedAsyncServlet.map.put(id, (int) progress);
                        }
//                        System.out.println("Update " + id+" " + progress);
                        lock.unlock();
                        local_counter = lineCounter.getAndIncrement();
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
            EmbeddedAsyncServlet.map.put(id,100);

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
            try {
                target.setRGB(x, y, new_color);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Exception");
                System.out.println(x+ " "+ src.getWidth()+" "+y+" "+src.getHeight());
            }

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


    @Override
    public void process() {
        BufferedImage src = null;
        try {
            src = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Filter filter = new Filter(src);
        try {
            filter.process(8, Mode.HORIZONTAL_PROCESSING);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        BufferedImage target = filter.getTarget();
        try {
            ImageIO.write(target, format, file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
