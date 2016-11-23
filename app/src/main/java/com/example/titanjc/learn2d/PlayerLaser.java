package com.example.titanjc.learn2d;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by TITANJC on 11/23/2016.
 */

public class PlayerLaser implements GameObject {
    private Rect rectangle;

    public PlayerLaser() {
        this.rectangle = new Rect(5,20,10,0);
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
