import bagel.Font;
import bagel.Window;

import java.util.List;

public class MenuRenderer {

    private final Font titleFont;
    private final Font infoFont;

    public MenuRenderer(Font titleFont, Font infoFont) {
        this.titleFont = titleFont;
        this.infoFont = infoFont;
    }

    public void drawTitle() {
        titleFont.drawString("MUSIC DANCE", 220, 250);
        infoFont.drawString("Press 1  2  3 to play a level", 300, 440);
        infoFont.drawString("Press 4 for custom charts", 300, 480);
        infoFont.drawString("Press E to create your own chart", 300, 520);
        infoFont.drawString("Press ESC to quit", 300, 560);
    }

    public void drawCustom(List<CustomChartManager.Entry> list, int page, int totalPage) {
        String head = "CUSTOM CHARTS  (Page " + (page + 1) + " " + totalPage + ")";
        drawCenter(head, 120, infoFont);
        int start = page * CustomChartManager.ITEMS_PER_PAGE;
        for (int i = 0; i < CustomChartManager.ITEMS_PER_PAGE; i++) {
            int idx = start + i;
            if (idx >= list.size())
                break;
            String line = (i + 1) + ". " + list.get(idx).displayName;
            infoFont.drawString(line, 300, 200 + i * 40);
        }
        infoFont.drawString("LEFT  RIGHT  Turn Page   ESC Return", 260, 680);
    }

    public void drawCenter(String msg, double y, Font font) {
        double x = (Window.getWidth() - font.getWidth(msg)) / 2.0;
        font.drawString(msg, x, y);
    }
}
