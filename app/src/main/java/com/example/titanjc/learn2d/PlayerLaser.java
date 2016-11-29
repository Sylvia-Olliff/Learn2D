package com.example.titanjc.learn2d;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by TITANJC on 11/23/2016.
 */

public class PlayerLaser implements GameObject {
    private Rect rectangle;
    private AnimationManager animationManager;
    private int count = 0;
    private Animation laserFlash;
    public Rect getRectangle() {
        return rectangle;
    }

    public PlayerLaser(Rect rectangle) {
        this.rectangle = rectangle;
        Bitmap flash = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.laser_red_shot);
        Bitmap laser = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.laser_red);

        this.laserFlash = new Animation(new Bitmap[]{flash}, 0.5f);
        Animation laserBolt = new Animation(new Bitmap[]{laser}, 0.5f);

        animationManager = new AnimationManager(new Animation[]{laserFlash, laserBolt});

    }

    public void move() {
        rectangle.top -= ((Constants.LASER_BOLT_SPEED) / MainThread.MAX_FPS);
        rectangle.bottom -= ((Constants.LASER_BOLT_SPEED) / MainThread.MAX_FPS);
    }

    @Override
    public void draw(Canvas canvas) {
        if(count != MainThread.MAX_FPS/2) {
            count++;
        } else {
            laserFlash.stop();
        }
        animationManager.draw(canvas, rectangle);
    }

    @Override
    public void update() {
        animationManager.playAnim(1);
        animationManager.update();
    }

    public void update(Point point) {

        rectangle.set(point.x - rectangle.width()/2, point.y - rectangle.height()/2, point.x + rectangle.width()/2, point.y + rectangle.height()/2);

        animationManager.playAnim(1);
        animationManager.update();
    }
}
