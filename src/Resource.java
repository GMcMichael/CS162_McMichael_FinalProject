import processing.core.PApplet;

import java.awt.*;

public class Resource extends drawnObject {

    private char type;
    private int amount = 100;
    private float regenAmount;//todo set regen for resources
    private final float baseRegenAmount = 0.5f;
    private final int maxSize = 15;

    public Resource(PApplet p, int x, int y, float size, char type){
        super(p, x, y, size);
        this.type = type;
        switch (type){
            case 'f':
                setC(Color.YELLOW);
                break;
            case 'w':
                setC(Color.CYAN);
                break;
        }
        regenAmount = getSize()*baseRegenAmount;
    }

    public void interact(drawnObject d){
        //cast d to human and use it to add the resources type to the human object and remove the resources based on human size or new variable called collection amount that is randomized (have to stay longer to collect more)
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
        amount += regenAmount;
        int newSize = amount/10;
        if(newSize > maxSize) newSize = maxSize;
        setSize(newSize);
    }
}
