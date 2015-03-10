/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ucsc.hci.rankit;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.Circle;

import java.util.HashMap;
import java.util.List;

public class StableArrayAdapter extends ArrayAdapter<RankObjects> {
    private ObjType type;
    final int INVALID_ID = -1;
    private final Context context;
    int[] colorsRed = new int[] {R.color.red1, R.color.red2, R.color.red3, R.color.red4};
    int[] colorsPurple = new int[] {R.color.purple1, R.color.purple2, R.color.purple3, R.color.purple4};
    int[] colorsGreen = new int[] {R.color.green1, R.color.green2, R.color.green3, R.color.green4};

    HashMap<RankObjects, Integer> mIdMap = new HashMap<RankObjects, Integer>();

    public StableArrayAdapter(Context context, int textViewResourceId, List<RankObjects> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
        }
        type = objects.get(0).getType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.item_view, parent, false);
        }

        RankObjects currentObjects = getItem(position);

        TextView titleText = (TextView) itemView.findViewById(R.id.item_title);
        titleText.setText(currentObjects.getTitle());
        //titleText.setTextSize(R.dimen.abc_text_size_body_1_material);

        Drawable rounded_corners = (Drawable) itemView.getResources().getDrawable(R.drawable.rounded_corners);
        if(type == ObjType.MOVIES) {
            rounded_corners.setColorFilter(new
                    PorterDuffColorFilter(itemView.getResources().getColor(colorsRed[position]), PorterDuff.Mode.MULTIPLY));
        } else if (type == ObjType.MUSIC) {
            rounded_corners.setColorFilter(new
                    PorterDuffColorFilter(itemView.getResources().getColor(colorsPurple[position]), PorterDuff.Mode.MULTIPLY));
        } else { // Images
            rounded_corners.setColorFilter(new
                    PorterDuffColorFilter(itemView.getResources().getColor(colorsGreen[position]), PorterDuff.Mode.MULTIPLY));
        }
        //itemView.setBackgroundDrawable(rounded_corners);
        //itemView.setBackground(rounded_corners);
        if (Build.VERSION.SDK_INT >= 16) {

            itemView.setBackground(rounded_corners);

        } else {

            itemView.setBackgroundDrawable(rounded_corners);
        }

        TextView directorText = (TextView) itemView.findViewById(R.id.item_director);
        directorText.setText(currentObjects.getDirector());
        //directorText.setTextSize(R.dimen.abc_text_size_body_1_material);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.item_image);

        //RoundedImageView imageView = (RoundedImageView) itemView.findViewById(R.id.item_image);
        //imageView.setImageResource(currentObjects.getIconID());
        //imageView.setImageBitmap(currentObjects.getIcon());

        imageView.setImageDrawable(currentObjects.getIcon());

        imageView.setPadding(10, 10, 10, 10);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);




        TextView rankText = (TextView) itemView.findViewById(R.id.item_rank);
        rankText.setText(""+(position+1));

        return itemView;
    }

    @Override
    public long getItemId(int position) {
        if (position < 0 || position >= mIdMap.size()) {
            return INVALID_ID;
        }
        RankObjects item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return android.os.Build.VERSION.SDK_INT < 21; //lollipop is 21, lower version kitkat is 19.
        //return android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP (21);
    // return true;
    }


}
