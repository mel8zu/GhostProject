package rbh9dm.cs2110.virginia.edu.ghost_hunt;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Student User on 4/12/2015.
 */
public class HealthBar {

    private int x;
    private int y;
    private Bitmap health;
    private Bitmap death;
    private Rect sourceRect;
    private int damage;
    private int healthLevel;

    public HealthBar(Bitmap health, Bitmap death, int x, int y, int healthLevel) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.death = death;
        this.sourceRect = new Rect(0,0,health.getWidth(),health.getHeight());
        this.damage = 0;
        this.healthLevel = healthLevel;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Bitmap getHealth() {
        return health;
    }

    public void setHealth(Bitmap health) {
        this.health = health;
    }

    public Bitmap getDeath() {
        return death;
    }

    public void setDeath(Bitmap death) {
        this.death = death;
    }

    public Rect getSourceRect() {
        return sourceRect;
    }

    public void setSourceRect(Rect sourceRect) {
        this.sourceRect = sourceRect;
    }

    public int getDamage() {
        return damage;
    }

    public boolean setDamage(int damage) {
        if (damage<=0) {
            this.damage = 0;
            return false;
        }
        else if (damage < health.getWidth()) {
            this.damage = damage;
            this.sourceRect.set(0, 0, health.getWidth() - damage, health.getHeight());
            return false;
        }
        else {
            this.damage = health.getWidth()-1;
            this.sourceRect.set(0, 0, 1, health.getHeight());
            return true;
        }
    }

    public boolean addDamage(int dam) {
        return setDamage(getDamage() + dam);
    }

    public void subtractDamage(int dam) {
        setDamage(getDamage() - dam);
    }

    public void clearDamage() {
        setDamage(0);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(death, x, y, null);
        Rect destRect = new Rect(x, y, x+sourceRect.right,y+health.getHeight());
        canvas.drawBitmap(health, sourceRect, destRect, null);
    }

}
