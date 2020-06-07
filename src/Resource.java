import processing.core.PApplet;
import java.awt.Color;
/**
 * Resource.java
 * @author Garrett McMichael
 * @version 1
 */
public class Resource extends drawnObject {

    private char type;
    private int amount = Canvas.getFoodStartAmount();
    private float regenAmount;
    private float baseRegenAmount = Canvas.getFoodBaseRegenAmount();
    private int maxSize = Canvas.getFoodMaxSize();

    /**
     * Resources' Constructor
     * @param p A variable of type PApplet
     * @param x A variable of type int
     * @param y A variable of type int
     * @param size A variable fo type float
     * @param type A variable fo type Character
     */
    public Resource(PApplet p, int x, int y, float size, char type){
        super(p, x, y, size);
        this.type = type;
        switch (type){
            case 'f':
                setC(Color.YELLOW);
                setAmount(Canvas.getFoodStartAmount());
                setBaseRegenAmount(Canvas.getFoodBaseRegenAmount());
                setMaxSize(Canvas.getFoodMaxSize());
                break;
            case 'w':
                setC(Color.CYAN);
                setAmount(Canvas.getWaterStartAmount());
                setBaseRegenAmount(Canvas.getWaterBaseRegenAmount());
                setMaxSize(Canvas.getWaterMaxSize());
                break;
        }
    }

    /**
     * Handles interactions with human objects
     * @param d A variable of type drawnObject
     */
    public void interact(drawnObject d){
        if(d.getClass().getTypeName().equals("Human")){
            Human h = (Human)d;
            float collectAmount = h.getResourceGatherAmount();
            if(amount < collectAmount) collectAmount = amount;
            h.addResources(type, collectAmount);
            amount -= collectAmount;
        }
    }

    /**
     * Called every frame to update the object
     */
    public void Update(){
        //replenish a small amount of resources
        int newSize = amount/10;
        if(newSize > maxSize) newSize = maxSize;
        setSize(newSize);
    }

    /**
     * Called from Canvas Class to regenerate resources
     */
    public void regen(){
        amount += baseRegenAmount;
    }

    /**
     * Sets the Resources' resource amount variable
     * @param amount A variable of type int
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Sets the Resources' resource regen amount variable
     * @param baseRegenAmount A variable of type float
     */
    public void setBaseRegenAmount(float baseRegenAmount) {
        this.baseRegenAmount = baseRegenAmount;
    }

    /**
     * Sets the Resources' max size variable
     * @param maxSize A variable of type int
     */
    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }
}
