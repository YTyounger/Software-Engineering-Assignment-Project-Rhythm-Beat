package entity;

import bagel.DrawOptions;
import bagel.Image;
import bagel.Input;
import bagel.util.Point;
import bagel.util.Vector2;
import event.EnemyGotShootEvent;
import event.ProjectileMissEvent;

public class Projectile extends Entity {
    private Vector2 velocity;
    private double rotation;
    private final double SPEED = 6;

    public Projectile(double x, double y, double deltaX, double deltaY) {
        super(x, y, new Image("res/arrow.png"));
        rotation = Math.atan2(deltaY, deltaX);
        velocity = new Vector2(deltaX, deltaY).normalised().mul(SPEED);
    }

    /**
     * @param input
     */
    public void update(Input input) {
        Point newPosition = new Point(getX(), getY()).asVector().add(velocity).asPoint();
        setX(newPosition.x);
        setY(newPosition.y);
        if (getX() < 0 || getY() < 0)
            new ProjectileMissEvent(this).add();
    }

    public void updateCollisionWithEnemy(Enemy enemy) {
        if (getDistance(enemy.getX(), enemy.getY()) <= 62) {
            new EnemyGotShootEvent(enemy, this).add();
        }
    }

    @Override
    public void drawElement() {
        getImage().draw(getX(), getY(), new DrawOptions().setRotation(rotation));
    }
}
