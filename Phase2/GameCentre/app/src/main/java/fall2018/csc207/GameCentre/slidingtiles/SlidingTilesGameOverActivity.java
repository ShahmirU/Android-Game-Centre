package fall2018.csc207.GameCentre.slidingtiles;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc207.GameCentre.LoginController;
import fall2018.csc207.GameCentre.Player;
import fall2018.csc207.GameCentre.PlayerMenuActivity;
import fall2018.csc207.GameCentre.R;
//Learning how to use the observer design pattern was adapted from
// https://www.youtube.com/watch?v=aFCgLf4CwM0&t=553s

/**
 * Activity which shows the final score of the player after their game has ended.
 */
public class SlidingTilesGameOverActivity extends AppCompatActivity implements Observer {

    /**
     * The display of the player's current score from their most recent game.
     */
    private TextView currentScore;

    /**
     * The display of the player's highest score overall.
     */
    private TextView highScore;

    /**
     * A controller for the sliding tiles game over page.
     */
    private SlidingTilesGameOverController slidingTilesGameOverController;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slidingtiles_gameover);

        slidingTilesGameOverController = new SlidingTilesGameOverController();
        slidingTilesGameOverController.addObserver(this);

        addBackToMenuListener();

        this.currentScore = findViewById(R.id.SlidingTilesScores);
        this.highScore = findViewById(R.id.HighScore);

        int myScore = SlidingTilesGameActivity.score;
        this.currentScore.setText(Integer.toString(myScore));

        ArrayList<Player> loadedPlayers = slidingTilesGameOverController.loadPlayers();

        //check if the user got a new high score and save highest score
        slidingTilesGameOverController.updateHighScore(myScore);
        loadedPlayers = slidingTilesGameOverController.updatePlayerScore(loadedPlayers, myScore);

        slidingTilesGameOverController.savePlayers(loadedPlayers, this);
    }

    /**
     * Activate Back to Menu button.
     */
    private void addBackToMenuListener() {
        Button menuButton = findViewById(R.id.BackToMenu);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToMenu();
            }
        });
    }

    /**
     * Display the game menu.
     */
    private void switchToMenu() {
        Intent tmp = new Intent(this, PlayerMenuActivity.class);
        startActivity(tmp);
    }

    @Override
    public void update(Observable o, Object arg) {
        //display player's highest score
        String score = Integer.toString(LoginController.getCurrentPlayer().getHighScore(SlidingTilesStartingActivity.gameChosen));
        highScore.setText(score);
    }
}
