import processing.core.PApplet;

public class Creature extends drawnObject {
    
    private float maxSpeed;
    private float xVelocity;
    private float yVelocity;
    private float xAcceleration;
    private float yAcceleration;
    private Point target;
    private int interactionRange = (int) getSize();
    private drawnObject interactTarget;

    public Creature(PApplet p, int x, int y, float size){
        super(p, x, y, size);
        maxSpeed = 1;//(p.displayWidth/1000);//todo update max speed
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
            //todo random movement is just setting acceleration to -1, 0, or 1. Update when acceleration scale is set
            int x = (int) Math.round((Math.random()*2))-1;
            int y = (int) Math.round((Math.random()*2))-1;
            setxAcceleration(x);
            setyAcceleration(y);
        } else if(Math.sqrt(Math.pow((target.getX() - getX()), 2) + Math.pow((target.getY() - getY()), 2)) < interactionRange){
            //todo maybe split this into x and y so it doesnt move in as straight of a line
            if(interactTarget != null) interactTarget.interact(this);//sends the first char of the class name in lowercase as the parameter
        } else {
            //todo actually make acceleration when scale is set
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
