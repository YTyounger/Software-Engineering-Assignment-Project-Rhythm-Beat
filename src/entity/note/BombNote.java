package entity.note;

import bagel.Image;
import entity.Lane;
import enums.NoteStatus;
import event.BombEvent;

public class BombNote extends NormalNote {
    private Lane lane;

    public BombNote(int x, Lane lane) {
        super(x, 100, new Image("res/noteBomb.png"));
        this.lane = lane;
    }

    
    /** 
     * @return NoteStatus
     */
    public NoteStatus getNoteTriggerStatus() {
        NoteStatus status = super.getNoteTriggerStatus();
        if (status != null)
            status = NoteStatus.BOMB;
        new BombEvent(lane).add();
        return status;
    }
}
