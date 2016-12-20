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
    private int frameCount;
    private LaserFlash laserFlash;
    public Rect getRectangle() {
        return rectangle;
    }

    public PlayerLaser(Rect rectangle) {
        this.rectangle = rectangle;
        Rect playerFlashRect = new Rect();

        playerFlashRect.right = rectangle.right + Constants.LASER_FLASH_SIZE;
        playerFlashRect.bottom = rectangle.bottom + Constants.LASER_FLASH_SIZE;
        playerFlashRect.left = playerFlashRect.right - (Constants.LASER_FLASH_SIZE*2);
        playerFlashRect.top = playerFlashRect.bottom - (Constants.LASER_FLASH_SIZE*2);
        this.laserFlash = new LaserFlash(playerFlashRect, BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.laser_red_shot));

        frameCount = 0;
        Bitmap laser = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.laser_red);
        Animation laserBolt = new Animation(new Bitmap[]{laser}, 0.5f);

        animationManager = new AnimationManager(new Animation[]{laserBolt});

    }

    public void move() {
        rectangle.top -= ((Constants.LASER_BOLT_SPEED) / MainThread.MAX_FPS);
        rectangle.bottom -= ((Constants.LASER_BOLT_SPEED) / MainThread.MAX_FPS);
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

    public boolean botShot(Enemy enemy) {
        return Rect.intersects(rectangle, enemy.getRectangle());
    }
}
