package com.example.titanjc.learn2d;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by TITANJC on 12/19/2016.
 */

public class HighScoreHandler {
    private SharedPreferences sharedPrefs;
    private Editor editor;
    private int[] highScores;
    private static int[] HighScores;

    public HighScoreHandler() {
        sharedPrefs = Constants.CURRENT_CONTEXT.getSharedPreferences("LevelScores", Context.MODE_PRIVATE);
        highScores = new int[10];
        for (int i = 0; i < 10; i++) {
            highScores[i] = sharedPrefs.getInt(Integer.toString(i + 1), 90000 - (i * 10000));
        }
    }

    public static int[] getHighScores() { return HighScores; }

    public boolean checkHighScores(int score) {
        if (score > highScores[9]) {
            int i = 0;
            while (i < 10 && highScores[i] > score) {
                i++;
            }

            if(i < 10) {
                for (int x = 9; x > i; x--) {
                    highScores[x] = highScores[x - 1];
                }
                highScores[i] = score;
                HighScores = highScores.clone();
                try {
                    if (!updateHighScores())
                        throw new Exception("failed to update highscore");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    private boolean updateHighScores() {
        editor = sharedPrefs.edit();
        for (int i = 0; i < 10; i++) {
            editor.putInt(Integer.toString(i + 1), highScores[i]);
        }
        return editor.commit();
    }
}
