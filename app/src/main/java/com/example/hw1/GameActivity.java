package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.hw1.Interfaces.StepCallBack;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    private ImageButton game_IMG_rightArrow;
    private ImageButton game_IMG_leftArrow;
    private ImageView[] game_IMG_girls;
    private ImageView[][] game_IMG_apples;
    private ImageView[][] game_IMG_dwarfs;
    private ImageView[] game_IMG_hearts;
    private Timer timer;
    private GameManager gameManager;
    private TextView game_TXT_score;
    private static int countDwarfs;

    private int rows;
    private int cols;
    private int lives;
    private StepDetector stepDetector;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        lives = getResources().getInteger(R.integer.LIVES);
        cols = getResources().getInteger(R.integer.LANES);
        rows = getResources().getInteger(R.integer.ROWS);

        findViews();
        initViews();

        mediaPlayer = MediaPlayer.create(this, R.raw.crash_sound);
        Toast toast = Toast.makeText(this, "Oops!", Toast.LENGTH_SHORT);
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        gameManager = new GameManager(game_IMG_hearts.length, rows, cols, cols / 2, toast, vibrator);
        startTimer();
    }

    private void initViews() {
        if(MenuActivity.getTilt()) { // Tilt Mode
            game_IMG_leftArrow.setVisibility(View.INVISIBLE);
            game_IMG_rightArrow.setVisibility(View.INVISIBLE);
            initStepDetector();
        }else {
            game_IMG_leftArrow.setOnClickListener(view -> updateGirlPosition(-1));
            game_IMG_rightArrow.setOnClickListener(view -> updateGirlPosition(1));
        }
    }

    private void initStepDetector() {
        stepDetector = new StepDetector(this, new StepCallBack() {
            @Override
            public void stepRight() {
                updateGirlPosition(1);
            }
            @Override
            public void stepLeft() {
                updateGirlPosition(-1);
            }
        });
    }

    private void updateGirlPosition(int moveSide) {
        game_IMG_girls[gameManager.getGirlCol()].setVisibility(View.INVISIBLE);
        gameManager.moveGirl(moveSide);
        game_IMG_girls[gameManager.getGirlCol()].setVisibility(View.VISIBLE);
    }

    private void findViews() {
        game_IMG_rightArrow = findViewById(R.id.game_IMG_rightArrow);
        game_IMG_leftArrow = findViewById(R.id.game_IMG_leftArrow);

        game_IMG_girls = new ImageView[]{
                findViewById(R.id.game_IMG_girl1),
                findViewById(R.id.game_IMG_girl2),
                findViewById(R.id.game_IMG_girl3),
                findViewById(R.id.game_IMG_girl4),
                findViewById(R.id.game_IMG_girl5)
        };

        game_IMG_hearts = new ImageView[]{
                findViewById(R.id.game_IMG_heart3),
                findViewById(R.id.game_IMG_heart2),
                findViewById(R.id.game_IMG_heart1),
        };

        game_IMG_apples = new ImageView[rows][cols];
        game_IMG_dwarfs = new ImageView[rows][cols];

        String applesID, dwarfsID;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                applesID = "game_IMG_apple" + i + j;
                int resID = getResources().getIdentifier(applesID, "id", getPackageName());
                game_IMG_apples[i][j] = findViewById(resID);
                game_IMG_apples[i][j].setVisibility(View.INVISIBLE);

                dwarfsID = "game_IMG_dwarf" + i + j;
                int resDwarfID = getResources().getIdentifier(dwarfsID, "id", getPackageName());
                game_IMG_dwarfs[i][j] = findViewById(resDwarfID);
                game_IMG_dwarfs[i][j].setVisibility(View.INVISIBLE);
            }
        }
        game_TXT_score = findViewById(R.id.game_TXT_score);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(MenuActivity.getTilt()) {
            stepDetector.start();
        }
        startTimer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(MenuActivity.getTilt()) {
            stepDetector.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(MenuActivity.getTilt()) {
            stepDetector.stop();
        }
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(() -> updateUI());
                        }
                    }, 0, MenuActivity.getSpeed());
        }

    private void updateUI() {
        boolean collision = gameManager.updateGame();
        if (collision){
            mediaPlayer.start();
        }
        if (gameManager.getLives() < lives) {
            game_IMG_hearts[gameManager.getLives()].setVisibility(View.INVISIBLE);
        }
        if (gameManager.getLives() == 0) {
            timer.cancel();
            openWinnerActivity();
            finish();
        }
        updateDwarfs();
        boolean[][] isAppleVisible = gameManager.getIsAppleVisible();
        boolean[][] isDwarfVisible = gameManager.getIsDwarfVisible();
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++) {
                game_IMG_apples[i][j].setVisibility(isAppleVisible[i][j] ? View.VISIBLE : View.INVISIBLE);
                game_IMG_dwarfs[i][j].setVisibility(isDwarfVisible[i][j] ? View.VISIBLE : View.INVISIBLE);
            }
    }

    private void updateDwarfs(){
        countDwarfs = gameManager.getCountDwarfs();
        game_TXT_score.setText(" " + countDwarfs);
    }

    public static int getScore(){
        return countDwarfs;
    }

    public void openWinnerActivity() {
        Intent intent = new Intent(this, WinnerActivity.class);
        startActivity(intent);
        finish();
    }

}