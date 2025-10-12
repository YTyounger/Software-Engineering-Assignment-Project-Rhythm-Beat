package event;

import core.Game;
import entity.Enemy;
import entity.Projectile;

public class EnemyGotShootEvent extends GameEvent {
    private Enemy enemy;
    private Projectile projectile;

    public EnemyGotShootEvent(Enemy enemy, Projectile projectile) {
        this.enemy = enemy;
        this.projectile = projectile;
    }

    /**
     * @return boolean
     */
    public boolean handle() {
        Game.getGameInstance().removeEntity(enemy);
        Game.getGameInstance().removeEntity(projectile);
        return true;
    }
}
