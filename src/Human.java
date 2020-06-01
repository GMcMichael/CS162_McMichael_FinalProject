import processing.core.PApplet;
import java.awt.Color;
import java.util.ArrayList;

public class Human extends Humanoid{

    private float food = 100;
    private float water = 100;
    private int interationRange = 200;
    private float resourceGatherAmount;
    private final float baseResourceUse = 0.1f;//todo update base resource use
    private final int sizeResourceUse = 25;
    private final int zombieSeekSize = 11;

    public Human(PApplet p, int x, int y, float size){
        super(p, x, y, size);
        setC(Color.BLUE);
        setSize(size);
    }

    @Override
    public void Update(){
        search();
        super.Update();
    }
    //make Humans do something other than seek food and water. Current idea is after they reach a certain size they seek out
    // zombies or maybe shelter to produce more humans, then if they start running out of food and reach a certain lower size they start seeking food and water away from zombies again
    // make humans only go for food and water if they are above a certain size based on human size
    private void search(){
        //search for food or water based on current resource amounts
        ArrayList<drawnObject> targets;
        if(getSize() > zombieSeekSize){
            targets = Canvas.getZombies();
        } else {
            if (food == water) targets = Canvas.getAllResources();
            else if (food < water) targets = Canvas.getFood();
            else targets = Canvas.getWater();
        }
        drawnObject closest = null;
        double closestDist = -1;
        for (drawnObject r: targets){
            double dist = Math.sqrt(Math.pow((r.getX() - getX()), 2) + Math.pow((r.getY() - getY()), 2));//distance formula
                if(closestDist == -1){
                    closestDist = dist;
                    closest = r;
                } else if(dist < closestDist) {
                    closestDist = dist;
                    closest = r;
                }
        }
        if(closest != null){
            setInteractTarget(closest);
            setTarget(new Point((int)closest.getX(), (int)closest.getY()));
        } else {
            setTarget(null);
        }
    }

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

    public void addResources(char type, float amount){
        switch (type){
            case 'f':
                setFood(getFood() + amount);
                break;
            case 'w':
                setWater(getWater() + amount);
                break;
        }
    }

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

    @Override
    public void interact(drawnObject d){
        if(d.getClass().getTypeName().equals("Zombie")) fight((Creature) d);
    }

    @Override
    public void death(){
        super.death();
        Canvas.removeHuman(this);
    }

    @Override
    public void setSize(float size){
        resourceGatherAmount = size;//todo update setting resourceGatherAmount
        super.setSize(size);
    }

    public float getFood() {
        return food;
    }

    public void setFood(float food) {
        this.food = food;
    }

    public float getWater() {
        return water;
    }

    public void setWater(float water) {
        this.water = water;
    }

    public float getResourceGatherAmount() {
        return resourceGatherAmount;
    }
}
