package fall2018.csc207.GameCentre.perfectpairs;

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
 * Activity that show per game high scores.
 */
public class PerfectPairsScoreBoardActivity extends AppCompatActivity implements Observer {

    /**
     * HashMap to store highest three scores, where the key represents the score number, and
     * its mapped value is the player with that score.
     */
    private static List<Player> topThreeScores = new ArrayList<>();

    /**
     * Text to display the highest scoring player in the Perfect Pairs game.
     */
    private TextView firstPlayer;

    /**
     * Text to display the highest score in the Perfect Pairs game.
     */
    private TextView firstScore;

    private PerfectPairsScoreBoardController perfectPairsScoreBoardController;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_perfect_pairs_scoreboard);
        addBackToGameListener();

        this.firstPlayer = findViewById(R.id.FirstPlayerName);

        this.firstScore = findViewById(R.id.FirstPlayerScore);


        perfectPairsScoreBoardController = new PerfectPairsScoreBoardController();
        perfectPairsScoreBoardController.addObserver(this);

        firstPlayer.setText("N/A");
        firstScore.setText("N/A");

        Player tmpPlayer1 = new Player();


        ArrayList<Player> loadPlayers = perfectPairsScoreBoardController.loadPlayers();

        int highestScore = loadPlayers.get(0).getHighScore("Perfect Pairs");
        String highestScorePlayer = loadPlayers.get(0).getUsername();


        tmpPlayer1 = perfectPairsScoreBoardController.setPlayer(tmpPlayer1, highestScore, highestScorePlayer, loadPlayers);

        topThreeScores.add(tmpPlayer1);

        perfectPairsScoreBoardController.saveScores((ArrayList<Player>) topThreeScores, this);
        topThreeScores = perfectPairsScoreBoardController.loadScores();

        firstPlayer.setText(tmpPlayer1.getUsername());
        firstScore.setText(Integer.toString(tmpPlayer1.getHighScore("Perfect Pairs")));


    }

//    /**
//     * Switch to the SlidingTilesScoreBoardActivity view to view scores for each type of game.
//     */
//    private void switchToPerfectPairsScoreboard() {
//        Intent tmp = new Intent(this, PerfectPairsScoreBoardActivity.class);
//        startActivity(tmp);
//    }
//
//    /**
//     * Activate the scoreboard button.
//     */
//    private void addPerfectPairsScoreBoardButtonListener() {
//        Button scoreButton = findViewById(R.id.PerfectPairsScoreBoard);
//        scoreButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switchToPerfectPairsScoreboard();
//            }
//        });
//    }

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
        Intent tmp = new Intent(this, PerfectPairsStartingActivity.class);
        startActivity(tmp);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
