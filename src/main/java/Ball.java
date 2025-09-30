import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Ball extends Moveable {

    private Ellipse2D ellipse2D;

    public Ball(double x, double y,double vx, double vy, double ax, double ay, double size, Color color) {
        super(x, y, vx, vy, ax, ay, size, color);
        ellipse2D = new Ellipse2D.Double(x, y, width, height);
    }

    public Ball(Rectangle2D.Double boundingBox, Moveable moveStats){
        super(moveStats);
    }

    @Override
    public void paint(Graphics2D g2d){
        ellipse2D.setFrame(x, y, width, height);
        g2d.setColor(this.getColor());
        g2d.fill(ellipse2D);
        g2d.setColor(Color.red);
        g2d.draw(this);
    }
}
