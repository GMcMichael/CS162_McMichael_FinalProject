import processing.core.PApplet;
import java.awt.Color;
import java.util.ArrayList;
/**
 * Zombie.java
 * @author Garrett McMichael
 * @version 1
 */
public class Zombie extends Creature {

    private int detectionRange = Canvas.getZombieDetectionRange();

    /**
     * Zombies' Constructor
     * @param p A variable of type PApplet
     * @param x A variable of type int
     * @param y A variable of type int
     * @param size A variable of type float
     */
    public Zombie(PApplet p, int x, int y, float size){
        super(p, x, y, size);
        setC(Canvas.getZombieColor());
    }

    /**
     * Function Overridden from Creature Class
     * Called every frame to update the Zombie object
     */
    @Override
    public void Update(){
        search();
        super.Update();
    }

    /**
     * Finds the closest human within a circle from
     * the Zombie object and sets it to be the target
     */
    private void search(){
        ArrayList<Human> humans = Canvas.getHumans();
        Human closest = null;
        double closestDist = -1;
        for (Human h: humans){
            double dist = Math.sqrt(Math.pow((h.getX() - getX()), 2) + Math.pow((h.getY() - getY()), 2));//distance formula
            if(dist <= detectionRange) {//human found
                if(closestDist == -1){
                    closestDist = dist;
                    closest = h;
                } else if(dist < closestDist) {
                    closestDist = dist;
                    closest = h;
                }
            }
        }
        if(closest != null){
            setInteractTarget(closest);
            setTarget(new Point((int)closest.getX(), (int)closest.getY()));
        } else {
            setTarget(null);
        }
    }

    /**
     * Function Overridden from Creature Class
     * Called when the Zombie should
     * die and handles it death
     */
    @Override
    public void death(){
        super.death();
        Canvas.removeZombie(this);
    }

}
