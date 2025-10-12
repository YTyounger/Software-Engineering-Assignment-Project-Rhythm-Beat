package event;

import entity.Lane;

public class BombEvent extends GameEvent implements UpdateScoreInterface {
    private Lane lane;
    
    public BombEvent(Lane lane) {
        this.lane = lane;
    }

    
    /** 
     * @return boolean
     */
    public boolean handle() {
        lane.clearLane();
        return true;
    }
}
