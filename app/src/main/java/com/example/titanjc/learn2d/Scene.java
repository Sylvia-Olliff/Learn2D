package com.example.titanjc.learn2d;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by TITANJC on 11/21/2016.
 */

public interface Scene {
    public void update();
    public void draw(Canvas canvas);
    public void terminate(String scene);
    public void recieveTouch(MotionEvent event);
}
