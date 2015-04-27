package rbh9dm.cs2110.virginia.edu.ghost_hunt;

import android.graphics.Bitmap;
import android.graphics.Rect;


/**
 * Created by Student User on 4/14/2015.
 */
public class Ghost extends Character {

    private int speed;
    private double angle;
    private double xVelocity;
    private double yVelocity;
    private Background background;
    private boolean friendly;
    private long timeFriendly;

    public Ghost(Bitmap bitmap, int x, int y, int fps, int frameNr, int numStill, int numUp, int numLeft, int numRight, int numDown, int speed, double angle, Background background) {
        super(bitmap, x, y, fps, frameNr, numStill, numUp, numLeft, numRight, numDown);
        this.speed = speed;
        this.angle = angle;
        this.xVelocity = this.speed * Math.cos(this.angle);
        this.yVelocity = this.speed * Math.sin(this.angle);
        this.background = background;
        this.friendly = false;
        this.timeFriendly = 0;
    }

    /*
    Getters & Setter
     */
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    public double getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }

    public Background getBackground() {
        return background;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public boolean isFriendly() {
        return friendly;
    }

    public void setFriendly(boolean friendly) {
        this.friendly = friendly;
    }

    public long getTimeFriendly() {
        return timeFriendly;
    }

    public void setTimeFriendly(long timeFriendly) {
        this.timeFriendly = timeFriendly;
    }

    /*
                makes yVelocity positive
                 */
    public void posYVelocity() {
        yVelocity = Math.abs(yVelocity);
    }

    /*
    makes xVelocity positive
     */
    public void posXVelocity() {
        xVelocity = Math.abs(xVelocity);
    }
    /*
    makes yVelocity negative
    */
    public void negYVelocity() {
        yVelocity = -Math.abs(yVelocity);
    }
    /*
    makes xVelocity negative
    */
    public void negXVelocity() {
        xVelocity = -Math.abs(xVelocity);
    }

    /*
    Adjusts x and y velocities based on the direction the background is going
     */
    public void adjustSpeed(int direction) {
        if (direction == 4) {
            yVelocity = yVelocity - background.getSpeed();
        } else if (direction == 2) {
            xVelocity = xVelocity + background.getSpeed();
        } else if (direction == 3) {
            xVelocity = xVelocity - background.getSpeed();
        } else if (direction == 1) {
            yVelocity = yVelocity + background.getSpeed();
        }
    }

    /*
    Resets x and y velocities to what they are when the background is still
     */
    public void reset(int direction) {
        if (direction == 4) {
            yVelocity = yVelocity + background.getSpeed();
        } else if (direction == 2) {
            xVelocity = xVelocity - background.getSpeed();
        } else if (direction == 3) {
            xVelocity = xVelocity + background.getSpeed();
        } else if (direction == 1) {
            yVelocity = yVelocity - background.getSpeed();
        }
    }

    /*
    update method: moves the ghost, decides what image to display, changes the ghosts direction when it hits edge of background
     */
    public void update(long gameTime) {
        int direction = background.getDirection();
        if (gameTime > frameTicker + framePeriod) {
            frameTicker = gameTime;
            if (xVelocity <= 0 && Math.abs(xVelocity) >= Math.abs(yVelocity)) {
                if (currentFrame > numLeft - 1)
                    currentFrame = 0;
                this.sourceRect.left = (numStill + numDown + numUp + numRight) * spriteWidth + currentFrame * spriteWidth;
                this.sourceRect.right = this.sourceRect.left + spriteWidth;
                currentFrame++;
            } else if (xVelocity > 0 && Math.abs(xVelocity) >= Math.abs(yVelocity)) {
                if (currentFrame > numRight - 1)
                    currentFrame = 0;
                this.sourceRect.left = (numStill + numDown + numUp) * spriteWidth + currentFrame * spriteWidth;
                this.sourceRect.right = this.sourceRect.left + spriteWidth;
                currentFrame++;
            } else if (yVelocity <= 0) {
                if (currentFrame > numUp - 1)
                    currentFrame = 0;
                this.sourceRect.left = (numStill + numUp) * spriteWidth + currentFrame * spriteWidth;
                this.sourceRect.right = this.sourceRect.left + spriteWidth;
                currentFrame++;
            } else if (yVelocity > 0) {
                if (currentFrame > numDown - 1)
                    currentFrame = 0;
                this.sourceRect.left = (numStill) * spriteWidth + currentFrame * spriteWidth;
                this.sourceRect.right = this.sourceRect.left + spriteWidth;
                currentFrame++;
            }
        }
            adjustSpeed(direction);
            x += xVelocity;
            y += yVelocity;
            reset(direction);


            if (x < background.getXCoord() + 2) {
                posXVelocity();
            } else if (x + spriteWidth > background.getXCoord() + background.getWidth() - 2) {
                negXVelocity();
            }
            if (y < background.getYCoord() + 2) {
                posYVelocity();
            } else if (y + spriteHeight > background.getYCoord() + background.getHeight() - 2) {
                negYVelocity();
            }

    }

    public boolean isPosX(){
        if(xVelocity>0){
            return true;
        }
        return false;
    }
    public boolean isPosY(){
        if(yVelocity>0){
            return true;
        }
        return false;
    }

    public Rect getHitbox() {
        Rect destRect = new Rect(getX(), getY(), getX() + spriteWidth, getY() + spriteHeight);
        return destRect;
    }
}