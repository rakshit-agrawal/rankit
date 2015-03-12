package ucsc.hci.rankit;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;


public class MainActivity extends ActionBarActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "fSVwg82cp3vgygtBPUhCfzHT0";
    private static final String TWITTER_SECRET = "CNGDRxqQwubPlXITLb1aJ6YENr9BkJ7PEW4l3xQqkBbv3zFRCJ";
    private static final String FINAL_STRING_MESSAGE = "Test String";

    public static String FinalString = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void menuActivityCaller(View v){

        Intent intent = new Intent(this, MenuPageActivity.class);
        intent.putExtra(FINAL_STRING_MESSAGE,FinalString);
        startActivity(intent);

    }


    public void loginActivityCaller(View v){

        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(FINAL_STRING_MESSAGE,FinalString);
        startActivity(intent);

    }


}
