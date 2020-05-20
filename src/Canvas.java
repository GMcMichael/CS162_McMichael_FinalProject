import processing.core.PApplet;
import java.util.ArrayList;

public class Canvas extends PApplet {

    private static ArrayList<drawnObject> drawingObjects = new ArrayList<drawnObject>();

    public static void removeObject(){

    }

    public void settings(){
        size(500, 500);
    }

    public void setup(){
        //what to do before simulation is started
    }

    public void draw(){
        for(drawnObject d: drawingObjects){
            d.draw(this);
        }
    }

}
