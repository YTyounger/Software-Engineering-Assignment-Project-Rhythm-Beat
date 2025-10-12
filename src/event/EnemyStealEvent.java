package event;

import entity.Lane;
import entity.note.NormalNote;

public class EnemyStealEvent extends GameEvent {
    private Lane lane;
    private NormalNote note;

    public EnemyStealEvent(Lane lane, NormalNote note) {
        this.lane = lane;
        this.note = note;
    }

    
    /** 
     * @return boolean
     */
    public boolean handle() {
        lane.removeNote(note);
        return true;
    }
}
