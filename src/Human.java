import processing.core.PApplet;
import java.awt.Color;

public class Human extends Humanoid{

    //todo add food and water
    //private int food;
    //private int water;

    public Human(PApplet p, int x, int y, float size){
        super(p, x, y, size);
        setC(Color.BLUE);
        //todo remove this when i want humans to do something other than move straight up
        setyAcceleration(-1);
    }

    @Override
    public void Update(){
        super.Update();
    }

    @Override
    public void death(){
        super.death();
        Canvas.removeHuman(this);
    }
}
