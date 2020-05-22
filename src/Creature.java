import processing.core.PApplet;

public class Creature extends drawnObject {

    //todo add health and strength
    //private int health;
    //private int strength;
    private float maxSpeed;
    private float xVelocity;
    private float yVelocity;
    private float xAcceleration;
    private float yAcceleration;

    public Creature(PApplet p, int x, int y, float size){
        super(p, x, y, size);
    }

    public void move(){
        xVelocity += xAcceleration;
        yVelocity += yAcceleration;
        if(xVelocity > maxSpeed) xVelocity = maxSpeed;
        else if(xVelocity < -maxSpeed) xVelocity = -maxSpeed;
        if(yVelocity > maxSpeed) yVelocity = maxSpeed;
        else if(yVelocity < -maxSpeed) yVelocity = -maxSpeed;
        setX(getX() + xVelocity);
        setY(getY() + yVelocity);
    }

    public void death(){
        Canvas.removeObject(this);
    }

    public void Update(){
        move();
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public float getxAcceleration() {
        return xAcceleration;
    }

    public void setxAcceleration(float xAcceleration) {
        this.xAcceleration = xAcceleration;
    }

    public float getyAcceleration() {
        return yAcceleration;
    }

    public void setyAcceleration(float yAcceleration) {
        this.yAcceleration = yAcceleration;
    }
}
