import processing.core.PApplet;

public class Creature extends drawnObject {
    
    private int maxSpeed = Canvas.getMaxSpeed();
    private float xVelocity;
    private float yVelocity;
    private float xAcceleration;
    private float yAcceleration;
    private Point target;
    private int interactionRange = (int) getSize();
    private drawnObject interactTarget;

    public Creature(PApplet p, int x, int y, float size){
        super(p, x, y, size);
    }

    public void move(){
        calculateAcceleration(target);
        xVelocity += xAcceleration;
        yVelocity += yAcceleration;
        if(xVelocity > maxSpeed) xVelocity = maxSpeed;
        else if(xVelocity < -maxSpeed) xVelocity = -maxSpeed;
        if(yVelocity > maxSpeed) yVelocity = maxSpeed;
        else if(yVelocity < -maxSpeed) yVelocity = -maxSpeed;
        float newX = getX() + xVelocity;
        float newY = getY() + yVelocity;
        if((newX > 0) && (newX < Canvas.getWidth())) setX(newX);
        if((newY > 0) && (newY < Canvas.getHeight())) setY(newY);
    }

    public void calculateAcceleration(Point target){
        if(target == null){
            int x = (int) Math.round((Math.random()*2))-1;
            int y = (int) Math.round((Math.random()*2))-1;
            setxAcceleration(x);
            setyAcceleration(y);
        } else if(Math.sqrt(Math.pow((target.getX() - getX()), 2) + Math.pow((target.getY() - getY()), 2)) < interactionRange){
            if(interactTarget != null) interactTarget.interact(this);//sends the first char of the class name in lowercase as the parameter
        } else {
            if (target.getX() > getX()) setxAcceleration(1);
            else if (target.getX() < getX()) setxAcceleration(-1);
            if (target.getY() > getY()) setyAcceleration(1);
            else if (target.getY() < getY()) setyAcceleration(-1);
        }
    }

    public void interact(drawnObject d){
        //todo base creature interact function
    }

    public void death(){
        Canvas.removeObject(this);
    }

    public void Update(){
        if(getSize() <= 0) death();
        move();
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
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

    public Point getTarget() {
        return target;
    }

    public void setTarget(Point target) {
        this.target = target;
    }

    public drawnObject getInteractTarget() {
        return interactTarget;
    }

    @Override
    public void setSize(float size){
        interactionRange = (int) getSize();
        super.setSize(size);
    }

    public void setInteractTarget(drawnObject interactTarget) {
        this.interactTarget = interactTarget;
    }
}
