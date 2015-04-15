package rbh9dm.cs2110.virginia.edu.ghost_hunt;

import android.graphics.Bitmap;

/**
 * Created by Student User on 4/14/2015.
 */
public class Ghost extends Character  {

    private int speed;
    private double angle;
    private double xVelocity;
    private double yVelocity;
    private Background background;

    public Ghost (Bitmap bitmap, int x, int y, int fps, int frameNr, int numStill, int numUp, int numLeft, int numRight, int numDown, int speed, double angle, Background background) {
        super(bitmap, x, y, fps, frameNr, numStill, numUp, numLeft, numRight, numDown);
        this.speed = speed;
        this.angle = angle;
        this.xVelocity = this.speed * Math.cos(this.angle);
        this.yVelocity = this.speed * Math.sin(this.angle);
        this.background = background;
    }

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

    public void posYVelocity() {
        yVelocity = Math.abs(yVelocity);
    }

    public void posXVelocity() {
        xVelocity = Math.abs(xVelocity);
    }

    public void negYVelocity() {
        yVelocity = -Math.abs(yVelocity);
    }

    public void negXVelocity() {
        xVelocity = -Math.abs(xVelocity);
    }

    public void adjustSpeed(int direction) {
        if (direction == 4) {
            yVelocity = yVelocity - background.getSpeed();
         }
         else if (direction == 2) {
            xVelocity = xVelocity + background.getSpeed();
         }
         else if (direction == 3) {
             xVelocity = xVelocity - background.getSpeed();
         }
         else if (direction == 1) {
            yVelocity = yVelocity + background.getSpeed();
         }
    }

    public void reset(int direction) {
        if (direction == 4) {
            yVelocity = yVelocity + background.getSpeed();
        }
        else if (direction == 2) {
            xVelocity = xVelocity - background.getSpeed();
        }
        else if (direction == 3) {
            xVelocity = xVelocity + background.getSpeed();
        }
        else if (direction == 1) {
            yVelocity = yVelocity - background.getSpeed();
        }
    }

    public void update(long gameTime) {
        if (gameTime > frameTicker + framePeriod) {
            int direction = background.getDirection();
            if (xVelocity <= 0 && Math.abs(xVelocity) >= Math.abs(yVelocity)) {
                if (currentFrame > numLeft-1)
                    currentFrame = 0;
                this.sourceRect.left = (numStill+numDown+numUp+numRight)*spriteWidth + currentFrame * spriteWidth;
                this.sourceRect.right = this.sourceRect.left + spriteWidth;
                currentFrame++;
            }
            else if (xVelocity > 0 && Math.abs(xVelocity) >= Math.abs(yVelocity)) {
                if (currentFrame > numRight-1)
                    currentFrame = 0;
                this.sourceRect.left = (numStill+numDown+numUp)*spriteWidth + currentFrame * spriteWidth;
                this.sourceRect.right = this.sourceRect.left + spriteWidth;
                currentFrame++;
            }
            else if (yVelocity <= 0) {
                if (currentFrame > numUp-1)
                    currentFrame = 0;
                this.sourceRect.left = (numStill+numUp)*spriteWidth + currentFrame * spriteWidth;
                this.sourceRect.right = this.sourceRect.left + spriteWidth;
                currentFrame++;
            }
            else if (yVelocity > 0) {
                if (currentFrame > numDown - 1)
                    currentFrame = 0;
                this.sourceRect.left = (numStill)*spriteWidth + currentFrame * spriteWidth;
                this.sourceRect.right = this.sourceRect.left + spriteWidth;
                currentFrame++;
            }
            adjustSpeed(direction);
            x+=xVelocity;
            y+=yVelocity;
            reset(direction);

            if (x< background.getXCoord()+2) {
                posXVelocity();
            }
            else if (x+spriteWidth>background.getXCoord()+background.getWidth()-2) {
                negXVelocity();
            }
            if (y< background.getYCoord()+2) {
                posYVelocity();
            }
            else if (y+spriteHeight>background.getYCoord()+background.getHeight()-2) {
                negYVelocity();
            }
        }
    }
}
