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
    private Rect exitButton;

    private int buttonEdgeLeft;
    private int buttonEdgeRight;

    private Drawable bg;
    private Drawable startButtonImg;
    private Drawable exitButtonImg;

    public MainMenuScene() {
        buttonEdgeLeft = Constants.SCREEN_WIDTH/7;
        buttonEdgeRight = 6*Constants.SCREEN_WIDTH/7;
        startButton = new Rect(buttonEdgeLeft, Constants.SCREEN_HEIGHT/8, buttonEdgeRight, 2*Constants.SCREEN_HEIGHT/8);
        exitButton = new Rect(buttonEdgeLeft, 6*Constants.SCREEN_HEIGHT/8, buttonEdgeRight, 7*Constants.SCREEN_HEIGHT/8);

        bg = ContextCompat.getDrawable( Constants.CURRENT_CONTEXT, R.drawable.star_background);
        bg.setBounds(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        //L,T,R,B
        startButtonImg = ContextCompat.getDrawable( Constants.CURRENT_CONTEXT, R.drawable.button_start);
        startButtonImg.setBounds(startButton);

        exitButtonImg = ContextCompat.getDrawable( Constants.CURRENT_CONTEXT, R.drawable.button_exit);
        exitButtonImg.setBounds(exitButton);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        bg.draw(canvas);
        startButtonImg.draw(canvas);
        exitButtonImg.draw(canvas);
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
