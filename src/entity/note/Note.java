package entity.note;

import bagel.Image;
import enums.NoteStatus;

public abstract class Note {
    private double x;
    private double y;
    private Image image;

    public Note(double x, double y, Image image) {
        this.image = image;
        this.x = x;
        this.y = y;
    }

    
    /** 
     * @param speed
     */
    public void update(double speed) {
        y += speed;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void drawElement() {
        image.draw(x, y);
    }

    public NoteStatus getNoteTriggerStatus() {
        double distance = Math.abs(657 - getY());
        if (distance <= 15)
            return NoteStatus.PERFECT;
        else if (distance <= 50)
            return NoteStatus.GOOD;
        else if (distance <= 100)
            return NoteStatus.BAD;
        else if (distance <= 200)
            return NoteStatus.MISS;
        else
            return null;
    }
}
