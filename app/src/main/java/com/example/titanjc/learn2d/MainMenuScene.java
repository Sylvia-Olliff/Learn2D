package com.example.titanjc.learn2d;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;

/**
 * Created by TITANJC on 12/9/2016.
 */

public class MainMenuScene implements Scene {
    private Rect startButton;

    private int buttonEdgeLeft;
    private int buttonEdgeRight;

    public MainMenuScene() {
        buttonEdgeLeft = Constants.SCREEN_WIDTH/7;
        buttonEdgeRight = 6*Constants.SCREEN_WIDTH/7;
        startButton = new Rect(buttonEdgeLeft, Constants.SCREEN_HEIGHT/8, buttonEdgeRight, (2*Constants.SCREEN_HEIGHT/8));
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        Drawable bg = ContextCompat.getDrawable( Constants.CURRENT_CONTEXT, R.drawable.star_background);
        bg.setBounds(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        bg.draw(canvas);

        Drawable testButton = ContextCompat.getDrawable( Constants.CURRENT_CONTEXT, R.drawable.button_start);
        //L,T,R,B
        testButton.setBounds(buttonEdgeLeft, Constants.SCREEN_HEIGHT/8, buttonEdgeRight, (2*Constants.SCREEN_HEIGHT/8));
        testButton.draw(canvas);
    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 1;
    }

    @Override
    public void recieveTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            if(startButton.contains(x, y)) {
                terminate();
            }
        }
    }


}
