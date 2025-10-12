package event;

import java.util.List;
import java.util.ArrayList;

public abstract class GameEvent {
    public static List<GameEvent> events = new ArrayList<>();
    public static List<GameEvent> nextEvents = new ArrayList<>();
    
    public void add() {
        nextEvents.add(this);
    }

    public static void update() {
        events.addAll(nextEvents);
        nextEvents.clear();
        events.removeIf(GameEvent::handle);
    }

    public abstract boolean handle();
}
