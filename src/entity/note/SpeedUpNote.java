package entity.note;

import bagel.Image;
import enums.NoteStatus;
import event.SpeedUpEvent;

public class SpeedUpNote extends NormalNote {
    public SpeedUpNote(int x) {
        super(x, 100, new Image("res/noteSpeedUp.png"));
    }

    
    /** 
     * @return NoteStatus
     */
    public NoteStatus getNoteTriggerStatus() {
        NoteStatus status = super.getNoteTriggerStatus();
        if (status != null)
            status = NoteStatus.SPEEDUP;
        new SpeedUpEvent().add();
        return status;
    }
}
