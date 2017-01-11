package com.example.titanjc.learn2d;

import android.graphics.Canvas;
import android.util.Pair;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by TITANJC on 11/21/2016.
 */

public class SceneManager {
    private ArrayList<Scene> scenes = new ArrayList<>();
    private static HashMap<String, Integer> sceneNames;
    private static int ACTIVE_SCENE;
    //TODO Change ACTIVE_SCENE to private, and accessed through a method.

    public SceneManager() {
        ACTIVE_SCENE = 0;
        scenes.add(new MainMenuScene());
        scenes.add(new GameplayScene());
        scenes.add(new HighScoreScene());
        sceneNames = new HashMap<>();
        sceneNames.put("MainMenuScene", 0);
        sceneNames.put("GameplayScene", 1);
        sceneNames.put("HighScoreScene", 2);
    }

    public void recieveTouch(MotionEvent event) {
        scenes.get(ACTIVE_SCENE).recieveTouch(event);
    }

    public void update() {
        scenes.get(ACTIVE_SCENE).update();
    }

    public void draw(Canvas canvas) {
        scenes.get(ACTIVE_SCENE).draw(canvas);
    }

    public static void changeScene(String sceneName) {
        ACTIVE_SCENE = sceneNames.get(sceneName);
    }

}
