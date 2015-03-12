package ucsc.hci.rankit;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ucsc.hci.rankit.DynamicImageGridAdapter.*;


public class ImagesActivityMain extends ActionBarActivity {

    private static final String TAG = ImagesActivityMain.class.getName();
    private DynamicImageGridView gridView;

    public static ArrayList<RankObjects> mObjectList = new ArrayList<RankObjects>();


    private static final String IMAGES_GET_REQUEST = "https://rankitcrowd.appspot.com/RankItWeb/default/get_items.json?token=get_my_data&type=images&count=4";
    private ArrayList<RankObjects> myObjects = new ArrayList<RankObjects>();

    private List<ImageDataBox> listItems = new ArrayList<ImageDataBox>();

    //public ArrayList<RankObjects> mObjectList = new ArrayList<RankObjects>();
    public List<ImageDataBox> BigList = new ArrayList<ImageDataBox>();






    private static final String DEBUG_TAG = "InternetActions";

    public InputStream is = null;

    public InputStream is2 = null;

    public InputStream is3 = null;



    public TextView main_text;


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

        internetActions();



        for (int i = 0; i < Image.sImageStrings.length; ++i) {
            mObjectList.add(Image.sImageStrings[i]);
        }

        Resources res = getResources();
        for (int i = 0; i < mObjectList.size(); ++i) {
            Log.d("Show Items", mObjectList.get(i).getTitle());
            mObjectList.get(i).setTitle("Loading...");
            mObjectList.get(i).setDirector(" ");




        //Drawable d1 = res.getDrawable(R.drawable.image_2);
        //Log.d("Got Image", d1.toString());




            Integer img_name=R.drawable.loading_dots;

            Drawable d1; // =res.getDrawable(R.drawable.image_0);
            /*
            switch (i){
                case 0:{
                    img_name = R.drawable.loading_dots;
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
            }*/


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






    public void submitAction(View v) {

        String var_string = "";
        for ( Map.Entry<Integer, Integer> entry : DynamicImageGridAdapter.post_dict.entrySet() ) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();

            var_string = var_string + "pos"+ key.toString() + "=" + value.toString()+"&";
            Log.d("key",key.toString());
            Log.d("value",value.toString());
            Log.d("url",var_string);

        }

        Log.d("url",var_string);

        var_string = var_string + "type=images";



        try{
            postMovieRanks(var_string);
        }catch (Exception e){
            e.printStackTrace();
        }

        Intent intent = new Intent(this, SubmitComplete.class);
        //intent.putExtra(MOVIES_CALL_STRING,MoviesCallString);
        startActivity(intent);

    }










    //----------Internet




    private void internetActions() {

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()) {
            new MakeGetRequest().execute(IMAGES_GET_REQUEST);
            // Parse operations
            jsonActions();
        }
        else {
            //else cases
        }
    }



    // Uses AsyncTask to create a task away from the main UI thread. This task takes a
    // URL string and uses it to create an HttpUrlConnection. Once the connection
    // has been established, the AsyncTask downloads the contents of the webpage as
    // an InputStream. Finally, the InputStream is converted into a string, which is
    // displayed in the UI by the AsyncTask's onPostExecute method.
    private class MakeGetRequest extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return ""+ getText(R.string.try_again_error);
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //main_text.setText(result);
        }
    }



    // Given a URL, establishes an HttpUrlConnection and retrieves
    // the web page content as a InputStream, which it returns as
    // a string.
    private String downloadUrl(String myurl) throws IOException {
        //  InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        //int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();

            // IN_STREAM = is;

            // Convert the InputStream into a string
            // String contentAsString = readIt(is, len);
            //  text_2.setText(contentAsString);

            // Log.d("Input Stream from download URL's try",contentAsString);


            //  String instream_test = readIt(IN_STREAM,len);
            //  Log.d("IN_STREAM from download URL's try",instream_test);


            //   return contentAsString;
            return "Test OK";
            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {


            // if (is != null) {
            //     is.close();
            //}
        }
    }




    //Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }


    // JSON ACTIONS

    public void jsonActions() {
        new PerformJsonOperations().execute();
        Log.d("JSON", "Reached JsonActions");


    }









    //-----------Internet End











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



    public void userCaller(View v){

        Intent intent = new Intent(this, UserStatistics.class);
        //intent.putExtra(USERS_CALL_STRING,UsersCallString);
        startActivity(intent);

    }








    private class PerformJsonOperations extends AsyncTask<Void, Void, List<ImageDataBox>>  {

        protected List<ImageDataBox> doInBackground(Void... params) {

            // params comes from the execute() call: params[0] is the url.
            try {
                List<ImageDataBox> w = JsonOperations();
                Log.d("Json Parsing", "Logging from PerformJsonOperations");

                //Log.d("Json Parsing", w.get(w.size() - 1).name);

                return w;

            } catch (IOException e) {
                List<ImageDataBox> x = new ArrayList<ImageDataBox>();
                return x;

            }
        }




        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(List<ImageDataBox> result) {
            Log.d("Json Parsing", "Reaching on Post Execute");

            try {
                mObjectList = DisplayDetails(result);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d("mObject List", mObjectList.toString());

            //String icon_url = result.icon_url;
            // String icon = result.icon_name;
            // String text3 = result.relative_humidity;

            //  String fulltext = icon_url + icon + text3;

            //jsonText.setText(fulltext);

            //return mObjectList;
        }
        public List<ImageDataBox> JsonOperations() throws IOException {


            // JSON operations


            RankitImageParsing bookParse = new RankitImageParsing();
            ImageDataBox book_data = new ImageDataBox();
            List<ImageDataBox> datalist = new ArrayList<ImageDataBox>();
            Log.d("Json Parsing", "In Json Operations now");


            //Send the Input Stream to JSON Parser
            try {
                // String instream_test = readIt(is,800);
                // Log.d("IN_STREAM from JsonOperations",instream_test);


                datalist = bookParse.ParseJson(is);
                Log.d("Json Parsing", datalist.get(datalist.size() - 1).name);

            } catch (JSONException e) {
                e.printStackTrace();
            } finally{

            }
            //DisplayJson(book_data);

            for (int i = 0; i < datalist.size(); i++) {
                Log.d("ImageName",datalist.get(i).name);
            }


            BigList = datalist;


            //---------------
            return datalist;


        }


        private ArrayList<RankObjects> DisplayDetails(List<ImageDataBox> result) throws IOException, InterruptedException {

            Log.d("Json Parsing", "Inside DisplayDetails function");

            //BigList = result;

            List<ImageDataBox> itemList = result;

            for (int i = 0; i < itemList.size(); i++) {

                String name = itemList.get(i).name;
                String director = itemList.get(i).creator;
                String img_url = itemList.get(i).image_file;

                Log.d("Name of Image", name);

                String full_url = "https://rankitcrowd.appspot.com/RankItWeb/default/download/" + img_url;

                //URL url = new URL(full_url);
                //Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                Log.d("Big List", mObjectList.toString());

                //mObjectList.add(new RankObjects(name, director, bmp, ObjType.IMAGES));
                Log.d("Big List", mObjectList.toString());

            }



            for (int i = 0; i < mObjectList.size(); ++i) {
                Log.d("Show Items", mObjectList.get(i).getTitle());
                mObjectList.get(i).setTitle(result.get(i).name);
                mObjectList.get(i).setDirector(" ");
                mObjectList.get(i).setItemID(result.get(i).item_id);
                //mObjectList.get(i).setIcon(result.get(i).cover_art);





                String img_url = itemList.get(i).image_file;
                Log.d("Cover Art",img_url);

                String full_url = "https://rankitcrowd.appspot.com/RankItWeb/default/download/" + img_url;
                Log.d("Cover URL",full_url);

                String sendingurl = full_url + "-" + i;
                Log.d("StringSending",sendingurl);


                try{
                    getImageActions(sendingurl);
                    //Drawable item_img = drawable_from_url(full_url);

                } catch (Exception e){
                    e.printStackTrace();

                }


                //Drawable item_img = Drawable.createFromStream(is2,"test");


                //mObjectList.get(i).setIcon(item_img);






            }


            String toShow = "Nothing";

            for(int i = 0; i< result.size(); i++){
                ImageDataBox el = result.get(i);

                toShow = el.name.toString();
            }

            //main_text.setText(toShow);

            return mObjectList;


        }



        //-----------------Get Image

        private void getImageActions(String full_url) {

            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if(networkInfo != null && networkInfo.isConnected()) {
                new MakeImageGetRequest().execute(full_url);
                // Parse operations
                //jsonActions();
            }
            else {
                //else cases
            }
        }



        // Uses AsyncTask to create a task away from the main UI thread. This task takes a
        // URL string and uses it to create an HttpUrlConnection. Once the connection
        // has been established, the AsyncTask downloads the contents of the webpage as
        // an InputStream. Finally, the InputStream is converted into a string, which is
        // displayed in the UI by the AsyncTask's onPostExecute method.
        private class MakeImageGetRequest extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... urls) {

                // params comes from the execute() call: params[0] is the url.
                try {
                    return downloadImageUrl(urls[0]);
                } catch (IOException e) {
                    return ""+ getText(R.string.try_again_error);
                }
            }
            // onPostExecute displays the results of the AsyncTask.
            @Override
            protected void onPostExecute(String result) {
                //main_text.setText(result);
            }
        }



        // Given a URL, establishes an HttpUrlConnection and retrieves
        // the web page content as a InputStream, which it returns as
        // a string.
        private String downloadImageUrl(String myurl) throws IOException {
            //  InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.
            //int len = 500;

            try {

                Log.d("URL",myurl);
                String[] urlmain = myurl.split("-");

                for(int i=0;i < urlmain.length; i++){
                    Log.d("SPLIT",urlmain[i]);
                }

                String urltosend = urlmain[0];
                Integer pos = Integer.parseInt(urlmain[1]);

                URL url = new URL(urltosend);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                int response = conn.getResponseCode();
                Log.d(DEBUG_TAG, "The response is: " + response);
                is2 = conn.getInputStream();

                try {
                    Log.d("Stream is2",is2.toString());
                    Drawable item_img = Drawable.createFromStream(is2,"test");

                    mObjectList.get(pos).setIcon(item_img);
                } catch (Exception e){
                    e.printStackTrace();
                }


                //   return contentAsString;
                return "Test OK";
                // Makes sure that the InputStream is closed after the app is
                // finished using it.
            } finally {


                // if (is != null) {
                //     is.close();
                //}
            }
        }




        //-------------Over








    }




    //-----------------Submit Actions

    private void postMovieRanks(String full_url) {

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()) {
            new MakeMoviePostRequest().execute(full_url);
            // Parse operations
            //jsonActions();
        }
        else {
            //else cases
        }
    }



    // Uses AsyncTask to create a task away from the main UI thread. This task takes a
    // URL string and uses it to create an HttpUrlConnection. Once the connection
    // has been established, the AsyncTask downloads the contents of the webpage as
    // an InputStream. Finally, the InputStream is converted into a string, which is
    // displayed in the UI by the AsyncTask's onPostExecute method.
    private class MakeMoviePostRequest extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadMoviePostUrl(urls[0]);
            } catch (IOException e) {
                return ""+ getText(R.string.try_again_error);
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //main_text.setText(result);
        }
    }



    // Given a URL, establishes an HttpUrlConnection and retrieves
    // the web page content as a InputStream, which it returns as
    // a string.
    private String downloadMoviePostUrl(String myurl) throws IOException {
        //  InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        //int len = 500;

        try {

            String base_url = "https://rankitcrowd.appspot.com/RankItWeb/default/rank_items.json?";




            String urltosend = base_url+myurl;

            URL url = new URL(urltosend);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "The response is: " + response);
            is3 = conn.getInputStream();



            //   return contentAsString;
            return "Test OK";
            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {


            // if (is != null) {
            //     is.close();
            //}
        }
    }




    //-------------Over







}