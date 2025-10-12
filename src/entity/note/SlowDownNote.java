package entity.note;

import bagel.Image;
import enums.NoteStatus;
import event.SlowDownEvent;

public class SlowDownNote extends NormalNote {
    public SlowDownNote(int x) {
        super(x, 100, new Image("res/noteSlowDown.png"));
    }

    
    /** 
     * @return NoteStatus
     */
    public NoteStatus getNoteTriggerStatus() {
        NoteStatus status = super.getNoteTriggerStatus();
        if (status != null)
            status = NoteStatus.SLOWDOWN;
        new SlowDownEvent().add();
        return status;
    }
}
