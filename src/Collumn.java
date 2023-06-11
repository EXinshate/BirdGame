import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Collumn {
    BufferedImage image;
    int x, y;
    int width, height;
    int gap;
    int distance;
    Random random = new Random();

    public Collumn(int n) throws Exception{
        image = ImageIO.read(getClass().getResource("column.png"));
        width = image.getWidth();
        height = image.getHeight();
        gap = 144;
        distance = 245;
        x = 550 + (n - 1) * distance;
        y = random.nextInt(218) + 132;
    }

    public void step(){
        x--;
        if(x == -width / 2){
            x = distance / 2 - width / 2;
            y = random.nextInt(218) + 132;
        }
    }
}
