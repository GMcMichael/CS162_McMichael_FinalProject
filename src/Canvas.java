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
    private static int humans = 0;
    private static int zombies = 0;
    private static int minHumans = 20;
    private static int minZombies = 20;
    private static int maxHumans = 100;
    private static int maxZombies = 100;
    private static int minFood = 0;
    private static int maxFood = 0;
    private static int minWater = 0;
    private static int maxWater = 0;
    private static int foodBaseRegenAmount = 10;
    private static int waterBaseRegenAmount = 10;
    private static int foodMaxSize = 15;
    private static int waterMaxSize = 15;
    private static int foodStartAmount = 100;
    private static int waterStartAmount = 100;
    private static int useTimerMax = 50;
    private int resourceUseTimer = useTimerMax;
    private static int baseResourceUse = 1;
    private static int sizeResourceUse = 25;
    private static int zombieSeekSize = 11;
    private static int maxSpeed = 1;
    private static int zombieDetectionRange = 200;
    private static int spawnOffset;
    private PFont font;

    public void settings(){
        size(width, height);
    }

    public void setup(){
        if(minFood == 0) minFood = minHumans/10;
        if(maxFood == 0) maxFood = maxHumans/10;
        if(minWater == 0) minWater = minHumans/10;
        if(maxWater == 0) maxWater = maxHumans/10;
        spawnOffset = (width/10);

        font = createFont("Arial",16);
        textFont(font);
        textAlign(CENTER);

        //creates humans and zombies
        if(humans == 0) humans = (int)((Math.random()*(maxHumans-minHumans))+minHumans);
        if(zombies == 0) zombies = (int)((Math.random()*(maxZombies-minZombies))+minZombies);
        for (int i = 0; i < humans; i++) {
            int x = (int) ((Math.random() * ((width - spawnOffset) - spawnOffset)) + spawnOffset);
            int y = height - spawnOffset;
            humanObjects.add(new Human(this, x, y, 10));
        }
        for (int i = 0; i < zombies; i++) {
            int x = (int) ((Math.random() * ((width - spawnOffset) - spawnOffset)) + spawnOffset);
            int y = spawnOffset;
            zombieObjects.add(new Zombie(this, x, y, 10));
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
        resourceUseTimer--;
        if(resourceUseTimer < 0) {
            ArrayList<Human> currHumans = new ArrayList<Human>();
            currHumans.addAll(humanObjects);
            for (Human h: currHumans) {
                h.useResources();
            }
            ArrayList<Resource> currResources = new ArrayList<Resource>();
            currResources.addAll(foodObjects);
            currResources.addAll(waterObjects);
            for(Resource r : currResources){
                r.regen();
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
        /*if(humanObjects.size() > 0){
            text("Resource Use Timer: " + resourceUseTimer, (width/2), (height-20));
            text("(For First Human Object ->) Food: " + humanObjects.get(0).getFood() + ", Water: " + humanObjects.get(0).getWater() + ", Size: " + humanObjects.get(0).getSize(), (width/2), (height-5));
        }*/
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

    public static ArrayList<drawnObject> getZombies() {
        ArrayList<drawnObject> zombies = new ArrayList<drawnObject>();
        zombies.addAll(zombieObjects);
        return zombies;
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

    public static void setWidth(int newWidth) {
        width = newWidth;
    }

    public static int getHeight() {
        return height;
    }

    public static void setHeight(int newHeight) {
        height = newHeight;
    }

    public static ArrayList<drawnObject> getAllResources() {
        ArrayList<drawnObject> allResources = new ArrayList<drawnObject>();
        allResources.addAll(foodObjects);
        allResources.addAll(waterObjects);
        return  allResources;
    }

    public static ArrayList<drawnObject> getFood() {
        ArrayList<drawnObject> food = new ArrayList<drawnObject>();
        food.addAll(foodObjects);
        return food;
    }

    public static ArrayList<drawnObject> getWater() {
        ArrayList<drawnObject> water = new ArrayList<drawnObject>();
        water.addAll(waterObjects);
        return water;
    }

    public static void setHumans(int humans){
        Canvas.humans = humans;
    }

    public static void setZombies(int zombies){
        Canvas.zombies = zombies;
    }

    public static int getMinHumans() {
        return minHumans;
    }

    public static void setMinHumans(int minHumans) {
        Canvas.minHumans = minHumans;
    }

    public static int getMinZombies() {
        return minZombies;
    }

    public static void setMinZombies(int minZombies) {
        Canvas.minZombies = minZombies;
    }

    public static int getMaxHumans() {
        return maxHumans;
    }

    public static void setMaxHumans(int maxHumans) {
        Canvas.maxHumans = maxHumans;
    }

    public static int getMaxZombies() {
        return maxZombies;
    }

    public static void setMaxZombies(int maxZombies) {
        Canvas.maxZombies = maxZombies;
    }

    public static int getMinFood() {
        return minFood;
    }

    public static void setMinFood(int minFood) {
        Canvas.minFood = minFood;
    }

    public static int getMaxFood() {
        return maxFood;
    }

    public static void setMaxFood(int maxFood) {
        Canvas.maxFood = maxFood;
    }

    public static int getMinWater() {
        return minWater;
    }

    public static void setMinWater(int minWater) {
        Canvas.minWater = minWater;
    }

    public static int getMaxWater() {
        return maxWater;
    }

    public static void setMaxWater(int maxWater) {
        Canvas.maxWater = maxWater;
    }

    public static int getUseTimerMax(){
        return useTimerMax;
    }

    public static void setUseTimerMax(int useTimerMax){
        Canvas.useTimerMax = useTimerMax;
    }

    public static int getFoodBaseRegenAmount() {
        return foodBaseRegenAmount;
    }

    public static void setFoodBaseRegenAmount(int foodBaseRegenAmount) {
        Canvas.foodBaseRegenAmount = foodBaseRegenAmount;
    }

    public static int getWaterBaseRegenAmount() {
        return waterBaseRegenAmount;
    }

    public static void setWaterBaseRegenAmount(int waterBaseRegenAmount) {
        Canvas.waterBaseRegenAmount = waterBaseRegenAmount;
    }

    public static int getFoodMaxSize() {
        return foodMaxSize;
    }

    public static void setFoodMaxSize(int foodMaxSize) {
        Canvas.foodMaxSize = foodMaxSize;
    }

    public static int getWaterMaxSize() {
        return waterMaxSize;
    }

    public static void setWaterMaxSize(int waterMaxSize) {
        Canvas.waterMaxSize = waterMaxSize;
    }

    public static int getFoodStartAmount() {
        return foodStartAmount;
    }

    public static void setFoodStartAmount(int foodStartAmount) {
        Canvas.foodStartAmount = foodStartAmount;
    }

    public static int getWaterStartAmount() {
        return waterStartAmount;
    }

    public static void setWaterStartAmount(int waterStartAmount) {
        Canvas.waterStartAmount = waterStartAmount;
    }

    public static int getBaseResourceUse() {
        return baseResourceUse;
    }

    public static void setBaseResourceUse(int baseResourceUse) {
        Canvas.baseResourceUse = baseResourceUse;
    }

    public static int getSizeResourceUse() {
        return sizeResourceUse;
    }

    public static void setSizeResourceUse(int sizeResourceUse) {
        Canvas.sizeResourceUse = sizeResourceUse;
    }

    public static int getZombieSeekSize() {
        return zombieSeekSize;
    }

    public static void setZombieSeekSize(int zombieSeekSize) {
        Canvas.zombieSeekSize = zombieSeekSize;
    }

    public static int getMaxSpeed() {
        return maxSpeed;
    }

    public static void setMaxSpeed(int maxSpeed) {
        Canvas.maxSpeed = maxSpeed;
    }

    public static int getZombieDetectionRange() {
        return zombieDetectionRange;
    }

    public static void setZombieDetectionRange(int zombieDetectionRange) {
        Canvas.zombieDetectionRange = zombieDetectionRange;
    }
}
