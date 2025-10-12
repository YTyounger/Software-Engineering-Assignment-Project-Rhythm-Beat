package event;

import entity.Lane;
import entity.note.Note;
import enums.NoteStatus;

public class NoteMissEvent extends GameEvent implements UpdateScoreInterface {
    private Lane lane;
    private Note note;

    public NoteMissEvent(Lane lane, Note note) {
        this.lane = lane;
        this.note = note;
    }

    
    /** 
     * @return boolean
     */
    public boolean handle() {
        lane.removeNote(note);
        updateScore(NoteStatus.MISS);
        return true;
    }
}
