package rbh9dm.cs2110.virginia.edu.ghost_hunt;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Bryan on 4/26/15.
 */
public class Objects {


    private Background background;
    private int xCoord;
    private int yCoord;
    private int centerX;
    private int centerY;
    private int spriteWidth;
    private int spriteHeight;
    private Bitmap bitmap;
    protected Rect sourceRect;

    private int frameNr; //Number of sprites in the spritesheet
    private int currentFrame; //Keeps track of which sprite in a sequence we are currently cutting out
    private long frameTicker; //Time of the last update
    private int framePeriod; //How long between updates

    public Objects(Bitmap bitmap, int xCoord, int yCoord, Background b) {
        this.bitmap = bitmap;
        this.xCoord = xCoord;
        this.yCoord = yCoord;


        spriteWidth = bitmap.getWidth();
        spriteHeight = bitmap.getHeight();



        centerX = xCoord + spriteWidth/2;
        centerY = yCoord + spriteHeight/2;

        background = b;


    }

    public void adjustForBackgroundMove() {
        int change = background.getSpeed();
        int direction = background.getDirection();
        if (direction == 1) {
            this.setyCoord(this.getyCoord() + change);
        }
        else if (direction == 2) {
            this.setxCoord(this.getxCoord() + change);
        }
        else if (direction == 3) {
            this.setxCoord(this.getxCoord() - change);
        }
        else if (direction == 4) {
            this.setyCoord(this.getyCoord() - change);
        }
    }

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
        this.centerX = xCoord + spriteWidth/2;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
        this.centerY = yCoord + spriteHeight/2;
    }

    public int getWidth() {
        return spriteWidth;
    }

    public void setWidth(int width) {
        this.spriteWidth = width;
        this.centerX = xCoord + width/2;
    }

    public int getHeight() {
        return spriteHeight;
    }

    public void setHeight(int height) {
        this.spriteHeight = height;
        this.centerY = yCoord + height/2;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void draw(Canvas canvas) {
        Rect destRect = new Rect(xCoord, yCoord, xCoord + spriteWidth, yCoord + spriteHeight);
        canvas.drawBitmap(bitmap, sourceRect, destRect, null);
    }

}

