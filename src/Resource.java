import processing.core.PApplet;

import java.awt.*;

public class Resource extends drawnObject {

    private char type;
    private int amount = Canvas.getFoodStartAmount();
    private float regenAmount;
    private float baseRegenAmount = Canvas.getFoodBaseRegenAmount();
    private int maxSize = Canvas.getFoodMaxSize();

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

    public void interact(drawnObject d){
        if(d.getClass().getTypeName().equals("Human")){
            Human h = (Human)d;
            float collectAmount = h.getResourceGatherAmount();
            if(amount < collectAmount) collectAmount = amount;
            h.addResources(type, collectAmount);
            amount -= collectAmount;
        }
    }

    public void Update(){
        //replenish a small amount of resources
        int newSize = amount/10;
        if(newSize > maxSize) newSize = maxSize;
        setSize(newSize);
    }

    public void regen(){
        amount += baseRegenAmount;
    }

    public int getAmount(){
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setBaseRegenAmount(float baseRegenAmount) {
        this.baseRegenAmount = baseRegenAmount;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }
}
