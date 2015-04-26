package rbh9dm.cs2110.virginia.edu.ghost_hunt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.Image;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.ImageView;

import java.lang.reflect.Array;
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
    private long displayScore;
    private int killedGhosts;
    private Paint paint;
    private int screenWidth;
    private int screenHeight;
    private boolean gameOver;
    private int difficulty;
    private int level;
    private Play play;
    private boolean setScore;
    private ArrayList<Coin> coinList;
    private ArrayList<Objects> objectlist;
    private Sounds sound;
    private Bitmap ghostBit;
    private Bitmap coinBit;
    private Bitmap objectBit;
    private Bitmap bulletBit;
    private ArrayList<Barriers> barrierList;
    private ArrayList<Alert> alertList;
    private Alert alert;

    /*
    Constructor: where the stuff that appears on screen is declared
     */
    public GameView(Context context, int level, int difficulty) {
        super(context);
        this.difficulty = difficulty;
        this.level = level;
        getHolder().addCallback(this);

        sound = new Sounds(context);

        score = 0;
        displayScore = 0;
        prevTime = System.currentTimeMillis();
        killedGhosts = 0;

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(60);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        ghostBit = BitmapFactory.decodeResource(getResources(), R.drawable.ghost);
        coinBit = BitmapFactory.decodeResource(getResources(), R.drawable.spinning_coin_gold);
        objectBit = BitmapFactory.decodeResource(getResources(), R.drawable.gemresized);
        bulletBit = BitmapFactory.decodeResource(getResources(), R.drawable.bullet);
        Bitmap characterBit = BitmapFactory.decodeResource(getResources(), R.drawable.maincharacter);
        character = new Character(characterBit, 0, 30, 3, 14,6,2,2,2,2);
        character.setX(screenWidth/2 - character.getSpriteWidth()/2);
        if (level == 3) background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.desert3), 0, 0, 5);
        else if (level == 2) background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.winter2), 0, 0, 5);
        else  background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.forest), 0, 0, 5);

        alertList = new ArrayList<Alert>();
       // alertList.add(alert);
        alert = new Alert(710,10,BitmapFactory.decodeResource(getResources(), R.drawable.alerti));
        bulletList = new ArrayList<Bullet>();
        ghostList = new ArrayList<Ghost>();
        coinList = new ArrayList<Coin>();
        Objects gem1 = new Objects(BitmapFactory.decodeResource(getResources(), R.drawable.gemresized), 400, 400, background);

        objectlist = new ArrayList<Objects>();

        barrierList = new ArrayList<Barriers>();
        initCreateGems();
        initCreateGhosts();
        if(level == 1){
            initCreateBarriersRock();
        }
        else if(level ==2){
            initCreateBarriersIceberg();
        }
        else{
            initCreateBarriersCactus();
        }

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
        setScore = false;
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
        while (ghostList.size() < 6) {
            Random rand = new Random();
            int x = rand.nextInt(2000);
            int y = rand.nextInt(2000);
            if(Math.sqrt(Math.pow(x-character.getX(),2) + Math.pow(y-character.getY(),2)) < 80) {
                x = rand.nextInt(2000);
                y = rand.nextInt(2000);
            }
            int speed = rand.nextInt(5) + 3;
            double angle = rand.nextDouble()*6;
            Ghost ghost1 = new Ghost(ghostBit, x, y, 3, 9, 1, 2, 2, 2, 2, speed, angle, background);
            ghostList.add(ghost1);
        }
    }

    public void initCreateBarriersCactus(){
        Random generator = new Random();
        int max = 20;
        int bX = background.getXCoord();
        int bY = background.getYCoord();
        for(int i = 0; i < 8; i++){

            Barriers cactus =  new Barriers(BitmapFactory.decodeResource(getResources(), R.drawable.cactusresized), generator.nextInt(bX + background.getWidth())-60, generator.nextInt(bY + background.getHeight()) + bY-60,5);
            if(character.getHitbox().intersects(cactus.getXCoord(), cactus.getYCoord(), cactus.getXCoord()+cactus.getWidth(), cactus.getYCoord()+cactus.getHeight())==false);
            barrierList.add(cactus);
        }

    }

    public void initCreateGems(){
        Random generator = new Random();
        int max = 20;
        int bX = background.getXCoord();
        int bY = background.getYCoord();
        for(int i = 0; i < 8; i++){

            Objects gem =  new Objects(BitmapFactory.decodeResource(getResources(), R.drawable.gemresized), generator.nextInt(bX + background.getWidth())-80, generator.nextInt(bY + background.getHeight()) + bY-80,background);
            if(character.getHitbox().intersects(gem.getxCoord(), gem.getyCoord(), gem.getxCoord()+gem.getWidth(), gem.getyCoord()+gem.getHeight())==false);

            objectlist.add(gem);
        }

    }

    public void initCreateBarriersIceberg(){
        Random generator = new Random();
        int max = 20;
        int bX = background.getXCoord();
        int bY = background.getYCoord();
        for(int i = 0; i < 8; i++){

            Barriers iceberg =  new Barriers(BitmapFactory.decodeResource(getResources(), R.drawable.iceresized), generator.nextInt(bX + background.getWidth())-60, generator.nextInt(bY + background.getHeight()) + bY-60,5);
            if(character.getHitbox().intersects(iceberg.getXCoord(), iceberg.getYCoord(), iceberg.getXCoord()+iceberg.getWidth(), iceberg.getYCoord()+iceberg.getHeight())==false);
            barrierList.add(iceberg);
        }

    }

    public void initCreateBarriersRock(){
        Random generator = new Random();
        int max = 20;
        int bX = background.getXCoord();
        int bY = background.getYCoord();
        for(int i = 0; i < 8; i++){

            Barriers rock =  new Barriers(BitmapFactory.decodeResource(getResources(), R.drawable.smallstone), generator.nextInt(bX + background.getWidth())-60, generator.nextInt(bY + background.getHeight()) + bY-60,5);
            if(character.getHitbox().intersects(rock.getXCoord(), rock.getYCoord(), rock.getXCoord() + rock.getWidth(), rock.getYCoord() + rock.getHeight())==false);
            barrierList.add(rock);
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
                for(Barriers element : barrierList){
                    element.setDirection(1);
                }

            } else {
                left.handleActionDown((int) event.getX(), (int) event.getY());
                if (left.isTouched()) {
                    background.setDirection(2);
                    for(Barriers element : barrierList){
                        element.setDirection(2);
                    }

                } else {
                    right.handleActionDown((int) event.getX(), (int) event.getY());
                    if (right.isTouched()) {
                        background.setDirection(3);
                        for(Barriers element : barrierList){
                            element.setDirection(3);
                        }

                    } else {
                        down.handleActionDown((int) event.getX(), (int) event.getY());
                        if (down.isTouched()) {
                            background.setDirection(4);
                            for(Barriers element : barrierList){
                                element.setDirection(4);
                            }

                        } else {
                            background.setDirection(0);
                            for(Barriers element : barrierList){
                                element.setDirection(0);
                            }

                            if (System.currentTimeMillis() > lastShot + 1000) {
                                bulletList.add(new Bullet(bulletBit, character, 13, event.getX(), event.getY()));
                                sound.playGun();
                                lastShot = System.currentTimeMillis();
                            }
                        }
                    }
                }
            }

        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            background.setDirection(0);
            for(Barriers element : barrierList){
                element.setDirection(0);
            }

        }

        return true;
    }


    /*
    Where everything is updated. Called once per run through the game loop
     */
    boolean interesects = false;
    public void update() {
        if(gameOver && !setScore) {
            thread.setRunning(false);
            setScore = true;
            sound.release();
            play.updateHighScore(("0000000000"+displayScore).substring((""+displayScore).length()));
        }
        for (Barriers element : barrierList) {
            if ((character.getHitbox2().intersects(element.getXCoord(), element.getYCoord(), element.getXCoord() + element.getWidth(), element.getYCoord() + element.getHeight()) == true)) {

                interesects = true;


            /* initialize app */

            }
        }

        if (interesects == true) {
            int currentDirection = background.getDirection();
            background.setDirection(0);
            for (Barriers element : barrierList) {
                element.setDirection(0);
            }
            interesects = false;

            if (currentDirection == 1) {

                background.setYCoord(background.getYCoord() - 5);
                for (Barriers element : barrierList) {
                    element.setYCoord(element.getYCoord() - 5);
                }
            } else if (currentDirection == 4) {
                background.setYCoord(background.getYCoord() + 5);
                for (Barriers element : barrierList) {
                    element.setYCoord(element.getYCoord() + 5);
                }
            } else if (currentDirection == 2) {
                background.setXCoord(background.getXCoord() - 5);
                for (Barriers element : barrierList) {
                    element.setXCoord(element.getXCoord() - 5);
                }
            } else if (currentDirection == 3) {
                background.setXCoord(background.getXCoord() + 5);
                for (Barriers element : barrierList) {
                    element.setXCoord(element.getXCoord() + 5);
                }
            }

        }

        background.update();
        for (Barriers element : barrierList) {
            element.update();
        }
        character.update(System.currentTimeMillis(),background.getDirection());
        for(Coin c : coinList) {
            c.update(System.currentTimeMillis());
        }
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
                b.setyVelocity(b.getyVelocity() - background.getSpeed());
                b.update();
                b.setyVelocity(b.getyVelocity() + background.getSpeed());
            }
            if(b.getDistance()>550)
                iterator.remove();
        }
        for(Ghost g : ghostList) {
            g.update(System.currentTimeMillis());
        }
        displayScore();
        createGhostMap();
        manageHealth();
        manageCoins();
        manageObjects();
        while (ghostDown()) {
            ghostDown();
        }

    }

    public void manageObjects() {
        Iterator iterate = objectlist.iterator();
        while (iterate.hasNext()) {
            Objects c = (Objects) iterate.next();
            c.adjustForBackgroundMove();
            double distanceX = (character.getX() + character.getSpriteWidth()/2) - c.getCenterX();
            double distanceY = (character.getY() + character.getSpriteHeight()/2) - c.getCenterY();
            double distance = Math.sqrt(Math.pow(distanceX,2)+Math.pow(distanceY,2));
            if (distance < 0.75*character.getSpriteWidth()) {
                //sound.playGemBoost();
                iterate.remove();
                score+=7500;
            }

        }
    }
    public void manageCoins() {
        Iterator iterate = coinList.iterator();
        while (iterate.hasNext()) {
            Coin c = (Coin) iterate.next();
            c.adjustForBackgroundMove();
            double distanceX = (character.getX() + character.getSpriteWidth()/2) - c.getCenterX();
            double distanceY = (character.getY() + character.getSpriteHeight()/2) - c.getCenterY();
            double distance = Math.sqrt(Math.pow(distanceX,2)+Math.pow(distanceY,2));
            if (distance < 0.75*character.getSpriteWidth()) {
                sound.playGemBoost();
                iterate.remove();
                score+=2500;
            }
            else if (c.howLongOnScreen(System.currentTimeMillis()) > 8000) {
                iterate.remove();
            }
        }
    }


    public int getClosestGhost() {
        int index = 0;
        double distanceX = ghostList.get(index).getX()+0.5*ghostList.get(index).getSpriteWidth()-(character.getX()+0.5*character.getSpriteWidth());
        double distanceY = ghostList.get(index).getY()+0.5*ghostList.get(index).getSpriteHeight()-(character.getY()+0.5*character.getSpriteHeight());
        Double min = Math.sqrt(Math.pow(distanceX,2) + Math.pow(distanceY, 2));
        for (int i = 1; i < ghostList.size(); i++) {
            distanceX = ghostList.get(i).getX()+0.5*ghostList.get(index).getSpriteWidth()-(character.getX()+0.5*character.getSpriteWidth());
            distanceY = ghostList.get(i).getY()+0.5*ghostList.get(index).getSpriteHeight()-(character.getY()+0.5*character.getSpriteHeight());
            Double dist = Math.sqrt(Math.pow(distanceX,2) + Math.pow(distanceY, 2));
            if(dist.compareTo(min)<0) {
               min = dist;
               index = i;
            }
        }
        return index;
    }

    public void createGhostMap() {
        if ((displayScore / 400)*level  > ghostList.size() + killedGhosts*.568 && ghostList.size()<=(10 + 3 * difficulty)) {
            Random rand = new Random();
            int width = background.getWidth();
            int bx = background.getXCoord();
            int height = background.getHeight();
            int by = background.getYCoord();
            int x = rand.nextInt(width) + bx;
            int y = rand.nextInt(height) + by;
            while(Math.sqrt(Math.pow(x-character.getX(),2) + Math.pow(y-character.getY(),2))< 300) {
                x = rand.nextInt(width) + bx;
                y = rand.nextInt(height) + by;
            }
            int speed = rand.nextInt(5) + 2*difficulty;
            double angle = rand.nextDouble()*6;
            Ghost ghost1 = new Ghost(ghostBit, x, y, 3, 9, 1, 2, 2, 2, 2, speed, angle, background);
            ghostList.add(ghost1);
        }
    }

    public void manageHealth() {
        if(alertList.contains(alert)) {
            alertList.remove(alert);
        }
        if (ghostList.size() != 0) {
            int index = getClosestGhost();
            double distanceX = ghostList.get(index).getX() + 0.5 * ghostList.get(index).getSpriteWidth() - (character.getX() + 0.5 * character.getSpriteWidth());
            double distanceY = ghostList.get(index).getY() + 0.5 * ghostList.get(index).getSpriteHeight() - (character.getY() + 0.5 * character.getSpriteHeight());
            if (Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2)) < 0.75 * ((double) character.getSpriteWidth())) {
                gameOver = true;
            } else if (Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2)) < 2 * character.getSpriteWidth()) {
                gameOver = health.addDamage(difficulty);
                alertList.add(alert);



            }
        }
    }

    public void displayScore() {
        score = score + 1 + (-prevTime + System.currentTimeMillis())/10;
        displayScore = ((int) (score/100)) * 100;
        prevTime = System.currentTimeMillis();
    }

    public boolean ghostDown() {
        for (int i = 0; i < ghostList.size(); i++) {
            for (int j = 0; j < bulletList.size(); j++) {
                Bullet f = bulletList.get(j);
                Rect e = new Rect(f.getXCoord(),f.getYCoord(),f.getXCoord() + f.getWidth(), f.getYCoord() + f.getHeight());
                if (Rect.intersects(ghostList.get(i).getHitbox(),e)) {
                    int coinX = ghostList.get(i).getX();
                    int coinY = ghostList.get(i).getY();
                    coinList.add(new Coin(coinBit, coinX, coinY, 3, 8, System.currentTimeMillis(),background));
                    ghostList.remove(i);
                    bulletList.remove(j);
                    sound.playScream();
                    score += 2500;
                    killedGhosts++;
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
        background.draw(canvas);
        for (Coin c : coinList) {
            c.draw(canvas);
        }

        for (Objects c : objectlist) {
            c.draw(canvas);
        }

        character.draw(canvas);
        up.draw(canvas);
        left.draw(canvas);
        down.draw(canvas);
        right.draw(canvas);
        int index = getClosestGhost();
        for(Alert element : alertList){
            element.draw(canvas);
        }
        for(Barriers element : barrierList){
            element.draw(canvas);
        }
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

    public void shutDownThread() {
        thread.setRunning(false);
    }

    public void restartThread() {
        thread.setRunning(true);
    }
}
