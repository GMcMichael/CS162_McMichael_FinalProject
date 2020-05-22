import processing.core.PApplet;
import java.awt.Color;

abstract class drawnObject {

    private float x;
    private float y;
    private Color c = Color.GRAY;
    private float size;

    private PApplet p;

    public drawnObject(PApplet p, int x, int y, float size){
        this.p = p;
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public void draw(){
        p.stroke(Color.GRAY.getRGB());
        p.fill(c.getRGB());
        p.circle(x, y, size);
        //p.ellipse(x, y, size, size);
    }

    public abstract void Update();

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Color getC() {
        return c;
    }

    public void setC(Color c) {
        this.c = c;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public PApplet getP() {
        return p;
    }

    public void setP(PApplet p) {
        this.p = p;
    }
}
