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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class StableArrayAdapter extends ArrayAdapter<Movies> {

    final int INVALID_ID = -1;
    private final Context context;

    HashMap<Movies, Integer> mIdMap = new HashMap<Movies, Integer>();

    public StableArrayAdapter(Context context, int textViewResourceId, List<Movies> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.item_view, parent, false);
        }

        Movies currentMovie = getItem(position);

        TextView titleText = (TextView) itemView.findViewById(R.id.item_title);
        titleText.setText(currentMovie.getTitle());

        TextView directorText = (TextView) itemView.findViewById(R.id.item_director);
        directorText.setText(currentMovie.getDirector());

        ImageView imageView = (ImageView) itemView.findViewById(R.id.item_image);
        imageView.setImageResource(currentMovie.getIconID());

        TextView rankText = (TextView) itemView.findViewById(R.id.item_rank);
        rankText.setText(""+(position+1));

        return itemView;
    }

    @Override
    public long getItemId(int position) {
        if (position < 0 || position >= mIdMap.size()) {
            return INVALID_ID;
        }
        Movies item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
