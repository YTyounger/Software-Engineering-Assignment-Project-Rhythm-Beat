package event;

import entity.Lane;
import entity.note.HoldNote;
import entity.note.Note;
import enums.NoteStatus;

public class NotePressedEvent extends GameEvent implements UpdateScoreInterface {
    private Lane lane;
    private Note note;

    public NotePressedEvent(Lane lane, Note note) {
        this.lane = lane;
        this.note = note;
    }

    
    /** 
     * @return boolean
     */
    public boolean handle() {
        NoteStatus status = note.getNoteTriggerStatus();
        if (status == null)
            return true;
        if (note instanceof HoldNote) {
            if (status != NoteStatus.MISS) {
                ((HoldNote) note).setHolding(true);
            }
        } else {
            lane.removeNote(note);
        }
        updateScore(status);
        return true;
    }
}
