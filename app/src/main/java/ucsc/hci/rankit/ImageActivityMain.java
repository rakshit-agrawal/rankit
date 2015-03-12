package ucsc.hci.rankit;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class ImageActivityMain extends ActionBarActivity {

    // Create a string for the ImageView label
    private static final String IMAGEVIEW_TAG = "icon bitmap";

    private static final String TAG = ImageActivityMain.class.getName();
    private DynamicGridView gridView;

    public ArrayList<RankObjects> mObjectList = new ArrayList<RankObjects>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_activity_main);



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


        for (int i = 0; i < Book.sBookStrings.length; ++i) {
            mObjectList.add(Book.sBookStrings[i]);
        }



        Resources res = getResources();
        //Drawable d1 = res.getDrawable(R.drawable.image_2);
        //Log.d("Got Image", d1.toString());



        for (int i = 0; i < mObjectList.size(); ++i) {
            Integer img_name=0;
            Drawable d1;
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


        gridView = (DynamicGridView) findViewById(R.id.dynamic_image_grid);
        gridView.setAdapter(new DynamicGridAdapter(this,
                new ArrayList<RankObjects>(Arrays.asList(Image.sImageStrings)),
                getResources().getInteger(R.integer.column_count)));


        // Create a string for the ImageView label

        // Creates a new ImageView
        final ImageView imageView = new ImageView(this);

        // Sets the bitmap for the ImageView from an icon bit map (defined elsewhere)
        Bitmap mIconBitmap = Bitmap.createBitmap(20,20, Bitmap.Config.ALPHA_8);
        imageView.setImageBitmap(mIconBitmap);

        // Sets the tag
        imageView.setTag(IMAGEVIEW_TAG);

        // Sets a long click listener for the ImageView using an anonymous listener object that
// implements the OnLongClickListener interface
        imageView.setOnLongClickListener(new View.OnLongClickListener() {

            // Defines the one method for the interface, which is called when the View is long-clicked
            public boolean onLongClick(View v) {

                // Create a new ClipData.
                // This is done in two steps to provide clarity. The convenience method
                // ClipData.newPlainText() can create a plain text ClipData in one step.

                int i  =  (Integer) v.getTag();

                // Create a new ClipData.Item from the ImageView object's tag
                //ClipData.Item item = new ClipData.Item(v.getTag());
                ClipData.Item item = new ClipData.Item((CharSequence)Integer.toString(i));


                // Create a new ClipData using the tag as a label, the plain text MIME type, and
                // the already-created item. This will create a new ClipDescription object within the
                // ClipData, and set its MIME type entry to "text/plain"

                String[] clipDescription = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                ClipData dragData = new ClipData((CharSequence)Integer.toString(i), clipDescription,item);

                //ClipData myelement = new ClipData(toString(v.getTag()), ClipDescription.MIMETYPE_TEXT_PLAIN, item);

                // Instantiates the drag shadow builder.
                View.DragShadowBuilder myShadow = new MyDragShadowBuilder(imageView);

                // Starts the drag

                v.startDrag(dragData,  // the data to be dragged
                        myShadow,  // the drag shadow builder
                        null,      // no need to use local data
                        0          // flags (not currently used, set to 0)
                );

                return false;

            }

             });



        // Creates a new drag event listener
        myDragEventListener mDragListen = new myDragEventListener();


        // Sets the drag event listener for the View
        imageView.setOnDragListener(mDragListen);












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



    protected class myDragEventListener implements View.OnDragListener {

        // This is the method that the system calls when it dispatches a drag event to the
        // listener.
        public boolean onDrag(View v, DragEvent event) {

            // Defines a variable to store the action type for the incoming event
            final int action = event.getAction();

            // Handles each of the expected events
            switch(action) {

                case DragEvent.ACTION_DRAG_STARTED:

                    // Determines if this View can accept the dragged data
                    if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {

                        // As an example of what your application might do,
                        // applies a blue color tint to the View to indicate that it can accept
                        // data.
                        v.setBackgroundColor(Color.BLUE);

                        //v.setColorFilter(Color.BLUE);

                        // Invalidate the view to force a redraw in the new tint
                        v.invalidate();

                        // returns true to indicate that the View can accept the dragged data.
                        return true;

                    }

                    // Returns false. During the current drag and drop operation, this View will
                    // not receive events again until ACTION_DRAG_ENDED is sent.
                    return false;

                case DragEvent.ACTION_DRAG_ENTERED:

                    // Applies a green tint to the View. Return true; the return value is ignored.

                    v.setBackgroundColor(Color.GREEN);
                    //v.setColorFilter(Color.GREEN);

                    // Invalidate the view to force a redraw in the new tint
                    v.invalidate();

                    return true;

                case DragEvent.ACTION_DRAG_LOCATION:

                    // Ignore the event
                    return true;

                case DragEvent.ACTION_DRAG_EXITED:

                    // Re-sets the color tint to blue. Returns true; the return value is ignored.
                    //v.setColorFilter(Color.BLUE);
                    v.setBackgroundColor(Color.BLUE);


                    // Invalidate the view to force a redraw in the new tint
                    v.invalidate();

                    return true;

                case DragEvent.ACTION_DROP:

                    // Gets the item containing the dragged data
                    ClipData.Item item = event.getClipData().getItemAt(0);

                    // Gets the text data from the item.
                    CharSequence dragData = item.getText();


                    // Displays a message containing the dragged data.
                    Toast.makeText(ImageActivityMain.this, "Dragged data is " + dragData, Toast.LENGTH_LONG);

                    // Turns off any color tints
                    //v.clearColorFilter();


                    // Invalidates the view to force a redraw
                    v.invalidate();

                    // Returns true. DragEvent.getResult() will return true.
                    return true;

                case DragEvent.ACTION_DRAG_ENDED:

                    // Turns off any color tinting
                   // v.clearColorFilter();

                    // Invalidates the view to force a redraw
                    v.invalidate();

                    // Does a getResult(), and displays what happened.
                    if (event.getResult()) {
                        Toast.makeText(ImageActivityMain.this, "The drop was handled.", Toast.LENGTH_LONG);

                    } else {
                        Toast.makeText(ImageActivityMain.this, "The drop didn't work.", Toast.LENGTH_LONG);

                    }

                    // returns true; the value is ignored.
                    return true;

                // An unknown action type was received.
                default:
                    Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
                    break;
            }

            return false;
        }
    };








}
