package rbh9dm.cs2110.virginia.edu.ghost_hunt;

import android.graphics.Bitmap;
import android.support.v4.graphics.BitmapCompat;

/**
 * Created by Pylo on 4/14/15.
 */
public class Immovable {
    private int xCoord;
    private int yCoord;
    private int width;
    private int height;
    private Bitmap bitmap;
    private boolean canMove;

    public Immovable(Bitmap bitmap, int xCoord, int yCoord, int width, int height) {
        this.bitmap = bitmap;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.width = width;
        this.height = height;
        canMove = false;

    }

    public int getImageLength(Background b) {
        return 0;
    }

    public int getImageHeight(Background b) {
        return 0;
    }

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }
}
