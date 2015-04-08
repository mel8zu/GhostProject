package rbh9dm.cs2110.virginia.edu.ghost_hunt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

/**
 * Created by Student User on 4/7/2015.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread thread;
    private Background background;
    private Character character;
    private DirectionButton up;
    private DirectionButton left;
    private DirectionButton right;
    private DirectionButton down;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);

        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.pug), 0, 0, 5);
        character = new Character(BitmapFactory.decodeResource(getResources(), R.drawable.androidguy), 300, 300);
        up = new DirectionButton(BitmapFactory.decodeResource(getResources(), R.drawable.button), 400, 800);
        left = new DirectionButton(BitmapFactory.decodeResource(getResources(), R.drawable.button), 200, 1000);
        right = new DirectionButton(BitmapFactory.decodeResource(getResources(), R.drawable.button), 600, 1000);
        down = new DirectionButton(BitmapFactory.decodeResource(getResources(), R.drawable.button), 400, 1200);

        thread = new GameThread(getHolder(), this);

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry) {
            try {
                thread.join();
                retry = false;
            } catch(InterruptedException e) {

            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            up.handleActionDown((int) event.getX(), (int) event.getY());
            if (up.isTouched()) {
                background.setDirection(1);
            } else {
                left.handleActionDown((int) event.getX(), (int) event.getY());
                if (left.isTouched()) {
                    background.setDirection(2);
                } else {
                    right.handleActionDown((int) event.getX(), (int) event.getY());
                    if (right.isTouched()) {
                        background.setDirection(3);
                    } else {
                        down.handleActionDown((int) event.getX(), (int) event.getY());
                        if (down.isTouched()) {
                            background.setDirection(4);
                        }
                        else {
                            background.setDirection(0);
                        }
                    }
                }
            }
        }
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            background.setDirection(0);
        }
        return true;
    }

    public void update() {
        background.update();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        background.draw(canvas);
        character.draw(canvas);
        up.draw(canvas);
        left.draw(canvas);
        down.draw(canvas);
        right.draw(canvas);
    }



}
