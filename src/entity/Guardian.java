package entity;

import java.util.Comparator;
import java.util.List;

import bagel.Image;
import bagel.Input;
import bagel.Keys;
import core.Game;
import event.ShootProjectileEvent;

public class Guardian extends Entity {
    public Guardian() {
        super(800, 600, new Image("res/guardian.png"));
    }

    
    /** 
     * @param input
     */
    public void update(Input input) {
        if (input.wasPressed(Keys.LEFT_SHIFT)) {
            List<Enemy> enemies = Game.getGameInstance().getEnemies();
            Enemy clostest = enemies.stream().min(Comparator.comparingDouble(e -> e.getDistance(getX(), getY()))).orElse(null);
            if (clostest == null)
                return;
            new ShootProjectileEvent(this, clostest).add();
        }
    }
}
