package fall2018.csc207.GameCentre.perfectpairs;

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

public class PerfectPairsGameOverActivity extends AppCompatActivity implements Observer {

    /**
     * The display of the player's current score from their most recent game.
     */
    private TextView currentScore;

    /**
     * The display of the player's highest score overall.
     */
    private TextView highScore;

    private PerfectPairsGameOverController perfectPairsGameOverController;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect_pairs_gameover);
        perfectPairsGameOverController = new PerfectPairsGameOverController();
        perfectPairsGameOverController.addObserver(this);

        addBackToMenuListener();

        this.currentScore = findViewById(R.id.PerfectPairsScores);
        this.highScore = findViewById(R.id.HighScore);

        int myScore = PerfectPairsMainActivity.score;
        this.currentScore.setText(Integer.toString(myScore));

        ArrayList<Player> loadedPlayers = perfectPairsGameOverController.loadPlayers();

        //check if the user got a new high score and save highest score
        perfectPairsGameOverController.updateHighScore(myScore);
        loadedPlayers = perfectPairsGameOverController.updatePlayerScore(loadedPlayers, myScore);

        perfectPairsGameOverController.savePlayers(loadedPlayers, this);

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
        String score = Integer.toString(LoginController.getCurrentPlayer().getHighScore("Perfect Pairs"));
        highScore.setText(score);
    }
}
