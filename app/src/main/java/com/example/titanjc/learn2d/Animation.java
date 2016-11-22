package com.example.titanjc.learn2d;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.provider.Settings;

/**
 * Created by TITANJC on 11/21/2016.
 */

public class Animation {
    private Bitmap[] frames;
    private int frameIndex;

    private boolean isPlaying = false;
    public boolean isPlaying() {return this.isPlaying;}
    public void play() {
        this.isPlaying = true;
        this.frameIndex = 0;
        this.lastFrame = System.currentTimeMillis();
    }
    public void stop() {this.isPlaying = false;}

    private float frameTime;
    private long lastFrame;

    public Animation(Bitmap[] frames, float animationTime) {
        this.frames = frames;
        frameIndex = 0;
        frameTime = animationTime/frames.length;

        lastFrame = System.currentTimeMillis();
    }

    public void draw(Canvas canvas, Rect destination) {
        if(!isPlaying)
            return;

        scaleRect(destination);

        canvas.drawBitmap(frames[frameIndex], null, destination, new Paint());
    }

    private void scaleRect(Rect rect) {
        float whRatio = (float) (frames[frameIndex].getWidth()) / (float) (frames[frameIndex].getHeight());

        if (rect.width() > rect.height()) {
            rect.left = rect.right - (int) (rect.height() * whRatio);
        } else {
            rect.top = rect.bottom - (int) (rect.width() * (1/whRatio));
        }
    }

    public void update() {
        if(!isPlaying)
            return;

        if(System.currentTimeMillis() - lastFrame > frameTime * 1000) {
            frameIndex++;
            frameIndex = frameIndex >= frames.length ? 0 : frameIndex;
            lastFrame = System.currentTimeMillis();
        }
    }

}
