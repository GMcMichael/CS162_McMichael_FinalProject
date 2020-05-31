import processing.core.PApplet;

public class Main {

    private final static String[] OPTIONS = new String[] {"--present", "Canvas"};

    /*
    what I have done sense last milestone:
    Added Humans need for food and water and timer for using food and water
    Food and water collection sites randomly spawning
    Humans seek out food and water based on what they need
     */

    public static void main(String[] args){
        PApplet.main(OPTIONS);
    }
    //todo implement the needs for humans
    // maybe add a human that can be controlled by the user
    // maybe add user interface to change probability
    // maybe add file saving and reading to save probabilities
    // maybe add sound and image
}
//todo add resources(food, water, shelter) that humans move towards. resources have hordes of zombies humans try to ignore.
// away from resources, zombies are more spread out but there is an occasional horde. if humans get near zombies they will start following them until they lose them or die.