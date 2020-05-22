import processing.core.PApplet;
import java.awt.Color;

public class Human extends Humanoid{

    //todo add food and water
    //private int food;
    //private int water;

    public Human(PApplet p, int x, int y, float size){
        super(p, x, y, size);
        setC(Color.BLUE);
        //todo remove this when I want humans to do something other than move straight up
        setTarget(new Point(x, 0));
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
}
