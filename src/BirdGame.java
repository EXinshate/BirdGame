import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class BirdGame extends JPanel {
    BufferedImage background;
    BufferedImage startImage;
    BufferedImage gameOverImage;

    Ground ground;
    Bird bird;

    Collumn collumn1, collumn2;
    int score;
    int state;
    public static final  int START = 0;
    public static final int RUNNING = 1;
    public static final int GAME_OVER = 2;

    public BirdGame() throws Exception{
        background = ImageIO.read(getClass().getResource("bg.png"));
        startImage = ImageIO.read(getClass().getResource("start.png"));
        gameOverImage = ImageIO.read(getClass().getResource("gameover.png"));
        ground = new Ground();
        collumn1 = new Collumn(1);
        collumn2 = new Collumn(2);
        bird = new Bird();
        score = 0;
        state = START;
    }
    public void paint(Graphics g){
        g.drawImage(background, 0 , 0, null);
        g.drawImage(ground.image,ground.x,ground.y,null);
        g.drawImage(collumn1.image,collumn1.x - collumn1.width / 2,collumn1.y-
                collumn1.height / 2,null);
        g.drawImage(collumn2.image, collumn2.x - collumn2.width / 2,
                collumn2.x - collumn2.height / 2, null);
        Graphics2D g2 = (Graphics2D) g;
        g2.rotate(-bird.alpha, bird.x, bird.y);
        g.drawImage(bird.image, bird.x - bird.width / 2, bird.y - bird.height / 2, null);
        g2.rotate(bird.alpha, bird.x, bird.y);

        Font f = new Font(Font.SANS_SERIF, Font.BOLD, 40);
        g.setFont(f);
        g.drawString("" + score, 40, 60);
        g.setColor(Color.white);
        g.drawString("" + score, 40 - 3, 60 - 3);
        switch (state){
            case START:
                g.drawImage(startImage, 0, 0, null);
                break;
            case GAME_OVER:
                g.drawImage(gameOverImage, 0,0,null);
                break;
        }
    }
    public static void main(String[] args) throws Exception{
        JFrame frame = new JFrame();
        BirdGame game = new BirdGame();
        frame.add(game);
        frame.setSize(440, 670);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        game.action();
    }
    public void action() throws Exception {
        MouseListener l = new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                try{
                    switch (state){
                        case START:
                            state = RUNNING;
                            break;
                        case RUNNING:
                            bird.flappy();
                            break;
                        case GAME_OVER:
                            collumn1 = new Collumn(1);
                            collumn2 = new Collumn(2);
                            bird = new Bird();
                            score = 0;
                            state = START;
                            break;
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        };
        addMouseListener(l);
        while (true) {
            switch (state) {
                case START:
                    bird.fly();
                    ground.step();
                    break;
                case RUNNING:
                    ground.step();
                    collumn1.step();
                    collumn2.step();
                    bird.fly();
                    bird.step();
                    if (bird.x == collumn1.x || bird.x == collumn2.x) {
                        score++;
                    }
                    if (bird.hit(ground) || bird.hit(collumn1) || bird.hit(collumn2)) {
                        state = GAME_OVER;
                    }
                    break;
            }
            repaint();
            Thread.sleep(1000 / 60);
        }
    }
}
