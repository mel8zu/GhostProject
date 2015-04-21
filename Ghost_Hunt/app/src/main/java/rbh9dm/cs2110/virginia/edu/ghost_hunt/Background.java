package rbh9dm.cs2110.virginia.edu.ghost_hunt;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by Student User on 4/7/2015.
 */
public class Background extends Moveable {

    private int speed;
    private int direction;
    private boolean isOnScreen;

    public Background(Bitmap bitmap, int xCoord, int yCoord,int speed) {
        super(bitmap, xCoord, yCoord);
        this.speed = speed;
        this.direction = 0;
        isOnScreen = true;
    }



    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return this.direction;
    }

    public boolean checkOnScreen(int xCoord, int yCoord) {
        return true;
    }

    public void update () {
        Log.i("Hi","direction is:"+direction);
        if (direction == 1)
            this.setYCoord(this.getYCoord() + speed);
        else if (direction == 2)
            this.setXCoord(this.getXCoord()+speed);
        else if (direction == 3)
            this.setXCoord(this.getXCoord()-speed);
        else if (direction == 4)
            this.setYCoord(this.getYCoord()-speed);
        else {}
    }


}
