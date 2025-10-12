import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class CustomChartManager {

    public static final String LIST_FILE = "res/custom_list.txt";
    public static final int ITEMS_PER_PAGE = 9;

    public static class Entry {
        public String musicPath, chartPath, displayName;
    }

    public static List<Entry> loadList() {
        List<Entry> list = new ArrayList<>();
        File f = new File(LIST_FILE);
        if (!f.exists())
            return list;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String l;
            while ((l = br.readLine()) != null) {
                String[] e = l.split(",", 3);
                if (e.length < 3)
                    continue;
                Entry ce = new Entry();
                ce.musicPath = "res/" + e[0];
                ce.chartPath = "res/" + e[1];
                ce.displayName = e[2];
                list.add(ce);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public static void appendIfAbsent(String wav, String csv, String name) {
        try {
            File file = new File(LIST_FILE);
            if (!file.exists())
                file.createNewFile();
            List<String> lines = Files.readAllLines(file.toPath());
            boolean exists = lines.stream().anyMatch(l -> l.startsWith(wav + ","));
            if (!exists)
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
                    bw.write(wav + "," + csv + "," + name);
                    bw.newLine();
                }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
