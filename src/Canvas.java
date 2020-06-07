import processing.core.PApplet;
import processing.core.PFont;
import java.awt.Color;
import java.util.ArrayList;
/**
 * Canvas.java
 * @author Garrett McMichael
 * @version 1
 */
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

    /**
     * Sets the PApplets size
     */
    public void settings(){
        size(width, height);
    }

    /**
     * Ran before the PApplet displays
     * Creates the Human, Zombie, and Resource objects
     */
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
            foodObjects.add(new Resource(this, x, y, 10, 'f'));
        }
        for(int i = 0; i < water; i++){
            int x = (int) ((Math.random() * ((width - spawnOffset) - spawnOffset)) + spawnOffset);
            int y = (int) ((Math.random() * ((height - spawnOffset) - spawnOffset)) + spawnOffset);
            waterObjects.add(new Resource(this, x, y, 10, 'w'));
        }
        drawObjects.addAll(foodObjects);
        drawObjects.addAll(waterObjects);
    }

    /**
     * Called every frame
     * Handles the resource timer
     * Updates all the drawnObjects
     * Displays current Humans and Zombie numbers
     */
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
    }

    /**
     * Removes the variable d from the drawnObject variable holder
     * @param d A variable of type drawnObject
     */
    public static void removeObject(drawnObject d){
        drawObjects.remove(d);
    }

    /**
     * Returns an ArrayList of type Human holding all the human objects
     * @return An ArrayList of type Human
     */
    public static ArrayList<Human> getHumans(){
        return humanObjects;
    }

    /**
     * Returns an ArrayList of type drawnObject holding all the zombie objects
     * @return An ArrayList of type drawnObject
     */
    public static ArrayList<drawnObject> getZombies() {
        ArrayList<drawnObject> zombies = new ArrayList<drawnObject>();
        zombies.addAll(zombieObjects);
        return zombies;
    }

    /**
     * Removes the Human passed in from the Canvas human variable holder
     * @param h A variable of type Human
     */
    public static void removeHuman(Human h){
        humans--;
        humanObjects.remove(h);
    }

    /**
     * Adds the Zombie passed in to the Canvas zombie variable holder
     * @param z A variable of type Zombie
     */
    public static void addZombie(Zombie z){
        zombies++;
        zombieObjects.add(z);
        drawObjects.add(z);
    }

    /**
     * Removes the Zombie passed in from the Canvas zombie variable holder
     * @param z A variable of type Zombie
     */
    public static void removeZombie(Zombie z){
        zombies--;
        zombieObjects.remove(z);
    }

    /**
     * Returns the Canvas width variable
     * @return A variable of type int
     */
    public static int getWidth() {
        return width;
    }

    /**
     * Sets the Canvas width variable
     * @param width A variable of type int
     */
    public static void setWidth(int width) {
        Canvas.width = width;
    }

    /**
     * Returns the Canvas height variable
     * @return A variable of type int
     */
    public static int getHeight() {
        return height;
    }

    /**
     * Sets the Canvas height variable
     * @param height A variable of type int
     */
    public static void setHeight(int height) {
        Canvas.height = height;
    }

    /**
     * Returns an ArrayList of type drawnObject holding all Resource objects
     * @return An ArrayList of type drawnObject
     */
    public static ArrayList<drawnObject> getAllResources() {
        ArrayList<drawnObject> allResources = new ArrayList<drawnObject>();
        allResources.addAll(foodObjects);
        allResources.addAll(waterObjects);
        return  allResources;
    }

    /**
     * Returns an ArrayList of type drawnObject holding all food objects
     * @return An ArrayList of type drawnObject
     */
    public static ArrayList<drawnObject> getFood() {
        ArrayList<drawnObject> food = new ArrayList<drawnObject>();
        food.addAll(foodObjects);
        return food;
    }

    /**
     * Returns an ArrayList of type drawnObject holding all water objects
     * @return An ArrayList of type drawnObject
     */
    public static ArrayList<drawnObject> getWater() {
        ArrayList<drawnObject> water = new ArrayList<drawnObject>();
        water.addAll(waterObjects);
        return water;
    }

    /**
     * Sets the humans variable
     * @param humans A variable of type int
     */
    public static void setHumans(int humans){
        Canvas.humans = humans;
    }

    /**
     * Sets the zombies variable
     * @param zombies A variable of type int
     */
    public static void setZombies(int zombies){
        Canvas.zombies = zombies;
    }

    /**
     * Returns the minimum humans variable
     * @return A variable of type int
     */
    public static int getMinHumans() {
        return minHumans;
    }

    /**
     * Sets the minimum humans variable
     * @param minHumans A variable of type int
     */
    public static void setMinHumans(int minHumans) {
        Canvas.minHumans = minHumans;
    }

    /**
     * Returns the minimum zombies variable
     * @return A variable of type int
     */
    public static int getMinZombies() {
        return minZombies;
    }

    /**
     * Sets the minimum zombies variable
     * @param minZombies A variable of type int
     */
    public static void setMinZombies(int minZombies) {
        Canvas.minZombies = minZombies;
    }

    /**
     * Returns the maximum humans variable
     * @return A variable of type int
     */
    public static int getMaxHumans() {
        return maxHumans;
    }

    /**
     * Sets the maximum humans variable
     * @param maxHumans A variable of type int
     */
    public static void setMaxHumans(int maxHumans) {
        Canvas.maxHumans = maxHumans;
    }

    /**
     * Returns the maximum zombies variable
     * @return A variable of type int
     */
    public static int getMaxZombies() {
        return maxZombies;
    }

    /**
     * Sets the maximum zombies variable
     * @param maxZombies A variable of type int
     */
    public static void setMaxZombies(int maxZombies) {
        Canvas.maxZombies = maxZombies;
    }

    /**
     * Returns the minimum food variable
     * @return A variable of type int
     */
    public static int getMinFood() {
        return minFood;
    }

    /**
     * Sets the minimum food variable
     * @param minFood A variable of type int
     */
    public static void setMinFood(int minFood) {
        Canvas.minFood = minFood;
    }

    /**
     * Returns the maximum food variable
     * @return A variable of type int
     */
    public static int getMaxFood() {
        return maxFood;
    }

    /**
     * Sets the maximum food variable
     * @param maxFood A variable of type int
     */
    public static void setMaxFood(int maxFood) {
        Canvas.maxFood = maxFood;
    }

    /**
     * Returns the minimum water variable
     * @return A variable of type int
     */
    public static int getMinWater() {
        return minWater;
    }

    /**
     * Sets the minimum water variable
     * @param minWater A variable of type int
     */
    public static void setMinWater(int minWater) {
        Canvas.minWater = minWater;
    }

    /**
     * Returns the maximum water variable
     * @return A variable of type int
     */
    public static int getMaxWater() {
        return maxWater;
    }

    /**
     * Sets the maximum water variable
     * @param maxWater A variable of type int
     */
    public static void setMaxWater(int maxWater) {
        Canvas.maxWater = maxWater;
    }

    /**
     * Returns the use timer maximum variable
     * @return A variable of type int
     */
    public static int getUseTimerMax(){
        return useTimerMax;
    }

    /**
     * Sets the use timer maximum variable
     * @param useTimerMax A variable of type int
     */
    public static void setUseTimerMax(int useTimerMax){
        Canvas.useTimerMax = useTimerMax;
    }

    /**
     * Returns the food base regen variable
     * @return A variable of type int
     */
    public static int getFoodBaseRegenAmount() {
        return foodBaseRegenAmount;
    }

    /**
     * Sets the food base regen variable
     * @param foodBaseRegenAmount A variable of type int
     */
    public static void setFoodBaseRegenAmount(int foodBaseRegenAmount) {
        Canvas.foodBaseRegenAmount = foodBaseRegenAmount;
    }

    /**
     * Returns the water base regen variable
     * @return A variable of type int
     */
    public static int getWaterBaseRegenAmount() {
        return waterBaseRegenAmount;
    }

    /**
     * Sets the water base regen variable
     * @param waterBaseRegenAmount A variable of type int
     */
    public static void setWaterBaseRegenAmount(int waterBaseRegenAmount) {
        Canvas.waterBaseRegenAmount = waterBaseRegenAmount;
    }

    /**
     * Returns the food maximum size variable
     * @return A variable of type int
     */
    public static int getFoodMaxSize() {
        return foodMaxSize;
    }

    /**
     * Sets the food maximum size variable
     * @param foodMaxSize A variable of type int
     */
    public static void setFoodMaxSize(int foodMaxSize) {
        Canvas.foodMaxSize = foodMaxSize;
    }

    /**
     * Returns the water maximum size variable
     * @return A variable of type int
     */
    public static int getWaterMaxSize() {
        return waterMaxSize;
    }

    /**
     * Sets the water maximum size variable
     * @param waterMaxSize A variable of type int
     */
    public static void setWaterMaxSize(int waterMaxSize) {
        Canvas.waterMaxSize = waterMaxSize;
    }

    /**
     * Returns the food resources starting amount
     * @return A variable of type int
     */
    public static int getFoodStartAmount() {
        return foodStartAmount;
    }

    /**
     * Sets the food resources starting amount
     * @param foodStartAmount A variable of type int
     */
    public static void setFoodStartAmount(int foodStartAmount) {
        Canvas.foodStartAmount = foodStartAmount;
    }

    /**
     * Returns the water resources starting amount
     * @return A variable of type int
     */
    public static int getWaterStartAmount() {
        return waterStartAmount;
    }

    /**
     * Sets the water resources starting amount
     * @param waterStartAmount A variable of type int
     */
    public static void setWaterStartAmount(int waterStartAmount) {
        Canvas.waterStartAmount = waterStartAmount;
    }

    /**
     * Returns the base resource use variable
     * @return A variable of type int
     */
    public static int getBaseResourceUse() {
        return baseResourceUse;
    }

    /**
     * Sets the base resource use variable
     * @param baseResourceUse A variable of type int
     */
    public static void setBaseResourceUse(int baseResourceUse) {
        Canvas.baseResourceUse = baseResourceUse;
    }

    /**
     * Returns the size resource use variable
     * @return A variable of type int
     */
    public static int getSizeResourceUse() {
        return sizeResourceUse;
    }

    /**
     * Sets the size resource use variable
     * @param sizeResourceUse A variable of type int
     */
    public static void setSizeResourceUse(int sizeResourceUse) {
        Canvas.sizeResourceUse = sizeResourceUse;
    }

    /**
     * Returns the zombie seek size variable
     * @return A variable of type int
     */
    public static int getZombieSeekSize() {
        return zombieSeekSize;
    }

    /**
     * Sets the zombie seek size variable
     * @param zombieSeekSize A variable of type int
     */
    public static void setZombieSeekSize(int zombieSeekSize) {
        Canvas.zombieSeekSize = zombieSeekSize;
    }

    /**
     * Returns the max speed variable
     * @return A variable of type int
     */
    public static int getMaxSpeed() {
        return maxSpeed;
    }

    /**
     * Sets the max speed variable
     * @param maxSpeed A variable of type int
     */
    public static void setMaxSpeed(int maxSpeed) {
        Canvas.maxSpeed = maxSpeed;
    }

    /**
     * Returns the zombie detection range variable
     * @return A variable of type int
     */
    public static int getZombieDetectionRange() {
        return zombieDetectionRange;
    }

    /**
     * Sets the zombie detection size variable
     * @param zombieDetectionRange A variable of type int
     */
    public static void setZombieDetectionRange(int zombieDetectionRange) {
        Canvas.zombieDetectionRange = zombieDetectionRange;
    }
}
