import bagel.*;
import core.Game;
import editor.ChartEditor;
import editor.CsvChartWriter;
import entity.Lane;
import enums.GameStatus;

import javax.swing.*;
import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class MusicDance extends AbstractGame {

    private static final int W = 1024, H = 768;
    private final Image bg;
    private final Font titleF = new Font("res/FSO8BITR.ttf", 64);
    private final Font infoF = new Font("res/FSO8BITR.ttf", 24);
    private final MenuRenderer menu = new MenuRenderer(titleF, infoF);

    private GameStatus state = GameStatus.GAMETITLE;
    private boolean paused = false;
    private Game game;
    private Track track;

    /* --- editor --- */
    private ChartEditor editor;
    private String wavName, csvName, displayName;

    /* --- custom menu --- */
    private List<CustomChartManager.Entry> customList;
    private int page = 0;

    public MusicDance() {
        super(W, H, "MUSIC DANCE");
        bg = new Image("res/background.png");
    }

    public static void main(String[] a) {
        new MusicDance().run();
    }

    protected void update(Input in) {
        if (in.wasPressed(Keys.ESCAPE) && state != GameStatus.EDITOR_RECORDING && state != GameStatus.CUSTOM_SELECT)
            Window.close();
        if (in.wasPressed(Keys.TAB))
            paused = !paused;
        bg.draw(W / 2.0, H / 2.0);

        if (!paused)
            switch (state) {
                case GAMETITLE:
                    titleInput(in);
                    break;
                case LEVEL1:
                    levelPlay(in);
                    break;
                case LEVEL2:
                    levelPlay(in);
                    break;
                case LEVEL3:
                    levelPlay(in);
                    break;
                case CUSTOM_SELECT:
                    customMenu(in);
                    break;
                case CUSTOM_PLAY:
                    levelPlay(in);
                    break;
                case EDITOR_SELECT:
                    openMusicFile();
                    break;
                case EDITOR_RECORDING:
                    editorFlow(in);
                    break;
                case EDITOR_FINISH:
                case GAMECLEAR:
                    if (in.wasPressed(Keys.SPACE))
                        state = GameStatus.GAMETITLE;
                    break;
                default: {
                }
            }
        drawUI();
    }

    private void titleInput(Input in) {
        if (in.wasPressed(Keys.NUM_1))
            startFixedLevel(1, "res/track1.wav");
        else if (in.wasPressed(Keys.NUM_2))
            startFixedLevel(2, "res/track2.wav");
        else if (in.wasPressed(Keys.NUM_3))
            startFixedLevel(3, "res/track3.wav");
        else if (in.wasPressed(Keys.NUM_4)) {
            customList = CustomChartManager.loadList();
            page = 0;
            state = GameStatus.CUSTOM_SELECT;
        } else if (in.wasPressed(Keys.E))
            state = GameStatus.EDITOR_SELECT;
    }

    private void startFixedLevel(int lv, String wav) {
        game = GameLoader.loadLevelCsv(lv);
        track = new Track(wav);
        track.start();
        state = lv == 1 ? GameStatus.LEVEL1 : lv == 2 ? GameStatus.LEVEL2 : GameStatus.LEVEL3;
    }

    private void levelPlay(Input in) {
        game.update(in);
        if (game.isOver()) {
            state = GameStatus.GAMECLEAR;
            track.pause();
        }
    }

    private void customMenu(Input in) {
        if (in.wasPressed(Keys.LEFT) && page > 0)
            page--;
        if (in.wasPressed(Keys.RIGHT) && (page + 1) * CustomChartManager.ITEMS_PER_PAGE < customList.size())
            page++;
        if (in.wasPressed(Keys.ESCAPE)) {
            state = GameStatus.GAMETITLE;
            return;
        }
        for (int k = 1; k <= 9; k++) {
            if (in.wasPressed(Keys.valueOf("NUM_" + k))) {
                int idx = page * CustomChartManager.ITEMS_PER_PAGE + (k - 1);
                if (idx < customList.size())
                    startCustom(customList.get(idx));
            }
        }
    }

    private void startCustom(CustomChartManager.Entry e) {
        game = GameLoader.loadCustom(e.chartPath);
        track = new Track(e.musicPath);
        track.start();
        state = GameStatus.CUSTOM_PLAY;
    }

    private void openMusicFile() {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                File src = fc.getSelectedFile();
                File dst = new File("res/" + src.getName());
                Files.copy(src.toPath(), dst.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                wavName = dst.getName();
                displayName = wavName.replace(".wav", "");
                csvName = displayName + "-edit.csv";
                editor = new ChartEditor(List.of(
                        new Lane("Left", 282),
                        new Lane("Right", 742),
                        new Lane("Up", 432),
                        new Lane("Down", 592)));
                track = new Track("res/" + wavName);
                track.start();
                state = GameStatus.EDITOR_RECORDING;
            } catch (Exception ex) {
                ex.printStackTrace();
                state = GameStatus.GAMETITLE;
            }
        } else
            state = GameStatus.GAMETITLE;
    }

    private void editorFlow(Input in) {
        editor.getLanes().forEach(entity.Lane::drawElement);
        editor.update(in);
        if (editor.isFinished()) {
            try {
                CsvChartWriter.save("res/" + csvName, editor.getLanes(), editor.getRecords());
                CustomChartManager.appendIfAbsent(wavName, csvName, displayName);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            track.pause();
            state = GameStatus.EDITOR_FINISH;
        }
    }

    private void drawUI() {
        switch (state) {
            case GAMETITLE:
                menu.drawTitle();
                break;
            case CUSTOM_SELECT:
                menu.drawCustom(customList, page,
                        (customList.size() + CustomChartManager.ITEMS_PER_PAGE - 1)
                                / CustomChartManager.ITEMS_PER_PAGE);
                break;
            case LEVEL1:
            case LEVEL2:
            case LEVEL3:
            case CUSTOM_PLAY:
                game.drawElements();
                break;
            case GAMECLEAR:
                menu.drawCenter("CLEAR!  Score " + game.getScore(), 300, infoF);
                break;
            case EDITOR_FINISH:
                menu.drawCenter("Chart saved! Press SPACE", 400, infoF);
                break;
            default: {
            }
        }
    }
}
