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
public class Movie {
    //public static final RankObjects[] sMovieStrings = new RankObjects[];

    String full_url = "https://rankitcrowd.appspot.com/RankItWeb/default/download/";

    public static URL url;
    public static Bitmap bmp; //= BitmapFactory.decodeStream(url.openConnection().getInputStream());

    public static final RankObjects[] sMovieStrings = new RankObjects[]

/*
    public RankObjects[] SendList(List<MovieDataBox> x) throws IOException {


        List<MovieDataBox> itemList = x;

        for (int i = 0; i < itemList.size(); i++) {

            String name = itemList.get(i).name;
            String director = itemList.get(i).director;
            String img_url = itemList.get(i).cover_art;

            String full_url = "https://rankitcrowd.appspot.com/RankItWeb/default/download/" + img_url;

            URL url = new URL(full_url);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());


                   // sMovieStrings.add(new RankObjects(name, director, bmp, ObjType.MOVIES));
        }

        return sMovieStrings;


    }*/

    {
            new RankObjects("Loading... ", " ", bmp, ObjType.MOVIES),
            new RankObjects("Loading... ", " ", bmp, ObjType.MOVIES),
            new RankObjects("Loading... ", " ", bmp, ObjType.MOVIES),
            new RankObjects("Loading... ", " ", bmp, ObjType.MOVIES)

            /*

            new RankObjects("Avengers", "Joss Whedon", bmp, ObjType.MOVIES),
            new RankObjects("Avatar", "James Cameron", R.drawable.avatar, ObjType.MOVIES),
            new RankObjects("Titanic", "James Cameron", R.drawable.titanic, ObjType.MOVIES),
            new RankObjects("The Dark Knight", "Christopher Nolan", R.drawable.the_dark_knight, ObjType.MOVIES)
            */
    };

    public Movie() throws IOException {
        url = new URL(full_url);
        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

    }
}
