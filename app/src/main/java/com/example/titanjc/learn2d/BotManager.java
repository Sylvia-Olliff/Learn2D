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
        Constants.BOT_MOVE_SPEED = 180;
        Constants.LASER_BOLT_SPEED = 290;
        Constants.LASER_BOLT_SIZE = 90;
        Constants.LASER_FLASH_SIZE = 60;
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
            Random r = new Random();
            int spawnType = r.nextInt(101 - 1) + 1;
            if (spawnType >= 80)
                spawn(2);
            else
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
        if(player.canFire())
            this.playerLasers.add(player.fire());
    }

    private void spawn(int type) {

        Bitmap idleImgNorm = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.enemy_ship);
        Bitmap walkRNorm = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.enemy_ship);
        Bitmap walkLNorm = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.enemy_ship);

        Bitmap idleImgUFO = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.enemy_ufo);
        Bitmap walkRUFO = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.enemy_ufo);
        Bitmap walkLUFO = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.enemy_ufo);

        Random rand = new Random();
        int botFloor = rand.nextInt(37 - 27) + 27;
        int moveFloor = ((botFloor*Constants.SCREEN_HEIGHT)/108);

        if (type == 1) {
            enemies.add(0, new BotNormal(idleImgNorm, walkRNorm, walkLNorm, new Rect(110, 110, 260, 260), moveFloor));
            Random r = new Random();
            enemies.get(0).update(new Point(r.nextInt(Constants.SCREEN_WIDTH-1) + 1, -Constants.SCREEN_HEIGHT/4));
        } else if(type == 2) {
            enemies.add(0, new BotUFO(idleImgUFO, walkRUFO, walkLUFO, new Rect(200, 200, 350, 350), moveFloor));
            Random r = new Random();
            enemies.get(0).update(new Point(r.nextInt(Constants.SCREEN_WIDTH-1) + 1, -Constants.SCREEN_HEIGHT/4));
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
