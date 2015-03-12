package ucsc.hci.rankit;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import ucsc.hci.rankit.DynamicImageGridAdapter.*;


public class ImagesActivityMain extends ActionBarActivity {

    private static final String TAG = ImagesActivityMain.class.getName();
    private DynamicImageGridView gridView;

    public static ArrayList<RankObjects> mObjectList = new ArrayList<RankObjects>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //--- Spinner feature start

        Spinner spinner = (Spinner) findViewById(R.id.images_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.images_categories, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        //--- Spinner feature end



        for (int i = 0; i < Image.sImageStrings.length; ++i) {
            mObjectList.add(Image.sImageStrings[i]);
        }



        Resources res = getResources();
        //Drawable d1 = res.getDrawable(R.drawable.image_2);
        //Log.d("Got Image", d1.toString());



        for (int i = 0; i < mObjectList.size(); ++i) {
            Integer img_name=0;

            Drawable d1; // =res.getDrawable(R.drawable.image_0);
            switch (i){
                case 0:{
                    img_name = R.drawable.image_0;
                    break;
                }
                case 1:{
                    img_name = R.drawable.image_1;
                    break;
                }
                case 2:{
                    img_name = R.drawable.image_2;
                    break;
                }
                case 3:{
                    img_name = R.drawable.image_3;
                    break;
                }
                default:{
                    img_name = R.drawable.image_0;
                }
            }


                d1 = res.getDrawable(img_name);

            Log.d("Show Items", mObjectList.get(i).getTitle());
            try {
                Log.d("Inside try", d1.toString());

                mObjectList.get(i).setIcon(d1);
                Log.d("try done", d1.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        gridView = (DynamicImageGridView) findViewById(R.id.dynamic_image_grid);
        gridView.setAdapter(new DynamicImageGridAdapter(this,
                new ArrayList<RankObjects>(Arrays.asList(Image.sImageStrings)),
                getResources().getInteger(R.integer.column_count_images)));

/*
        // add callback to stop edit mode if needed
        gridView.setOnDropListener(new DynamicImageGridView.OnDropListener()
        {
            @Override
            public void onActionDrop()
            {
                gridView.stopEditMode();
            }
        });
*/

        gridView.setOnDragListener(new DynamicImageGridView.OnDragListener() {
            @Override
            public void onDragStarted(int position) {
                Log.d(TAG, "drag started at position " + position);


                //Drawable currImage = mObjectList.get(position).getIcon();
                Drawable currImage = DynamicImageGridAdapter.dictionary.get(position);
                //Drawable currImage = DynamicImageGridAdapter.currentImage;
                //Log.d("Drawable now", currImage.toString());
                ImageView bigImage = (ImageView) findViewById(R.id.big_image);
                bigImage.setImageDrawable(currImage);


            }

            @Override
            public void onDragPositionsChanged(int oldPosition, int newPosition) {
                Log.d(TAG, String.format("drag item position changed from %d to %d", oldPosition, newPosition));
                //Log.d(TAG, CheeseObjects.format("drag item position changed from %d to %d", oldPosition, newPosition));
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                gridView.startEditMode(position);
                return true;
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ImagesActivityMain.this, parent.getAdapter().getItem(position).toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_images_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

/*
    public void selectFrag(View view) {
        Fragment fr;

        if (view == findViewById(R.id.button2)) {
            fr = new ImageFragment();


            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_place, fr);
            fragmentTransaction.commit();

        }
    }
*/
}