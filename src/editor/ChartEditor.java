package editor;

import bagel.*;
import entity.Lane;
import entity.note.NoteRecord;

import java.util.*;

public class ChartEditor {

    private final Font tipFont = new Font("res/FSO8BITR.ttf", 24);
    private final Map<String, Image> lane2img = Map.of(
            "Left", new Image("res/noteLeft.png"),
            "Right", new Image("res/noteRight.png"),
            "Up", new Image("res/noteUp.png"),
            "Down", new Image("res/noteDown.png"),
            "Special", new Image("res/noteDown.png"));

    private final Map<Keys, String> key2lane = Map.of(
            Keys.LEFT, "Left",
            Keys.RIGHT, "Right",
            Keys.UP, "Up",
            Keys.DOWN, "Down",
            Keys.SPACE, "Special");

    private final List<Lane> lanes;
    private final List<NoteRecord> records = new ArrayList<>();
    private int frame = 0;
    private boolean finished = false;

    public ChartEditor(List<Lane> lanes) {
        this.lanes = lanes;
    }

    public boolean isFinished() {
        return finished;
    }

    public List<NoteRecord> getRecords() {
        return records;
    }

    public List<Lane> getLanes() {
        return lanes;
    }

    public void update(Input input) {
        frame++;

        String tip = "Press up down left right to add notes, ESC to finish";
        double x = (Window.getWidth() - tipFont.getWidth(tip)) / 2.0;
        tipFont.drawString(tip, x, 30);

        key2lane.forEach((key, laneType) -> {
            if (input.wasPressed(key)) {
                // CSV 记录
                String noteType = laneType.equals("Special") ? "DoubleScore" : "Normal";
                records.add(new NoteRecord(laneType, noteType, frame));
                // 可视化
                Lane lane = lanes.stream()
                        .filter(l -> l.getLaneType().equals(laneType)).findFirst().orElse(null);
                if (lane != null) {
                    lane.addTempVisual(lane2img.get(laneType));
                }
            }
        });

        if (input.wasPressed(Keys.ESCAPE))
            finished = true;

        lanes.forEach(l -> l.updateTempVisual(2));
    }
}
