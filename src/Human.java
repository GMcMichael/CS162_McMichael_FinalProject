import processing.core.PApplet;
import java.awt.Color;
import java.util.ArrayList;
/**
 * Human.java
 * @author Garrett McMichael
 * @version 1
 */
public class Human extends Creature{

    private float food = 100;
    private float water = 100;
    private float resourceGatherAmount;
    private final float baseResourceUse = (float) Canvas.getBaseResourceUse()/10;
    private final int sizeResourceUse = Canvas.getSizeResourceUse();
    private final int zombieSeekSize = Canvas.getZombieSeekSize();
    private boolean resourceTarget;
    private boolean reachedTarget = true;
    private int minResourceSize = 10;

    /**
     * Humans' Constructor
     * @param p A variable of type PApplet
     * @param x A variable of type int
     * @param y A variable of type int
     * @param size A variable of type float
     */
    public Human(PApplet p, int x, int y, float size){
        super(p, x, y, size);
        setC(Color.BLUE);
        setSize(size);
    }

    /**
     * Function Overridden from Creature Class
     * Called every frame to update the Human object
     */
    @Override
    public void Update(){
        search();
        super.Update();
    }

    /**
     * Finds the closest desired target within a circle from
     * the Human object and sets it to be the new target
     */
    private void search(){
        //search for food or water based on current resource amounts
        ArrayList<drawnObject> targets;
        if(getSize() > zombieSeekSize){
            targets = Canvas.getZombies();
            resourceTarget = false;
            reachedTarget = true;
        } else {
            if (food == water) targets = Canvas.getAllResources();
            else if (food < water) targets = Canvas.getFood();
            else targets = Canvas.getWater();
            resourceTarget = true;
        }
        drawnObject closest = null;
        double closestDist = -1;
        for (drawnObject r: targets){
            if((resourceTarget && (((Resource)r).getSize() > minResourceSize) && reachedTarget) || !resourceTarget) {
                double dist = Math.sqrt(Math.pow((r.getX() - getX()), 2) + Math.pow((r.getY() - getY()), 2));//distance formula
                if (closestDist == -1) {
                    closestDist = dist;
                    closest = r;
                } else if (dist < closestDist) {
                    closestDist = dist;
                    closest = r;
                }
            }
        }
        if(closest != null){
            setInteractTarget(closest);
            setTarget(new Point((int)closest.getX(), (int)closest.getY()));
            if(resourceTarget) reachedTarget = false;
        } else if(reachedTarget) {//!resourceTarget
            setTarget(null);
        }
    }

    /**
     * Handles resource use and size change base on current resources
     * Called from Canvas
     */
    public void useResources(){
        food -= baseResourceUse*getSize();
        water -= baseResourceUse*getSize();
        if(food < 0 || water < 0) {
            setSize(getSize()-1);
            food += sizeResourceUse;
            water += sizeResourceUse;
        } else if(food >= 100 & water >= 100){
            setSize(getSize()+1);
            food -= sizeResourceUse;
            water -= sizeResourceUse;
        }
    }

    /**
     * Handles adding resources to the Humans class
     * Called from the Resource class
     * @param type A variable of type Character
     * @param amount A variable of type float
     */
    public void addResources(char type, float amount){
        reachedTarget = true;
        switch (type){
            case 'f':
                setFood(getFood() + amount);
                break;
            case 'w':
                setWater(getWater() + amount);
                break;
        }
    }

    /**
     * Handles interactions with Zombie Objects
     * @param d A variable of type Creature
     */
    public void fight(Creature d){
        int winChance = (int) (60 * (getSize() / d.getSize()));//gets the chance the humans will win based on size difference * base 60% win chance
        int rand = (int) Math.round(Math.random() * 100);//gets an int from 0-100
        if(winChance >= rand){
            d.death();
            if(((winChance*0.8) >= rand) && (getSize() > 1)) setSize(getSize()-1);//20% chance that humans will "lose someone" in the fight and decrease in size
        }
        else {
            rand = (int) Math.round(Math.random() * 100);
            if(rand <= 80) Canvas.addZombie(new Zombie(getP(), (int) getX(), (int) getY(), getSize()));//20% chance to become zombie at death
            death();
        }
    }

    /**
     * Function Overridden from Creature Class
     * Handles interactions with drawnObjects Class
     * Called from the Creature Class
     * @param d A variable of type drawnObject
     */
    @Override
    public void interact(drawnObject d){
        if(d.getClass().getTypeName().equals("Zombie")) fight((Creature) d);
    }

    /**
     * Function Overridden from Creature Class
     * Called when the Human should
     * die and handles it death
     */
    @Override
    public void death(){
        super.death();
        Canvas.removeHuman(this);
    }

    /**
     * Function Overridden from Creature Class
     * Handles setting Human object size
     * @param size A variable of type float
     */
    @Override
    public void setSize(float size){
        resourceGatherAmount = size;
        super.setSize(size);
    }

    /**
     * Returns the Humans' food variable
     * @return A variable of type float
     */
    public float getFood() {
        return food;
    }

    /**
     * Sets the Humans' food variable
     * @param food A variable of type float
     */
    public void setFood(float food) {
        this.food = food;
    }

    /**
     * Returns the Humans water variable
     * @return
     */
    public float getWater() {
        return water;
    }

    /**
     * Sets the Humans' water variable
     * @param water A variable of type float
     */
    public void setWater(float water) {
        this.water = water;
    }

    /**
     * Returns the Humans' resourceGatherAmount variable
     * @return A variable of type float
     */
    public float getResourceGatherAmount() {
        return resourceGatherAmount;
    }
}
