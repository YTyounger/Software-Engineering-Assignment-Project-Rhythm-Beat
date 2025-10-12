package event;

import core.Game;

public class SlowDownEvent extends GameEvent {
    
    /** 
     * @return boolean
     */
    public boolean handle() {
        Game.getGameInstance().noteSlowDown();
        return true;
    }
}
