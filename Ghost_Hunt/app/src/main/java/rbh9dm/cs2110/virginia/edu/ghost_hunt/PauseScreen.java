package rbh9dm.cs2110.virginia.edu.ghost_hunt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import static rbh9dm.cs2110.virginia.edu.ghost_hunt.R.menu.menu_level;

/**
 * Created by Pylo on 4/21/15.
 */
public class PauseScreen extends ActionBarActivity {
    private static final String TAG="Level Selection";

    private Button levelButton;

    @Override
    protected void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);
        Log.i(TAG, "Calling Return to Level Select");

        setContentView(R.layout.pause_screen);

        final Context context=this;

        levelButton = (Button) findViewById(R.id.button);
        levelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Calling onClick() on Return to Game Button");
                Intent intent = new Intent(context, Play.class);
                startActivity(intent);
            }
        });

        Log.i(TAG, "Calling Level 2 button");
        levelButton = (Button) findViewById(R.id.L2);
        levelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Calling onClick() on Level button");
                Intent intent=new Intent(context, LevelView.class);
                startActivity(intent);
            }
        });

        Log.i(TAG, "Calling Level 3 button");
        levelButton = (Button) findViewById(R.id.L3);
        levelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Calling onClick() on Home Screen Button");
                Intent intent=new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(menu_level, menu);
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
}
