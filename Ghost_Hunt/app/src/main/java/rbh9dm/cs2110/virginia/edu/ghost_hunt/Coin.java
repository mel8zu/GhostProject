package rbh9dm.cs2110.virginia.edu.ghost_hunt;

import android.graphics.Bitmap;

/**
 * Created by Student User on 4/22/2015.
 */
public class Coin extends Immovable {

    private long timeCreated;
    private Background background;

    public Coin(Bitmap bitmap, int xCoord, int yCoord, int width, int height, long timeNow, Background b) {
        super(bitmap, xCoord, yCoord, width, height);
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
}
