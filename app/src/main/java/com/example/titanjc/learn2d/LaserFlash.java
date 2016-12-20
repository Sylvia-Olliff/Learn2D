package com.example.titanjc.learn2d;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by TITANJC on 12/16/2016.
 */

public class LaserFlash implements GameObject {
    private Rect rectangle;
    private AnimationManager animationManager;


    public LaserFlash(Rect rectangle, Bitmap flash) {
        this.rectangle = rectangle;

        Animation laserFlash = new Animation(new Bitmap[]{flash}, 0.5f);
        animationManager = new AnimationManager(new Animation[]{laserFlash});
    }

    @Override
    public void draw(Canvas canvas) {
        animationManager.draw(canvas, rectangle);
    }

    @Override
    public void update() {
        animationManager.playAnim(0);
        animationManager.update();
    }
}
