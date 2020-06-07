import processing.core.PApplet;
import java.awt.Color;

/**
 * drawnObject.java
 * @author Garrett McMichael
 * @version 1
 */
abstract class drawnObject {

    private int x;
    private int y;
    private Color c = Color.GRAY;
    private float size;
    private PApplet p;

    /**
     * drawnObjects' Constructor
     * @param p A variable of type PApplet
     * @param x A variable of type int
     * @param y A variable of type int
     * @param size A variable of type float
     */
    public drawnObject(PApplet p, int x, int y, float size){
        this.p = p;
        this.x = x;
        this.y = y;
        this.size = size;
    }

    /**
     * Called every frame to draw the object on the PApplet
     * Called from the Canvas class
     */
    public void draw(){
        p.stroke(Color.GRAY.getRGB());
        p.fill(c.getRGB());
        p.circle(x, y, size);
    }

    /**
     * Called every frame to update the object
     * Called from the Canvas class
     */
    public abstract void Update();

    /**
     * Called when a drawnObject is interacting with this object
     * @param d A variable of type drawnObject
     */
    public abstract void interact(drawnObject d);

    /**
     * Returns the objects' x coordinate
     * @return A variable of type int
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the objects' x coordinate
     * @param x A variable of type int
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns the objects' y coordinate
     * @return A variable of type int
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the objects' y coordinate
     * @param y A variable of type int
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Sets the objects' color
     * @param c A variable of type Color
     */
    public void setC(Color c) {
        this.c = c;
    }

    /**
     * Returns the objects' size variable
     * @return a variable of type float
     */
    public float getSize() {
        return size;
    }

    /**
     * Sets the objects' size variable
     * @param size A variable of type float
     */
    public void setSize(float size) {
        this.size = size;
    }

    /**
     * Returns the objects' p variable
     * @return A variable of type PApplet
     */
    public PApplet getP() {
        return p;
    }
}
