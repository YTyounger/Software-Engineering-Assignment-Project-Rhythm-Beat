package event;

import core.Game;

public class SpeedUpEvent extends GameEvent {
    
    /** 
     * @return boolean
     */
    public boolean handle() {
        Game.getGameInstance().noteSpeedUp();
        return true;
    }
}
