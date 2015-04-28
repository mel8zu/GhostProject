package rbh9dm.cs2110.virginia.edu.ghost_hunt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
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
    private DirectionButton layBarrier;
    /*private DirectionButton layBarrier2;
    private ArrayList<DirectionButton> layBarrierList;*/
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
    private Sounds sound;
    private int numCoins;
    private BuyButton heart;
    private BuyButton saiyanButton;
    private BuyButton pacButton;
    private BuyButton bombButton;
    private boolean isBomb;
    private BuyButton bulletButton;
    private long powerupTime;
    private boolean isSaiyan;
    private boolean isPacman;
    private Random rand;
    private int numSuperBullets;
    private ArrayList<Ghost> friendlyGhostList;
    private Boom boom;
    private Rect rectTop;
    private Rect rectLeft;
    private Rect rectRight;
    private Rect rectBottom;
    private Rect gRectTop;
    private Rect gRectLeft;
    private Rect gRectRight;
    private Rect gRectBottom;
    private int numBarriersCanLay;
    private long lastAddToNumBarrCanLay;

    private Bitmap characterBit;
    private Bitmap ghostBit;
    private Bitmap megaGhostBit;
    private Bitmap blueGhostBit;
    private Bitmap saiyanBit;
    private Bitmap pacmanBit;
    private Bitmap backgroundBit;
    private Bitmap bulletBit;
    private Bitmap saiyanBlastBit;
    private Bitmap heartBit;
    private Bitmap saiyanButtonBit;
    private Bitmap pacmanButtonBit;
    private Bitmap bombButtonBit;
    private Bitmap explosionBit;
    private Bitmap healthBit;
    private Bitmap deathBit;
    private Bitmap upMap;
    private Bitmap downMap;
    private Bitmap leftMap;
    private Bitmap rightMap;
    private Bitmap bulletButtonBit;
    private Bitmap greenGhostBit;
    private Bitmap barrierMap;
    private ArrayList<Barriers> barrierList;
    private ArrayList<Alert> alertList;
    private ArrayList<Objects> objectlist;
    private Bitmap alertBit;
    private Alert alert;
    private boolean barrierMode;
    private Bitmap redXBit;
    private Bitmap gemBit;
    private int maxNumGhosts;
    private int maxRandGhostSpeed;
    private int minGhostSpeed;

    /*
    Constructor: where the stuff that appears on screen is declared
     */
    public GameView(Context context, int level, int difficulty, int x, int y) {
        super(context);
        this.difficulty = difficulty;
        this.level = level;
        getHolder().addCallback(this);
        rand = new Random();
        barrierMode = false;
        if (difficulty == 1 || difficulty == 2) {
            maxNumGhosts = 6;
            maxRandGhostSpeed = 3;
            minGhostSpeed = 5;
        }
        else {
            maxNumGhosts = 8;
            maxRandGhostSpeed = 4;
            minGhostSpeed = 7;
        }

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(60);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        powerupTime = 0;
        isSaiyan = false;
        isPacman = false;
        isBomb = false;
        numSuperBullets = 0;

        rectTop = new Rect(0,0,0,0);
        rectLeft = new Rect(0,0,0,0);;
        rectRight = new Rect(0,0,0,0);;
        rectBottom = new Rect(0,0,0,0);;
        gRectTop = new Rect(0,0,0,0);;
        gRectLeft = new Rect(0,0,0,0);;
        gRectRight = new Rect(0,0,0,0);;
        gRectBottom = new Rect(0,0,0,0);;
        numBarriersCanLay = 5;
        lastAddToNumBarrCanLay = System.currentTimeMillis();

        ghostBit = BitmapFactory.decodeResource(getResources(), R.drawable.ghost);
        megaGhostBit = BitmapFactory.decodeResource(getResources(), R.drawable.mega_ghost);
        blueGhostBit = BitmapFactory.decodeResource(getResources(), R.drawable.blueghost);
        characterBit = BitmapFactory.decodeResource(getResources(), R.drawable.maincharacter);
        saiyanBit = BitmapFactory.decodeResource(getResources(), R.drawable.saiyan);
        pacmanBit = BitmapFactory.decodeResource(getResources(), R.drawable.pacman);
        heartBit = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        saiyanButtonBit = BitmapFactory.decodeResource(getResources(), R.drawable.saiyanbutton);
        pacmanButtonBit = BitmapFactory.decodeResource(getResources(), R.drawable.pacmanbutton);
        bombButtonBit = BitmapFactory.decodeResource(getResources(), R.drawable.bomb);
        upMap = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_u);
        leftMap = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_l);
        rightMap = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_r);
        downMap = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_d);
        bulletBit = BitmapFactory.decodeResource(getResources(), R.drawable.bullet);
        saiyanBlastBit = BitmapFactory.decodeResource(getResources(), R.drawable.saiyanball);
        explosionBit = BitmapFactory.decodeResource(getResources(), R.drawable.explosion);
        healthBit = BitmapFactory.decodeResource(getResources(), R.drawable.health);
        deathBit = BitmapFactory.decodeResource(getResources(), R.drawable.dead);
        bulletButtonBit = BitmapFactory.decodeResource(getResources(), R.drawable.superbullet);
        greenGhostBit = BitmapFactory.decodeResource(getResources(), R.drawable.greenghost);
        alertBit = BitmapFactory.decodeResource(getResources(), R.drawable.alerti);
        redXBit = BitmapFactory.decodeResource(getResources(), R.drawable.redx);
        gemBit = BitmapFactory.decodeResource(getResources(), R.drawable.gemresized);

        if(level==1) {
            barrierMap = BitmapFactory.decodeResource(getResources(), R.drawable.smallstone);
        }
        else if(level==2){
            barrierMap = BitmapFactory.decodeResource(getResources(), R.drawable.iceresized);
        }
        else if(level==3){
            barrierMap = BitmapFactory.decodeResource(getResources(), R.drawable.cactusresizedd);
        }

        sound = new Sounds(context);

        score = 0;
        displayScore = 0;
        prevTime = System.currentTimeMillis();
        killedGhosts = 0;
        numCoins = 0;
        heart = new BuyButton(heartBit,screenWidth - heartBit.getWidth() - 10, screenHeight/2, 1, System.currentTimeMillis());
        saiyanButton = new BuyButton(saiyanButtonBit,screenWidth - saiyanButtonBit.getWidth() - 10, screenHeight/2 - 20 - heartBit.getHeight(), 1, System.currentTimeMillis());
        pacButton = new BuyButton(pacmanButtonBit,screenWidth - pacmanButtonBit.getWidth() - 10, screenHeight/2 - 30 - heartBit.getHeight() - saiyanButtonBit.getHeight(), 1, System.currentTimeMillis());
        bulletButton = new BuyButton(bulletButtonBit,screenWidth - bulletButtonBit.getWidth() - 10, screenHeight/2 - 40 - heartBit.getHeight() - saiyanButtonBit.getHeight() - pacmanButtonBit.getHeight(), 1, System.currentTimeMillis());
        bombButton = new BuyButton(bombButtonBit, screenWidth - bombButtonBit.getWidth() - 10, screenHeight/2 - 50 - heartBit.getHeight() - saiyanButtonBit.getHeight() - pacmanButtonBit.getHeight() - bulletButtonBit.getHeight(), 1, System.currentTimeMillis());

        character = new Character(characterBit, 0, 300, 6, 14,6,2,2,2,2);
        character.setX(screenWidth/2 - character.getSpriteWidth()/2);

        if (level == 3) {
            backgroundBit = BitmapFactory.decodeResource(getResources(), R.drawable.desert3);
        }
        else if (level == 2) {
            backgroundBit = BitmapFactory.decodeResource(getResources(), R.drawable.winter2);
        }
        else  {
            backgroundBit = BitmapFactory.decodeResource(getResources(), R.drawable.forest);
        }
        background = new Background(backgroundBit, x, y, 5);
        Objects gem1 = new Objects(BitmapFactory.decodeResource(getResources(), R.drawable.gemresized), 400, 400, background);
        boom = new Boom(explosionBit, 0, 0, 6, 40, 0, background);

        bulletList = new ArrayList<Bullet>();
        ghostList = new ArrayList<Ghost>();
        coinList = new ArrayList<Coin>();
        friendlyGhostList = new ArrayList<Ghost>();
        initCreateGhosts();
        objectlist = new ArrayList<Objects>();
        initCreateGems();




        barrierList = new ArrayList<Barriers>();
        alertList = new ArrayList<Alert>();

        alert = new Alert(screenWidth - alertBit.getWidth() - 10,10,alertBit);
        initCreateGhosts();



        down = new DirectionButton(downMap , (int) (screenWidth/2 - 0.5*downMap.getWidth()), screenHeight - downMap.getHeight() - 25);
        up = new DirectionButton(upMap , (int) (screenWidth/2 - 0.5*upMap.getWidth()), down.getY() - downMap.getHeight() - 25);
        left = new DirectionButton(leftMap , down.getX() - leftMap.getWidth() - 10, screenHeight - leftMap.getHeight() - 25);
        right = new DirectionButton(rightMap , down.getX() + downMap.getWidth() + 10, screenHeight - rightMap.getHeight() - 25);
        //layBarrierList = new ArrayList<DirectionButton>();
        layBarrier = new DirectionButton(barrierMap , screenWidth-barrierMap.getWidth()-10, alertBit.getWidth() + 20);
        /*layBarrierList.add(layBarrier);
        layBarrier2 = new DirectionButton(barrierMap , 710, 180);*/

        health = new HealthBar(healthBit,deathBit,10,10, character.getHealthLevel());

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

    public void moreBarriers() {
        if (System.currentTimeMillis()>lastAddToNumBarrCanLay + 10000 && (numBarriersCanLay+barrierList.size()) < 5) {

            numBarriersCanLay++;
            lastAddToNumBarrCanLay = System.currentTimeMillis();
        }
    }

    public void initCreateGems(){
        int bX = background.getXCoord();
        int bY = background.getYCoord();
        for(int i = 0; i < 4; i++){
            Objects gem =  new Objects(gemBit, rand.nextInt(background.getWidth()-2*gemBit.getWidth())+bX+gemBit.getWidth(), rand.nextInt(background.getHeight()-2*gemBit.getHeight())+bY+gemBit.getHeight(), background);
            while (character.getHitbox().intersects(gem.getxCoord(), gem.getyCoord(), gem.getxCoord()+gem.getWidth(), gem.getyCoord()+gem.getHeight())) {
                gem =  new Objects(gemBit, rand.nextInt(background.getWidth()-2*gemBit.getWidth())+bX+gemBit.getWidth(), rand.nextInt(background.getHeight()-2*gemBit.getHeight())+bY+gemBit.getHeight(), background);
            }
            objectlist.add(gem);
        }

    }

    public void initCreateGhosts() {
        while (ghostList.size() < 4) {
            int x = rand.nextInt(background.getWidth() - Math.max(ghostBit.getWidth(), megaGhostBit.getWidth())) + background.getXCoord();
            int y = rand.nextInt(background.getHeight() - Math.max(ghostBit.getHeight(), megaGhostBit.getHeight())) + background.getYCoord();
            while(Math.sqrt(Math.pow(x-character.getX(),2) + Math.pow(y-character.getY(),2)) < 200) {
                x = rand.nextInt(background.getWidth() - Math.max(ghostBit.getWidth(), megaGhostBit.getWidth())) + background.getXCoord();
                y = rand.nextInt(background.getHeight() - Math.max(ghostBit.getHeight(), megaGhostBit.getHeight())) + background.getYCoord();
            }
            int speed = rand.nextInt(maxRandGhostSpeed) + minGhostSpeed;
            double angle = rand.nextDouble()*6;
            Ghost ghost1 = new Ghost(BitmapFactory.decodeResource(getResources(), R.drawable.ghost), x, y, 6, 9, 1, 2, 2, 2, 2, speed, angle, background);
            ghostList.add(ghost1);

            x = rand.nextInt(background.getWidth() - Math.max(ghostBit.getWidth(), megaGhostBit.getWidth())) + background.getXCoord();
            y = rand.nextInt(background.getHeight() - Math.max(ghostBit.getHeight(), megaGhostBit.getHeight())) + background.getYCoord();
            while(Math.sqrt(Math.pow(x-character.getX(),2) + Math.pow(y-character.getY(),2)) < 200) {
                x = rand.nextInt(background.getWidth() - Math.max(ghostBit.getWidth(), megaGhostBit.getWidth())) + background.getXCoord();
                y = rand.nextInt(background.getHeight() - Math.max(ghostBit.getHeight(), megaGhostBit.getHeight())) + background.getYCoord();
            }
            speed = rand.nextInt(maxRandGhostSpeed-1) + minGhostSpeed-2;
            angle = rand.nextDouble() * 6;

            MegaGhost megaGhost1 = new MegaGhost(BitmapFactory.decodeResource(getResources(), R.drawable.mega_ghost), x, y, 6, 9, 1, 2, 2, 2, 2, speed, angle, background);
            ghostList.add(megaGhost1);
        }
    }
    /*
    When the screen is touched, this method tells the game what to do
     */
    @Override


    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            /*if ((level == 1) && (barrierMode == true)) {
                barrierList.add(new Barriers(BitmapFactory.decodeResource(getResources(), R.drawable.smallstone), (int) event.getX(), (int) event.getY(), 5));
            } else if ((level == 2) && (barrierMode == true)) {
                barrierList.add(new Barriers(BitmapFactory.decodeResource(getResources(), R.drawable.iceresized), (int) event.getY(), (int) event.getY(), 5));
            } else {
                if (barrierMode == true)
                    barrierList.add(new Barriers(BitmapFactory.decodeResource(getResources(), R.drawable.cactusresizedd), character.getX(), character.getY() + 200, 5));
            }
            layBarrier.handleActionDown((int) event.getX(), (int) event.getY());
            if (layBarrier.isTouched()) {
                if (level == 1) {
                    barrierList.add(new Barriers(BitmapFactory.decodeResource(getResources(), R.drawable.smallstone), character.getX(), character.getY() + 100, background.getSpeed()));
                } else if (level == 2) {
                    barrierList.add(new Barriers(BitmapFactory.decodeResource(getResources(), R.drawable.iceresized), character.getX(), character.getY() + 100, background.getSpeed()));
                } else {
                    barrierList.add(new Barriers(BitmapFactory.decodeResource(getResources(), R.drawable.cactusresizedd), character.getX(), character.getY() + 100, background.getSpeed()));
                    if (count % 2 == 0) {
                        layBarrierList.add(layBarrier2);
                        barrierMode = true;
                        count++;
                    } else if (count % 2 != 0) {
                        layBarrierList.remove(layBarrier2);
                        barrierMode = false;
                        count++;
                    }
                }*/

            up.handleActionDown((int) event.getX(), (int) event.getY());
            if (up.isTouched()) {
                background.setDirection(1);
                for (Barriers element : barrierList) {
                    element.setDirection(1);
                }
            } else {
                left.handleActionDown((int) event.getX(), (int) event.getY());
                if (left.isTouched()) {
                    background.setDirection(2);
                    for (Barriers element : barrierList) {
                        element.setDirection(2);
                    }
                } else {
                    right.handleActionDown((int) event.getX(), (int) event.getY());
                    if (right.isTouched()) {
                        background.setDirection(3);
                        for (Barriers element : barrierList) {
                            element.setDirection(3);
                        }
                    } else {
                        down.handleActionDown((int) event.getX(), (int) event.getY());
                        if (down.isTouched()) {
                            background.setDirection(4);
                            for (Barriers element : barrierList) {
                                element.setDirection(4);
                            }
                        } else {
                            background.setDirection(0);
                            for (Barriers element : barrierList) {
                                element.setDirection(0);
                            }
                            heart.handleActionDown((int) event.getX(), (int) event.getY());
                            bulletButton.handleActionDown((int) event.getX(), (int) event.getY());
                            saiyanButton.handleActionDown((int) event.getX(), (int) event.getY());
                            pacButton.handleActionDown((int) event.getX(), (int) event.getY());
                            bombButton.handleActionDown((int) event.getX(), (int) event.getY());
                            layBarrier.handleActionDown((int) event.getX(), (int) event.getY());
                            if ((numCoins >= heart.getMinCost() && heart.isTouched())) {
                                if (System.currentTimeMillis() > heart.getLastBuy() + 1000) {
                                    health.subtractDamage(20);
                                    numCoins--;
                                    heart.setLastBuy(System.currentTimeMillis());
                                }
                            } else if ((numCoins >= bulletButton.getMinCost() && bulletButton.isTouched())) {
                                if (System.currentTimeMillis() > bulletButton.getLastBuy() + 1000) {
                                    numCoins--;                                    numSuperBullets++;

                                    bulletButton.setLastBuy(System.currentTimeMillis());
                                }
                            } else if ((numCoins >= saiyanButton.getMinCost() && saiyanButton.isTouched())) {
                                if (System.currentTimeMillis() > saiyanButton.getLastBuy() + 1000) {
                                    powerupTime = System.currentTimeMillis();
                                    sound.stopWaka();
                                    sound.playFlashbang();
                                    character.setBitmap(saiyanBit);
                                    character.setFrameNr(14);
                                    character.setNumStill(6);
                                    character.setNumDown(2);
                                    character.setNumLeft(2);
                                    character.setNumUp(2);
                                    character.setNumRight(2);
                                    character.setCurrentFrame(0);
                                    character.setSpriteWidth(character.getBitmap().getWidth() / character.getFrameNr());
                                    character.setSpriteHeight(character.getBitmap().getHeight());
                                    character.setSourceRect(new Rect(0, 0, character.getSpriteWidth(), character.getSpriteHeight()));
                                    if (!isSaiyan) {
                                        background.setSpeed(background.getSpeed() + 5);
                                    }
                                    for (Barriers b : barrierList) {
                                        b.setSpeed(background.getSpeed());
                                    }
                                    for (Ghost g : ghostList) {
                                        if (g instanceof MegaGhost)
                                            g.setBitmap(megaGhostBit);
                                        else
                                            g.setBitmap(ghostBit);
                                    }
                                    numCoins -= 1;
                                    isSaiyan = true;
                                    isPacman = false;
                                    saiyanButton.setLastBuy(System.currentTimeMillis());
                                }
                            } else if ((numCoins >= pacButton.getMinCost() && pacButton.isTouched())) {
                                if (System.currentTimeMillis() > pacButton.getLastBuy() + 1000) {
                                    powerupTime = System.currentTimeMillis();
                                    sound.playWaka();
                                    character.setBitmap(pacmanBit);
                                    character.setFrameNr(17);
                                    character.setNumStill(1);
                                    character.setNumDown(4);
                                    character.setNumLeft(4);
                                    character.setNumUp(4);
                                    character.setNumRight(4);
                                    character.setCurrentFrame(0);
                                    character.setSpriteWidth(character.getBitmap().getWidth() / character.getFrameNr());
                                    character.setSpriteHeight(character.getBitmap().getHeight());
                                    character.setSourceRect(new Rect(0, 0, character.getSpriteWidth(), character.getSpriteHeight()));
                                    if (isSaiyan) {
                                        background.setSpeed(background.getSpeed() - 5);
                                        for (Barriers b : barrierList) {
                                            b.setSpeed(background.getSpeed());
                                        }
                                    }
                                    for (Ghost g : ghostList) {
                                        g.setBitmap(blueGhostBit);
                                    }
                                    numCoins -= 1;
                                    isPacman = true;
                                    isSaiyan = false;
                                    pacButton.setLastBuy(System.currentTimeMillis());
                                }
                            } else if ((numCoins >= bombButton.getMinCost() && bombButton.isTouched())) {
                                if (System.currentTimeMillis() > bombButton.getLastBuy() + 1000 && !isBomb) {
                                    isBomb = true;
                                    sound.playBoom();
                                    boom.setxCoord(character.getX());
                                    boom.setyCoord(character.getY());
                                    boom.setCurrentFrame(0);
                                    numCoins -= 1;
                                    bombButton.setLastBuy(System.currentTimeMillis());
                                }
                            } else if (layBarrier.isTouched()) {
                                barrierMode = !barrierMode;
                            }else if (barrierMode && numBarriersCanLay>0 && barrierList.size()<5) {
                                int x = (int) event.getX();
                                int y = (int) event.getY();
                                if( Math.sqrt(Math.pow(x - character.getX(),2)+Math.pow(y-character.getY(),2))>150) {
                                    if (level == 1) {
                                        barrierList.add(new Barriers(BitmapFactory.decodeResource(getResources(), R.drawable.smallstone), (int) event.getX(), (int) event.getY(), background.getSpeed(),System.currentTimeMillis()));
                                    } else if (level == 2) {
                                        barrierList.add(new Barriers(BitmapFactory.decodeResource(getResources(), R.drawable.iceresized), (int) event.getX(), (int) event.getY(), background.getSpeed(),System.currentTimeMillis()));
                                    } else {
                                        barrierList.add(new Barriers(BitmapFactory.decodeResource(getResources(), R.drawable.cactusresizedd), (int) event.getX(), (int) event.getY(), background.getSpeed(),System.currentTimeMillis()));
                                    }
                                    numBarriersCanLay--;
                                }
                            }else if (System.currentTimeMillis() > lastShot + 1000 && !isPacman) {
                                if (isSaiyan) {
                                    bulletList.add(new Bullet(saiyanBlastBit, character, 22, event.getX(), event.getY(), 800, true, false));
                                    sound.playLaser();
                                    lastShot = System.currentTimeMillis();
                                } else if (numSuperBullets > 0) {
                                    numSuperBullets--;
                                    bulletList.add(new Bullet(bulletBit, character, 13, event.getX(), event.getY(), 550, false, true));
                                    sound.playGun();
                                    lastShot = System.currentTimeMillis();
                                } else {
                                    bulletList.add(new Bullet(bulletBit, character, 13, event.getX(), event.getY(), 550, false, false));
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
            for (Barriers element : barrierList) {
                element.setDirection(0);
            }
        }
        //}

        return true;
    }


    /*
    Where everything is updated. Called once per run through the game loop
     */
    boolean interesects = false;
    public void update() {
        if (!gameOver) {
            if (isSaiyan || isPacman) {
                if (System.currentTimeMillis() >  powerupTime + 20000) {
                    character.setBitmap(characterBit);
                    character.setFrameNr(14);
                    character.setNumStill(6);
                    character.setNumDown(2);
                    character.setNumLeft(2);
                    character.setNumUp(2);
                    character.setNumRight(2);
                    character.setCurrentFrame(0);
                    character.setSpriteWidth(character.getBitmap().getWidth()/character.getFrameNr());;
                    character.setSpriteHeight(character.getBitmap().getHeight());
                    character.setSourceRect(new Rect(0,0,character.getSpriteWidth(),character.getSpriteHeight()));
                    if (isSaiyan) {
                        background.setSpeed(background.getSpeed() - 5);
                        isSaiyan = false;
                        for (Barriers b : barrierList) {
                            b.setSpeed(background.getSpeed());
                        }
                    }
                    if (isPacman) {
                        isPacman = false;
                        sound.stopWaka();
                        for(Ghost g:ghostList) {
                            if (g instanceof MegaGhost)
                                g.setBitmap(megaGhostBit);
                            else
                                g.setBitmap(ghostBit);
                        }
                    }

                }
            }
            if (isBomb) {
                boom.adjustForBackgroundMove();
                boom.update(System.currentTimeMillis());
                getBombGhosts();
                if (boom.getCurrentFrame()==30)
                    isBomb = false;
            }
            moreBarriers();

            for(int j = 0; j<barrierList.size(); j++) {
                if (System.currentTimeMillis() > barrierList.get(j).getCreated() + 15000) {
                    barrierList.remove(j);
                    j--;
                }
                else {
                    for (int i = 0; i < ghostList.size(); i++) {
                        Barriers barrier = barrierList.get(j);
                        Ghost g = ghostList.get(i);
                        Rect rect = new Rect(barrier.getXCoord(), barrier.getYCoord(), barrier.getXCoord() + barrier.getWidth(), barrier.getYCoord() + barrier.getHeight());
                        Rect gBox = g.getHitbox();
                        if (rect.intersect(gBox)) {
                            if (rect.width() * rect.height() > 0.50 * g.getSpriteWidth() * g.getSpriteHeight()) {
                                ghostList.remove(i);
                                i--;
                            } else {
                                rectTop.set(barrier.getXCoord(), barrier.getYCoord(), barrier.getXCoord() + barrier.getWidth(), barrier.getYCoord() + 15);
                                rectLeft.set(barrier.getXCoord(), barrier.getYCoord(), barrier.getXCoord() + 15, barrier.getYCoord() + barrier.getHeight());
                                rectRight.set(barrier.getXCoord() + barrier.getWidth() - 15, barrier.getYCoord(), barrier.getXCoord() + barrier.getWidth(), barrier.getYCoord() + barrier.getHeight());
                                rectBottom.set(barrier.getXCoord(), barrier.getYCoord() + barrier.getHeight() - 15, barrier.getXCoord() + barrier.getWidth(), barrier.getYCoord() + barrier.getHeight());
                                gRectTop.set(g.getX(), g.getY(), g.getX() + g.getSpriteWidth(), g.getY() + 15);
                                gRectLeft.set(g.getX(), g.getY(), g.getX() + 15, g.getY() + g.getSpriteHeight());
                                gRectRight.set(g.getX() + g.getSpriteWidth() - 15, g.getY(), g.getX() + g.getSpriteWidth(), g.getY() + g.getSpriteHeight());
                                gRectBottom.set(g.getX(), g.getY() + g.getSpriteHeight() - 15, g.getX() + g.getSpriteWidth(), +g.getY() + g.getSpriteHeight());

                                if (Rect.intersects(rectTop, gRectBottom))
                                    g.negYVelocity();
                                if (Rect.intersects(rectBottom, gRectTop))
                                    g.posYVelocity();
                                if (Rect.intersects(rectLeft, gRectRight))
                                    g.negXVelocity();
                                if (Rect.intersects(rectRight, gRectLeft))
                                    g.posXVelocity();
                            }
                        }

                    }
                }
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

                    background.setYCoord(background.getYCoord() - background.getSpeed());
                    for (Barriers element : barrierList) {
                        element.setYCoord(element.getYCoord() - background.getSpeed());
                    }
                } else if (currentDirection == 4) {
                    background.setYCoord(background.getYCoord() + background.getSpeed());
                    for (Barriers element : barrierList) {
                        element.setYCoord(element.getYCoord() + background.getSpeed());
                    }
                } else if (currentDirection == 2) {
                    background.setXCoord(background.getXCoord() - background.getSpeed());
                    for (Barriers element : barrierList) {
                        element.setXCoord(element.getXCoord() - background.getSpeed());
                    }
                } else if (currentDirection == 3) {
                    background.setXCoord(background.getXCoord() + background.getSpeed());
                    for (Barriers element : barrierList) {
                        element.setXCoord(element.getXCoord() + background.getSpeed());
                    }
                }

            }
            for (Coin c : coinList) {
                c.update(System.currentTimeMillis());
            }
            Iterator<Bullet> iterator = bulletList.iterator();
            while (iterator.hasNext()) {
                Bullet b = iterator.next();
                int dir = background.getDirection();
                if (dir == 0) {
                    b.update();
                } else if (dir == 1) {
                    b.setyVelocity(b.getyVelocity() + background.getSpeed());
                    b.update();
                    b.setyVelocity(b.getyVelocity() - background.getSpeed());
                } else if (dir == 2) {
                    b.setxVelocity(b.getxVelocity() + background.getSpeed());
                    b.update();
                    b.setxVelocity(b.getxVelocity() - background.getSpeed());
                } else if (dir == 3) {
                    b.setxVelocity(b.getxVelocity() - background.getSpeed());
                    b.update();
                    b.setxVelocity(b.getxVelocity() + background.getSpeed());
                } else if (dir == 4) {
                    b.setyVelocity(b.getyVelocity() - background.getSpeed());
                    b.update();
                    b.setyVelocity(b.getyVelocity() + background.getSpeed());
                }
                if (b.getDistance() > b.getMaxDistance())
                    iterator.remove();
            }
            background.update();
            for (Barriers element : barrierList) {
                element.update();
            }
            character.update(System.currentTimeMillis(), background.getDirection());
            walkOffScreen();
            for (Ghost g : ghostList) {
                g.update(System.currentTimeMillis());
            }
            for (Ghost g : friendlyGhostList) {
                g.update(System.currentTimeMillis());
            }
            displayScore();
            createGhostMap();
            manageHealth();
            manageCoins();
            friendlyRemove();
            friendlyTimeOver();
            manageObjects();
            if (!isPacman)
                ghostDown();
        }
        if(gameOver && !setScore) {
            thread.setRunning(false);
            setScore = true;
            sound.release();
            play.updateHighScore(("0000000000" + displayScore).substring(("" + displayScore).length()));
        }

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
                score+=10000;
                numCoins+=3;
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
        int index = -1;
        double distanceX, distanceY;
        Double min=10000000.0;
        for (int i = 0; i < ghostList.size(); i++) {
            distanceX = ghostList.get(i).getX()+0.5*ghostList.get(i).getSpriteWidth()-(character.getX()+0.5*character.getSpriteWidth());
            distanceY = ghostList.get(i).getY()+0.5*ghostList.get(i).getSpriteHeight()-(character.getY()+0.5*character.getSpriteHeight());
            Double dist = Math.sqrt(Math.pow(distanceX,2) + Math.pow(distanceY, 2));
            if(dist.compareTo(min)<0) {
                min = dist;
                index = i;
            }
        }
        return index;
    }

    public int getClosestGhost(Ghost friendly) {
        int index = -1;
        double distanceX, distanceY;
        Double min=10000000.0;
        for (int i = 0; i < ghostList.size(); i++) {
            distanceX = ghostList.get(i).getX()+0.5*ghostList.get(i).getSpriteWidth()-(friendly.getX()+0.5*friendly.getSpriteWidth());
            distanceY = ghostList.get(i).getY()+0.5*ghostList.get(i).getSpriteHeight()-(friendly.getY()+0.5*friendly.getSpriteHeight());
            Double dist = Math.sqrt(Math.pow(distanceX,2) + Math.pow(distanceY, 2));
            if(dist.compareTo(min)<0) {
                min = dist;
                index = i;
            }
        }
        return index;
    }


    public void getBombGhosts() {
        double distanceX;
        double distanceY;

        for (int i = 0; i < ghostList.size(); i++) {
            distanceX = ghostList.get(i).getX() + 0.5 * ghostList.get(i).getSpriteWidth() - (boom.getCenterX());
            distanceY = ghostList.get(i).getY() + 0.5 * ghostList.get(i).getSpriteHeight() - (boom.getCenterY());
            Double dist = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
            if (dist < 200) {
                ghostList.remove(i);
                killedGhosts++;
                score += 1000;
                i--;
            }
        }
    }

    public double calcDistance(Ghost g1, Ghost g2) {
        double distanceX = g1.getX()+0.5*g1.getSpriteWidth()-(g2.getX()+0.5*g2.getSpriteWidth());
        double distanceY = g1.getY()+0.5*g1.getSpriteHeight()-(g2.getY()+0.5*g2.getSpriteHeight());
        double dist = Math.sqrt(Math.pow(distanceX,2) + Math.pow(distanceY, 2));
        return dist;
    }

    public void friendlyRemove() {
        if (ghostList.size() > 0) {
            for (Ghost f : friendlyGhostList) {
                int index = getClosestGhost(f);
                double distance = calcDistance(f, ghostList.get(index));
                if (distance < 1.25 * character.getSpriteWidth()) {
                    int coinX = ghostList.get(index).getX();
                    int coinY = ghostList.get(index).getY();
                    coinList.add(new Coin(BitmapFactory.decodeResource(getResources(), R.drawable.spinning_coin_gold), coinX, coinY, 6, 8, System.currentTimeMillis(), background));
                    ghostList.remove(index);
                    sound.playScream();
                    score += 2500;
                    killedGhosts++;
                    health.subtractDamage(2);
                }
            }
        }
    }

    public void friendlyTimeOver() {
        for(int i = 0; i < friendlyGhostList.size(); i++) {
            if (friendlyGhostList.get(i).getTimeFriendly() < System.currentTimeMillis() - 5000) {
                friendlyGhostList.remove(i);
                i--;
            }
        }
    }

    public void createGhostMap() {
        if ((displayScore / 1000)*level  > ghostList.size() + killedGhosts*.568 && ghostList.size()<=maxNumGhosts) {
            Ghost ghost1 = null;
            MegaGhost megaGhost1 = null;
            if (isPacman) {
                ghost1 = generateGhost(blueGhostBit);
                megaGhost1 = generateMegaGhost(blueGhostBit);
            }
            else {
                ghost1 = generateGhost(ghostBit);
                megaGhost1 = generateMegaGhost(megaGhostBit);
            }
            if (rand.nextInt(100)<20 && objectlist.size()<10) {
                int bX = background.getXCoord();
                int bY = background.getYCoord();
                Objects gem =  new Objects(gemBit, rand.nextInt(background.getWidth()-2*gemBit.getWidth())+bX+gemBit.getWidth(), rand.nextInt(background.getHeight()-2*gemBit.getHeight())+bY+gemBit.getHeight(), background);
                while (character.getHitbox().intersects(gem.getxCoord(), gem.getyCoord(), gem.getxCoord()+gem.getWidth(), gem.getyCoord()+gem.getHeight())) {
                    gem =  new Objects(gemBit, rand.nextInt(background.getWidth()-2*gemBit.getWidth())+bX+gemBit.getWidth(), rand.nextInt(background.getHeight()-2*gemBit.getHeight())+bY+gemBit.getHeight(), background);
                }
                objectlist.add(gem);
            }
            ghostList.add(ghost1);
            ghostList.add(megaGhost1);
        }
    }

    public Ghost generateGhost(Bitmap bit) {
        int x = rand.nextInt(background.getWidth() - Math.max(bit.getWidth(), bit.getWidth())) + background.getXCoord();
        int y = rand.nextInt(background.getHeight() - Math.max(bit.getHeight(), bit.getHeight())) + background.getYCoord();
        while (Math.sqrt(Math.pow(x - character.getX(), 2) + Math.pow(y - character.getY(), 2)) < 200) {
            x = rand.nextInt(background.getWidth() - Math.max(bit.getWidth(), bit.getWidth())) + background.getXCoord();
            y = rand.nextInt(background.getHeight() - Math.max(bit.getHeight(), bit.getHeight())) + background.getYCoord();
        }
        int speed = rand.nextInt(maxRandGhostSpeed) + minGhostSpeed;
        double angle = rand.nextDouble() * 6;
        Ghost ghost1 = new Ghost(bit, x, y, 6, 9, 1, 2, 2, 2, 2, speed, angle, background);
        return ghost1;
    }

    public MegaGhost generateMegaGhost(Bitmap bit) {
        int x = rand.nextInt(background.getWidth() - Math.max(bit.getWidth(), bit.getWidth())) + background.getXCoord();
        int y = rand.nextInt(background.getHeight() - Math.max(bit.getHeight(), bit.getHeight())) + background.getYCoord();
        while (Math.sqrt(Math.pow(x - character.getX(), 2) + Math.pow(y - character.getY(), 2)) < 200) {
            x = rand.nextInt(background.getWidth() - Math.max(bit.getWidth(), bit.getWidth())) + background.getXCoord();
            y = rand.nextInt(background.getHeight() - Math.max(bit.getHeight(), bit.getHeight())) + background.getYCoord();
        }
        int speed = rand.nextInt(maxRandGhostSpeed - 1) + minGhostSpeed - 2;
        double angle = rand.nextDouble() * 6;
        MegaGhost ghost1 = new MegaGhost(bit, x, y, 6, 9, 1, 2, 2, 2, 2, speed, angle, background);
        return ghost1;
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
                if (isPacman) {
                    if (ghostList.get(index) instanceof MegaGhost)
                        score+=2500;
                    score+=2500;
                    int coinX = ghostList.get(index).getX();
                    int coinY = ghostList.get(index).getY();
                    ghostList.remove(index);
                    coinList.add(new Coin(BitmapFactory.decodeResource(getResources(), R.drawable.spinning_coin_gold), coinX, coinY, 6, 8, System.currentTimeMillis(), background));
                    sound.playScream();
                    killedGhosts++;
                    health.subtractDamage(2);
                }
                else if (difficulty>1)
                    gameOver = true;
            } else if (Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2)) < 2 * character.getSpriteWidth()) {
                alertList.add(alert);
                if (difficulty>1) {
                    double random = rand.nextDouble();
                    if (isPacman) {
                        if (random < 0.15) {
                            if (ghostList.get(index) instanceof MegaGhost) {
                                gameOver = health.addDamage(2);
                            } else {
                                gameOver = health.addDamage(1);
                            }
                        }
                    } else if (random < .33) {
                        if (ghostList.get(index) instanceof MegaGhost) {
                            gameOver = health.addDamage(2);
                        } else {
                            gameOver = health.addDamage(1);
                        }
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
                    if (f.isSuperBull()) {
                        int coinX = ghostList.get(i).getX();
                        int coinY = ghostList.get(i).getY();
                        coinList.add(new Coin(BitmapFactory.decodeResource(getResources(), R.drawable.spinning_coin_gold), coinX, coinY, 6, 8, System.currentTimeMillis(), background));
                        Ghost ghost = ghostList.remove(i);
                        ghost.setFriendly(true);
                        ghost.setTimeFriendly(System.currentTimeMillis());
                        ghost.setBitmap(greenGhostBit);
                        friendlyGhostList.add(ghost);
                        bulletList.remove(j);
                        i--;
                        j--;
                        sound.playScream();
                        score += 5000;
                        killedGhosts++;
                        health.subtractDamage(2);
                    }
                    else if (ghostList.get(i) instanceof MegaGhost) {
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
            boom.setyCoord(boom.getyCoord()-shift);
            for(Barriers o : barrierList){
                o.setYCoord(o.getYCoord()-shift);
            }
            for (Objects o : objectlist) {
                o.setyCoord(o.getyCoord()-shift);
            }
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
            boom.setxCoord(boom.getxCoord()-shift);
            for(Barriers o : barrierList){
                o.setXCoord(o.getXCoord()-shift);
            }
            for(Objects o : objectlist) {
                o.setxCoord(o.getxCoord() - shift);
            }
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
            boom.setxCoord(boom.getxCoord()+shift);
            for(Barriers o : barrierList){
                o.setXCoord(o.getXCoord()+shift);
            }
            for(Objects o : objectlist) {
                o.setxCoord(o.getxCoord()+shift);
            }
            for(Ghost g : ghostList) {
                g.setX(g.getX() + shift);
            }
            for(Bullet b : bulletList) {
                b.setXCoord(b.getXCoord() + shift);
            }
            for(Coin coin : coinList) {
                coin.setxCoord(coin.getxCoord() + shift);
            }
        }
        else if (c == 'b') {
            int shift = background.getHeight() - 20;
            background.setYCoord(background.getYCoord() + shift);
            boom.setyCoord(boom.getyCoord()+shift);
            for(Barriers o : barrierList){
                o.setYCoord(o.getYCoord()+shift);
            }
            for (Objects o : objectlist) {
                o.setyCoord(o.getyCoord() + shift);
            }
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

    public void setGameOver(boolean bool) {
        gameOver = bool;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    /*
    Method that calls each thing's draw method so as to display each thing on the screen
    Only draws ghosts when they are on the screen
     */
    @Override
    public void draw(Canvas canvas) {
        if(level == 2) canvas.drawARGB(255,60,155,213);
        else if(level == 1) canvas.drawARGB(255,31,110,37);
        else canvas.drawARGB(255,213,193,174);

        if (!gameOver) {
            try {
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
                health.draw(canvas);
                for (Alert element : alertList) {
                    element.draw(canvas);
                }
                if (!barrierMode)
                    layBarrier.draw(canvas);
            /*for(DirectionButton element : layBarrierList) {
                element.draw(canvas);
            }*/
                for (Bullet b : bulletList) {
                    b.draw(canvas);
                }
                for (Ghost g : ghostList) {
                    if (g.getX() <= screenWidth || g.getY() <= screenHeight || g.getX() >= -g.getSpriteWidth() || g.getY() >= -g.getSpriteHeight())
                        g.draw(canvas);
                }
                for (Ghost g : friendlyGhostList) {
                    if (g.getX() <= screenWidth || g.getY() <= screenHeight || g.getX() >= -g.getSpriteWidth() || g.getY() >= -g.getSpriteHeight())
                        g.draw(canvas);
                }
                if (numCoins >= heart.getMinCost()) {
                    heart.draw(canvas);
                }
                if (numCoins >= saiyanButton.getMinCost()) {
                    saiyanButton.draw(canvas);
                }
                if (numCoins >= pacButton.getMinCost()) {
                    pacButton.draw(canvas);
                }

                if (numCoins >= bombButton.getMinCost()) {
                    bombButton.draw(canvas);
                }

                if (numCoins >= bulletButton.getMinCost()) {
                    bulletButton.draw(canvas);
                }
                if (isBomb) {
                    boom.draw(canvas);
                }
                for (int i = 0; i < barrierList.size(); i++) {
                    barrierList.get(i).draw(canvas);
                }

                if (numCoins >= bulletButton.getMinCost()) {
                    bulletButton.draw(canvas);
                }
                if (barrierMode)
                    canvas.drawBitmap(redXBit, layBarrier.getX(), layBarrier.getY(), null);
                canvas.drawText(""+numBarriersCanLay, layBarrier.getX(), layBarrier.getY() + paint.getTextSize() + Math.max(barrierMap.getHeight(),redXBit.getHeight()),paint);
            } catch (Exception e) {
                Log.i("draw", "can't draw right now");
            }
        }
        canvas.drawText(("0000000000" + displayScore).substring(("" + displayScore).length()), health.getX(), health.getY() + health.getHealth().getHeight() + paint.getTextSize(), paint);
        canvas.drawText("Coins: " + numCoins, health.getX(), health.getY() + health.getHealth().getHeight() + 2 * paint.getTextSize(), paint);
        canvas.drawText("F.Bullets: " + numSuperBullets, health.getX(), health.getY() + health.getHealth().getHeight() + 3 * paint.getTextSize(), paint);

        }


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isSaiyan() {
        return isSaiyan;
    }

    public void setSaiyan(boolean saiyan) {
        this.isSaiyan = saiyan;
    }

    public long getPowerUpTime() {
        return powerupTime;
    }

    public void setpowerupTime(int saiyanTime) {
        this.powerupTime = saiyanTime;
    }

    public int getNumCoins() {
        return numCoins;
    }

    public void setNumCoins(int numCoins) {
        this.numCoins = numCoins;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public void setDamage(int x) { health.setDamage(x); }

    public HealthBar getHealth() {
        return health;
    }

    public void setHealth(HealthBar health) { this.health = health; }

    public long getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getDisplayScore() {
        return displayScore;
    }

    public void setDisplayScore(int displayScore) {
        this.displayScore = displayScore;
    }

    public int getKilledGhosts() {
        return killedGhosts;
    }

    public void setKilledGhosts(int killedGhosts) {
        this.killedGhosts = killedGhosts;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getBackgroundX() {
        return background.getXCoord();
    }

    public void setBackgroundX(int x) {
        this.background.setXCoord(x);
    }

    public int getBackgroundY() {
        return background.getYCoord();
    }

    public void setBackgroundY(int y) {
        this.background.setYCoord(y);
    }


    public void recycleBits() {
        saiyanBit.recycle();
        saiyanBit = null;
        characterBit.recycle();
        characterBit = null;
        ghostBit.recycle();
        ghostBit = null;
        megaGhostBit.recycle();
        megaGhostBit = null;
        backgroundBit.recycle();
        backgroundBit = null;
        bulletBit.recycle();
        bulletBit = null;
        saiyanBlastBit.recycle();
        saiyanBlastBit = null;
        heartBit.recycle();
        heartBit = null;
        saiyanButtonBit.recycle();
        saiyanButtonBit = null;
        healthBit.recycle();
        healthBit = null;
        deathBit.recycle();
        deathBit = null;
        upMap.recycle();
        upMap = null;
        downMap.recycle();
        downMap = null;
        leftMap.recycle();
        leftMap = null;
        rightMap.recycle();
        rightMap = null;
        pacmanBit.recycle();
        pacmanBit = null;
        pacmanButtonBit.recycle();
        pacmanButtonBit = null;
        blueGhostBit.recycle();
        blueGhostBit = null;
        bulletButtonBit.recycle();
        bulletButtonBit = null;
        bombButtonBit.recycle();
        bombButtonBit = null;
        explosionBit.recycle();
        explosionBit = null;
        redXBit.recycle();
        redXBit = null;
        gemBit.recycle();
        gemBit = null;
        sound.release();
    }

    public void shutDownThread() {
        thread.setRunning(false);
    }

    public void restartThread() {
        thread.setRunning(true);
    }

}