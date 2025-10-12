package event;

import core.Game;
import entity.Enemy;
import entity.Guardian;
import entity.Projectile;

public class ShootProjectileEvent extends GameEvent {
    private Guardian guardian;
    private Enemy enemy;

    public ShootProjectileEvent(Guardian guardian, Enemy enemy) {
        this.guardian = guardian;
        this.enemy = enemy;
    }

    
    /** 
     * @return boolean
     */
    public boolean handle() {
        Projectile projectile = new Projectile(guardian.getX(), guardian.getY(),
                enemy.getX() - guardian.getX(), enemy.getY() - guardian.getY());
        Game.getGameInstance().addProjectile(projectile);
        return true;
    }
}
