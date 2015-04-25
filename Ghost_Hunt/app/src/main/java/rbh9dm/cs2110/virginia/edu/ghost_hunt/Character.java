package rbh9dm.cs2110.virginia.edu.ghost_hunt;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Student User on 4/8/2015.
 */
public class Character {
    protected int x; //x location
    protected int y; //y location
    protected Bitmap bitmap; //holds the spritesheet
    protected Rect sourceRect; //Rectangle that tells where to cut a sprite from the spritesheet
    protected int frameNr; //Number of sprites in the spritesheet
    protected int currentFrame; //Keeps track of which sprite in a sequence we are currently cutting out
    protected long frameTicker; //Time of the last update
    protected int framePeriod; //How long between updates
    protected int spriteWidth; //width of a sprite
    protected int spriteHeight; //height of a sprite
    protected int numStill;
    protected int numUp;
    protected int numLeft;
    protected int numRight;
    protected int numDown;
    protected int healthLevel;

    /*
    Constructor
     */
    public Character(Bitmap bitmap, int x, int y, int fps, int frameNr, int numStill, int numUp, int numLeft, int numRight, int numDown) {
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
        this.numStill = numStill;
        this.numUp = numUp;
        this.numLeft = numLeft;
        this.numRight = numRight;
        this.numDown = numDown;
        this.healthLevel = 100;

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

    public int getHealthLevel() {
        return healthLevel;
    }

    public void setHealthLevel(int healthLevel) {
        this.healthLevel = healthLevel;

    }

    public int getNumStill() {
        return numStill;
    }

    public void setNumStill(int numStill) {
        this.numStill = numStill;
    }

    public int getNumUp() {
        return numUp;
    }

    public void setNumUp(int numUp) {
        this.numUp = numUp;
    }

    public int getNumLeft() {
        return numLeft;
    }

    public void setNumLeft(int numLeft) {
        this.numLeft = numLeft;
    }

    public int getNumRight() {
        return numRight;
    }

    public void setNumRight(int numRight) {
        this.numRight = numRight;
    }

    public int getNumDown() {
        return numDown;
    }

    public void setNumDown(int numDown) {
        this.numDown = numDown;
    }

    /*
                        Updates character once per frame period
                         Takes a direction (0=still,1=up,2=left,3=right,4=down) to determine which sprite to print
                        */
    public void update(long gameTime, int direction) {
        if (gameTime > frameTicker + framePeriod) {
            frameTicker = gameTime;
            if (direction == 4) {
                if (currentFrame > numDown - 1)
                    currentFrame = 0;
                this.sourceRect.left = (numStill)*spriteWidth + currentFrame * spriteWidth;
                this.sourceRect.right = this.sourceRect.left + spriteWidth;
                currentFrame++;
            }
            else if (direction == 2) {
                if (currentFrame > numLeft - 1)
                    currentFrame = 0;
                this.sourceRect.left = (numStill + numUp + numDown + numRight)*spriteWidth + currentFrame * spriteWidth;
                this.sourceRect.right = this.sourceRect.left + spriteWidth;
                currentFrame++;
            }
            else if (direction == 3) {
                if (currentFrame > numRight - 1)
                    currentFrame = 0;
                this.sourceRect.left = (numStill + numDown + numUp)*spriteWidth + currentFrame * spriteWidth;
                this.sourceRect.right = this.sourceRect.left + spriteWidth;
                currentFrame++;
            }
            else if (direction == 1) {
                if (currentFrame > numUp - 1)
                    currentFrame = 0;
                this.sourceRect.left = (numStill + numDown)*spriteWidth + currentFrame * spriteWidth;
                this.sourceRect.right = this.sourceRect.left + spriteWidth;
                currentFrame++;
            }
            else {
                if (currentFrame > numStill - 1)
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

    public Rect getHitbox() {
        Rect destRect = new Rect(getX(), getY(), getX() + spriteWidth, getY() + spriteHeight);
        return destRect;
    }


}
