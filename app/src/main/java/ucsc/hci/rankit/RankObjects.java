package ucsc.hci.rankit;

/**
 * Created by yanfeitu on 2/22/15.
 */

public class RankObjects {
    private String title;
    private String director;
    private int iconID;

    public RankObjects(String title, String director, int iconID) {
        this.title = title;
        this.director = director;
        this.iconID = iconID;
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



}
