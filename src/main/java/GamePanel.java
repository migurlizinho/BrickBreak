import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class GamePanel extends JComponent {

    private Graphics2D g2;
    private BufferedImage image;
    private int width;
    private int height;
    private Thread thread;
    private boolean start = true;

    private final int FPS = 60;
    private final int TARGET_TIME = 1000000000/FPS;

    private final double GRAVITY = 2;

    public GamePanel(int width, int height){
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
    }

    private Ball b1;
    private Triangle t1;

    public void start(){
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        double s1 = 200;
        b1 = new Ball((width / 2) - s1/2, 100, 0, 0, 0, GRAVITY, s1, Color.ORANGE);
        t1 = new Triangle(new Rectangle2D.Double((width / 2), 100, s1, s1), Color.ORANGE);

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(start){
                    long startTime = System.nanoTime();
                    drawBrackground();
                    drawGame();
                    render();
                    long time = System.nanoTime() - startTime;
                    if(time < TARGET_TIME){
                        long sleep = (TARGET_TIME - time) / 1000000;
                        sleep(sleep);
                    }
                }
            }
        });
        thread.start();
    }

    private void drawBrackground(){
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(0, 0, width, height);
    }

    private void drawGame(){
        t1.move(0, 0, width, height);
        t1.paint(g2);
        b1.move(0 , 0, width, height);
        b1.paint(g2);
    }

    private void render(){
        Graphics g = getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
    }

    private void sleep(long speed){
        try {
            Thread.sleep(speed);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "GamePanel{" +
                "g2=" + g2 +
                ", image=" + image +
                ", width=" + width +
                ", height=" + height +
                ", thread=" + thread +
                ", start=" + start +
                ", FPS=" + FPS +
                ", TARGET_TIME=" + TARGET_TIME +
                ", b1=" + b1 +
                '}';
    }
}
