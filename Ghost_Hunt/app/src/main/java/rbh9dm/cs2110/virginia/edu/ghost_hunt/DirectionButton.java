package rbh9dm.cs2110.virginia.edu.ghost_hunt;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Student User on 4/8/2015.
 */
public class DirectionButton {
    private int x;
    private int y;
    private Bitmap bitmap;
    private boolean touched;

    public DirectionButton(Bitmap bitmap, int x, int y) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
    }

    public Bitmap getBitMap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setYC(int y) {
        this.y = y;
    }

    public boolean isTouched() {
        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x , y, null);
    }
    /*Checks if the user has touched inside the boundaries of the button*/
    public void handleActionDown(int eventX, int eventY) {
        if(eventX >= x && eventX <= x+bitmap.getWidth()) {
            if(eventY >= y && eventY <= y+bitmap.getHeight()) {
                setTouched(true);
            }
            else {
                setTouched(false);
            }
        }
        else {
            setTouched(false);
        }
    }
}
