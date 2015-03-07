package ucsc.hci.rankit;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MusicActivityMain extends ActionBarActivity {

    private List<RankObjects> myObjects = new ArrayList<RankObjects>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //to add back arrow at top;

        ArrayList<RankObjects> mObjectList = new ArrayList<RankObjects>();
        for (int i = 0; i < Music.sMusicStrings.length; ++i) {
            mObjectList.add(Music.sMusicStrings[i]);
        }

        StableArrayAdapter adapter = new StableArrayAdapter(this, R.layout.item_view, mObjectList);
        DynamicListView listView = (DynamicListView) findViewById(R.id.MusicListView);

        listView.setObjectList(mObjectList);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
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
}