package rbh9dm.cs2110.virginia.edu.ghost_hunt;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import static rbh9dm.cs2110.virginia.edu.ghost_hunt.R.menu.menu_level;

/**
 * Created by Pylo on 4/11/15.
 */
public class LevelView extends ActionBarActivity {

    private static final String TAG="Level Selection";

    private Button levelButton;

    @Override
    protected void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);
        Log.i(TAG, "Calling Level 1 button");

        setContentView(R.layout.level_select);

        final Context context=this;

        if (isTaskRoot()) {
            Intent intent = new Intent(context, SplashActivity.class);
            startActivity(intent);
            finish();
        }

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        Log.i("hi","here is the bundle!! : " + bundle);
        final int hardness = (Integer) bundle.get("hardness");

        Log.i("hi", "here is the hardness: " + hardness);

        levelButton = (Button) findViewById(R.id.L1);
        levelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Calling onClick() on Forrest button");
                Intent intent = new Intent(context, Play.class);
                intent.putExtra("hardness",hardness);
                intent.putExtra("level" ,1);
                startActivity(intent);
            }
        });

        Log.i(TAG, "Calling Level 2 button");
        levelButton = (Button) findViewById(R.id.L2);
        levelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Calling onClick() on Forrest button");
                Intent intent=new Intent(context, Play.class);
                intent.putExtra("hardness",hardness);
                intent.putExtra("level" ,2);
                startActivity(intent);
            }
        });

        Log.i(TAG, "Calling Level 3 button");
        levelButton = (Button) findViewById(R.id.L3);
        levelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Calling onClick() on Forrest button");
                Intent intent=new Intent(context, Play.class);
                intent.putExtra("hardness",hardness);
                intent.putExtra("level" ,3);
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
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        finish();
    }

}
