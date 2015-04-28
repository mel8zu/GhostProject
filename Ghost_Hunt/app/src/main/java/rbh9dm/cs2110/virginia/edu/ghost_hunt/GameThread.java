package rbh9dm.cs2110.virginia.edu.ghost_hunt;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Student User on 4/7/2015.
 */
public class GameThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;

    private final static int MAX_FPS = 50;
    private final static int MAX_FRAME_SKIPS = 5;
    private final static int FRAME_PERIOD = 1000/MAX_FPS;

    public GameThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        Canvas canvas;

        long beginTime;
        long timeDiff;
        int sleepTime;
        int framesSkipped;

        while(running) {
            canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    beginTime = System.currentTimeMillis();
                    framesSkipped = 0;
                    this.gameView.update();
                    if (!gameView.getGameOver()) {
                        this.gameView.draw(canvas);
                    }
                    timeDiff = System.currentTimeMillis() - beginTime;
                    sleepTime = (int) (FRAME_PERIOD - timeDiff);
                    if (sleepTime > 0) {
                        try {
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {}
                    }

                    while(sleepTime<0 && framesSkipped<MAX_FRAME_SKIPS) {
                        this.gameView.update();
                        sleepTime+=FRAME_PERIOD;
                        framesSkipped++;
                    }
                }
            }finally {
                if (canvas!=null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
