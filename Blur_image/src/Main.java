import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class Main {

    static String file_name = "wallpaper";
    static String file_format = "jpg";
    static String path = "res/";


    public static void main(String[] args) throws IOException {
        BufferedImage src = null;
        src = ImageIO.read(new File(path + file_name + "." + file_format));

        Filter filter = new Filter(src);
        try {
            filter.process(8, Mode.HORIZONTAL_PROCESSING);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        BufferedImage target = filter.getTarget();
        ImageIO.write(target, file_format, new File(path + file_name + "_output." + file_format));
        System.out.println("image created");

    }
}
