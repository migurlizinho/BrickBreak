import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Vector;
import java.util.function.Consumer;

public class GamePanel extends JComponent implements KeyListener {
    private Graphics2D g2;
    private BufferedImage image;
    private final int width;
    private final int height;
    private Thread thread;
    private final boolean start = true;

    private static final int FPS = 60;
    private static final int TARGET_TIME = 1000000000/FPS;

    private final static double GRAVITY = 2;

    private Vector<Moveable> movables;
    private Vector<Controlable> controlables;

    public GamePanel(int width, int height){
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
    }

    public void start(){
        setFocusable(true);
        addKeyListener(this);
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        movables = new Vector<>();
        controlables = new Vector<>();

        double s1 = 75;
        //moveables.add(new Ball((width / 2) - s1/2, 100, 0, 0, 0, GRAVITY, s1, Color.ORANGE));

        Racket racket = new Racket(new Rectangle2D.Double((width / 2) - s1/2, 100, s1, s1), Color.ORANGE, false, 5, 5);
        racket.addAction(KeyEvent.VK_W, Controlable.moveUp).addAction(KeyEvent.VK_A, Controlable.moveLeft)
                .addAction(KeyEvent.VK_S, Controlable.moveDown).addAction(KeyEvent.VK_D, Controlable.moveRight);
        controlables.add(racket);

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(start){
                    long startTime = System.nanoTime();
                    drawBackground();
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

    private void drawBackground(){
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(0, 0, width, height);
    }

    private void drawGame(){
        movables.forEach(new Consumer<Moveable>() {
            @Override
            public void accept(Moveable moveable) {
                moveable.move(0, 0, width, height);
                moveable.paint(g2);
            }
        });
        controlables.forEach(new Consumer<Controlable>() {
            @Override
            public void accept(Controlable controlable) {
                controlable.move(0, 0, width, height);
                controlable.paint(g2);
            }
        });
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
                ", moveables=" + movables +
                ", controlables=" + controlables +
                '}';
    }

    @Override
    public void keyPressed(KeyEvent e) {
        for (Controlable controlable : controlables) {
            controlable.runAction(e.getKeyCode());
        }
    }

    @Override public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        for (Controlable controlable : controlables) {
            controlable.setVx(0);
            controlable.setVy(0);
        }
    }
}