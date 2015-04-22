package rbh9dm.cs2110.virginia.edu.ghost_hunt;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Student User on 4/21/2015.
 */
public abstract class Play extends Activity {
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
        onStop();
    }
}
