package editor;

import entity.Lane;
import entity.note.NoteRecord;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

public class CsvChartWriter {

    public static void save(String filePath, List<Lane> lanes, List<NoteRecord> records) throws Exception {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Lane l : lanes) {
                bw.write(String.format("Lane,%s,%d%n", l.getLaneType(), l.getX()));
            }
            for (NoteRecord r : records) {
                bw.write(String.format("%s,%s,%d%n", r.getLaneType(), r.getNoteType(), r.getFrame()));
            }
        }
    }
}
