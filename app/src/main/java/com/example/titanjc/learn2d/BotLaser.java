package com.example.titanjc.learn2d;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by TITANJC on 11/23/2016.
 */

public class BotLaser implements GameObject {
    private Rect rectangle;
    private AnimationManager animationManager;
    private LaserFlash laserFlash;
    private int frameCount;
    public Rect getRectangle() {
        return rectangle;
    }

    public BotLaser(Rect rectangle) {
        this.rectangle = rectangle;
        Rect botFlashRect = new Rect();
        botFlashRect.left = rectangle.left - Constants.LASER_FLASH_SIZE;
        botFlashRect.top = rectangle.top - Constants.LASER_FLASH_SIZE;
        botFlashRect.right = botFlashRect.left + (Constants.LASER_FLASH_SIZE*2);
        botFlashRect.bottom = botFlashRect.top + (Constants.LASER_FLASH_SIZE*2);
        this.laserFlash = new LaserFlash(botFlashRect, BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.laser_green_shot));
        frameCount = 0;
        Bitmap laser = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.laser_green);
        Animation laserBolt = new Animation(new Bitmap[]{laser}, 0.5f);

        animationManager = new AnimationManager(new Animation[]{ laserBolt});
    }

    public void move() {
        rectangle.top += ((Constants.LASER_BOLT_SPEED) / MainThread.MAX_FPS);
        rectangle.bottom += ((Constants.LASER_BOLT_SPEED) / MainThread.MAX_FPS);
    }

    @Override
    public void draw(Canvas canvas) {
        if (frameCount < 5) {
            laserFlash.draw(canvas);
            frameCount++;
        } else {
            laserFlash = null;
        }
        animationManager.draw(canvas, rectangle);
    }

    @Override
    public void update() {
        if (frameCount < 5) {
            laserFlash.update();
        }
        animationManager.playAnim(0);
        animationManager.update();
    }

    public void update(Point point) {
        rectangle.set(point.x - rectangle.width()/2, point.y - rectangle.height()/2, point.x + rectangle.width()/2, point.y + rectangle.height()/2);

        animationManager.playAnim(0);
        animationManager.update();
    }

    public boolean playerShot(RectPlayer player) {
        return Rect.intersects(rectangle, player.getRectangle());
    }


}
