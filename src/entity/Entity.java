package entity;

import bagel.Image;
import bagel.Input;

public abstract class Entity {
    private double x;
    private double y;
    private Image image;

    public Entity(double x, double y, Image image) {
        this.x = x;
        this.y = y;
        this.image = image;
    }

    
    /** 
     * @param x
     * @param y
     * @return double
     */
    public double getDistance(double x, double y) {
        return Math.sqrt(Math.pow(this.y - y, 2) + Math.pow(this.x - x, 2));
    }

    public void drawElement() {
        image.draw(x, y);
    }

    public abstract void update(Input input);

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Image getImage() {
        return image;
    }
}
