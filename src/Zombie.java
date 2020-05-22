import processing.core.PApplet;
import java.awt.Color;
import java.util.ArrayList;

public class Zombie extends Humanoid {

    private int detectionRange = 200;

    public Zombie(PApplet p, int x, int y, float size){
        super(p, x, y, size);
        setC(Color.RED);
    }

    //humans avoid zombies so only zombies will look for humans (currently)
     @Override
    public void Update(){
        search();
        super.Update();
    }

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
            setTarget(new Point((int)closest.getX(), (int)closest.getY()));
        } else {
            setTarget(null);
        }
    }



    @Override
    public void death(){
        super.death();
        Canvas.removeZombie(this);
    }

}
