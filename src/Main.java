import processing.core.PApplet;

public class Main {

    private final static String[] OPTIONS = new String[] {"--present", "Canvas"};

    public static void main(String[] args){
        PApplet.main(OPTIONS);
    }
}
//todo add resources(food, water, shelter) that humans move towards. resources have hordes of zombies humans try to ignore.
// away from resources, zombies are more spread out but there is an occasional horde. if humans get near zombies they will start following them until they lose them or die.