package com.example.hw1;


import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

public class GameManager {

    private final int ticksToNewApple;
    private int ticks;
    private int lives;
    private int girlCol;

    private final boolean[][] isAppleVisible;
    private final int rows;
    private final int cols;
    private final Vibrator vibrator;
    private final Toast toast;

    Random rand = new Random();

    public GameManager(int lives, int rows, int cols, int girlStartCol, int ticksToNewCat, Toast toast, Vibrator vibrator) {
        this.ticksToNewApple = ticksToNewCat;
        ticks = 0;
        girlCol = girlStartCol;
        this.rows = rows;
        this.cols = cols;
        isAppleVisible = new boolean[rows][cols];
        this.lives = lives;
        this.vibrator = vibrator;
        this.toast = toast;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                isAppleVisible[i][j] = false;
            }
        }
    }

    public void moveGirl(int direction) {
        if (direction < 0 && girlCol != 0) //move left
            girlCol--;
        if (direction > 0 && girlCol != cols - 1) //move right
            girlCol++;
    }

    public int getGirlCol() {
        return girlCol;
    }

    public boolean[][] getIsAppleVisible() {
        return isAppleVisible;
    }

    public void checkCollision() {
        if (isAppleVisible[rows - 1][girlCol]) {
            if (lives > 0)
                lives--;
            toast.show();
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        }
    }

    private void addCat() {
        isAppleVisible[0][rand.nextInt(cols)] = true;
    }

    private void updateCats() {
        for (int i = rows - 1; i > 0; i--)
            for (int j = 0; j < cols; j++)
                isAppleVisible[i][j] = isAppleVisible[i - 1][j];

        for (int i = 0; i < cols; i++) {
            isAppleVisible[0][i] = false;
        }
    }

    public void updateGame() {
        checkCollision();
        updateCats();
        ticks++;
        if (ticks == ticksToNewApple) {
            ticks = 0;
            addCat();
        }

        Log.d("isAppleVisible", Arrays.deepToString(isAppleVisible));
    }


    public int getLives() {
        return lives;
    }


}
