import processing.core.PApplet;
import processing.core.PFont;

import java.awt.*;
import java.util.ArrayList;

public class Canvas extends PApplet {

    private static int width = 1000;
    private static int height = 1000;
    private static ArrayList<drawnObject> drawObjects = new ArrayList<drawnObject>();
    private static ArrayList<Human> humanObjects = new ArrayList<Human>();
    private static ArrayList<Zombie> zombieObjects = new ArrayList<Zombie>();
    private static int humans;
    private static int zombies;
    private static int minHumans = 20;
    private static int minZombies = 20;
    private static int maxHumans = 100;
    private static int maxZombies = 100;
    private static int spawnOffset = (width/20);
    private PFont font;

    public void settings(){
        size(width, height);
    }

    public void setup(){
        //what to do before simulation is started
        font = createFont("Arial",16);
        textFont(font);
        textAlign(CENTER);

        humans = (int)(Math.random()*maxHumans);
        if(humans < minHumans) humans = minHumans;
        zombies = (int)(Math.random()*maxZombies);
        if(zombies < minZombies) zombies = minZombies;
        //todo randomize human and zombie spawns and size
        for (int i = 0; i < humans; i++) {
            int x = (int) ((Math.random() * ((width - spawnOffset) - spawnOffset)) + spawnOffset);
            humanObjects.add(new Human(this, x, 800, 10));
        }
        for (int i = 0; i < zombies; i++) {
            int x = (int) ((Math.random() * ((width - spawnOffset) - spawnOffset)) + spawnOffset);
            zombieObjects.add(new Zombie(this, x, 200, 10));
        }
        drawObjects.addAll(humanObjects);
        drawObjects.addAll(zombieObjects);
    }

    public void draw(){
        background(255);
        for(drawnObject d: drawObjects){
            d.Update();
            d.draw();
        }
        fill(Color.BLACK.getRGB());
        text("Zombies: " + zombies, (width/2), 50);
        text("Humans: " + humans, (width/2), (height-50));
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

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }
}
