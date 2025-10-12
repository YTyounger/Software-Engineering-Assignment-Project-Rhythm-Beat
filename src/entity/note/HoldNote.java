package entity.note;

import bagel.Image;
import enums.NoteStatus;

public class HoldNote extends Note {
    private boolean holding;

    public HoldNote(double x, String lane) {
        super(x, 24, new Image(String.format("res/holdNote%s.png", lane)));
        holding = false;
    }

    
    /** 
     * @return double
     */
    public double getLowerBoundY() {
        return getY() + 82;
    }

    public double getUpperBoundY() {
        return getY() - 82;
    }

    public boolean getHolding() {
        return holding;
    }

    public void setHolding(boolean holding) {
        this.holding = holding;
    }

    @Override
    public NoteStatus getNoteTriggerStatus() {
        double distance;
        if (!holding)
            distance = Math.abs(657 - getLowerBoundY());
        else 
            distance = Math.abs(657 - getUpperBoundY());
        if (distance <= 15)
            return NoteStatus.PERFECT;
        else if (distance <= 50)
            return NoteStatus.GOOD;
        else if (distance <= 100)
            return NoteStatus.BAD;
        else if (distance <= 200)
            return NoteStatus.MISS;
        else if (holding)
            return NoteStatus.MISS;
        else
            return null;
    }
}
