package rbh9dm.cs2110.virginia.edu.ghost_hunt;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by Student User on 4/7/2015.
 */
public class Moveable {

    private Bitmap bitmap;
    private int xCoord;
    private int yCoord;
    private boolean canMove;

    public Moveable(Bitmap bitmap, int xCoord, int yCoord) {
        this.bitmap = bitmap;
        this.xCoord = xCoord;
        this.yCoord = yCoord;

    }

    public int getXCoord() {
        return xCoord;
    }

    public void setXCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getYCoord() {
        return yCoord;
    }

    public void setYCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, xCoord, yCoord, null);
    }

}
