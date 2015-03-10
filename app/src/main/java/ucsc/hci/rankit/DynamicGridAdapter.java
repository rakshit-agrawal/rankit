package ucsc.hci.rankit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class DynamicGridAdapter extends BaseDynamicGridAdapter {
    public DynamicGridAdapter(Context context, List<RankObjects> items, int columnCount) {
        super(context, items, columnCount);
    }

    int[] colorsBlue = new int[] {R.color.blue1, R.color.blue2, R.color.blue3, R.color.blue4};

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CheeseViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_grid, null);
            holder = new CheeseViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CheeseViewHolder) convertView.getTag();
        }
        holder.build(position,convertView);
        return convertView;
    }

    private class CheeseViewHolder {
        private TextView rankText;
        private ImageView image;
        private TextView titleText;

        private CheeseViewHolder(View view) {
            rankText = (TextView) view.findViewById(R.id.item_rank);
            image = (ImageView) view.findViewById(R.id.item_img);
            image.setPadding(10, 10, 10, 10);
            image.setScaleType(ImageView.ScaleType.FIT_CENTER);


            titleText = (TextView) view.findViewById(R.id.item_title);
        }

        void build(int position, View view) {
            RankObjects cObj = (RankObjects) getItem(position);
            rankText.setText(""+(position+1));
            image.setImageResource(cObj.getIconID());
            image.setBackgroundColor(view.getResources().getColor(colorsBlue[position]));
            titleText.setText(cObj.getTitle());
        }
    }
}