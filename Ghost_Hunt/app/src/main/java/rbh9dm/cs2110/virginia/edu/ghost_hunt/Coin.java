package rbh9dm.cs2110.virginia.edu.ghost_hunt;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Student User on 4/22/2015.
 */
public class Coin {

    private long timeCreated;
    private Background background;
    private int xCoord;
    private int yCoord;
    private int centerX;
    private int centerY;
    private int spriteWidth;
    private int spriteHeight;
    private Bitmap bitmap;
    private Rect sourceRect; //Rectangle that tells where to cut a sprite from the spritesheet
    private int frameNr; //Number of sprites in the spritesheet
    private int currentFrame; //Keeps track of which sprite in a sequence we are currently cutting out
    private long frameTicker; //Time of the last update
    private int framePeriod; //How long between updates

    public Coin(Bitmap bitmap, int xCoord, int yCoord, int fps, int frameNr, long timeNow, Background b) {
        this.bitmap = bitmap;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        currentFrame = 0;
        this.frameNr = frameNr;
        spriteWidth = bitmap.getWidth()/frameNr;
        spriteHeight = bitmap.getHeight();
        sourceRect = new Rect(0,0,spriteWidth,spriteHeight);
        framePeriod = 1000/fps;
        frameTicker = 1;
        centerX = xCoord + spriteWidth/2;
        centerY = yCoord + spriteHeight/2;
        timeCreated = timeNow;
        background = b;
     }

    public long howLongOnScreen(long timeNow) {
        return timeNow - timeCreated;
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

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void update(long gameTime) {
        if (gameTime > frameTicker + framePeriod) {
            frameTicker = gameTime;
            if (currentFrame < frameNr-1)
                currentFrame++;
            else
                currentFrame = 0;
            sourceRect.left = currentFrame * spriteWidth;
            sourceRect.right = sourceRect.left + spriteWidth;
        }
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }

    public void draw(Canvas canvas) {
        Rect destRect = new Rect(xCoord, yCoord, xCoord + spriteWidth, yCoord + spriteHeight);
        canvas.drawBitmap(bitmap, sourceRect, destRect, null);
    }
}
