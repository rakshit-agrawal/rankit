package ucsc.hci.rankit;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicImageGridAdapter extends BaseDynamicImageGridAdapter {
    public DynamicImageGridAdapter(Context context, List<RankObjects> items, int columnCount) {
        super(context, items, columnCount);
    }
    public static Drawable currentImage;

    public static Map<Integer, Drawable> dictionary = new HashMap<Integer, Drawable>();

    public static Map<Integer, Integer> post_dict = new HashMap<Integer, Integer>();


    //TODO: Please add new color palette for all colors. Palette length required to be 8 colors each

    int[] colorsGreen = new int[] {R.color.green1, R.color.green2, R.color.green3, R.color.green4, R.color.green4, R.color.green4, R.color.green4};

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CheeseViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_grid, null);
            holder = new CheeseViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CheeseViewHolder) convertView.getTag();
        }


        /*

        if(DynamicImageGridView.selectedFlag==true) {
            currentImage = holder.image.getDrawable();
            try {
                Log.d("In Grid", currentImage.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            DynamicImageGridView.selectedFlag = false;
        }*/

        holder.build(position,convertView);
        return convertView;
    }

    private class CheeseViewHolder {
        private TextView rankText;
        private ImageView image;
        private TextView titleText;

        private CheeseViewHolder(View view) {
            rankText = (TextView) view.findViewById(R.id.item_image_rank);
            image = (ImageView) view.findViewById(R.id.image_thumbs);
            image.setPadding(10, 10, 10, 10);
            image.setScaleType(ImageView.ScaleType.FIT_CENTER);


            //titleText = (TextView) view.findViewById(R.id.item_title);
        }

        void build(int position, View view) {
            RankObjects cObj = (RankObjects) getItem(position);
            rankText.setText(""+(position+1));
            //image.setImageResource(cObj.getIconID());
            image.setImageDrawable(cObj.getIcon());
            image.setPadding(10, 10, 10, 10);
            image.setMaxHeight(2);
            image.setBackgroundColor(view.getResources().getColor(colorsGreen[position]));
            //titleText.setText(cObj.getTitle());

            dictionary.put(position,cObj.getIcon());
            post_dict.put(position,cObj.getItemID());
            try {
                Log.d("My Dict", dictionary.toString());
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}