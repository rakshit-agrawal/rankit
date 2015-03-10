package ucsc.hci.rankit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class RankObjects {
    private ObjType type;
    private String title;
    private String director;
    private int iconID;
    //private Bitmap icon;
    private Integer item_id;

    private Drawable icon;


    public RankObjects(String title, String director, Drawable icon, ObjType type) {
        this.title = title;
        this.director = director;
        this.icon = icon;
        this.type = type;
    }


    public void setItemID(Integer x) {
        this.item_id = x;
        // return title;
    }


    public String getDirector() {
        return director;


    }


    public void setDirector(String x) {
        this.director = x;
        // return title;
    }

    public String getTitle() {
        return title;
    }


    public void setTitle(String x) {
        this.title = x;
       // return title;
    }

    public Drawable getIcon() {
        return icon;
    }


    public void setIcon(Drawable x) throws IOException {
        //this.director = x;

        //String full_url = "https://rankitcrowd.appspot.com/RankItWeb/default/download/" + x;

        //URL url = new URL(full_url);
        //Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

        this.icon = x;

        // return title;
    }

    public int getIconID() {
        return iconID;
    }

    public ObjType getType() {
        return type;
    }

}
