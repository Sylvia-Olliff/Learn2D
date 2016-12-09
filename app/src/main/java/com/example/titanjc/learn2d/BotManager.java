package com.example.titanjc.learn2d;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by TITANJC on 11/23/2016.
 */

public class BotManager {
    private ArrayList<Enemy> enemies;
    private ArrayList<BotLaser> botLasers;
    private ArrayList<PlayerLaser> playerLasers;

    private long startTime;
    private long initTime;

    private int score;
    public int getScore() {return score;}

    public BotManager() {
        enemies = new ArrayList<>();
        botLasers = new ArrayList<>();
        playerLasers = new ArrayList<>();
        Constants.MOVE_FLOOR = (Constants.SCREEN_HEIGHT/3);
        Constants.BOT_MOVE_SPEED = 180;
        Constants.LASER_BOLT_SPEED = 290;
        score = 0;
    }

    public void update() {
        if(startTime < Constants.INIT_TIME)
            startTime = Constants.INIT_TIME;

        int elapsedTime = (int) (System.currentTimeMillis() - startTime);

        startTime = System.currentTimeMillis();

        for(Iterator<Enemy> enemyIterator = enemies.iterator(); enemyIterator.hasNext();) {
            Enemy enemy = enemyIterator.next();
            if (botShot(enemy)) {
                enemy.laserHit();
                if (enemy.isDead())
                    enemyIterator.remove();
            }
            enemy.move();
            if (enemy.shouldFire())
                botLasers.add(0, enemy.fire());
        }

        for(Iterator<BotLaser> laserIterator = botLasers.iterator(); laserIterator.hasNext();) {
            BotLaser laser = laserIterator.next();
            laser.move();
            laser.update();
            if(laser.getRectangle().bottom >= Constants.SCREEN_HEIGHT)
                laserIterator.remove();
        }

        for(Iterator<PlayerLaser> laserIterator = playerLasers.iterator(); laserIterator.hasNext();) {
            PlayerLaser laser = laserIterator.next();
            laser.move();
            laser.update();
            if(laser.getRectangle().top <= 0)
                laserIterator.remove();
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

        for (BotLaser laser : botLasers) {
            laser.draw(canvas);
        }

        for (PlayerLaser laser : playerLasers) {
            laser.draw(canvas);
        }
    }

    private boolean shouldSpawn() {
        if (enemies.size() < 10) {
            //TODO: Add more random generation. for now always keep static number of enemies
            return true;
        } else {
            return false;
        }
    }

    public void playerFired(RectPlayer player) {
        //TODO add a check that limits the number of PlayerLasers on the field at once
        this.playerLasers.add(player.fire());
    }

    private void spawn(int type) {

        Bitmap idleImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.enemy_ship);
        Bitmap walkR = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.enemy_ship);
        Bitmap walkL = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.enemy_ship);

        int xStart = (int) (Math.random()*(Constants.SCREEN_WIDTH ));

        if (type == 1) {
            enemies.add(0, new BotNormal(idleImg, walkR, walkL, new Rect(110, 110, 260, 260)));
            Random r = new Random();
            enemies.get(0).update(new Point(r.nextInt(Constants.SCREEN_WIDTH-1) + 1, -Constants.SCREEN_HEIGHT/4));
        } else if(type == 2) {
            //TODO: Add UFO spawning
            enemies.add(0, new BotNormal(idleImg, walkR, walkL, new Rect(200, 200, xStart, 200)));
        }
    }

    public boolean playerCollide(RectPlayer player) {
        for (Enemy enemy : enemies) {
            if (enemy.playerCollide(player))
                return true;
        }
        return false;
    }

    public boolean playerShot(RectPlayer player) {
        for (BotLaser laser : botLasers) {
            if(laser.playerShot(player)) {
                return true;
            }
        }
        return false;
    }

    public boolean botShot(Enemy enemy) {
        for(Iterator<PlayerLaser> laserIterator = playerLasers.iterator(); laserIterator.hasNext();) {
            PlayerLaser laser = laserIterator.next();
            if (laser.botShot(enemy)) {
                laserIterator.remove();
                score += enemy.getScoreValue();
                return true;
            }
        }
        return false;
    }
}
