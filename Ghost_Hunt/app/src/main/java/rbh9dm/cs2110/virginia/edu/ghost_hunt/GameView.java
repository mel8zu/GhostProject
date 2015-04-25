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
    //private ArrayList<MegaGhost> megaGhostList;
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
    private Sounds sound;
    private Bitmap ghostBit;
    private int numCoins;
    private BuyButton heart;
    private Bitmap megaGhostBit;
    private BuyButton saiyanButton;
    private Bitmap characterBit;
    private Bitmap saiyanBit;
    private long saiyanTime;
    private boolean saiyan;

    /*
    Constructor: where the stuff that appears on screen is declared
     */
    public GameView(Context context, int level, int difficulty) {
        super(context);
        this.difficulty = difficulty;
        this.level = level;
        getHolder().addCallback(this);

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(60);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        saiyanTime = 0;
        saiyan = false;

        sound = new Sounds(context);

        score = 0;
        displayScore = 0;
        prevTime = System.currentTimeMillis();
        killedGhosts = 0;
        numCoins = 0;
        Bitmap heartBit = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        Bitmap saiyanButtonBit = BitmapFactory.decodeResource(getResources(), R.drawable.saiyanbutton);
        heart = new BuyButton(heartBit,screenWidth - heartBit.getWidth() - 10, screenHeight/2, 1, System.currentTimeMillis());
        saiyanButton = new BuyButton(saiyanButtonBit,screenWidth - saiyanButtonBit.getWidth() - 10, screenHeight/2 - 20 - heartBit.getHeight(), 1, System.currentTimeMillis());
        ghostBit = BitmapFactory.decodeResource(getResources(), R.drawable.ghost);
        megaGhostBit = BitmapFactory.decodeResource(getResources(), R.drawable.mega_ghost);
        characterBit = BitmapFactory.decodeResource(getResources(), R.drawable.maincharacter);
        saiyanBit = BitmapFactory.decodeResource(getResources(), R.drawable.saiyan);

        character = new Character(characterBit, 0, 300, 6, 14,6,2,2,2,2);
        character.setX(screenWidth/2 - character.getSpriteWidth()/2);

        if (level == 3) background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.desert3), 0, 0, 5);
        else if (level == 2) background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.winter2), 0, 0, 5);
        else  background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.forest), 0, 0, 5);

        bulletList = new ArrayList<Bullet>();
        ghostList = new ArrayList<Ghost>();
        //megaGhostList = new ArrayList<MegaGhost>();
        coinList = new ArrayList<Coin>();
        initCreateGhosts();

        bulletList = new ArrayList<Bullet>();
        ghostList = new ArrayList<Ghost>();
        coinList = new ArrayList<Coin>();
        initCreateGhosts();

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
        while (ghostList.size() < 4) {
            Random rand = new Random();
            int x = rand.nextInt(background.getWidth() - Math.max(ghostBit.getWidth(), megaGhostBit.getWidth())) + background.getXCoord();
            int y = rand.nextInt(background.getHeight() - Math.max(ghostBit.getHeight(), megaGhostBit.getHeight())) + background.getYCoord();
            while(Math.sqrt(Math.pow(x-character.getX(),2) + Math.pow(y-character.getY(),2)) < 200) {
                x = rand.nextInt(background.getWidth() - Math.max(ghostBit.getWidth(), megaGhostBit.getWidth())) + background.getXCoord();
                y = rand.nextInt(background.getHeight() - Math.max(ghostBit.getHeight(), megaGhostBit.getHeight())) + background.getYCoord();
            }
            int speed = rand.nextInt(3) + difficulty * 2 + 2;
            double angle = rand.nextDouble()*6;
            Ghost ghost1 = new Ghost(BitmapFactory.decodeResource(getResources(), R.drawable.ghost), x, y, 6, 9, 1, 2, 2, 2, 2, speed, angle, background);
            ghostList.add(ghost1);

            x = rand.nextInt(background.getWidth() - Math.max(ghostBit.getWidth(), megaGhostBit.getWidth())) + background.getXCoord();
            y = rand.nextInt(background.getHeight() - Math.max(ghostBit.getHeight(), megaGhostBit.getHeight())) + background.getYCoord();
            while(Math.sqrt(Math.pow(x-character.getX(),2) + Math.pow(y-character.getY(),2)) < 200) {
                x = rand.nextInt(background.getWidth() - Math.max(ghostBit.getWidth(), megaGhostBit.getWidth())) + background.getXCoord();
                y = rand.nextInt(background.getHeight() - Math.max(ghostBit.getHeight(), megaGhostBit.getHeight())) + background.getYCoord();
            }
            angle = rand.nextDouble() * 6;

            MegaGhost megaGhost1 = new MegaGhost(BitmapFactory.decodeResource(getResources(), R.drawable.mega_ghost), x, y, 6, 9, 1, 2, 2, 2, 2, speed-2, angle, background);
            ghostList.add(megaGhost1);
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
                            heart.handleActionDown((int) event.getX(), (int) event.getY());
                            saiyanButton.handleActionDown((int) event.getX(), (int) event.getY());
                            if ((numCoins >= heart.getMinCost() && heart.isTouched())  ) {
                                if (System.currentTimeMillis() > heart.getLastBuy() + 1000) {
                                    health.subtractDamage(5);
                                    numCoins--;
                                    heart.setLastBuy(System.currentTimeMillis());
                                }
                            }
                            else if ((numCoins >= saiyanButton.getMinCost() && saiyanButton.isTouched())  ) {
                                if (System.currentTimeMillis() > heart.getLastBuy() + 1000) {
                                    character.setBitmap(saiyanBit);
                                    background.setSpeed(background.getSpeed() + 5);
                                    numCoins-=1;
                                    saiyan = true;
                                    saiyanTime = System.currentTimeMillis();
                                    saiyanButton.setLastBuy(System.currentTimeMillis());
                                }
                            }
                            else if (System.currentTimeMillis() > lastShot + 1000) {
                                if (saiyan) {
                                    bulletList.add(new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.saiyanball), character, 22, event.getX(), event.getY(), 800, true));
                                    sound.playLaser();
                                    lastShot = System.currentTimeMillis();
                                }
                                else  {
                                bulletList.add(new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.bullet), character, 13, event.getX(), event.getY(), 550, false));
                                sound.playGun();
                                lastShot = System.currentTimeMillis();
                                }
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
        if(gameOver && !setScore) {
            thread.setRunning(false);
            setScore = true;
            sound.release();
            play.updateHighScore(("0000000000"+displayScore).substring((""+displayScore).length()));
        }
        if (saiyan == true) {
            if (System.currentTimeMillis() > saiyanTime + 20000) {
                character.setBitmap(characterBit);
                background.setSpeed(background.getSpeed() - 5);
                saiyan = false;
            }
        }
        background.update();
        character.update(System.currentTimeMillis(),background.getDirection());
        walkOffScreen();
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
            if(b.getDistance()>b.getMaxDistance())
                iterator.remove();
        }
        for(Ghost g : ghostList) {
            g.update(System.currentTimeMillis());
        }
        displayScore();
        createGhostMap();
        manageHealth();
        manageCoins();

        ghostDown();


    }

    public void walkOffScreen() {
        int charCenterX = character.getX() + character.getSpriteWidth()/2;
        int charCenterY = character.getY() + character.getSpriteHeight()/2;
        if (charCenterX > background.getXCoord() + background.getWidth())
            shift('r');
        else if (charCenterX < background.getXCoord())
            shift('l');
        if (charCenterY > background.getYCoord() + background.getHeight())
            shift('b');
        else if (charCenterY < background.getYCoord())
            shift('t');
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
                sound.playCash();
                iterate.remove();
                numCoins++;
                score+=2500;
                health.subtractDamage(4);
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
        if ((displayScore / 400)*level  > ghostList.size() + killedGhosts*.568 && ghostList.size()<=(5*difficulty)) {
            Random rand = new Random();
            int x = rand.nextInt(background.getWidth() - Math.max(ghostBit.getWidth(), megaGhostBit.getWidth())) + background.getXCoord();
            int y = rand.nextInt(background.getHeight() - Math.max(ghostBit.getHeight(), megaGhostBit.getHeight())) + background.getYCoord();
            while(Math.sqrt(Math.pow(x-character.getX(),2) + Math.pow(y-character.getY(),2)) < 200) {
                x = rand.nextInt(background.getWidth() - Math.max(ghostBit.getWidth(), megaGhostBit.getWidth())) + background.getXCoord();
                y = rand.nextInt(background.getHeight() - Math.max(ghostBit.getHeight(), megaGhostBit.getHeight())) + background.getYCoord();
            }
            int speed = rand.nextInt(3) + difficulty * 2 + 2;
            double angle = rand.nextDouble()*6;
            Ghost ghost1 = new Ghost(BitmapFactory.decodeResource(getResources(), R.drawable.ghost), x, y, 6, 9, 1, 2, 2, 2, 2, speed, angle, background);
            ghostList.add(ghost1);

            x = rand.nextInt(background.getWidth() - Math.max(ghostBit.getWidth(), megaGhostBit.getWidth())) + background.getXCoord();
            y = rand.nextInt(background.getHeight() - Math.max(ghostBit.getHeight(), megaGhostBit.getHeight())) + background.getYCoord();
            while(Math.sqrt(Math.pow(x-character.getX(),2) + Math.pow(y-character.getY(),2)) < 200) {
                x = rand.nextInt(background.getWidth() - Math.max(ghostBit.getWidth(), megaGhostBit.getWidth())) + background.getXCoord();
                y = rand.nextInt(background.getHeight() - Math.max(ghostBit.getHeight(), megaGhostBit.getHeight())) + background.getYCoord();
            }
            angle = rand.nextDouble() * 6;

            MegaGhost megaGhost1 = new MegaGhost(BitmapFactory.decodeResource(getResources(), R.drawable.mega_ghost), x, y, 6, 9, 1, 2, 2, 2, 2, speed-2, angle, background);
            ghostList.add(megaGhost1);
        }
    }

    public void manageHealth() {
        if (ghostList.size() != 0) {
            int index = getClosestGhost();
            double distanceX = ghostList.get(index).getX() + 0.5 * ghostList.get(index).getSpriteWidth() - (character.getX() + 0.5 * character.getSpriteWidth());
            double distanceY = ghostList.get(index).getY() + 0.5 * ghostList.get(index).getSpriteHeight() - (character.getY() + 0.5 * character.getSpriteHeight());
            if (Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2)) < 0.75 * ((double) character.getSpriteWidth())) {
                gameOver = true;
            } else if (Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2)) < 2 * character.getSpriteWidth()) {
                Random rand = new Random();
                double random = rand.nextDouble();
                if (random < .33) {
                    if (ghostList.get(index) instanceof MegaGhost) {
                        gameOver = health.addDamage(2 * difficulty);
                    } else {
                        gameOver = health.addDamage(difficulty);
                    }
                }
            }
        }
    }

    public void displayScore() {
        score = score + 1 + (-prevTime + System.currentTimeMillis())/10;
        displayScore = ((int) (score/100)) * 100;
        prevTime = System.currentTimeMillis();
    }

    public void ghostDown() {
        for (int i = 0; i < ghostList.size(); i++) {
            for (int j = 0; j < bulletList.size(); j++) {
                Bullet f = bulletList.get(j);
                Rect e = new Rect(f.getXCoord(),f.getYCoord(),f.getXCoord() + f.getWidth(), f.getYCoord() + f.getHeight());

                if (Rect.intersects(ghostList.get(i).getHitbox(),e)) {
                    if (ghostList.get(i) instanceof MegaGhost) {
                        if (f.isMega()) {
                            int coinX = ghostList.get(i).getX();
                            int coinY = ghostList.get(i).getY();
                            coinList.add(new Coin(BitmapFactory.decodeResource(getResources(), R.drawable.spinning_coin_gold), coinX, coinY, 6, 8, System.currentTimeMillis(), background));
                            ghostList.remove(i);
                            bulletList.remove(j);
                            i--;
                            j--;
                            sound.playScream();
                            score += 5000;
                            killedGhosts++;
                            health.subtractDamage(2);
                        }
                         else {
                            if (((MegaGhost) ghostList.get(i)).getGhostDamage() == 1) {
                                int coinX = ghostList.get(i).getX();
                                int coinY = ghostList.get(i).getY();
                                coinList.add(new Coin(BitmapFactory.decodeResource(getResources(), R.drawable.spinning_coin_gold), coinX, coinY, 6, 8, System.currentTimeMillis(), background));
                                ghostList.remove(i);
                                bulletList.remove(j);
                                i--;
                                j--;
                                sound.playScream();
                                score += 5000;
                                killedGhosts++;
                                health.subtractDamage(2);
                            } else {
                                bulletList.remove(j);
                                j--;
                                ((MegaGhost) ghostList.get(i)).setGhostDamage(1);
                                sound.playUgh();
                            }
                        }
                    } else {
                        int coinX = ghostList.get(i).getX();
                        int coinY = ghostList.get(i).getY();
                        coinList.add(new Coin(BitmapFactory.decodeResource(getResources(), R.drawable.spinning_coin_gold), coinX, coinY, 6, 8, System.currentTimeMillis(), background));
                        ghostList.remove(i);
                        bulletList.remove(j);
                        i--;
                        j--;
                        sound.playScream();
                        score += 2500;
                        killedGhosts++;
                        health.subtractDamage(1);

                    }
                }
            }
        }
    }

    public void shift(char c) {
        if (c == 't') {
            int shift = background.getHeight() - 20;
            background.setYCoord(background.getYCoord() - shift);
            for(Ghost g : ghostList) {
                g.setY(g.getY() - shift);
            }
            for(Bullet b : bulletList) {
                b.setYCoord(b.getYCoord() - shift);
            }
            for(Coin coin : coinList) {
                coin.setyCoord(coin.getyCoord() - shift);
            }
        }
        else if (c == 'l') {
            int shift = background.getWidth() - 20;
            background.setXCoord(background.getXCoord() - shift);
            for(Ghost g : ghostList) {
                g.setX(g.getX() - shift);
            }
            for(Bullet b : bulletList) {
                b.setXCoord(b.getXCoord() - shift);
            }
            for(Coin coin : coinList) {
                coin.setxCoord(coin.getxCoord() - shift);
            }
        }
        else if (c == 'r') {
            int shift = background.getWidth() - 20;
            background.setXCoord(background.getXCoord() + shift);
            for(Ghost g : ghostList) {
                g.setX(g.getX() + shift);
            }
            for(Bullet b : bulletList) {
                b.setXCoord(b.getXCoord() + shift);
            }
            for(Coin coin : coinList) {
                coin.setyCoord(coin.getyCoord() + shift);
            }
        }
        else if (c == 'b') {
            int shift = background.getHeight() - 20;
            background.setYCoord(background.getYCoord() + shift);
            for(Ghost g : ghostList) {
                g.setY(g.getY() + shift);
            }
            for(Bullet b : bulletList) {
                b.setYCoord(b.getYCoord() + shift);
            }
            for(Coin coin : coinList) {
                coin.setyCoord(coin.getyCoord() + shift);
            }
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
        for (Coin c : coinList) {
            c.draw(canvas);
        }
        character.draw(canvas);
        up.draw(canvas);
        left.draw(canvas);
        down.draw(canvas);
        right.draw(canvas);
        health.draw(canvas);
        for(Bullet b : bulletList) {
            b.draw(canvas);
        }
        for(Ghost g : ghostList) {
            if (g.getX()<=screenWidth || g.getY()<=screenHeight || g.getX() >= -g.getSpriteWidth() || g.getY() >=-g.getSpriteHeight())
                g.draw(canvas);
        }
        if (numCoins >= heart.getMinCost()) {
            heart.draw(canvas);
        }
        if (numCoins >= saiyanButton.getMinCost()) {
            saiyanButton.draw(canvas);
        }
        canvas.drawText(("0000000000"+displayScore).substring((""+displayScore).length()),health.getX(), health.getY() + health.getHealth().getHeight() + paint.getTextSize() , paint);
        canvas.drawText(""+numCoins,health.getX(), health.getY() + health.getHealth().getHeight() + 2*paint.getTextSize() , paint);

    }

    public void shutDownThread() {
        thread.setRunning(false);
    }

    public void restartThread() {
        thread.setRunning(true);
    }
}