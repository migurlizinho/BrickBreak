import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.Map;
import java.util.Vector;

public abstract class Controlable extends Moveable {
    public Map<KeyEvent, Runnable> actions;
    private static KeyEvent[] keys;

    public Controlable(Moveable moveable, KeyEvent... keys){
        super(moveable);
        this.keys = keys;

        if(this.getVx() < 0)
            this.setVx(-this.getVx());
        if(this.getVy() < 0)
            this.setVy(-this.getVy());
    }

    public void moveUp(){
        this.y -= this.getVy();
    }

    public void moveDown(){
        this.y += this.getVy();
    }

    public void moveLeft(){
        this.x -= this.getVx();
    }

    public void moveRight(){
        this.x += this.getVx();
    }
}
