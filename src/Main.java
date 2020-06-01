import processing.core.PApplet;

public class Main {

    private final static String[] OPTIONS = new String[] {"--present", "Canvas"};

    /*
    what I have done sense last milestone:
    Added Humans need for food and water and timer for using food and water
    Food and water collection sites randomly spawning
    Humans seek out food and water based on what they need
    Humans grow and shrink in size based on how much food and water they have
    After humans reach a certain size, they search for zombies to kill
     */

    public static void main(String[] args){
        PApplet.main(OPTIONS);
    }
    //todo
    // maybe add user interface to change probability
    // maybe add sound and image
    // maybe add file saving and reading to save probabilities
    // maybe add a human that can be controlled by the user
}
//todo add resources(food, water, shelter) that humans move towards. resources have hordes of zombies humans try to ignore.
// away from resources, zombies are more spread out but there is an occasional horde. if humans get near zombies they will start following them until they lose them or die.