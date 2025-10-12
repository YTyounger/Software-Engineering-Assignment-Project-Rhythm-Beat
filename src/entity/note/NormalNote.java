package entity.note;

import bagel.Image;

public class NormalNote extends Note {
    public NormalNote(int x, String lane) {
        super(x, 100, new Image(String.format("res/note%s.png", lane)));
    }

    public NormalNote(int x, int y, Image image) {
        super(x, y, image);
    }
}
