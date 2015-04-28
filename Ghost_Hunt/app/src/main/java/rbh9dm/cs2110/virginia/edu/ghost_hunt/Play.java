package rbh9dm.cs2110.virginia.edu.ghost_hunt;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Student User on 4/21/2015.
 */
public abstract class Play extends Activity {

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
        mp.setVolume(.7F,.7F);
        mp.start();
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        Log.i("hi","here is the bundle!! : " + bundle);
        final int hardness = (Integer) bundle.get("hardness");
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

    @Override
    public void onBackPressed() {
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
