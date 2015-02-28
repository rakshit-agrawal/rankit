package ucsc.hci.rankit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MoviesActivityMain extends ActionBarActivity {

    private List<RankObjects> myObjects = new ArrayList<RankObjects>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

}
