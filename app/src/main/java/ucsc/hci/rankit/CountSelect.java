package ucsc.hci.rankit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class CountSelect extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    public static int globalitemcount = 4;

    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_select);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
        int counter = settings.getInt("itemCount",4);
        globalitemcount = counter;



        //--- Spinner feature start

        Spinner spinner = (Spinner) findViewById(R.id.count_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> spinadapter = ArrayAdapter.createFromResource(this,
                R.array.count_selection, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        spinadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(spinadapter);

        spinner.setOnItemSelectedListener(this);

        spinner.setSelection(counter-2);

        //--- Spinner feature end
    }



    @Override
    protected void onStop(){
        super.onStop();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("itemCount", globalitemcount);

        // Commit the edits!
        editor.commit();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_submit_complete, menu);
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
        //intent.putExtra(FINAL_STRING_MESSAGE,FinalString);
        startActivity(intent);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d("In Item Select", "We're in item Select!!");
        Log.d("In Item Select", "text "+ parent.getItemAtPosition(position) + "--" + id);
        globalitemcount = position+2;
        Log.d("In Item Select", "GlobalItemCount" + globalitemcount);


        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("itemCount", globalitemcount);

        // Commit the edits!
        editor.commit();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.d("In Nothing Select", "We're in nothing Select!!");

    }
}
