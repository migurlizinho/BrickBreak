import java.awt.*;

public class Racket extends Controlable{

    public Racket(Double BoundBox, Color color, boolean gravityAffected, double VelX, double VelY) {
        super(BoundBox, color, gravityAffected, VelX, VelY);

    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.fill(this.getBounds2D());
    }
}
