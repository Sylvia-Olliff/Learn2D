package com.example.titanjc.learn2d;

import android.content.Intent;
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
    private Rect highButton;

    private Drawable bg;
    private Drawable startButtonImg;
    private Drawable exitButtonImg;
    private Drawable highButtonImg;

    public MainMenuScene() {
        int buttonEdgeLeft = Constants.SCREEN_WIDTH/7;
        int buttonEdgeRight = 6*Constants.SCREEN_WIDTH/7;
        startButton = new Rect(buttonEdgeLeft, Constants.SCREEN_HEIGHT/8, buttonEdgeRight, 2*Constants.SCREEN_HEIGHT/8);
        exitButton = new Rect(buttonEdgeLeft, 6*Constants.SCREEN_HEIGHT/8, buttonEdgeRight, 7*Constants.SCREEN_HEIGHT/8);
        highButton = new Rect(buttonEdgeLeft, 4*Constants.SCREEN_HEIGHT/8, buttonEdgeRight, 5*Constants.SCREEN_HEIGHT/8);

        bg = ContextCompat.getDrawable( Constants.CURRENT_CONTEXT, R.drawable.star_background);
        bg.setBounds(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        //L,T,R,B
        startButtonImg = ContextCompat.getDrawable( Constants.CURRENT_CONTEXT, R.drawable.button_start);
        startButtonImg.setBounds(startButton);

        exitButtonImg = ContextCompat.getDrawable( Constants.CURRENT_CONTEXT, R.drawable.button_exit);
        exitButtonImg.setBounds(exitButton);

        highButtonImg = ContextCompat.getDrawable( Constants.CURRENT_CONTEXT, R.drawable.button_high);
        highButtonImg.setBounds(highButton);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        bg.draw(canvas);
        startButtonImg.draw(canvas);
        exitButtonImg.draw(canvas);
        highButtonImg.draw(canvas);
    }

    @Override
    public void terminate(String scene) {
        SceneManager.changeScene(scene);
    }

    @Override
    public void recieveTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            if(startButton.contains(x, y)) {
                terminate("GameplayScene");
            }

            if(exitButton.contains(x, y)) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                Constants.CURRENT_CONTEXT.startActivity(intent);
            }

            if (highButton.contains(x, y)) {
                terminate("HighScoreScene");
            }
        }
    }


}
