package entity;

import java.util.ArrayList;
import java.util.List;

import bagel.Image;
import bagel.Input;
import bagel.Keys;
import entity.note.BombNote;
import entity.note.DoubleScoreNote;
import entity.note.HoldNote;
import entity.note.NormalNote;
import entity.note.Note;
import entity.note.SlowDownNote;
import entity.note.SpeedUpNote;
import event.EnemyStealEvent;
import event.NoteMissEvent;
import event.NotePressedEvent;
import event.NoteReleasedEvent;

public class Lane {
    private int x;
    private final int y = 384;
    private String laneType;
    private Image image;
    private List<Note> notes;
    private final Keys key;
    private int speed = 2;

    public Lane(String laneType, int x) {
        this.x = x;
        this.laneType = laneType;
        this.notes = new ArrayList<>();
        this.image = new Image(String.format("res/lane%s.png", laneType));

        // store the trigger key according to the lane type
        switch (laneType) {
            case "Left":
                key = Keys.LEFT;
                break;
            case "Right":
                key = Keys.RIGHT;
                break;
            case "Up":
                key = Keys.UP;
                break;
            case "Down":
                key = Keys.DOWN;
                break;
            case "Special":
                key = Keys.SPACE;
                break;
            default:
                key = Keys.ESCAPE;
        }
    }

    private static class TempVisual {
        double x, y;
        Image img;

        TempVisual(double x, double y, Image i) {
            this.x = x;
            this.y = y;
            img = i;
        }
    }

    private final List<TempVisual> temps = new ArrayList<>();

    public void addTempVisual(Image img) {
        temps.add(new TempVisual(x, 100, img));
    }

    public void updateTempVisual(int speed) {
        temps.forEach(t -> {
            t.y += speed;
            t.img.draw(t.x, t.y);
        });
        temps.removeIf(t -> t.y > 900);
    }

    public int getX() {
        return x;
    }

    /**
     * @param enemy
     */
    public void updateCollisionWithEnemy(Enemy enemy) {
        notes.stream().filter(e -> e instanceof NormalNote).map(NormalNote.class::cast)
                .filter(e -> enemy.isCollideWithNormalNote(e))
                .forEach(e -> new EnemyStealEvent(this, e).add());
    }

    public void drawElement() {
        image.draw(x, y);
        notes.forEach(e -> e.drawElement());
    }

    public void update(Input input) {

        notes.forEach(e -> e.update(speed));

        if (notes.size() == 0)
            return;
        Note note = notes.get(0);

        if (note instanceof NormalNote) {
            NormalNote firstNote = (NormalNote) note;
            if (firstNote.getY() >= 857) {
                new NoteMissEvent(this, firstNote).add();
            } else if (input.wasPressed(key)) {
                new NotePressedEvent(this, firstNote).add();
            }
        } else if (note instanceof HoldNote) {
            HoldNote firstNote = (HoldNote) note;

            if (input.wasPressed(key) && !firstNote.getHolding()) {
                new NotePressedEvent(this, firstNote).add();
            } else if (input.wasReleased(key) && firstNote.getHolding()) {
                new NoteReleasedEvent(this, firstNote).add();
            } else if (firstNote.getLowerBoundY() >= 857 && !firstNote.getHolding()) {
                new NoteMissEvent(this, firstNote).add();
            }
        }
    }

    public void addNote(String noteType) {
        if (noteType.equals("Normal"))
            notes.add(new NormalNote(x, laneType));
        else if (noteType.equals("Hold"))
            notes.add(new HoldNote(x, laneType));
        else if (noteType.equals("DoubleScore"))
            notes.add(new DoubleScoreNote(x));
        else if (noteType.equals("SpeedUp"))
            notes.add(new SpeedUpNote(x));
        else if (noteType.equals("SlowDown"))
            notes.add(new SlowDownNote(x));
        else if (noteType.equals("Bomb"))
            notes.add(new BombNote(x, this));
    }

    public void removeNote(Note note) {
        notes.remove(note);
    }

    public void clearLane() {
        notes.clear();
    }

    public String getLaneType() {
        return laneType;
    }

    public int getNoteCount() {
        return notes.size();
    }

    public void noteSpeedUp() {
        speed += 1;
    }

    public void noteSlowDown() {
        if (speed > 1)
            speed -= 1;
    }
}
