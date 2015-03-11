package ucsc.hci.rankit;

import android.graphics.drawable.Drawable;

/**
 * Created by yanfeitu on 2/27/15.
 */
public class Image {


    static Drawable d0 = Drawable.createFromPath("drawable/image_0.jpg");


    public static final RankObjects[] sImageStrings = new RankObjects[] {
            new RankObjects("Image1", "Image1", d0, ObjType.IMAGES),
            new RankObjects("Image2", "Image2", d0, ObjType.IMAGES),
            new RankObjects("Image3", "Image3", d0, ObjType.IMAGES),
            new RankObjects("Image4", "Image4", d0, ObjType.IMAGES)

    };

}
