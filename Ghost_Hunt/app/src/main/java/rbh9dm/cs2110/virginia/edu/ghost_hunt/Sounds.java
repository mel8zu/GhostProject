package rbh9dm.cs2110.virginia.edu.ghost_hunt;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.util.Random;

/**
 * Created by Student User on 4/22/2015.
 */
public class Sounds {

    private SoundPool soundPool;
    private Random rand;
    private int wakaID;

    Sounds(Context context) {
        rand = new Random();
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundPool.load(context, R.raw.shot, 1);
        soundPool.load(context, R.raw.scream1, 2);
        soundPool.load(context, R.raw.scream2, 3);
        soundPool.load(context, R.raw.scream3, 4);
        soundPool.load(context, R.raw.cash, 5);
        soundPool.load(context, R.raw.laser, 6);
        soundPool.load(context, R.raw.flashbang, 7);
        soundPool.load(context, R.raw.waka, 8);
        wakaID = 1;
    }

    public void playFlashbang() {
        soundPool.play(7,1,1,0,0,1);
    }

    public void playUgh() {
        soundPool.play(3,1,1,0,0,1);
    }

    public void playGun() {
        soundPool.play(1,1,1,0,0,1);
    }

    public void playLaser() {
        soundPool.play(6,1,1,0,0,1);
    }

    public void playScream() {
        int x = rand.nextInt(3) + 2;
        soundPool.play(x,1,1,0,0,1);
    }

    public void playCash() {
        soundPool.play(5,1,1,0,0,1);
    }

    public void playWaka(){
        wakaID = soundPool.play(8,1,1,0,-1,1);
    }
    public void stopWaka() {
        soundPool.stop(wakaID);
    }

    public void release() {
        soundPool.release();
    }

}
