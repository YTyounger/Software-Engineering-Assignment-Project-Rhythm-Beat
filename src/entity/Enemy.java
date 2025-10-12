package entity;

import bagel.Image;
import bagel.Input;
import entity.note.NormalNote;

public class Enemy extends Entity {

    private int xDirection;

    public Enemy(double x, double y) {
        super(x, y, new Image("res/enemy.png"));
        xDirection = Math.random() > 0.5 ? 1 : -1;
    }

    
    /** 
     * @param input
     */
    public void update(Input input) {
        setX(getX() + xDirection);
        if (getX() >= 900 || getX() <= 100)
            xDirection *= -1;
    }

    public boolean isCollideWithNormalNote(NormalNote note) {
        double distance = getDistance(note.getX(), note.getY());
        return distance <= 104;
    }
}
