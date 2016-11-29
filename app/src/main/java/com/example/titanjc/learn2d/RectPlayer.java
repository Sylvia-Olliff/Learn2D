package com.example.titanjc.learn2d;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by TITANJC on 11/14/2016.
 */

public class RectPlayer implements GameObject {

    private Rect rectangle;

    private Animation idle;
    private Animation walkRight;
    private Animation walkLeft;
    private AnimationManager animationManager;

    public Rect getRectangle() {
        return rectangle;
    }

    public RectPlayer(Rect rectangle) {
        this.rectangle = rectangle;

        Bitmap idleImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player);
        Bitmap walkR = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_right);
        Bitmap walkL = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_left);

        idle = new Animation(new Bitmap[]{idleImg}, 2);
        walkRight = new Animation(new Bitmap[]{walkR}, 2.0f);
        walkLeft = new Animation(new Bitmap[]{walkL}, 2.0f);



        animationManager = new AnimationManager(new Animation[]{idle, walkRight, walkLeft});
    }

    @Override
    public void draw(Canvas canvas) {
        animationManager.draw(canvas, rectangle);
    }

    @Override
    public void update() {
        animationManager.update();
    }

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
