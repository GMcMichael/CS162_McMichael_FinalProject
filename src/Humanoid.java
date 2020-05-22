import processing.core.PApplet;

public class Humanoid extends Creature {

    public Humanoid(PApplet p, int x, int y, float size){
        super(p, x, y, size);
    }

    //todo add random movement when there isn't a target by randomizing the target point
    //todo optimize acceleration by multiplying variable by pos/neg from change?
    public void calculateAcceleration(Point target){
        if(target.getX() > getX()) setxAcceleration(1);
        else if(target.getX() < getX()) setxAcceleration(-1);
        if(target.getY() > getY()) setyAcceleration(1);
        else if(target.getY() < getY()) setyAcceleration(-1);
    }

}
