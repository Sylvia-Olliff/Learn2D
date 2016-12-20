package com.example.titanjc.learn2d;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by TITANJC on 12/19/2016.
 */

public class HighScoreUpdater {
    SharedPreferences sharedPref;
    String[] highScores;

    public HighScoreUpdater() {
        sharedPref = Constants.CURRENT_CONTEXT.getSharedPreferences("LevelScores", Context.MODE_PRIVATE);
        highScores = new String[10];
        //TODO finish setting up HighScore setting
    }
}
