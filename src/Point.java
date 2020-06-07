/**
 * Point.java --- used to hold target coordinates for Creature class
 * @author Garrett McMichael
 * @version 1
 */
public class Point {

    private int x;
    private int y;

    /**
     * Points' Constructor
     * @param x A variable of type int
     * @param y A veriable of type int
     */
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the Points' x coordinate
     * @return A variable of type int
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the Points' y coordinate
     * @return A variable of type int
     */
    public int getY() {
        return y;
    }
}
