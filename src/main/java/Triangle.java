import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Triangle extends Moveable{

    Triangle_Shape shape;

    public Triangle(Rectangle2D.Double boundingBox, Color color){
        super(boundingBox,true, color);

        shape = new Triangle_Shape(new Point2D[]{
                new Point2D.Double(x + width/2, y),
                new Point2D.Double(x + width, y + height),
                new Point2D.Double(x, y + height)
        });
    }

    @Override
    public void paint(Graphics2D g2d) {
        shape.setShape(new Point2D[]{
                new Point2D.Double(x + width/2, y),
                new Point2D.Double(x + width, y + height),
                new Point2D.Double(x, y + height)
        });
        g2d.setColor(getColor());
        g2d.fill(shape);

        g2d.setColor(Color.RED);
        g2d.draw(this);
    }

    class Triangle_Shape extends Path2D.Double {
        public Triangle_Shape(Point2D... points) {
            moveTo(points[0].getX(), points[0].getY());
            lineTo(points[1].getX(), points[1].getY());
            lineTo(points[2].getX(), points[2].getY());
            closePath();
        }

        public void setShape(Point2D... points){
            reset();
            moveTo(points[0].getX(), points[0].getY());
            lineTo(points[1].getX(), points[1].getY());
            lineTo(points[2].getX(), points[2].getY());
            closePath();
        }
    }
}