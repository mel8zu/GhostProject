package rbh9dm.cs2110.virginia.edu.ghost_hunt;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Iterator;

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
    private HealthBar health;
    private ArrayList<Bullet> bulletList;
    private ArrayList<Ghost> ghostList;
    private long lastShot;
    private int score;
    private Paint paint;
    private int screenWidth;
    private int screenHeight;
    /*
    Constructor: where the stuff that appears on screen is declared
     */
    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);

        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.pug), 0, 0, 5);
        character = new Character(BitmapFactory.decodeResource(getResources(), R.drawable.maincharacter), 300, 300, 3, 14,6,2,2,2,2);
        up = new DirectionButton(BitmapFactory.decodeResource(getResources(), R.drawable.arrow_u), 400, 950);
        left = new DirectionButton(BitmapFactory.decodeResource(getResources(), R.drawable.arrow_l), 200, 1150);
        right = new DirectionButton(BitmapFactory.decodeResource(getResources(), R.drawable.arrow_r), 600, 1150);
        down = new DirectionButton(BitmapFactory.decodeResource(getResources(), R.drawable.arrow_d), 400, 1350);
        health = new HealthBar(BitmapFactory.decodeResource(getResources(), R.drawable.health),BitmapFactory.decodeResource(getResources(), R.drawable.dead),10,10, character.getHealthLevel());
        bulletList = new ArrayList<Bullet>();
        ghostList = new ArrayList<Ghost>();
        initCreateGhosts();
        score = 0;
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(60);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

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
    public void initCreateGhosts() {
        Ghost ghost1 = new Ghost(BitmapFactory.decodeResource(getResources(), R.drawable.ghost), 100, 100, 3, 9, 1,2,2,2,2, 8, 0.6, background);
        ghostList.add(ghost1);
    }
    /*
    When the screen is touched, this method tells the game what to do
     */
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
                            if (System.currentTimeMillis() > lastShot + 1000) {
                                bulletList.add(new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.bullet), character, 10, event.getX(), event.getY()));
                                lastShot = System.currentTimeMillis();
                            }
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
    /*
    Where everything is updated. Called once per run through the game loop
     */
    public void update() {
        background.update();
        character.update(System.currentTimeMillis(),background.getDirection());
                Iterator<Bullet> iterator = bulletList.iterator();
        while(iterator.hasNext()) {
            Bullet b = iterator.next();
            int dir = background.getDirection();
            if (dir == 0) {
                b.update();
            }
            else if (dir == 1) {
                b.setyVelocity(b.getyVelocity() + background.getSpeed());
                b.update();
                b.setyVelocity(b.getyVelocity() - background.getSpeed());
            }
            else if (dir == 2) {
                b.setxVelocity(b.getxVelocity() + background.getSpeed());
                b.update();
                b.setxVelocity(b.getxVelocity() - background.getSpeed());
            }
            else if (dir == 3) {
                b.setxVelocity(b.getxVelocity() - background.getSpeed());
                b.update();
                b.setxVelocity(b.getxVelocity() + background.getSpeed());
            }
            else if (dir == 4) {
                b.setyVelocity(b.getyVelocity() + background.getSpeed());
                b.update();
                b.setyVelocity(b.getyVelocity() - background.getSpeed());
            }
            if(b.getDistance()>300)
                iterator.remove();
        }
        for(Ghost g : ghostList) {
            g.update(System.currentTimeMillis());
        }

    }
    /*
    Method that calls each thing's draw method so as to display each thing on the screen
    Only draws ghosts when they are on the screen
     */
    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        background.draw(canvas);
        character.draw(canvas);
        up.draw(canvas);
        left.draw(canvas);
        down.draw(canvas);
        right.draw(canvas);
        for(Bullet b : bulletList) {
            b.draw(canvas);
        }
        for(Ghost g : ghostList) {
            if (g.getX()<=screenWidth || g.getY()<=screenHeight || g.getX() >= -g.getSpriteWidth() || g.getY() >=-g.getSpriteHeight())
                g.draw(canvas);
        }
        health.draw(canvas);
        canvas.drawText(("00000000"+score).substring((""+score).length()),750, 50, paint);
    }
}
