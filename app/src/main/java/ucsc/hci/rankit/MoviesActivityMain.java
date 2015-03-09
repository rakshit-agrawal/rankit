package ucsc.hci.rankit;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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


public class MoviesActivityMain extends ActionBarActivity {

    private static final String MOVIE_GET_REQUEST = "https://rankitcrowd.appspot.com/RankItWeb/default/get_items.json?token=get_my_data&type=movies";
    private List<RankObjects> myObjects = new ArrayList<RankObjects>();

    private static final String DEBUG_TAG = "InternetActions";

    public InputStream is = null;

    public TextView main_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        main_text = (TextView)findViewById(R.id.main_text);

        //--- Spinner feature start

        Spinner spinner = (Spinner) findViewById(R.id.movies_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> spinadapter = ArrayAdapter.createFromResource(this,
                R.array.movies_genres, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        spinadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(spinadapter);

        //--- Spinner feature end

        internetActions();

        ArrayList<RankObjects> mObjectList = new ArrayList<RankObjects>();

        for (int i = 0; i < Movie.sMovieStrings.length; ++i) {
            mObjectList.add(Movie.sMovieStrings[i]);
        }

        StableArrayAdapter adapter = new StableArrayAdapter(this, R.layout.item_view, mObjectList);
        DynamicListView listView = (DynamicListView) findViewById(R.id.MoviesListView);

        listView.setObjectList(mObjectList);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    private void internetActions() {

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()) {
            new MakeGetRequest().execute(MOVIE_GET_REQUEST);
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
            main_text.setText(result);
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movies_activity_main, menu);
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
        Intent myIntent = new Intent(getApplicationContext(), MenuPageActivity.class);
        startActivityForResult(myIntent, 0);

        return super.onOptionsItemSelected(item);
    }


    public class PerformJsonOperations extends AsyncTask<Void, Void, MovieDataBox> {

        protected MovieDataBox doInBackground(Void... params) {

            // params comes from the execute() call: params[0] is the url.
            try {
                MovieDataBox w = JsonOperations();
                return w;

            } catch (IOException e) {
                MovieDataBox x = new MovieDataBox();
                return x;

            }
        }



        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(MovieDataBox result) {

            DisplayDetails(result);
            //String icon_url = result.icon_url;
            // String icon = result.icon_name;
            // String text3 = result.relative_humidity;

            //  String fulltext = icon_url + icon + text3;

            //jsonText.setText(fulltext);
        }
        public MovieDataBox JsonOperations() throws IOException {


            // JSON operations


            RankitMovieParsing movieParse = new RankitMovieParsing();
            MovieDataBox movie_data = new MovieDataBox();

            //Send the Input Stream to JSON Parser
            try {
                // String instream_test = readIt(is,800);
                // Log.d("IN_STREAM from JsonOperations",instream_test);


                movie_data = movieParse.ParseJson(is);
            } catch (JSONException e) {
                e.printStackTrace();
            } finally{

            }
            //DisplayJson(movie_data);


            //---------------
            return movie_data;


        }


        private void DisplayDetails(MovieDataBox result) {

            main_text.setText(result.name);

        }


    }


}
