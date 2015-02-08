package ucsc.hci.rankit;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MenuPageActivity extends ActionBarActivity {

    public static String MoviesCallString = null;
    public static String BooksCallString = null;
    public static String MusicCallString = null;
    public static String ImagesCallString = null;

    public final static String MOVIES_CALL_STRING = "";
    public final static String BOOKS_CALL_STRING = "";
    public final static String MUSIC_CALL_STRING = "";
    public final static String IMAGES_CALL_STRING = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu_page, menu);
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


    public void moviesCaller(View v){

        Intent intent = new Intent(this, MenuPageActivity.class);
        intent.putExtra(MOVIES_CALL_STRING,MoviesCallString);
        startActivity(intent);

    }
    public void booksCaller(View v){

        Intent intent = new Intent(this, MenuPageActivity.class);
        intent.putExtra(BOOKS_CALL_STRING,BooksCallString);
        startActivity(intent);

    }
    public void musicCaller(View v){

        Intent intent = new Intent(this, MenuPageActivity.class);
        intent.putExtra(MUSIC_CALL_STRING,MusicCallString);
        startActivity(intent);

    }
    public void imagesCaller(View v){

        Intent intent = new Intent(this, MenuPageActivity.class);
        intent.putExtra(IMAGES_CALL_STRING,ImagesCallString);
        startActivity(intent);

    }




}
