package rbh9dm.cs2110.virginia.edu.ghost_hunt;

/**
 * Created by Student User on 3/31/2015.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Student User on 3/26/2015.
 */
public class Score extends ActionBarActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score);

        DataBaseHandler db = new DataBaseHandler(this);
        Log.i("Score", "created database");
        db.getHighScoreCount();
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
        Log.i("Score", "Set entries to 0");

        ArrayList<HighScore> scoreList = new ArrayList<HighScore>(db.getAllHighScores());
        scoreList.add(db.getHighScore(1));
        scoreList.add(db.getHighScore(2));
        scoreList.add(db.getHighScore(3));
        scoreList.add(db.getHighScore(4));
        scoreList.add(db.getHighScore(5));
        scoreList.add(db.getHighScore(6));
        scoreList.add(db.getHighScore(7));
        scoreList.add(db.getHighScore(8));
        scoreList.add(db.getHighScore(9));
        scoreList.add(db.getHighScore(10));

        TextView score1 = (TextView) findViewById(R.id.score1);
        TextView score2 = (TextView) findViewById(R.id.score2);
        TextView score3 = (TextView) findViewById(R.id.score3);
        TextView score4 = (TextView) findViewById(R.id.score4);
        TextView score5 = (TextView) findViewById(R.id.score5);
        TextView score6 = (TextView) findViewById(R.id.score6);
        TextView score7 = (TextView) findViewById(R.id.score7);
        TextView score8 = (TextView) findViewById(R.id.score8);
        TextView score9 = (TextView) findViewById(R.id.score9);
        TextView score10 = (TextView) findViewById(R.id.score10);

        Log.i("Score", "Created Text Views");

        score1.setText(scoreList.get(0).getScore());
        score2.setText(scoreList.get(1).getScore());
        score3.setText(scoreList.get(2).getScore());
        score4.setText(scoreList.get(3).getScore());
        score5.setText(scoreList.get(4).getScore());
        score6.setText(scoreList.get(5).getScore());
        score7.setText(scoreList.get(6).getScore());
        score8.setText(scoreList.get(7).getScore());
        score9.setText(scoreList.get(8).getScore());
        score10.setText(scoreList.get(9).getScore());

        Log.i("Score", "Set Text Views");
        Log.i("Score","Here is score 1: "+scoreList.get(0).getScore());

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
        startActivity(intent);
    }
}
