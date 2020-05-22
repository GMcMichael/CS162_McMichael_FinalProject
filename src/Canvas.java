import processing.core.PApplet;
import java.util.ArrayList;

public class Canvas extends PApplet {

    private static ArrayList<drawnObject> drawObjects = new ArrayList<drawnObject>();
    private static ArrayList<Human> humanObjects = new ArrayList<Human>();
    private static ArrayList<Zombie> zombieObjects = new ArrayList<Zombie>();
    private static int humans;
    private static int zombies;
    private static int minHumans = 20;
    private static int minZombies = 20;
    private static int maxHumans = 100;
    private static int maxZombies = 100;
    //todo display human and zombie numbers

    public void settings(){

        size(1000, 1000);
    }

    public void setup(){
        //what to do before simulation is started
        humans = (int)(Math.random()*maxHumans);
        if(humans < minHumans) humans = minHumans;
        zombies = (int)(Math.random()*maxZombies);
        if(zombies < minZombies) zombies = minZombies;
        //todo randomize human and zombie spawns and size
        for (int i = 0; i < humans; i++) {
            drawObjects.add(new Human(this, 0, 0, 0));
        }
        for (int i = 0; i < zombies; i++) {
            drawObjects.add(new Zombie(this, 0, 0, 0));
        }
    }

    public void draw(){
        for(drawnObject d: drawObjects){
            d.Update();
            d.draw();
        }
    }

    public static void removeObject(drawnObject d){
        drawObjects.remove(d);
    }

    public static void addObject(drawnObject d){
        drawObjects.add(d);
    }

    public static ArrayList<Human> getHumans(){
        return humanObjects;
    }

    public static void addHuman(Human h){
        humans++;
        humanObjects.add(h);
    }

    public static void removeHuman(Human h){
        humans--;
        humanObjects.remove(h);
    }

    public static void addZombie(Zombie z){
        zombies++;
        zombieObjects.add(z);
    }

    public static void removeZombie(Zombie z){
        zombies--;
        zombieObjects.remove(z);
    }

}
