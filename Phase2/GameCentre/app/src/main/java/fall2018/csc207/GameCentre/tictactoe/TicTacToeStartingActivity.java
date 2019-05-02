package fall2018.csc207.GameCentre.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import fall2018.csc207.GameCentre.PlayerMenuActivity;
import fall2018.csc207.GameCentre.R;

/**
 * The starting activity for tic tac toe.
 */
public class TicTacToeStartingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tic_tac_toe);
        addTicTacToeScoreBoardButtonListener();
        addBackToMenuListener();

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
    }

    public void startGame(View view) {
        Intent tmp = new Intent(this, TicTacToeGameActivity.class);
        startActivity(tmp);
    }

    /**
     * Activate the scoreboard button.
     */
    private void addTicTacToeScoreBoardButtonListener() {
        Button scoreButton = findViewById(R.id.tictactoe_scoreboard_button);
        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToTicTacToeScoreboard();
            }
        });
    }

    /**
     * Switch to the SlidingTilesScoreBoardActivity view to view scores for each type of game.
     */
    private void switchToTicTacToeScoreboard() {
        Intent tmp = new Intent(this, TicTacToeScoreBoardActivity.class);
        startActivity(tmp);
    }

    /**
     * Activate the Back To Menu button.
     */
    private void addBackToMenuListener() {
        Button menuButton = findViewById(R.id.back_to_menu);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToMenu();
            }
        });
    }

    /**
     * Switch to the PlayerMenuActivity view to play a game or view user's scores.
     */
    private void switchToMenu() {
        Intent tmp = new Intent(this, PlayerMenuActivity.class);
        startActivity(tmp);
    }
}