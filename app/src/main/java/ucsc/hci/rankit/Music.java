package ucsc.hci.rankit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanfeitu on 2/25/15.
 */
public class Music {
    //public static final RankObjects[] sMusicStrings = new RankObjects[];

    String full_url = "https://rankitcrowd.appspot.com/RankItWeb/default/download/";

    public static URL url;
    //public static Bitmap bmp; //= BitmapFactory.decodeStream(url.openConnection().getInputStream());
    public static Drawable bmp;

    public static final RankObjects[] sMusicStrings = new RankObjects[]

/*
    public RankObjects[] SendList(List<MusicDataBox> x) throws IOException {


        List<MusicDataBox> itemList = x;

        for (int i = 0; i < itemList.size(); i++) {

            String name = itemList.get(i).name;
            String director = itemList.get(i).director;
            String img_url = itemList.get(i).cover_art;

            String full_url = "https://rankitcrowd.appspot.com/RankItWeb/default/download/" + img_url;

            URL url = new URL(full_url);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());


                   // sMusicStrings.add(new RankObjects(name, director, bmp, ObjType.MUSIC));
        }

        return sMusicStrings;


    }*/

            {
                    new RankObjects("Loading... ", " ", bmp, ObjType.MUSIC),
                    new RankObjects("Loading... ", " ", bmp, ObjType.MUSIC),
                    new RankObjects("Loading... ", " ", bmp, ObjType.MUSIC),
                    new RankObjects("Loading... ", " ", bmp, ObjType.MUSIC)

            /*

            new RankObjects("Avengers", "Joss Whedon", bmp, ObjType.MUSIC),
            new RankObjects("Avatar", "James Cameron", R.drawable.avatar, ObjType.MUSIC),
            new RankObjects("Titanic", "James Cameron", R.drawable.titanic, ObjType.MUSIC),
            new RankObjects("The Dark Knight", "Christopher Nolan", R.drawable.the_dark_knight, ObjType.MUSIC)
            */
            };

    public Music() throws IOException {
        url = new URL(full_url);
        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

    }
}
