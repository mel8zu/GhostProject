package rbh9dm.cs2110.virginia.edu.ghost_hunt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Student User on 4/21/2015.
 */
public class Play extends Activity {

    protected GameView gameView;
    protected MediaPlayer mp;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isTaskRoot()) {
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
            finish();
        }
        mp = MediaPlayer.create(this, R.raw.ghostbusters);
        mp.setLooping(true);
        mp.setVolume(.6F,.6F);
        mp.start();
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        Log.i("hi","here is the bundle!! : " + bundle);
        final int hardness = (Integer) bundle.get("hardness");
        int level = (Integer) bundle.get("level");
        int x = 0;
        int y = 0;
        if(bundle.containsKey("x")) x = (Integer) bundle.get("x");
        if(bundle.containsKey("y")) y = (Integer) bundle.get("y");
        gameView = new GameView(this, level, hardness,x,y);
        int damage = 0;
        if(bundle.containsKey("health")) damage = (Integer) bundle.get("health");
        gameView.getHealth().addDamage(damage/10);
        if(bundle.containsKey("coins")) gameView.setNumCoins((Integer) bundle.get("coins"));
        if(bundle.containsKey("score")) gameView.setScore((Integer) bundle.get("score"));
        if(bundle.containsKey("displayScore")) gameView.setDisplayScore((Integer) bundle.get("displayScore"));
        if(bundle.containsKey("killedGhosts")) gameView.setKilledGhosts((Integer) bundle.get("killedGhosts"));


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(gameView);

    }

    protected void updateHighScore(String score) {
        DataBaseHandler db = new DataBaseHandler(this);
        Log.i("***********", "****************** scores in db: " + db.getHighScoreCount());
        if (db.getHighScoreCount() != 10) {
            db.deleteAll();
            db.addHighScore(new HighScore("0000000000"));
            db.addHighScore(new HighScore("0000000000"));
            db.addHighScore(new HighScore("0000000000"));
            db.addHighScore(new HighScore("0000000000"));
            db.addHighScore(new HighScore("0000000000"));
            db.addHighScore(new HighScore("0000000000"));
            db.addHighScore(new HighScore("0000000000"));
            db.addHighScore(new HighScore("0000000000"));
            db.addHighScore(new HighScore("0000000000"));
            db.addHighScore(new HighScore("0000000000"));
        }
        ArrayList<HighScore> scoreList = db.getAllHighScores();
        scoreList.add(new HighScore(score));
        Collections.sort(scoreList);
        Collections.reverse(scoreList);
        db.deleteAll();
        for(int i = 0; i<10; i++) {
            db.addHighScore(new HighScore(scoreList.get(i).getScore()));
        }
        Intent intent = new Intent(Play.this, MainActivity.class);
        Play.this.startActivity(intent);
    }

    public void save(GameView gv) {
        String path = this.getFilesDir().getAbsolutePath();
        File traceFile = new File(path + "/data.csv");
        int level = gv.getLevel();
        int difficulty = gv.getDifficulty();
        int coins = gv.getNumCoins();
        int x = gv.getBackgroundX();
        int y = gv.getBackgroundY();
        int health = gv.getHealth().getDamage();
        int score = (int) gv.getScore();
        int displayScore = (int) gv.getDisplayScore();
        int killedGhosts = gv.getKilledGhosts();
        try
        {

            BufferedWriter outputStream = new BufferedWriter(new FileWriter(traceFile));
            String everything = "";


            everything = ((Integer) level).toString() + "," + ((Integer) difficulty).toString() + "," + ((Integer) coins).toString() +
                    "," + ((Integer) x).toString() + "," + ((Integer) y).toString() +
                    "," + ((Integer) health).toString() + ((Integer) score).toString() + "," + ((Integer) displayScore).toString()
                    + "," + ((Integer) killedGhosts).toString();
            outputStream.write(everything);
            outputStream.close();
            // Refresh the data so it can seen when the device is plugged in a
            // computer. You may have to unplug and replug the device to see the
            // latest changes. This is not necessary if the user should not modify
            // the files.
            MediaScannerConnection.scanFile((Context) (this),
                    new String[]{traceFile.toString()},
                    null,
                    null);

        }
        catch (IOException e)
        {
            Log.e("com", "Unable to write to the TraceFile.csv file.");
        }

    }

    @Override
    public void onBackPressed() {
        save(gameView);
        gameView.shutDownThread();
        mp.stop();
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onPause() {
        super.onPause();
        gameView.setGameOver(false);
        gameView.shutDownThread();
        gameView.recycleBits();
        gameView.shutDownThread();
        mp.stop();
        finish();
    }

    public void onResume() {
        super.onResume();
        mp.start();
        if (gameView != null) {
            gameView.restartThread();
        }
    };

}
