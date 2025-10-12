package entity;

public class EnemyFactory {
    
    /** 
     * @return Enemy
     */
    public static Enemy newEnemyWithRandomPosition() {
        double x = Math.random() * 800 + 100;
        double y = Math.random() * 400 + 100;
        return new Enemy(x, y);
    }
}
