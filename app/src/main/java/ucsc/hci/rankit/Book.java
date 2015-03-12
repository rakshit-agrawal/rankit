package ucsc.hci.rankit;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

/**
 * Created by yanfeitu on 2/25/15.
 */
public class Book {



    static Drawable d0 = Drawable.createFromPath("drawable/image_0.jpg");

    //Resources res = getResources();
    //Drawable drawable = res.getDrawable(R.drawable.myimage);


    //public static Bitmap d0 = BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.image_0);

    //public static Bitmap d0 = BitmapFactory.decodeFile("/home/rakshit/AndroidStudioProjects/RankIt/app/src/main/res/drawable/image_0.jpg");


    public static final RankObjects[] sBookStrings = new RankObjects[] {
            new RankObjects(" ", " ", d0, ObjType.IMAGES),
            new RankObjects(" ", " ", d0, ObjType.IMAGES),
            new RankObjects(" ", " ", d0, ObjType.IMAGES),
            new RankObjects(" ", " ", d0, ObjType.IMAGES)

    };

    /*
    public static final RankObjects[] sBookStrings = new RankObjects[] {
        //    new RankObjects("Book A", "Author A", R.drawable.sample_0, ObjType.BOOKS),
        //    new RankObjects("Book B", "Author B", R.drawable.sample_1, ObjType.BOOKS),
        //    new RankObjects("Book C", "Author C", R.drawable.sample_2, ObjType.BOOKS),
        //    new RankObjects("Book D", "Author D", R.drawable.sample_3, ObjType.BOOKS)
    };*/
}
