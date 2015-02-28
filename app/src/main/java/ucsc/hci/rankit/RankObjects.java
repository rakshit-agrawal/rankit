package ucsc.hci.rankit;

public class RankObjects {
    private ObjType type;
    private String title;
    private String director;
    private int iconID;

    public RankObjects(String title, String director, int iconID, ObjType type) {
        this.title = title;
        this.director = director;
        this.iconID = iconID;
        this.type = type;
    }

    public String getDirector() {
        return director;
    }

    public String getTitle() {
        return title;
    }

    public int getIconID() {
        return iconID;
    }

    public ObjType getType() {
        return type;
    }

}
