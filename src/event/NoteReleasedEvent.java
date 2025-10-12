package event;

import entity.Lane;
import entity.note.HoldNote;
import entity.note.Note;
import enums.NoteStatus;

public class NoteReleasedEvent extends GameEvent implements UpdateScoreInterface {
    private Lane lane;
    private Note note;

    public NoteReleasedEvent(Lane lane, Note note) {
        this.lane = lane;
        this.note = note;
    }

    
    /** 
     * @return boolean
     */
    public boolean handle() {
        if (!(note instanceof HoldNote))
            return true;
        HoldNote hNote = (HoldNote) note;
        NoteStatus status = hNote.getNoteTriggerStatus();
        lane.removeNote(hNote);
        updateScore(status);
        return true;
    }
}
