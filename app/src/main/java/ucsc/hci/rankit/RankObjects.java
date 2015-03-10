package ucsc.hci.rankit;

import android.graphics.Bitmap;

public class RankObjects {
    private ObjType type;
    private String title;
    private String director;
    private int iconID;
    private Bitmap icon;

    public RankObjects(String title, String director, Bitmap icon, ObjType type) {
        this.title = title;
        this.director = director;
        this.icon = icon;
        this.type = type;
    }

    public String getDirector() {
        return director;
    }

    public String getTitle() {
        return title;
    }

    public Bitmap getIcon() {
        return icon;
    }
    public int getIconID() {
        return iconID;
    }

    public ObjType getType() {
        return type;
    }

}
