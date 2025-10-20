import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class Controlable extends Moveable{
    private final Map<Integer, Function<Controlable, Boolean>> actions;
    private double VelX, VelY;

    public Controlable(Rectangle2D.Double BoundBox, Color color, boolean gravityAffected,  double VelX, double VelY){
        super(BoundBox, gravityAffected, color);
        this.VelX = VelX;
        this.VelY = VelY;

        actions = new HashMap<>();

        if(this.getVx() < 0)
            this.setVx(-this.getVx());
        if(this.getVy() < 0)
            this.setVy(-this.getVy());
    }

    public Function<Controlable, Boolean> getAction(int Keycode){
        return actions.get(Keycode);
    }

    public void runAction(int Keycode){
        Function<Controlable, Boolean> action = actions.get(Keycode);
        if(action != null)
            action.apply(this);
    }

    public Controlable addAction(Integer Keycode, Function<Controlable, Boolean> action){
        if(Keycode != -1 && action != null)
            actions.put(Keycode, action);
        return this;
    }

    public final static Function<Controlable, Boolean> moveUp = new Function<Controlable, Boolean>() {
        @Override
        public Boolean apply(Controlable controlable) {
            controlable.setVy(-controlable.VelY);
            return true;
        }
    };
    public final static Function<Controlable, Boolean> moveDown = new Function<Controlable, Boolean>() {
        @Override
        public Boolean apply(Controlable controlable) {
            controlable.setVy(controlable.VelY);
            return true;
        }
    };
    public final static Function<Controlable, Boolean> moveLeft = new Function<Controlable, Boolean>() {
        @Override
        public Boolean apply(Controlable controlable) {
            controlable.setVx(-controlable.VelX);
            return true;
        }
    };
    public final static Function<Controlable, Boolean> moveRight = new Function<Controlable, Boolean>() {
        @Override
        public Boolean apply(Controlable controlable) {
            controlable.setVx(controlable.VelX);
            return true;
        }
    };

    @Override
    public String toString() {
        return super.toString();
    }
}