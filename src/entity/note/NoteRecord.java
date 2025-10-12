package entity.note;

public class NoteRecord {
    private int frame;
    private String type;
    private String lane;

    public NoteRecord(String lane, String type, int frame) {
        this.frame = frame;
        this.type = type;
        this.lane = lane;
    }

    
    /** 
     * @return int
     */
    public int getFrame() {
        return frame;
    }

    public String getNoteType() {
        return type;
    }

    public String getLaneType() {
        return lane;
    }
}
