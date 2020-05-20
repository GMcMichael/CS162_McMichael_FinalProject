import processing.core.PApplet;

abstract class drawnObject {

    private float x;
    private float y;
    private int c = 150;//150 is grey
    private float size;

    private PApplet p;

    public drawnObject(PApplet p, int x, int y){
        this.p = p;
        this.x = x;
        this.y = y;
    }

    public void draw(){
        p.stroke(150);
        p.fill(c);
        p.circle(x, y, size);
        //p.ellipse(x, y, size, size);
    }

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

    public int getC() {
        return c;
    }

    public void setC(int c) {
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
