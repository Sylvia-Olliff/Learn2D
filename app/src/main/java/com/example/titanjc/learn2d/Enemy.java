package com.example.titanjc.learn2d;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by TITANJC on 11/23/2016.
 */

public abstract class Enemy implements GameObject {
    protected Rect rectangle;

    private Animation idle;
    private Animation moveLeft;
    private Animation moveRight;

    private AnimationManager animationManager;

    public Enemy(Bitmap idleImg, Bitmap moveR, Bitmap moveL, Rect rectangle) {
        this.rectangle = rectangle;

        idle = new Animation(new Bitmap[]{idleImg}, 2);
        moveRight = new Animation(new Bitmap[]{moveR}, 2.0f);
        moveLeft = new Animation(new Bitmap[]{moveL}, 2.0f);

        animationManager = new AnimationManager(new Animation[]{idle, moveRight, moveLeft});
    }

    public void incrementY(float y) {
        rectangle.top += y;
        rectangle.bottom += y;
    }

    @Override
    public void draw(Canvas canvas) {
        animationManager.draw(canvas, rectangle);
    }

    @Override
    public void update() { animationManager.update(); }

    public void update(Point point) {
        float oldLeft = rectangle.left;

        rectangle.set(point.x - rectangle.width()/2, point.y - rectangle.height()/2, point.x + rectangle.width()/2, point.y + rectangle.height()/2);

        int state = 0;
        if (rectangle.left - oldLeft > 15)
            state = 1;
        else if (rectangle.left - oldLeft < -15)
            state = 2;

        animationManager.playAnim(state);
        animationManager.update();
    }
}
