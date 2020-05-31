import processing.core.PApplet;
import processing.core.PFont;
import java.awt.Color;
import java.util.ArrayList;

public class Canvas extends PApplet {

    private static int width = 1000;
    private static int height = 1000;
    private static ArrayList<drawnObject> drawObjects = new ArrayList<drawnObject>();
    private static ArrayList<Human> humanObjects = new ArrayList<Human>();
    private static ArrayList<Zombie> zombieObjects = new ArrayList<Zombie>();
    private static ArrayList<Resource> foodObjects = new ArrayList<Resource>();
    private static ArrayList<Resource> waterObjects = new ArrayList<Resource>();
    private static int humans;
    private static int zombies;
    private static final int minHumans = 20;
    private static final int minZombies = 20;
    private static final int maxHumans = 100;
    private static final int maxZombies = 100;
    private static final int minFood = minHumans/10;
    private static final int maxFood = maxHumans/10;
    private static final int minWater = minHumans/10;
    private static final int maxWater = maxHumans/10;
    private final int useTimerMax = 50;//todo update resource use timer
    private int resourceUseTimer = useTimerMax;
    private static int spawnOffset = (width/20);
    private PFont font;

    public void settings(){
        size(width, height);
    }

    public void setup(){
        font = createFont("Arial",16);
        textFont(font);
        textAlign(CENTER);

        //creates humans and zombies
        humans = (int)((Math.random()*(maxHumans-minHumans))+minHumans);
        zombies = (int)((Math.random()*(maxZombies-minZombies))+minZombies);
        //todo randomize human and zombie spawns across entire screen and size
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

        //creates resources
        int food = (int)((Math.random()*(maxFood-minFood))+minFood);
        int water = (int)((Math.random()*(maxWater-minWater))+minWater);
        for(int i = 0; i < food; i++){
            int x = (int) ((Math.random() * ((width - spawnOffset) - spawnOffset)) + spawnOffset);
            int y = (int) ((Math.random() * ((height - spawnOffset) - spawnOffset)) + spawnOffset);
            foodObjects.add(new Resource(this, x, y, 10, 'f'));//todo randomize size
        }
        for(int i = 0; i < water; i++){
            int x = (int) ((Math.random() * ((width - spawnOffset) - spawnOffset)) + spawnOffset);
            int y = (int) ((Math.random() * ((height - spawnOffset) - spawnOffset)) + spawnOffset);
            waterObjects.add(new Resource(this, x, y, 10, 'w'));//todo randomize size
        }
        drawObjects.addAll(foodObjects);
        drawObjects.addAll(waterObjects);
    }

    public void draw(){
        background(255);
        resourceUseTimer--;
        if(resourceUseTimer < 0) {
            ArrayList<Human> currHumans = new ArrayList<Human>();
            currHumans.addAll(humanObjects);
            for (Human h: currHumans) {
                h.useResources();
            }
            resourceUseTimer = useTimerMax;
        }
        ArrayList<drawnObject> curDrawObjects = new ArrayList<drawnObject>();
        curDrawObjects.addAll(drawObjects);
        for(drawnObject d: curDrawObjects){
            d.Update();
            d.draw();
        }
        fill(Color.BLACK.getRGB());
        text("Zombies: " + zombies, (width/2), 50);
        text("Humans: " + humans, (width/2), (height-50));
        if(humanObjects.size() > 0){
            text("Timer: " + resourceUseTimer, (width/2), (height-20));
            text("Food: " + humanObjects.get(0).getFood() + ", Water: " + humanObjects.get(0).getWater(), (width/2), (height-5));
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
        drawObjects.add(h);
    }

    public static void removeHuman(Human h){
        humans--;
        humanObjects.remove(h);
    }

    public static void addZombie(Zombie z){
        zombies++;
        zombieObjects.add(z);
        drawObjects.add(z);
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

    public static ArrayList<Resource> getAllResources() {
        ArrayList<Resource> allResources = new ArrayList<Resource>();
        allResources.addAll(foodObjects);
        allResources.addAll(waterObjects);
        return  allResources;
    }

    public static ArrayList<Resource> getFood() {
        return foodObjects;
    }

    public static ArrayList<Resource> getWater() {
        return waterObjects;
    }
}
