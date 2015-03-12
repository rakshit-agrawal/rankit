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
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class BooksActivityMain extends ActionBarActivity {

    private static final String TAG = BooksActivityMain.class.getName();
    private DynamicGridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //--- Spinner feature start

        Spinner spinner = (Spinner) findViewById(R.id.books_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.books_categories, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        //--- Spinner feature end


        ArrayList<RankObjects> mObjectList = new ArrayList<RankObjects>();

        for (int i = 0; i < Book.sBookStrings.length; ++i) {
            mObjectList.add(Book.sBookStrings[i]);
        }


        Resources res = getResources();
        //Drawable d1 = res.getDrawable(R.drawable.image_2);
        //Log.d("Got Image", d1.toString());



        for (int i = 0; i < mObjectList.size(); ++i) {
            Drawable d1 = res.getDrawable(R.drawable.image_0);
            Integer img_name=R.drawable.image_0;

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


        gridView = (DynamicGridView) findViewById(R.id.dynamic_grid);
        gridView.setAdapter(new DynamicGridAdapter(this,
                new ArrayList<RankObjects>(Arrays.asList(Book.sBookStrings)),
                getResources().getInteger(R.integer.column_count)));

/*
        // add callback to stop edit mode if needed
        gridView.setOnDropListener(new DynamicGridView.OnDropListener()
        {
            @Override
            public void onActionDrop()
            {
                gridView.stopEditMode();
            }
        });
*/

        gridView.setOnDragListener(new DynamicGridView.OnDragListener() {
            @Override
            public void onDragStarted(int position) {
                Log.d(TAG, "drag started at position " + position);
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
                Toast.makeText(BooksActivityMain.this, parent.getAdapter().getItem(position).toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_books_activity_main, menu);
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
}
