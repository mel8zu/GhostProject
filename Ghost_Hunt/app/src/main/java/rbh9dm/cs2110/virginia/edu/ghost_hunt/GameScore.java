package rbh9dm.cs2110.virginia.edu.ghost_hunt;

import android.graphics.Rect;
import android.graphics.Bitmap;

/**
 * Created by Pylo on 4/19/15.
 */
public class GameScore {
    private int x;
    private int y;
    private Bitmap bitmap;
    private Rect sourceRect;
    private int score;

    public GameScore(Bitmap bitmap, int x, int y, int score) {
        this.x = x;
        this.y = y;
        this.bitmap = bitmap;
        this.score = score;


    }

    public int getX() {return x;}

    public void setX(int x) {this.x = x;}

    public int getY() {return y;}

    public void setY(int y) {this.y = y;}

    public Rect getSourceRect() {return sourceRect;}

    public void setSourceRect(Rect sourceRect) {this.sourceRect = sourceRect;}

    public int getScore() {return score;}

    public void setScore(int score) {this.score = score;}


}
