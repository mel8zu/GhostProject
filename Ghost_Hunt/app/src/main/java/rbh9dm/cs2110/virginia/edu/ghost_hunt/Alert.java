package rbh9dm.cs2110.virginia.edu.ghost_hunt;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Bryan on 4/23/15.
 */
public class Alert  {


    protected int xCoord;
    protected int yCoord;
    protected Bitmap bitmap;
    protected int spriteWidth;
    protected int spriteHeight;
    protected Rect sourceRect;
    public Alert(int x, int y, Bitmap bitmap){
        xCoord = x;
        yCoord = y;
        this.bitmap=bitmap;
        spriteHeight = bitmap.getHeight();
        spriteWidth = bitmap.getWidth();

    }

    public int getY() {
        return yCoord;
    }

    public int getX() {
        return xCoord;
    }




        public void draw(Canvas canvas) {
            Rect destRect = new Rect(getX(), getY(), getX() + spriteWidth, getY() + spriteHeight);
            canvas.drawBitmap(bitmap, sourceRect, destRect, null);
        }







}
