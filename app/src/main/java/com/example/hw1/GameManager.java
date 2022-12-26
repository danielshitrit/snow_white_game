package com.example.hw1;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;
import java.util.Random;

public class GameManager {
    private int ticks;
    private int lives;
    private int girlCol;
    private int countDwarfs =0;
    private final boolean[][] isAppleVisible;
    private final boolean[][] isDwarfVisible;
    private final int rows;
    private final int cols;
    private final Vibrator vibrator;
    private final Toast toast;

    Random rand = new Random();

    public GameManager(int lives, int rows, int cols, int girlStartCol, Toast toast, Vibrator vibrator) {
        ticks = 0;
        countDwarfs = 0;
        girlCol = girlStartCol;
        this.rows = rows;
        this.cols = cols;
        isAppleVisible = new boolean[rows][cols];
        isDwarfVisible = new boolean[rows][cols];
        this.lives = lives;
        this.vibrator = vibrator;
        this.toast = toast;

        initMatrixGame();
    }

    public void initMatrixGame(){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                isAppleVisible[i][j] = false;
                isDwarfVisible[i][j] = false;
            }
        }
    }

    public void moveGirl(int moveSide) {
        if (moveSide < 0 && girlCol != 0) //move to left side
            girlCol--;
        if (moveSide > 0 && girlCol != cols - 1) //move to right side
            girlCol++;
    }

    public boolean checkCollision() {
        boolean collision = false;
        if (isAppleVisible[rows - 1][girlCol]) {
            if (lives > 0) {
                lives--;
            }
            toast.show();
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            collision = true;
        }
        return collision;
    }

    public void checkCollisionDwarf(){
        if (isDwarfVisible[rows - 1][girlCol]) {
            countDwarfs++;
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        }
    }

    private void addApples() {
        isAppleVisible[0][rand.nextInt(cols)] = true;
    }

    private void addDwarfs() {
        int numRnd = rand.nextInt(cols);
        if(isAppleVisible[0][numRnd] == false) {
            isDwarfVisible[0][numRnd] = true;
        }
    }

    private void updateApples() {
        for (int i = rows - 1; i > 0; i--)
            for (int j = 0; j < cols; j++)
                isAppleVisible[i][j] = isAppleVisible[i - 1][j];
        for (int i = 0; i < cols; i++) {
            isAppleVisible[0][i] = false;
        }
    }

    private void updateDwarfs() {
        for (int i = rows - 1; i > 0; i--)
            for (int j = 0; j < cols; j++)
                isDwarfVisible[i][j] = isDwarfVisible[i - 1][j];

        for (int i = 0; i < cols; i++) {
            isDwarfVisible[0][i] = false;
        }
    }

    public boolean updateGame() {
        boolean collision = checkCollision();
        updateApples();
        checkCollisionDwarf();
        updateDwarfs();

        ticks++;
        if (ticks%2 == 0) {
            addApples();
        }
        if (ticks%4 == 0) {
            addDwarfs();
        }
        return collision;
    }

    public int getLives() {
        return lives;
    }

    public int getGirlCol() {
        return girlCol;
    }

    public boolean[][] getIsAppleVisible() {
        return isAppleVisible;
    }

    public boolean[][] getIsDwarfVisible() {
        return isDwarfVisible;
    }

    public int getCountDwarfs(){
        return countDwarfs;
    }

}
