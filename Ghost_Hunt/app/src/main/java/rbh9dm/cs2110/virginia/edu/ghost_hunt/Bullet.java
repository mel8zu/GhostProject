package rbh9dm.cs2110.virginia.edu.ghost_hunt;

import android.graphics.Bitmap;

/**
 * Created by Student User on 4/10/2015.
 */
public class Bullet extends Moveable {

    private double speed; //how fast we want bullet to move
    private Character character; //character shooting the bullet
    private double targetX; //x location of where we want bullet to go towards
    private double targetY; //y location of where we want bullet to go towards
    private double deltaX; //change in x between character & target
    private double deltaY; //change in y between character & target
    private double xVelocity; //velocity in the x direction
    private double yVelocity; //velocity in the y direction
    private double distance; //distance the bullet has travelled
    private double maxDistance;
    private boolean mega;


    /*
    Constructor
     */
    public Bullet(Bitmap bitmap, Character character, double speed, double targetX, double targetY, int maxDistance, boolean mega) {
        super(bitmap, 0, 0);

        this.character = character;
        this.speed = speed;
        this.targetX = targetX;
        this.targetY = targetY;

        setXCoord(character.getX() + character.getSpriteWidth()/2);
        setYCoord(character.getY() + character.getSpriteHeight()/2);
        this.distance = 0;

        this.deltaX = this.targetX - getXCoord();
        this.deltaY = this.targetY - getYCoord();
        double delta = calcDistance(this.deltaX,this.deltaY);
        this.xVelocity = this.speed * (this.deltaX/delta);
        this.yVelocity = this.speed * this.deltaY/delta;
        this.maxDistance = maxDistance;
        this.mega = mega;
    }
    /*
    Getters & Setters
     */
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public double getTargetX() {
        return targetX;
    }

    public void setTargetX(double targetX) {
        this.targetX = targetX;
    }

    public double getTargetY() {
        return targetY;
    }

    public void setTargetY(double targetY) {
        this.targetY = targetY;
    }

    public double getDeltaX() {
        return deltaX;
    }

    public void setDeltaX(double deltaX) {
        this.deltaX = deltaX;
    }

    public double getDeltaY() {
        return deltaY;
    }

    public void setDeltaY(double deltaY) {
        this.deltaY = deltaY;
    }

    public double getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    public double getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(double maxDistance) {
        this.maxDistance = maxDistance;
    }

    public boolean isMega() {
        return mega;
    }

    public void setMega(boolean mega) {
        this.mega = mega;
    }

    /*
            Performs pythagorean theorem given change in x and change in y
             */
    public double calcDistance(double delta1, double delta2) {
        return Math.sqrt(Math.pow(delta1,2)+Math.pow(delta2,2));
    }
    /*
    Updates location and updates distance travelled
     */
    public void update() {
        setXCoord(getXCoord() + (int) xVelocity);
        setYCoord(getYCoord() + (int) yVelocity);
        distance+=speed;
    }


}
