package rbh9dm.cs2110.virginia.edu.ghost_hunt;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

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

    public HealthBar(Bitmap health, Bitmap death, int x, int y) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.death = death;
        this.sourceRect = new Rect(0,0,x+health.getWidth(),y+health.getHeight());
        this.damage = 0;
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

    public void setDamage(int damage) {
        this.damage = damage;
        if (damage < health.getWidth())
            this.sourceRect.set(x, y,(x+health.getWidth())-damage,y+health.getHeight());
        else
            this.sourceRect.set(x, y, 1 ,y+health.getHeight());
    }

    public void addDamage(int dam) {
        setDamage(getDamage() + dam);
    }

    public void subtractDamage(int dam) {
        setDamage(getDamage() - dam);
    }

    public void clearDamage() {
        setDamage(0);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(death, x, y, null);
        Rect destRect = new Rect(x, y, x+health.getWidth(),y+health.getHeight());
        canvas.drawBitmap(health, sourceRect, destRect, null);
    }

}
