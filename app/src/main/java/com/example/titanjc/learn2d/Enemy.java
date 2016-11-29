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

    private AnimationManager animationManager;
    private int count = 0;
    private boolean left;

    public Enemy(Bitmap idleImg, Bitmap moveR, Bitmap moveL, Rect rectangle) {
        this.rectangle = rectangle;
        this.left = (Math.random() < 0.5);
        Animation idle = new Animation(new Bitmap[]{idleImg}, 2);
        Animation moveRight = new Animation(new Bitmap[]{moveR}, 2.0f);
        Animation moveLeft = new Animation(new Bitmap[]{moveL}, 2.0f);

        animationManager = new AnimationManager(new Animation[]{idle, moveRight, moveLeft});
    }

    public void move() {
        if (count != MainThread.MAX_FPS * 3) {
            count++;
        } else {
            count = 0;
            left = (Math.random() < 0.5);
        }

        if(rectangle.bottom >= Constants.MOVE_FLOOR) {
            if (left) {
                if (rectangle.left - (Constants.BOT_MOVE_SPEED / MainThread.MAX_FPS) <= 0 ) {
                    left = false;
                    count = MainThread.MAX_FPS * 3;
                } else {
                    rectangle.left -= (Constants.BOT_MOVE_SPEED / MainThread.MAX_FPS);
                    rectangle.right -= (Constants.BOT_MOVE_SPEED / MainThread.MAX_FPS);
                }

            } else {
                if (rectangle.right - (Constants.BOT_MOVE_SPEED / MainThread.MAX_FPS) >= Constants.SCREEN_WIDTH ) {
                    left = true;
                    count = MainThread.MAX_FPS * 3;
                } else {
                    rectangle.left += (Constants.BOT_MOVE_SPEED / MainThread.MAX_FPS);
                    rectangle.right += (Constants.BOT_MOVE_SPEED / MainThread.MAX_FPS);
                }
            }
        } else {
            rectangle.top += ((Constants.BOT_MOVE_SPEED*2) / MainThread.MAX_FPS);
            rectangle.bottom += ((Constants.BOT_MOVE_SPEED*2) / MainThread.MAX_FPS);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        animationManager.draw(canvas, rectangle);
    }

    public boolean playerCollide(RectPlayer player) {
        return Rect.intersects(rectangle, player.getRectangle());
    }

    public boolean shouldFire() {
        return false;
    }

    public BotLaser fire() {
        return new BotLaser(new Rect(rectangle.left, rectangle.top, rectangle.right, rectangle.bottom - 15));
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
