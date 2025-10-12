import entity.Lane;
import entity.note.NoteRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import core.Game;

public class GameLoader {

    public static Game loadLevelCsv(int level) {
        return load("res/test" + level + ".csv", level);
    }

    public static Game loadCustom(String csvPath) {
        return load(csvPath, 0);
    }

    private static Game load(String csv, int level) {
        List<Lane> lanes = new ArrayList<>();
        List<NoteRecord> notes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csv))) {
            String l;
            while ((l = br.readLine()) != null) {
                String[] e = l.split(",");
                if ("Lane".equals(e[0]))
                    lanes.add(new Lane(e[1], Integer.parseInt(e[2])));
                else
                    notes.add(new NoteRecord(e[0], e[1], Integer.parseInt(e[2])));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new Game(level, lanes, notes);
    }
}
