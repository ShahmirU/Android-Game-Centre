package fall2018.csc207.GameCentre.slidingtiles;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc207.GameCentre.Player;
import fall2018.csc207.GameCentre.R;


/**
 * Activity that show per game high scores.
 */
public class SlidingTilesScoreBoardActivity extends AppCompatActivity implements Observer {

    /**
     * HashMap to store per game scores, where the key is the name of a game, and its mapped value
     * is the player with the highest score for that game.
     */
    private static Map<String, Player> perGameScores = new HashMap<String, Player>();

    /**
     * Save file to store the scores of the players.
     */
    private static String GAME_SCORES_SAVE_FILE = "game_scores.ser";


    /**
     * Text to display the highest scoring player in a sliding tiles 3x3 game
     */
    private TextView threeByThreeUser;

    /**
     * Text to display the highest scoring player in a sliding tiles 4x4 game
     */
    private TextView fourByFourUser;

    /**
     * Text to display the highest scoring player in a sliding tiles 5x5 game
     */
    private TextView fiveByFiveUser;

    /**
     * Text to display the highest score in a sliding tiles 3x3 game
     */
    private TextView threeByThreeScore;

    /**
     * Text to display the highest score in a sliding tiles 4x4 game
     */
    private TextView fourByFourScore;

    /**
     * Text to display the highest score in a sliding tiles 5x5 game
     */
    private TextView fiveByFiveScore;

    /**
     * The sliding tiles scoreboard controller.
     */
    private SlidingTilesScoreBoardController slidingTilesScoreBoardController;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sliding_tiles_scoreboard);
        addBackToGameListener();

        this.threeByThreeUser = findViewById(R.id.ThreeByThreePlayerName);
        this.fourByFourUser = findViewById(R.id.FourByFourPlayerName);
        this.fiveByFiveUser = findViewById(R.id.FiveByFivePlayerName);

        this.threeByThreeScore = findViewById(R.id.ThreeByThreePlayerScore);
        this.fourByFourScore = findViewById(R.id.FourByFourPlayerScore);
        this.fiveByFiveScore = findViewById(R.id.FiveByFivePlayerScore);

        slidingTilesScoreBoardController = new SlidingTilesScoreBoardController();
        slidingTilesScoreBoardController.addObserver(this);

        Player tmpPlayer3 = new Player();
        Player tmpPlayer4 = new Player();
        Player tmpPlayer5 = new Player();


        ArrayList<Player> loadPlayers = loadPlayers();

        int highestScore3x3 = loadPlayers.get(0).getHighScore("Sliding Tiles 3x3");
        String highestScore3x3Player = loadPlayers.get(0).getUsername();

        int highestScore4x4 = loadPlayers.get(0).getHighScore("Sliding Tiles 4x4");
        String highestScore4x4Player = loadPlayers.get(0).getUsername();

        int highestScore5x5 = loadPlayers.get(0).getHighScore("Sliding Tiles 5x5");
        String highestScore5x5Player = loadPlayers.get(0).getUsername();

        //Find the players with the highest scores in each game
        tmpPlayer3 = slidingTilesScoreBoardController.setPlayer(tmpPlayer3, highestScore3x3, highestScore3x3Player, "Sliding Tiles 3x3", loadPlayers);
        tmpPlayer4 = slidingTilesScoreBoardController.setPlayer(tmpPlayer4, highestScore4x4, highestScore4x4Player, "Sliding Tiles 4x4", loadPlayers);
        tmpPlayer5 = slidingTilesScoreBoardController.setPlayer(tmpPlayer5, highestScore5x5, highestScore5x5Player, "Sliding Tiles 5x5", loadPlayers);

        perGameScores.put("Sliding Tiles 3x3", tmpPlayer3);
        perGameScores.put("Sliding Tiles 4x4", tmpPlayer4);
        perGameScores.put("Sliding Tiles 5x5", tmpPlayer5);

        saveScores((HashMap<String, Player>) perGameScores, this);
        perGameScores = loadScores();

        threeByThreeUser.setText(perGameScores.get("Sliding Tiles 3x3").getUsername());
        threeByThreeScore.setText(Integer.toString(perGameScores.get("Sliding Tiles 3x3").getHighScore("Sliding Tiles 3x3")));

        fourByFourUser.setText(perGameScores.get("Sliding Tiles 4x4").getUsername());
        fourByFourScore.setText(Integer.toString(perGameScores.get("Sliding Tiles 4x4").getHighScore("Sliding Tiles 4x4")));

        fiveByFiveUser.setText(perGameScores.get("Sliding Tiles 5x5").getUsername());
        fiveByFiveScore.setText(Integer.toString(perGameScores.get("Sliding Tiles 5x5").getHighScore("Sliding Tiles 5x5")));

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
        Intent tmp = new Intent(this, SlidingTilesStartingActivity.class);
        startActivity(tmp);
    }

    /**
     * Load the highest scores per game from fileName.
     */
    HashMap<String, Player> loadScores() {
        HashMap<String, Player> perGameScores = new HashMap<>();
        try {
            FileInputStream var2 = new FileInputStream("/data/data/csc207.fall2018.gamecentre/files/game_scores.ser");
            BufferedInputStream var3 = new BufferedInputStream(var2);
            ObjectInputStream var4 = new ObjectInputStream(var3);
            perGameScores = (HashMap) var4.readObject();
            var4.close();
        } catch (FileNotFoundException e) {
            Log.e("score slidingTilesBoard activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("score slidingTilesBoard activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("score slidingTilesBoard activity", "File contained unexpected data type: " + e.toString());
        }
        return perGameScores;
    }

    /**
     * @return the new list of accounts already existing.
     */
    ArrayList<Player> loadPlayers() {
        ArrayList<Player> loadedPlayers = new ArrayList<>();
        try {
            FileInputStream var2 = new FileInputStream("/data/data/csc207.fall2018.gamecentre/files/save_player.ser");
            BufferedInputStream var3 = new BufferedInputStream(var2);
            ObjectInputStream var4 = new ObjectInputStream(var3);
            loadedPlayers = (ArrayList<Player>) var4.readObject();
            var4.close();
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
        return loadedPlayers;
    }

    /**
     * Save the highest scores per game to fileName.
     */
    void saveScores(HashMap<String, Player> perGameScores, Context context) {
        HashMap<String, Player> scoresCopy = (HashMap<String, Player>) perGameScores.clone();
        try {
            FileOutputStream fileOut = context.openFileOutput(GAME_SCORES_SAVE_FILE, Activity.MODE_PRIVATE);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOut);
            outputStream.writeObject(scoresCopy);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    @Override
    public void update(Observable o, Object arg) {

    }
}
