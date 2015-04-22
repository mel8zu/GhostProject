package rbh9dm.cs2110.virginia.edu.ghost_hunt;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v4.graphics.BitmapCompat;

/**
 * Created by Pylo on 4/14/15.
 */
public class Immovable {
    private int xCoord;
    private int yCoord;
    private int centerX;
    private int centerY;
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
        centerX = xCoord + width/2;
        centerY = yCoord + height/2;
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
        this.centerX = xCoord + width/2;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
        this.centerY = yCoord + height/2;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        this.centerX = xCoord + width/2;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        this.centerY = yCoord + height/2;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, xCoord, yCoord, null);
    }
}
