import processing.core.PApplet;

public class Creatures extends drawnObject {

    //todo add health
    //private int health;
    private float maxSpeed;
    private float xVelocity;
    private float yVelocity;
    private float xAcceleration;
    private float yAcceleration;

    public Creatures(PApplet p, int x, int y){
        super(p, x, y);
    }

    public void move(){
        xVelocity += xAcceleration;
        yVelocity += yAcceleration;
        setX(getX() + xVelocity);
        setY(getY() + yVelocity);
    }

}
