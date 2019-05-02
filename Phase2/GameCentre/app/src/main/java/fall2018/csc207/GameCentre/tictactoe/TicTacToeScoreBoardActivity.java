package fall2018.csc207.GameCentre.tictactoe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc207.GameCentre.Player;
import fall2018.csc207.GameCentre.R;

/**
 * The scoreboard for the tic tac toe game.
 */
public class TicTacToeScoreBoardActivity extends AppCompatActivity implements Observer {

    /**
     * HashMap to store highest three scores, where the key represents the score number, and
     * its mapped value is the player with that score.
     */
    private static List<Player> topThreeScores = new ArrayList<>();

    /**
     * Text to display the highest scoring player in the tic tac toe game.
     */
    private TextView firstPlayer;

    /**
     * Text to display the highest score in the tic tac toe game.
     */
    private TextView firstScore;

    /**
     * Scoreboard controller for tic tac toe.
     */
    private TicTacToeScoreBoardController ticTacToeScoreBoardController;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tictactoe_scoreboard);
        addBackToGameListener();

        this.firstPlayer = findViewById(R.id.FirstPlayerName);
        this.firstScore = findViewById(R.id.FirstPlayerScore);

        ticTacToeScoreBoardController = new TicTacToeScoreBoardController();
        ticTacToeScoreBoardController.addObserver(this);

        firstPlayer.setText("N/A");
        firstScore.setText("N/A");
        Player tmpPlayer1 = new Player();

        ArrayList<Player> loadPlayers = ticTacToeScoreBoardController.loadPlayers();

        int highestScore = loadPlayers.get(0).getHighScore("Tic Tac Toe");
        String highestScorePlayer = loadPlayers.get(0).getUsername();


        tmpPlayer1 = ticTacToeScoreBoardController.setPlayer(tmpPlayer1, highestScore, highestScorePlayer, loadPlayers);

        topThreeScores.add(tmpPlayer1);


        ticTacToeScoreBoardController.saveScores((ArrayList<Player>) topThreeScores, this);
        topThreeScores = ticTacToeScoreBoardController.loadScores();

        firstPlayer.setText(tmpPlayer1.getUsername());
        firstScore.setText(Integer.toString(tmpPlayer1.getHighScore("Tic Tac Toe")));

    }

    /**
     * Activate the back to game button
     */
    private void addBackToGameListener() {
        Button backToGameButton = findViewById(R.id.BackToGameButton);
        backToGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGame();
            }
        });
    }

    /**
     * Display the sliding tiles starting activity page
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, TicTacToeStartingActivity.class);
        startActivity(tmp);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
