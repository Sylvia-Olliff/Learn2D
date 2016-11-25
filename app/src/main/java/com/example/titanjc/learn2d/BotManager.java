package com.example.titanjc.learn2d;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by TITANJC on 11/23/2016.
 */

public class BotManager {
    private ArrayList<Enemy> enemies;
    private ArrayList<BotLaser> botLasers;

    private long startTime;
    private long initTime;

    private int score;

    public BotManager() {
        enemies = new ArrayList<>();
        botLasers = new ArrayList<>();
        Constants.MOVE_FLOOR = (Constants.SCREEN_HEIGHT/3);
        Constants.BOT_MOVE_SPEED = 180;
    }

    public void update() {
        if(startTime < Constants.INIT_TIME)
            startTime = Constants.INIT_TIME;

        int elapsedTime = (int) (System.currentTimeMillis() - startTime);

        startTime = System.currentTimeMillis();

        //TODO: Each enemy should have a random chance of firing. Enemy should control the chance, call the method on the enemy then call fire if returns true
        for(Enemy enemy : enemies) {
            enemy.move();
            if(enemy.shouldFire()) {
                enemy.fire();
            }
        }

        if (shouldSpawn()) {
            //TODO Change spawn type to random
            spawn(1);
        }
    }

    public void draw(Canvas canvas) {
        for (Enemy enemy : enemies) {
            enemy.draw(canvas);
        }

//        Paint paint = new Paint();
//        paint.setTextSize(100);
//        paint.setColor(Color.MAGENTA);
//        canvas.drawText("Score: " + score, 50, 50 + paint.descent() - paint.ascent(), paint);
    }

    private boolean shouldSpawn() {
        if (enemies.size() < 5) {
            //TODO: Add more random generation. for now always keep 1 enemies
            return true;
        } else {
            return false;
        }
    }

    private void spawn(int type) {

        Bitmap idleImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.enemy_ship);
        Bitmap walkR = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.enemy_ship);
        Bitmap walkL = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.enemy_ship);
        Bitmap flash = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.laser_green_shot);
        Bitmap laser = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.laser_green);

        int xStart = (int) (Math.random()*(Constants.SCREEN_WIDTH ));

        if (type == 1) {
            enemies.add(0, new BotNormal(idleImg, walkR, walkL, flash, laser, new Rect(110, 110, 260, 260)));
            Random r = new Random();
            enemies.get(0).update(new Point(r.nextInt(Constants.SCREEN_WIDTH-1) + 1, -Constants.SCREEN_HEIGHT/4));
        } else if(type == 2) {
            //TODO: Add UFO spawning
            enemies.add(0, new BotNormal(idleImg, walkR, walkL, flash, laser, new Rect(200, 200, xStart, 200)));
        }
    }

    public boolean playerCollide(RectPlayer player) {
        for (Enemy enemy : enemies) {
            if (enemy.playerCollide(player))
                return true;
        }
        return false;
    }
}
