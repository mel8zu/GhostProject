package rbh9dm.cs2110.virginia.edu.ghost_hunt;

/**
 * Created by Student User on 4/24/2015.
 */
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Student User on 4/24/2015.
 */
public class BuyButton {
    private int x;
    private int y;
    private Bitmap bitmap;
    private boolean touched;
    private int minCost;
    private long lastBuy;

    public BuyButton(Bitmap bitmap, int x, int y, int minCost, long lastBuy) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.minCost = minCost;
        this.lastBuy = lastBuy;
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

    public void setY(int y) {
        this.y = y;
    }

    public boolean isTouched() {
        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    public long getLastBuy() {
        return lastBuy;
    }

    public void setLastBuy(long lastBuy) {
        this.lastBuy = lastBuy;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x , y, null);
    }

    public void setMinCost(int minCost) {
        this.minCost = minCost;
    }

    public int getMinCost() {
        return minCost;
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
