package rbh9dm.cs2110.virginia.edu.ghost_hunt;

/**
 * Created by Student User on 3/31/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class DataBaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "highScoresManager";

    // Contacts table name
    private static final String TABLE_HIGH_SCORES = "highScores";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_SCORE = "score";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_HIGH_SCORES_TABLE = "CREATE TABLE " + TABLE_HIGH_SCORES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_SCORE + " TEXT"+")";
        db.execSQL(CREATE_HIGH_SCORES_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIGH_SCORES);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addHighScore(HighScore highScore) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SCORE, highScore.getScore()); // Contact Name

        // Inserting Row
        db.insert(TABLE_HIGH_SCORES, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    public HighScore getHighScore(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_HIGH_SCORES, new String[] { KEY_ID,
                        KEY_SCORE}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        HighScore highScore = new HighScore(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
        // return contact
        return highScore;
    }

    // Getting All Contacts
    public List<HighScore> getAllHighScores() {
        List<HighScore> highScoreList = new ArrayList<HighScore>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_HIGH_SCORES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HighScore highScore = new HighScore();
                highScore.setID(Integer.parseInt(cursor.getString(0)));
                highScore.setScore(cursor.getString(1));
                // Adding contact to list
                highScoreList.add(highScore);
            } while (cursor.moveToNext());
        }

        // return contact list
        return highScoreList;
    }

    // Updating single contact
    public int updateHighScore(HighScore highScore) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SCORE, highScore.getScore());

        // updating row
        return db.update(TABLE_HIGH_SCORES, values, KEY_ID + " = ?",
                new String[] { String.valueOf(highScore.getID()) });
    }

    // Deleting single contact
    public void deleteHighScore(HighScore highScore) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HIGH_SCORES, KEY_ID + " = ?",
                new String[] { String.valueOf(highScore.getID()) });
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HighScore> list = new ArrayList<HighScore>(getAllHighScores());
        for(HighScore highScore:list) {
            deleteHighScore(highScore);
        }
        db.close();
    }


    // Getting contacts Count
    public int getHighScoreCount() {
        String countQuery = "SELECT  * FROM " + TABLE_HIGH_SCORES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int x = cursor.getCount();
        cursor.close();
        // return count
        return x;
    }

}
