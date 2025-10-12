package entity.note;

import bagel.Image;
import enums.NoteStatus;
import event.DoubleScoreEvent;

public class DoubleScoreNote extends NormalNote {
    public DoubleScoreNote(int x) {
        super(x, 100, new Image("res/note2x.png"));
    }

    
    /** 
     * @return NoteStatus
     */
    public NoteStatus getNoteTriggerStatus() {
        NoteStatus status = super.getNoteTriggerStatus();
        if (status != null)
            status = NoteStatus.DOUBLE;
        new DoubleScoreEvent().add();
        return status;
    }
}
