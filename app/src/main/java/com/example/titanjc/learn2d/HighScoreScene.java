package com.example.titanjc.learn2d;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;

/**
 * Created by TITANJC on 12/19/2016.
 */

public class HighScoreScene implements Scene {
    private Drawable bg;
    private Drawable exitButtonImg;
    private Rect exitButton;
    private Paint scorePaint;
    private int[] highScores;

    public HighScoreScene() {
        int buttonEdgeLeft = Constants.SCREEN_WIDTH/7;
        int buttonEdgeRight = 6*Constants.SCREEN_WIDTH/7;
        highScores = new int[10];

        highScores = HighScoreHandler.getHighScores();

        exitButton = new Rect(buttonEdgeLeft, 6*Constants.SCREEN_HEIGHT/8, buttonEdgeRight, 7*Constants.SCREEN_HEIGHT/8);

        exitButtonImg = ContextCompat.getDrawable( Constants.CURRENT_CONTEXT, R.drawable.button_exit);
        exitButtonImg.setBounds(exitButton);

        bg = ContextCompat.getDrawable( Constants.CURRENT_CONTEXT, R.drawable.star_background);
        bg.setBounds(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
    }

    @Override
    public void update() {
        //TODO: Change to check boolean for update to improve performance
        highScores = HighScoreHandler.getHighScores();
    }

    @Override
    public void draw(Canvas canvas) {
        bg.draw(canvas);
        exitButtonImg.draw(canvas);

        scorePaint.setTextSize(100);
        scorePaint.setColor(Color.MAGENTA);
        for (int i = 0; i < highScores.length; i++) {
            canvas.drawText(i + ": " + highScores[i], Constants.SCREEN_WIDTH/2 - 50, ((i + 1) * 50) + scorePaint.descent() - scorePaint.ascent(), scorePaint);
        }
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

            if (exitButton.contains(x, y)) {
                terminate("MainMenuScene");
            }
        }

    }
}
