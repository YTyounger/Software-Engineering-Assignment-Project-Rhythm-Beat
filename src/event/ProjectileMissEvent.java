package event;

import core.Game;
import entity.Projectile;

public class ProjectileMissEvent extends GameEvent {
    private Projectile projectile;
    
    public ProjectileMissEvent(Projectile projectile) {
        this.projectile = projectile;
    }

    
    /** 
     * @return boolean
     */
    public boolean handle() {
        Game.getGameInstance().removeEntity(projectile);
        return true;
    }
}
