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
    private DirectionButton layBarrier;
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
    private long powerupTime;
    private boolean isSaiyan;
    private boolean isPacman;
    private boolean isBomb;
    private BuyButton bulletButton;
    private long powerupTime;
    private boolean isSaiyan;
    private boolean isPacman;
    private Random rand;
    private int numSuperBullets;
    private ArrayList<Ghost> friendlyGhostList;

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
    private Alert alert;

    /*
    Constructor: where the stuff that appears on screen is declared
     */
    public GameView(Context context, int level, int difficulty) {
        super(context);
        this.difficulty = difficulty;
        this.level = level;
        getHolder().addCallback(this);
        rand = new Random();

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

        bombButton = new BuyButton(bombButtonBit, screenWidth - bombButtonBit.getWidth() - 10, screenHeight/2 - 40 - heartBit.getHeight() - saiyanButtonBit.getHeight() - pacmanButtonBit.getHeight(), 1, System.currentTimeMillis());

        bulletButton = new BuyButton(bulletButtonBit,screenWidth - pacmanButtonBit.getWidth() - 10, screenHeight/2 - 40 - heartBit.getHeight() - saiyanButtonBit.getHeight() - pacmanButtonBit.getHeight(), 1, System.currentTimeMillis());


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
        background = new Background(backgroundBit, 0, 0, 5);
        Objects gem1 = new Objects(BitmapFactory.decodeResource(getResources(), R.drawable.gemresized), 400, 400, background);

        bulletList = new ArrayList<Bullet>();
        ghostList = new ArrayList<Ghost>();
        coinList = new ArrayList<Coin>();
        friendlyGhostList = new ArrayList<Ghost>();
        initCreateGhosts();
        objectlist = new ArrayList<Objects>();
        initCreateGems();




        barrierList = new ArrayList<Barriers>();
        alertList = new ArrayList<Alert>();

        alert = new Alert(710,10,BitmapFactory.decodeResource(getResources(), R.drawable.alerti));
        initCreateGhosts();



        down = new DirectionButton(downMap , (int) (screenWidth/2 - 0.5*downMap.getWidth()), screenHeight - downMap.getHeight() - 25);
        up = new DirectionButton(upMap , (int) (screenWidth/2 - 0.5*upMap.getWidth()), down.getY() - downMap.getHeight() - 25);
        left = new DirectionButton(leftMap , down.getX() - leftMap.getWidth() - 10, screenHeight - leftMap.getHeight() - 25);
        right = new DirectionButton(rightMap , down.getX() + downMap.getWidth() + 10, screenHeight - rightMap.getHeight() - 25);
        layBarrier = new DirectionButton(barrierMap , 710, 80);

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

    public void initCreateGems(){
        Random generator = new Random();
        int max = 20;
        int bX = background.getXCoord();
        int bY = background.getYCoord();
        for(int i = 0; i < 8; i++){

            Objects gem =  new Objects(BitmapFactory.decodeResource(getResources(), R.drawable.gemresized), generator.nextInt(bX + background.getWidth()-40), generator.nextInt(bY + background.getHeight()-40), background);
            if(character.getHitbox().intersects(gem.getxCoord(), gem.getyCoord(), gem.getxCoord()+gem.getWidth(), gem.getyCoord()+gem.getHeight())==false);

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
            layBarrier.handleActionDown((int) event.getX(), (int) event.getY());
            if(layBarrier.isTouched()){
                if(level==1){
                    barrierList.add(new Barriers(BitmapFactory.decodeResource(getResources(), R.drawable.smallstone), character.getX(), character.getY()+100,5));
                }
                else if(level==2){
                    barrierList.add(new Barriers(BitmapFactory.decodeResource(getResources(), R.drawable.iceresized), character.getX(), character.getY()+100,5));
                }
                else{
                    barrierList.add(new Barriers(BitmapFactory.decodeResource(getResources(), R.drawable.cactusresizedd), character.getX(), character.getY()+100,5));
                }
            }
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
                            heart.handleActionDown((int) event.getX(), (int) event.getY());
                            bulletButton.handleActionDown((int) event.getX(), (int) event.getY());
                            saiyanButton.handleActionDown((int) event.getX(), (int) event.getY());
                            pacButton.handleActionDown((int) event.getX(), (int) event.getY());
                            bombButton.handleActionDown((int) event.getX(), (int) event.getY());
                            if ((numCoins >= heart.getMinCost() && heart.isTouched())  ) {
                                if (System.currentTimeMillis() > heart.getLastBuy() + 1000) {
                                    health.subtractDamage(5);
                                    numCoins--;
                                    heart.setLastBuy(System.currentTimeMillis());
                                }
                            }
                            else if ((numCoins >= bulletButton.getMinCost() && bulletButton.isTouched())  ) {
                                if (System.currentTimeMillis() > bulletButton.getLastBuy() + 1000) {
                                    numCoins--;
                                    numSuperBullets++;
                                    bulletButton.setLastBuy(System.currentTimeMillis());
                                }
                            }
                            else if ((numCoins >= saiyanButton.getMinCost() && saiyanButton.isTouched())  ) {
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

                                    character.setSpriteWidth(character.getBitmap().getWidth()/character.getFrameNr());
                                    character.setSpriteHeight(character.getBitmap().getHeight());

                                    character.setSpriteWidth(character.getBitmap().getWidth() / character.getFrameNr());;
                                    character.setSpriteHeight(character.getBitmap().getHeight());;

                                    character.setSourceRect(new Rect(0,0,character.getSpriteWidth(),character.getSpriteHeight()));
                                    background.setSpeed(background.getSpeed() + 5);
                                    for(Ghost g:ghostList) {
                                        if (g instanceof MegaGhost)
                                            g.setBitmap(megaGhostBit);
                                        else
                                            g.setBitmap(ghostBit);
                                    }
                                    numCoins-=1;
                                    isSaiyan = true;
                                    isPacman = false;
                                    isBomb = false;
                                    saiyanButton.setLastBuy(System.currentTimeMillis());
                                }
                            }
                            else if ((numCoins >= pacButton.getMinCost() && pacButton.isTouched())  ) {
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
                                    character.setSpriteWidth(character.getBitmap().getWidth()/character.getFrameNr());
                                    character.setSpriteHeight(character.getBitmap().getHeight());
                                    character.setSourceRect(new Rect(0,0,character.getSpriteWidth(),character.getSpriteHeight()));
                                    for(Ghost g:ghostList) {
                                        g.setBitmap(blueGhostBit);
                                    }
                                    numCoins -= 1;
                                    isPacman = true;
                                    isSaiyan = false;
                                    isBomb = false;
                                    pacButton.setLastBuy(System.currentTimeMillis());
                                }
                            }
                            else if((numCoins >= bombButton.getMinCost() && bombButton.isTouched())){
                                if(System.currentTimeMillis() > bombButton.getLastBuy() + 1000) {
                                    isBomb = true;
                                    powerupTime = System.currentTimeMillis();
                                    //insert sound
                                    character.setBitmap(explosionBit);
                                    character.setFrameNr(40);
                                    character.setCurrentFrame(0);
                                    character.setSpriteWidth(character.getBitmap().getWidth()/character.getFrameNr());
                                    character.setSpriteHeight(character.getBitmap().getHeight());
                                    character.setSourceRect(new Rect(0,0,character.getSpriteWidth(),character.getSpriteHeight()));
                                    numCoins -= 1;
                                    isPacman = false;
                                    isSaiyan = false;
                                    getBombGhosts();
                                    bombButton.setLastBuy(System.currentTimeMillis());

                                }
                            }

                            else if (System.currentTimeMillis() > lastShot + 1000 && !isPacman) {
                                if (isSaiyan) {
                                    bulletList.add(new Bullet(saiyanBlastBit, character, 22, event.getX(), event.getY(), 800, true, false));
                                    sound.playLaser();
                                    lastShot = System.currentTimeMillis();
                                }
                                else if (numSuperBullets>0) {
                                    numSuperBullets--;
                                    bulletList.add(new Bullet(bulletBit, character, 13, event.getX(), event.getY(), 550, false, true));
                                    sound.playGun();
                                    lastShot = System.currentTimeMillis();
                                }
                                else  {
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
                    character.setSpriteHeight(character.getBitmap().getHeight());;
                    character.setSourceRect(new Rect(0,0,character.getSpriteWidth(),character.getSpriteHeight()));
                    if (isSaiyan) {
                        background.setSpeed(background.getSpeed() - 5);
                        isSaiyan = false;
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
            if(isBomb){
                if(System.currentTimeMillis() > powerupTime + 1000){
                    if(isSaiyan|| isPacman){
                        isBomb = false;
                    }
                    else {


                        character.setBitmap(characterBit);
                        character.setFrameNr(14);
                        character.setNumStill(6);
                        character.setNumDown(2);
                        character.setNumLeft(2);
                        character.setNumUp(2);
                        character.setNumRight(2);
                        character.setCurrentFrame(0);
                        character.setSpriteWidth(character.getBitmap().getWidth() / character.getFrameNr());
                        ;
                        character.setSpriteHeight(character.getBitmap().getHeight());
                        ;
                        character.setSourceRect(new Rect(0, 0, character.getSpriteWidth(), character.getSpriteHeight()));
                        isBomb = false;
                    }
                }
            }

            for(Barriers barrier: barrierList){
                for(Ghost ghost : ghostList){
                    if(ghost.getHitbox().intersects((barrier.getXCoord()),barrier.getYCoord(), barrier.getXCoord()+barrier.getWidth(), barrier.getYCoord()+barrier.getHeight())) {
                        if(ghost.isPosX()){
                            ghost.setX(ghost.getX()-1);
                            ghost.negXVelocity();

                        }
                        else if(ghost.isPosX()==false){
                            ghost.setX(ghost.getX()+1);
                            ghost.posXVelocity();
                        }
                        if(ghost.isPosY()){
                            ghost.setY(ghost.getY()+1);
                            ghost.negYVelocity();
                        }
                        else if(ghost.isPosY()==false){
                            ghost.setY(ghost.getY()-1);
                            ghost.posYVelocity();
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
            character.update(System.currentTimeMillis(), background.getDirection());
            walkOffScreen();
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
            play.updateHighScore(("0000000000"+displayScore).substring((""+displayScore).length()));
            recycleBits();
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

            distanceX = ghostList.get(i).getX()+0.5*ghostList.get(i).getSpriteWidth()-(character.getX()+0.5*character.getSpriteWidth());
            distanceY = ghostList.get(i).getY()+0.5*ghostList.get(i).getSpriteHeight()-(character.getY()+0.5*character.getSpriteHeight());
            Double dist = Math.sqrt(Math.pow(distanceX,2) + Math.pow(distanceY, 2));
            if(dist<350) {
               ghostList.remove(i);
               killedGhosts++;
               score += 1000;
               i--;
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
        if ((displayScore / 400)*level  > ghostList.size() + killedGhosts*.568 && ghostList.size()<=(5*difficulty)) {
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
        int speed = rand.nextInt(3) + difficulty * 2 + 2;
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
        int speed = rand.nextInt(3) + difficulty * 2 + 2;
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
                else if (isBomb){

                }
                else
                    gameOver = true;
            } else if (Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2)) < 2 * character.getSpriteWidth()) {
                double random = rand.nextDouble();
                if (isPacman) {
                    if (random < 0.15) {
                        if (ghostList.get(index) instanceof MegaGhost) {
                            gameOver = health.addDamage(2 * difficulty);
                            alertList.add(alert);
                        } else {
                            gameOver = health.addDamage(difficulty);
                            alertList.add(alert);
                        }
                    }
                }
                else if (random < .33) {
                    if (ghostList.get(index) instanceof MegaGhost) {
                        gameOver = health.addDamage(2 * difficulty);
                        alertList.add(alert);
                    } else {
                        gameOver = health.addDamage(difficulty);
                        alertList.add(alert);
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
            for(Barriers o : barrierList){
                o.setYCoord(o.getYCoord()-shift);
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
            for(Barriers o : barrierList){
                o.setXCoord(o.getXCoord()-shift);
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
            for(Barriers o : barrierList){
                o.setYCoord(o.getXCoord()+shift);
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
            for(Barriers o : barrierList){
                o.setYCoord(o.getYCoord()+shift);
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

    /*
    Method that calls each thing's draw method so as to display each thing on the screen
    Only draws ghosts when they are on the screen
     */
    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        if (!gameOver) {
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
            for(Alert element : alertList){
                element.draw(canvas);
            }
            for(Barriers element : barrierList){
                element.draw(canvas);
            }

            layBarrier.draw(canvas);

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

            if (numCoins >= bulletButton.getMinCost()) {
                bulletButton.draw(canvas);

            }
        }
        canvas.drawText(("0000000000"+displayScore).substring((""+displayScore).length()),health.getX(), health.getY() + health.getHealth().getHeight() + paint.getTextSize() , paint);
        canvas.drawText(""+numCoins,health.getX(), health.getY() + health.getHealth().getHeight() + 2*paint.getTextSize() , paint);
        canvas.drawText(""+numSuperBullets, health.getX(), health.getY() + health.getHealth().getHeight() + 3*paint.getTextSize(), paint);

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
    }

    public void shutDownThread() {
        thread.setRunning(false);
    }

    public void restartThread() {
        thread.setRunning(true);
    }
}