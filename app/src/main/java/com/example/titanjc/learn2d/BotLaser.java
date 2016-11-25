package com.example.titanjc.learn2d;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by TITANJC on 11/23/2016.
 */

public class BotLaser implements GameObject {
    private Rect rectangle;

    public BotLaser(Rect rectangle) {
        this.rectangle = rectangle;
    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void update() {

    }

    public Rect getRectangle() {
        return rectangle;
    }
}
