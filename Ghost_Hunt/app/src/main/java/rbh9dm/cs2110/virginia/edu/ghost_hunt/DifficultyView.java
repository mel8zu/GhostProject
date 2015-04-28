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
 * Created by Student User on 4/23/2015.
 */
public class DifficultyView extends ActionBarActivity {

    private static final String TAG = "Difficulty Selection";

    private Button difficultyButton;

    @Override
    protected void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);
        Log.i(TAG, "Calling easy button");

        setContentView(R.layout.difficulty_view);

        final Context context = this;

        if (isTaskRoot()) {
            Intent intent = new Intent(context, SplashActivity.class);
            startActivity(intent);
            finish();
        }

        difficultyButton = (Button) findViewById(R.id.d1);
        difficultyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Calling onClick() on easy button");
                Intent intent = new Intent(context, LevelView.class);
                intent.putExtra("hardness",1);
                startActivity(intent);
            }
        });

        Log.i(TAG, "Calling medium button");
        difficultyButton = (Button) findViewById(R.id.d2);
        difficultyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Calling onClick() on medium button");
                Intent intent = new Intent(context, LevelView.class);
                intent.putExtra("hardness",2);
                startActivity(intent);
            }
        });

        Log.i(TAG, "Calling hard button");
        difficultyButton = (Button) findViewById(R.id.d3);
        difficultyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Calling onClick() on hard button");
                Intent intent = new Intent(context, LevelView.class);
                intent.putExtra("hardness",3);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        finish();
    }
}
