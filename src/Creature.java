import processing.core.PApplet;
/**
 * Creature.java
 * @author Garrett McMichael
 * @version 1
 */
public class Creature extends drawnObject {
    
    private int maxSpeed = Canvas.getMaxSpeed();
    private float xVelocity;
    private float yVelocity;
    private float xAcceleration;
    private float yAcceleration;
    private Point target;
    private int interactionRange = (int) getSize();
    private drawnObject interactTarget;

    /**
     * Creatures' Constructor
     * @param p A variable of type PApplet
     * @param x A variable of type int
     * @param y A variable of type int
     * @param size A variable of type float
     */
    public Creature(PApplet p, int x, int y, float size){
        super(p, x, y, size);
    }

    /**
     * Moves the creature object on the coordinate plane while staying within the screen
     */
    public void move(){
        calculateAcceleration(target);
        xVelocity += xAcceleration;
        yVelocity += yAcceleration;
        if(xVelocity > maxSpeed) xVelocity = maxSpeed;
        else if(xVelocity < -maxSpeed) xVelocity = -maxSpeed;
        if(yVelocity > maxSpeed) yVelocity = maxSpeed;
        else if(yVelocity < -maxSpeed) yVelocity = -maxSpeed;
        int newX = (int) (getX() + xVelocity);
        int newY = (int) (getY() + yVelocity);
        if((newX > 0) && (newX < Canvas.getWidth())) setX(newX);
        if((newY > 0) && (newY < Canvas.getHeight())) setY(newY);
    }

    /**
     * Calculates the acceleration based on the Creatures' and targets' coordinates
     * @param target A variable of type Point
     */
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

    /**
     * Empty function to be overridden by children classes
     * @param d A variable of type drawnObject
     */
    public void interact(drawnObject d){
        //Overridden in children classes
    }

    /**
     * Called when the Creature should die and handles it
     */
    public void death(){
        Canvas.removeObject(this);
    }

    /**
     * Called every frame to update the Creature object
     */
    public void Update(){
        if(getSize() <= 0) death();
        move();
    }

    /**
     * Sets the Creatures' x-axis acceleration
     * @param xAcceleration A variable of type float
     */
    public void setxAcceleration(float xAcceleration) {
        this.xAcceleration = xAcceleration;
    }

    /**
     * Sets the Creatures' y-axis acceleration
     * @param yAcceleration A variable of type float
     */
    public void setyAcceleration(float yAcceleration) {
        this.yAcceleration = yAcceleration;
    }

    /**
     * Sets the Creatures' target Point
     * @param target A variable of type Point
     */
    public void setTarget(Point target) {
        this.target = target;
    }

    /**
     * Sets the Creatures' size
     * Overridden from the drawnObject class
     * @param size A variable of type float
     */
    @Override
    public void setSize(float size){
        interactionRange = (int) getSize();
        super.setSize(size);
    }

    /**
     * Sets the Creatures' interaction target
     * @param interactTarget A variable of type drawnObject
     */
    public void setInteractTarget(drawnObject interactTarget) {
        this.interactTarget = interactTarget;
    }
}
