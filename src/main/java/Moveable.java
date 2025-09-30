import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Moveable extends Rectangle2D.Double{
    private double vx;
    private double vy;
    private double ax;
    private double ay;
    private Color color;

    private void setStats(double vx, double vy, double ax, double ay, Color color){
        this.vx = vx;
        this.vy = vy;
        this.ax = ax;
        this.ay = ay;
        this.color = color;
    }

    private final double GRAVITY = 2;

    public Moveable(double x, double y,double vx, double vy, double ax, double ay, double size, Color color) {
        super(x, y, size, size);
        setStats(vx, vy, ax, ay, color);
    }

    public Moveable(Rectangle2D boundingBox, double vx, double vy, double ax, double ay, Color color){
        super(boundingBox.getX(), boundingBox.getY(), boundingBox.getWidth(), boundingBox.getHeight());
        setStats(vx, vy, ax, ay, color);

    }
    public Moveable(Rectangle2D boundingBox, boolean gravity, Color color){
        super(boundingBox.getX(), boundingBox.getY(), boundingBox.getWidth(), boundingBox.getHeight());
        setStats(0, 0, 0, gravity? GRAVITY : 0, color);
    }

    public Moveable(Moveable moveable){
        super(moveable.getX(), moveable.getY(), moveable.getWidth(), moveable.getHeight());
        setStats(moveable.vx, moveable.vy, moveable.ax, moveable.ay, moveable.color);
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public double getAx() {
        return ax;
    }

    public double getAy() {
        return ay;
    }

    public Color getColor() {
        return color;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public void setAx(double ax) {
        this.ax = ax;
    }

    public void setAy(double ay) {
        this.ay = ay;
    }

    public void invertVx(){
        this.vx *= -1;
    }

    public void invertVy(){
        this.vy *= -1;
    }

    @Override
    public String toString() {
        return "pos(" + x + "," + y + ")" + " vel(" + vx + "," + vy + ")" + " acc(" + ax + "," + ay + ")";
    }

    public abstract void paint(Graphics2D g2d);

    public void collisions(Rectangle2D.Double other){
        Rectangle2D.Double result = new Rectangle2D.Double();
        Rectangle2D.Double.intersect(this, other, result);
        if(!result.isEmpty()){
            if(result.width > result.height)
                this.invertVx();
            else
                this.invertVy();
        }
    }

    public void envioromentCollisions(int roof, int floor, int left, int right) {
        double x = this.getX();
        double y = this.getY();
        double vx = this.getVx();
        double vy = this.getVy();
        double size = this.getWidth();

        if (x + size > right) {
            this.invertVx();
            this.x = (right - size);
        }
        if (x < left) {
            this.invertVx();
            this.x = (left);
        }

        //System.out.println(y + " + " + size + " > " + floor);
        if (y + size > floor) {
            this.invertVy();
            this.y = (floor - size);
        }
        if (y < roof) {
            this.invertVy();
            this.y = (roof);
        }
    }

    public void move(int left, int roof, int right , int floor){
        vx += ax;
        vy += ay;
        x += vx;
        y += vy;
        envioromentCollisions(roof, floor, left, right);
    }
}
