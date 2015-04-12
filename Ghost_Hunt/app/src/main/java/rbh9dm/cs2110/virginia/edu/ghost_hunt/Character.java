package rbh9dm.cs2110.virginia.edu.ghost_hunt;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Student User on 4/8/2015.
 */
public class Character {
    private int x; //x location
    private int y; //y location
    private Bitmap bitmap; //holds the spritesheet
    private Rect sourceRect; //Rectangle that tells where to cut a sprite from the spritesheet
    private int frameNr; //Number of sprites in the spritesheet
    private int currentFrame; //Keeps track of which sprite in a sequence we are currently cutting out
    private long frameTicker; //Time of the ;ast update
    private int framePeriod; //How long between updates
    private int spriteWidth; //width of a sprite
    private int spriteHeight; //height of a sprite

    /*
    Constructor
     */
    public Character(Bitmap bitmap, int x, int y, int fps, int frameNr) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        currentFrame = 0;
        this.frameNr = frameNr;
        spriteWidth = bitmap.getWidth()/frameNr;
        spriteHeight = bitmap.getHeight();
        sourceRect = new Rect(0,0,spriteWidth,spriteHeight);
        framePeriod = 1000/fps;
        frameTicker = 1;
    }

    /*Getters and setters*/
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

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Rect getSourceRect() {
        return sourceRect;
    }

    public void setSourceRect(Rect sourceRect) {
        this.sourceRect = sourceRect;
    }

    public int getFrameNr() {
        return frameNr;
    }

    public void setFrameNr(int frameNr) {
        this.frameNr = frameNr;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public long getFrameTicker() {
        return frameTicker;
    }

    public void setFrameTicker(long frameTicker) {
        this.frameTicker = frameTicker;
    }

    public int getFramePeriod() {
        return framePeriod;
    }

    public void setFramePeriod(int framePeriod) {
        this.framePeriod = framePeriod;
    }

    public int getSpriteWidth() {
        return spriteWidth;
    }

    public void setSpriteWidth(int spriteWidth) {
        this.spriteWidth = spriteWidth;
    }

    public int getSpriteHeight() {
        return spriteHeight;
    }

    public void setSpriteHeight(int spriteHeight) {
        this.spriteHeight = spriteHeight;
    }
    /*
    Updates character once per frame period
     Takes a direction (0=still,1=up,2=left,3=right,4=down) to determine which sprite to print
    */
    public void update(long gameTime, int direction) {
        if (gameTime > frameTicker + framePeriod) {
            if (direction == 4) {
                if (currentFrame > 1)
                    currentFrame = 0;
                this.sourceRect.left = 6*spriteWidth + currentFrame * spriteWidth;
                this.sourceRect.right = this.sourceRect.left + spriteWidth;
                currentFrame++;
            }
            else if (direction == 2) {
                if (currentFrame > 1)
                    currentFrame = 0;
                this.sourceRect.left = 12*spriteWidth + currentFrame * spriteWidth;
                this.sourceRect.right = this.sourceRect.left + spriteWidth;
                currentFrame++;
            }
            else if (direction == 3) {
                if (currentFrame > 1)
                    currentFrame = 0;
                this.sourceRect.left = 10*spriteWidth + currentFrame * spriteWidth;
                this.sourceRect.right = this.sourceRect.left + spriteWidth;
                currentFrame++;
            }
            else if (direction == 1) {
                if (currentFrame > 1)
                    currentFrame = 0;
                this.sourceRect.left = 8*spriteWidth + currentFrame * spriteWidth;
                this.sourceRect.right = this.sourceRect.left + spriteWidth;
                currentFrame++;
            }
            else {
                if (currentFrame > 5)
                    currentFrame = 0;
                this.sourceRect.left = 0*spriteWidth + currentFrame * spriteWidth;
                this.sourceRect.right = this.sourceRect.left + spriteWidth;
                currentFrame++;
            }
        }
    }
    /*
    draws the sprite to the screen
    sourceRect tells which which image to cut out of the sprite sheet
    destRect tells where to print the image on the screen
     */
    public void draw(Canvas canvas) {
        Rect destRect = new Rect(getX(), getY(), getX() + spriteWidth, getY() + spriteHeight);
        canvas.drawBitmap(bitmap, sourceRect, destRect, null);
    }

}
