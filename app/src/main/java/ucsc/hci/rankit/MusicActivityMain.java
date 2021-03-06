package ucsc.hci.rankit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MusicActivityMain extends ActionBarActivity {

    private List<RankObjects> myObjects = new ArrayList<RankObjects>();


    private static final String MUSIC_GET_REQUEST = "https://rankitcrowd.appspot.com/RankItWeb/default/get_items.json?token=get_my_data&type=music&count=";

    private List<MusicDataBox> listItems = new ArrayList<MusicDataBox>();

    public ArrayList<RankObjects> mObjectList = new ArrayList<RankObjects>();
    public List<MusicDataBox> BigList = new ArrayList<MusicDataBox>();



    public static final String PREFS_NAME = "MyPrefsFile";

    private static final String DEBUG_TAG = "InternetActions";

    public InputStream is = null;

    public InputStream is2 = null;

    public InputStream is3 = null;

    public static Resources globalres;

    public static Drawable bmp;

    public static int itemcount;



    public TextView main_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //to add back arrow at top;


        SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
        int counter = settings.getInt("itemCount",4);
        itemcount = counter;

        //--- Spinner feature start

        Spinner spinner = (Spinner) findViewById(R.id.music_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> spinadapter = ArrayAdapter.createFromResource(this,
                R.array.music_genres, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        spinadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(spinadapter);


        //--- Spinner feature end



        internetActions();


        //ArrayList<RankObjects> mObjectList = new ArrayList<RankObjects>();
        /*
        for (int i = 0; i < Music.sMusicStrings.length; ++i) {
            mObjectList.add(Music.sMusicStrings[i]);
        }
        */

        for (int i = 0; i < itemcount; ++i) {
            RankObjects x;

            x = new RankObjects("Loading... ", " ", bmp, ObjType.MUSIC);

            mObjectList.add(x);
            Log.d("Items in display", mObjectList.get(i).getTitle());
        }


        Resources res = getResources();

        for (int i = 0; i < mObjectList.size(); ++i) {
            Log.d("Show Items", mObjectList.get(i).getTitle());
            mObjectList.get(i).setTitle("Loading...");
            mObjectList.get(i).setDirector(" ");

            Drawable d1 = new BitmapDrawable(res, Bitmap.createBitmap(10, 10, Bitmap.Config.ALPHA_8));

            try {
                mObjectList.get(i).setIcon(d1);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        /*

        Resources res = getResources();
        Drawable d1 = res.getDrawable(R.drawable.image_1);
        Log.d("Got Image", d1.toString());



        for (int i = 0; i < mObjectList.size(); ++i) {
            Log.d("Show Items", mObjectList.get(i).getTitle());
            try {
                Log.d("Inside try", d1.toString());

                mObjectList.get(i).setIcon(d1);
                Log.d("try done", d1.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        */


        StableArrayAdapter adapter = new StableArrayAdapter(this, R.layout.item_view, mObjectList);
        DynamicListView listView = (DynamicListView) findViewById(R.id.MusicListView);

        listView.setObjectList(mObjectList);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @Override
    protected void onStop(){
        super.onStop();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("itemCount", itemcount);

        // Commit the edits!
        editor.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_music_activity_main, menu);
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

        //to add back arrow at top;
        Intent myIntent = new Intent(getApplicationContext(), MenuPageActivity.class);
        startActivityForResult(myIntent, 0);

        return super.onOptionsItemSelected(item);
    }





    public void submitAction(View v) {

        String var_string = "";
        for ( Map.Entry<Integer, Integer> entry : StableArrayAdapter.post_dict.entrySet() ) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();

            var_string = var_string + "pos"+ key.toString() + "=" + value.toString()+"&";
            Log.d("key",key.toString());
            Log.d("value",value.toString());
            Log.d("url",var_string);

        }

        Log.d("url",var_string);

        var_string = var_string + "type=music";



        try{
            postMovieRanks(var_string);
        }catch (Exception e){
            e.printStackTrace();
        }

        Intent intent = new Intent(this, SubmitComplete.class);
        //intent.putExtra(MOVIES_CALL_STRING,MoviesCallString);
        startActivity(intent);

    }




    private void internetActions() {

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            String GET_REQUEST = MUSIC_GET_REQUEST + itemcount;
            new MakeGetRequest().execute(GET_REQUEST);
            // Parse operations
            jsonActions();
        } else {
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
                return "" + getText(R.string.try_again_error);
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





    private class PerformJsonOperations extends AsyncTask<Void, Void, List<MusicDataBox>>  {

        protected List<MusicDataBox> doInBackground(Void... params) {

            // params comes from the execute() call: params[0] is the url.
            try {
                List<MusicDataBox> w = JsonOperations();
                Log.d("Json Parsing", "Logging from PerformJsonOperations");

                //Log.d("Json Parsing", w.get(w.size() - 1).name);

                return w;

            } catch (IOException e) {
                List<MusicDataBox> x = new ArrayList<MusicDataBox>();
                return x;

            }
        }




        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(List<MusicDataBox> result) {
            Log.d("Json Parsing", "Reaching on Post Execute");

            try {
                mObjectList = DisplayDetails(result);
            } catch (IOException e) {
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
        public List<MusicDataBox> JsonOperations() throws IOException {


            // JSON operations


            RankitMusicParsing musicParse = new RankitMusicParsing();
            MusicDataBox music_data = new MusicDataBox();
            List<MusicDataBox> datalist = new ArrayList<MusicDataBox>();
            Log.d("Json Parsing", "In Json Operations now");


            //Send the Input Stream to JSON Parser
            try {
                // String instream_test = readIt(is,800);
                // Log.d("IN_STREAM from JsonOperations",instream_test);


                datalist = musicParse.ParseJson(is);
                Log.d("Json Parsing", datalist.get(datalist.size() - 1).name);

            } catch (JSONException e) {
                e.printStackTrace();
            } finally{

            }
            //DisplayJson(music_data);

            for (int i = 0; i < datalist.size(); i++) {
                Log.d("MusicName",datalist.get(i).name);
            }


            BigList = datalist;


            //---------------
            return datalist;


        }


        private ArrayList<RankObjects> DisplayDetails(List<MusicDataBox> result) throws IOException {

            Log.d("Json Parsing", "Inside DisplayDetails function");

            //BigList = result;

            List<MusicDataBox> itemList = result;

            for (int i = 0; i < itemList.size(); i++) {

                String name = itemList.get(i).name;
                String artist = itemList.get(i).artist;
                String img_url = itemList.get(i).album_art;

                Log.d("Name of Movie", name);

                String full_url = "https://rankitcrowd.appspot.com/RankItWeb/default/download/" + img_url;

                //URL url = new URL(full_url);
                //Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                Log.d("Big List", mObjectList.toString());

                //mObjectList.add(new RankObjects(name, director, bmp, ObjType.MOVIES));
                Log.d("Big List", mObjectList.toString());

            }



            for (int i = 0; i < mObjectList.size(); ++i) {
                Log.d("Show Items", mObjectList.get(i).getTitle());
                mObjectList.get(i).setTitle(result.get(i).name);
                mObjectList.get(i).setDirector(result.get(i).artist);
                mObjectList.get(i).setItemID(result.get(i).item_id);
                //mObjectList.get(i).setIcon(result.get(i).cover_art);




                //Drawable item_img = Drawable.createFromStream()





                String img_url = itemList.get(i).album_art;
                Log.d("Album Art",img_url);

                String full_url = "https://rankitcrowd.appspot.com/RankItWeb/default/download/" + img_url;
                Log.d("Image URL",full_url);

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
                MusicDataBox el = result.get(i);

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



    public void settingsCaller(View v){

        Intent intent = new Intent(this, CountSelect.class);
        startActivity(intent);

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




    public void userCaller(View v){

        Intent intent = new Intent(this, UserStatistics.class);
        //intent.putExtra(USERS_CALL_STRING,UsersCallString);
        startActivity(intent);

    }



}