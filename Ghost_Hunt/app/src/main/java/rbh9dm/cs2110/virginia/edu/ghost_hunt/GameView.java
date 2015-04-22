package rbh9dm.cs2110.virginia.edu.ghost_hunt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

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
    private long score;
    private long prevTime;
    private int displayScore;
    private Paint paint;
    private int screenWidth;
    private int screenHeight;
    private Context context;
    private boolean gameOver;
    private boolean scoreChanged;
    private int level;
    private int count;
    private Play play;

    /*
    Constructor: where the stuff that appears on screen is declared
     */
    public GameView(Context context, int level) {
        super(context);
        this.context = context;
        this.level = level;
        count = 0;
        getHolder().addCallback(this);

        score = 0;
        displayScore = 0;
        prevTime = System.currentTimeMillis();
        character = new Character(BitmapFactory.decodeResource(getResources(), R.drawable.maincharacter), 300, 300, 3, 14,6,2,2,2,2);
        if (level == 3) background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.desert3), 0, 0, 5);
        else if (level == 2) background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.winter2), 0, 0, 5);
        else  background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.forest), 0, 0, 5);
        character = new Character(BitmapFactory.decodeResource(getResources(), R.drawable.maincharacter), 300, 300, 3, 14,6,2,2,2,2);

        bulletList = new ArrayList<Bullet>();
        ghostList = new ArrayList<Ghost>();
        initCreateGhosts();

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(60);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        Bitmap upMap = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_u);
        Bitmap leftMap = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_l);
        Bitmap rightMap = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_r);
        Bitmap downMap = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_d);

        down = new DirectionButton(downMap , (int) (screenWidth/2 - 0.5*downMap.getWidth()), screenHeight - downMap.getHeight() - 25);
        up = new DirectionButton(upMap , (int) (screenWidth/2 - 0.5*upMap.getWidth()), down.getY() - downMap.getHeight() - 25);
        left = new DirectionButton(leftMap , down.getX() - leftMap.getWidth() - 10, screenHeight - leftMap.getHeight() - 25);
        right = new DirectionButton(rightMap , down.getX() + downMap.getWidth() + 10, screenHeight - rightMap.getHeight() - 25);
        health = new HealthBar(BitmapFactory.decodeResource(getResources(), R.drawable.health),BitmapFactory.decodeResource(getResources(), R.drawable.dead),10,10, character.getHealthLevel());

        gameOver = false;
        scoreChanged = false;
        play = (Play) context;

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
        while (count < 6) {
            Random rand = new Random();
            int x = rand.nextInt(2000);
            int y = rand.nextInt(2000);
            if(Math.sqrt(Math.pow(x-character.getX(),2) + Math.pow(y-character.getY(),2)) < 80) {
                x = rand.nextInt(2000);
                y = rand.nextInt(2000);
            }
            int speed = rand.nextInt(5) + 3;
            double angle = rand.nextDouble();
            Ghost ghost1 = new Ghost(BitmapFactory.decodeResource(getResources(), R.drawable.ghost), x, y, 3, 9, 1, 2, 2, 2, 2, speed, angle, background);
            ghostList.add(ghost1);
            count+=1;
        }
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
                        } else {
                            background.setDirection(0);
                            if (System.currentTimeMillis() > lastShot + 1000) {
                                bulletList.add(new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.bullet), character, 10, event.getX(), event.getY()));
                                lastShot = System.currentTimeMillis();
                            }
                        }
                    }
                }
            }

        } else if (event.getAction() == MotionEvent.ACTION_UP) {
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
            if(b.getDistance()>550)
                iterator.remove();
        }
        for(Ghost g : ghostList) {
            g.update(System.currentTimeMillis());
        }

        score = score + (-prevTime + System.currentTimeMillis());
        displayScore = (int) (score/1000) * 100;
        prevTime = System.currentTimeMillis();
        if ((displayScore / 400)*level  > count) {
            Random rand = new Random();
            int width = background.getWidth();
            int bx = background.getXCoord();
            int height = background.getHeight();
            int by = background.getYCoord();
            int x = rand.nextInt(width) + bx;
            int y = rand.nextInt(height) + by;
            int speed = rand.nextInt(5) + 5;
            double angle = rand.nextDouble();
            Ghost ghost1 = new Ghost(BitmapFactory.decodeResource(getResources(), R.drawable.ghost), x, y, 3, 9, 1, 2, 2, 2, 2, speed, angle, background);
            ghostList.add(ghost1);
            count +=1;
        }
        int index = getClosestGhost();
        if (Math.sqrt(Math.pow(ghostList.get(index).getX()-character.getX(),2) + Math.pow(ghostList.get(index).getY()-character.getY(),2)) < 225) {
            health.addDamage(1);
        }
        index = getClosestGhost();
        if (character.getHitbox().intersect(ghostList.get(index).getHitbox())) {
            System.exit(0);
        }
        while(ghostDown() == true) {
            ghostDown();
        }
    }


    public int getClosestGhost() {
        int index = 0;
        for (int i = 0; i < ghostList.size(); i++) {
            double min = 100000;
            double dist = Math.sqrt(Math.pow(ghostList.get(i).getX()-character.getX(),2) + Math.pow(ghostList.get(i).getY() - character.getY(), 2));
            if(dist < min) {
               min = dist;
               index = i;
            }
        }
        return index;
    }

    public boolean ghostDown() {
        for (int i = 0; i < ghostList.size(); i++) {
            for (int j = 0; j < bulletList.size(); j++) {
                Bullet f = bulletList.get(j);
                Rect e = new Rect(f.getXCoord(),f.getYCoord(),f.getXCoord() + f.getWidth(), f.getYCoord() + f.getHeight());
                if (ghostList.get(i).getHitbox().intersect(e)) {
                    ghostList.remove(i);
                    bulletList.remove(j);
                    return true;
                }
            }
        }
        return false;

    }
    /*
    Method that calls each thing's draw method so as to display each thing on the screen
    Only draws ghosts when they are on the screen
     */
    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        if(gameOver && !scoreChanged) {
            play.updateHighScore(("0000000000"+score).substring((""+score).length()));
            scoreChanged = true;
        }
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
        canvas.drawText(("0000000000"+displayScore).substring((""+displayScore).length()),750, 50, paint);
    }
}
