package ucsc.hci.rankit;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MoviesActivityMain extends ActionBarActivity {

    private List<Movies> myMovies = new ArrayList<Movies>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_activity_main);

        populateMovieList();
        populateListView();
    }

    private void populateMovieList() {
        myMovies.add(new Movies("Titanic", "James Cameron", R.drawable.titanic));
        myMovies.add(new Movies("Avengers", "Joss Whedon", R.drawable.avengers));
        myMovies.add(new Movies("Avatar", "James Cameron", R.drawable.avatar));
        myMovies.add(new Movies("Crouching", "Ang Lee", R.drawable.crouching));
    }

    private void populateListView() {
        ArrayAdapter<Movies> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.MoviesListView);
        list.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<Movies>{
        public MyListAdapter(){
            super(MoviesActivityMain.this, R.layout.item_view, myMovies);
        }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View itemView = convertView;
        if (itemView==null) {
            itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
        }

            Movies currentMovie = myMovies.get(position);

            ImageView imageView = (ImageView)itemView.findViewById(R.id.item_image);
            imageView.setImageResource(currentMovie.getIconID());

        TextView titleText = (TextView) itemView.findViewById(R.id.item_title);
        titleText.setText(currentMovie.getTitle());

        TextView directorText = (TextView) itemView.findViewById(R.id.item_director);
        directorText.setText(currentMovie.getDirector());

            return itemView;
        }

    }


    }
/*
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

        return super.onOptionsItemSelected(item);
    }
*/
