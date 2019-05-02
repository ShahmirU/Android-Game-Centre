package fall2018.csc207.GameCentre.tictactoe;

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

/**
 * The game over activity for tic tac toe.
 */
public class TicTacToeGameOverActivity extends AppCompatActivity implements Observer {

    /**
     * The display of the player's current score from their most recent game.
     */
    private TextView currentScore;

    /**
     * The display of the player's highest score overall.
     */
    private TextView highScore;

    /**
     * The ticTacToeGameOverController to handle the logic of the game over page.
     */
    private TicTacToeGameOverController ticTacToeGameOverController;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tictactoe_gameover);
        ticTacToeGameOverController = new TicTacToeGameOverController();
        ticTacToeGameOverController.addObserver(this);

        addBackToMenuListener();

        this.currentScore = findViewById(R.id.TicTacToeScores);
        this.highScore = findViewById(R.id.HighScore);

        int myScore = TicTacToeGameActivity.score;
        this.currentScore.setText(Integer.toString(myScore));

        ArrayList<Player> loadedPlayers = ticTacToeGameOverController.loadPlayers();

        //check if the user got a new high score and save highest score
        ticTacToeGameOverController.updateHighScore(myScore);
        loadedPlayers = ticTacToeGameOverController.updatePlayerScore(loadedPlayers, myScore);

        ticTacToeGameOverController.savePlayers(loadedPlayers, this);

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
        String score = Integer.toString(LoginController.getCurrentPlayer().getHighScore("Tic Tac Toe"));
        highScore.setText(score);
    }
}
