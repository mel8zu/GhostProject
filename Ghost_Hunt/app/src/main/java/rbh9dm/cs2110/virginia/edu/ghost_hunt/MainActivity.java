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

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class MainActivity extends ActionBarActivity {

    private static final String TAG="MainActivity";

    private Button scoresButton;
    private Button playButton;
    private Button loadButton;
    private Play play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "calling OnCreate() in MainActivity");

        setContentView(R.layout.activity_main);

        final Context context=this;

        /*MediaPlayer mp = MediaPlayer.create(this, R.raw.ghostbusters);
        mp.seekTo(10000);
        //mp.prepareAsync();
        mp.setLooping(true);
        mp.setVolume((float)0.5, (float)0.5);
        mp.start();*/

        scoresButton = (Button) findViewById(R.id.score_button);
        scoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Calling onClick() on score button");
                Intent intent=new Intent(context, Score.class);
                startActivity(intent);
            }
        });

        playButton = (Button) findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Calling onClick() on play button");
                Intent intent=new Intent(context, DifficultyView.class);
                startActivity(intent);
            }
        });

        loadButton = (Button) findViewById(R.id.load_button);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Calling onClick() on load button");
                Intent intent=new Intent(context, Play.class);

                try {

                    BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                            openFileInput("data.csv")));
                    String line = inputReader.readLine();
                    String[] in = line.split(",");



                    int level = Integer.parseInt(in[0]);
                    int difficulty = Integer.parseInt(in[1]);
                    int coins = Integer.parseInt(in[2]);
                    int x = Integer.parseInt(in[3]);
                    int y = Integer.parseInt(in[4]);
                    int health = Integer.parseInt(in[5]);
                    int score = Integer.parseInt(in[6]);
                    int displayScore = Integer.parseInt(in[7]);
                    int killedGhosts = Integer.parseInt(in[8]);
                    int numSuper = Integer.parseInt(in[9]);

                    intent.putExtra("level", level);
                    intent.putExtra("hardness",difficulty);
                    intent.putExtra("coins",coins);
                    intent.putExtra("x",x);
                    intent.putExtra("y",y);
                    intent.putExtra("health",health);
                    intent.putExtra("score",score);
                    intent.putExtra("displayScore",displayScore);
                    intent.putExtra("killedGhosts",killedGhosts);
                    intent.putExtra("super", numSuper);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        finish();
    }

}
