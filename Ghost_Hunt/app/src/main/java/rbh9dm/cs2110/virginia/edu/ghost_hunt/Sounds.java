package rbh9dm.cs2110.virginia.edu.ghost_hunt;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.Random;

/**
 * Created by Student User on 4/22/2015.
 */
public class Sounds {
    private MediaPlayer gun;
    private MediaPlayer scream1;
    private MediaPlayer scream2;
    private MediaPlayer scream3;
    private MediaPlayer coinCollect;
    private Random rand;

    Sounds(Context context) {
        rand = new Random();
        gun = MediaPlayer.create(context, R.raw.shot);
        scream1 = MediaPlayer.create(context, R.raw.scream1);
        scream2 = MediaPlayer.create(context, R.raw.scream2);
        scream3 = MediaPlayer.create(context, R.raw.scream3);
        coinCollect = MediaPlayer.create(context, R.raw.cash);
    }

    public void playGun() {
        gun.start();
    }

    public void playScream() {
        int x = rand.nextInt(3) + 1;
        if (x == 1)
            scream1.start();
        else if (x==2)
            scream2.start();
        else
            scream3.start();
    }

    public void playCash() {
        coinCollect.start();
    }

}
