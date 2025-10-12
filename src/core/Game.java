package core;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import bagel.Font;
import bagel.Input;
import bagel.Window;
import entity.Enemy;
import entity.EnemyFactory;
import entity.Entity;
import entity.Guardian;
import entity.Lane;
import entity.Projectile;
import entity.note.NoteRecord;
import enums.NoteStatus;
import event.GameEvent;

public class Game {

    private static Game instance;

    private int frame = 0;
    private List<Lane> lanes = new ArrayList<>();
    private List<NoteRecord> noteRecords = new ArrayList<>();
    private List<Entity> entities = new ArrayList<>();
    private boolean over = false;
    private int score = 0;
    private int level = 0;

    private NoteStatus lastNoteStatus = null;
    private String lastMessageString = null;
    private int lastStatusChangedFrame = 0;

    private final Font messageFont = new Font("res/FSO8BITR.ttf", 40);
    private final Font scoreFont = new Font("res/FSO8BITR.ttf", 30);

    private boolean doubleScoreStatus = false;

    public Game(int level, List<Lane> lanes, List<NoteRecord> noteRecords) {
        this.lanes = lanes;
        this.noteRecords = noteRecords;
        Game.instance = this;
        this.level = level;
        if (level == 3)
            this.entities.add(new Guardian());
    }

    /**
     * get the current game instance
     * 
     * @return Game
     */
    public static Game getGameInstance() {
        if (instance == null) {
            System.out.println("Error");
        }
        return instance;
    }

    public void drawElements() {
        lanes.forEach(Lane::drawElement);
        entities.forEach(Entity::drawElement);

        // draw score
        scoreFont.drawString(String.format("SCORE %d", score), 35, 35);

        // draw getting score message
        if (lastNoteStatus != null && lastStatusChangedFrame + 30 >= frame) {
            double x = (Window.getWidth() - messageFont.getWidth(lastMessageString)) / 2.0;
            double y = (Window.getHeight() - 30) / 2.0;
            messageFont.drawString(lastMessageString, x, y);
        }
    }

    public boolean isOver() {
        return over;
    }

    /**
     * @param input
     */
    public void update(Input input) {
        frame += 1;

        GameEvent.update();

        // check if all of the notes are consumed
        if (lanes.stream().map(Lane::getNoteCount).collect(Collectors.summingInt(Integer::intValue)) == 0
                && noteRecords.size() == 0) {
            this.over = true;
            return;
        }
        // add note
        if (noteRecords.size() != 0 && frame == noteRecords.get(0).getFrame()) {
            NoteRecord noteRecord = noteRecords.get(0);
            noteRecords.remove(0);
            Lane lane = lanes.stream().filter(e -> e.getLaneType().equals(noteRecord.getLaneType())).findFirst()
                    .orElse(null);
            lane.addNote(noteRecord.getNoteType());
        }
        // add enemy
        if (level == 3 && frame % 600 == 0) {
            Enemy enemy = EnemyFactory.newEnemyWithRandomPosition();
            entities.add(enemy);
        }

        // update
        lanes.stream().forEach(e -> e.update(input));
        entities.forEach(e -> e.update(input));

        // update collision
        entities.stream().filter(e -> e instanceof Enemy).forEach(e -> {
            lanes.forEach(o -> o.updateCollisionWithEnemy((Enemy) e));
            entities.stream().filter(o -> o instanceof Projectile).map(Projectile.class::cast)
                    .forEach(o -> o.updateCollisionWithEnemy((Enemy) e));
        });
    }

    /**
     * update score and message by noteStatus
     * 
     * @param noteStatus
     */
    public void updateScore(NoteStatus noteStatus) {
        int scoreFactor = doubleScoreStatus ? 2 : 1;
        // update score message
        if (noteStatus != null) {
            switch (noteStatus) {
                case PERFECT:
                    score += 10 * scoreFactor;
                    lastMessageString = "PERFECT";
                    break;
                case GOOD:
                    score += 5 * scoreFactor;
                    lastMessageString = "GOOD";
                    break;
                case BAD:
                    score -= 1 * scoreFactor;
                    lastMessageString = "BAD";
                    break;
                case MISS:
                    score -= 5 * scoreFactor;
                    lastMessageString = "MISS";
                    break;
                case DOUBLE:
                    lastMessageString = "Double Score";
                    break;
                case SPEEDUP:
                    score += 15 * scoreFactor;
                    lastMessageString = "Speed Up";
                    break;
                case SLOWDOWN:
                    score += 15 * scoreFactor;
                    lastMessageString = "Slow Down";
                    break;
                case BOMB:
                    lastMessageString = "Lane Clear";
                    break;
            }
            lastNoteStatus = noteStatus;
            lastStatusChangedFrame = frame;
        }
    }

    public List<Enemy> getEnemies() {
        return entities.stream().filter(e -> e instanceof Enemy)
                .map(Enemy.class::cast)
                .collect(Collectors.toList());
    }

    public int getScore() {
        return score;
    }

    public int getFrame() {
        return frame;
    }

    public void setDoubleScoreStatus(boolean doubleScoreStatus) {
        this.doubleScoreStatus = doubleScoreStatus;
    }

    public boolean getDoubleScoreStatus() {
        return doubleScoreStatus;
    }

    public void noteSpeedUp() {
        lanes.forEach(Lane::noteSpeedUp);
    }

    public void noteSlowDown() {
        lanes.forEach(Lane::noteSlowDown);
    }

    public void addProjectile(Projectile projectile) {
        entities.add(projectile);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }
}
