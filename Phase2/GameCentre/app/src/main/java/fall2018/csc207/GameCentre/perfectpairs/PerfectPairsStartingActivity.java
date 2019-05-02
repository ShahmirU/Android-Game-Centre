package fall2018.csc207.GameCentre.perfectpairs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import fall2018.csc207.GameCentre.LoginController;
import fall2018.csc207.GameCentre.Player;
import fall2018.csc207.GameCentre.PlayerMenuActivity;
import fall2018.csc207.GameCentre.R;

/**
 * The initial activity for the perfect pairs game.
 */
public class PerfectPairsStartingActivity extends AppCompatActivity {

    /**
     * The main save file.
     */
    public static final String SAVE_FILENAME = "perfect_pairs_save_file.ser";
    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "perfect_pairs_save_file_tmp.ser";
    /**
     * The perfectPairsGrid perfectPairsGridManager.
     */
    private PerfectPairsGridManager perfectPairsGridManager;

    /**
     * Checks whether slidingTilesBoardManager has been changed following page creation.
     */
    private boolean gridManagerChanged = false;

    /**
     * The current logged in currentPlayer.
     */
    Player currentPlayer;

    /**
     * The map containing usernames and their player object.
     */
    Map<String, Player> result;
    /**
     * Location of the account activity data.
     */
    public static final String FILENAME = "/data/data/csc207.fall2018.gamecentre/files/save_player.ser";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect_pairs_starting);

        perfectPairsGridManager = new PerfectPairsGridManager();
        PerfectPairsGrid board = perfectPairsGridManager.getPerfectPairsGrid();
        saveToFile(TEMP_SAVE_FILENAME);
        Intent intent = getIntent();
        currentPlayer = LoginController.getCurrentPlayer();

        setContentView(R.layout.activity_perfect_pairs_starting);
        addNewGameButtonListener();
        addAutoLoadButtonListener();
        addPerfectPairsScoreBoardButtonListener();
        addBackToMenuListener();
    }

    /**
     * Activate the Back To Menu button.
     */
    private void addBackToMenuListener() {
        Button menuButton = findViewById(R.id.Menu);
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

    /**
     * Switch to the perfectPairsMainActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, PerfectPairsMainActivity.class);
        saveToFile(PerfectPairsStartingActivity.TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }


    /**
     * Activate the scoreboard button.
     */
    private void addPerfectPairsScoreBoardButtonListener() {
        Button scoreButton = findViewById(R.id.PerfectPairsScoreBoard);
        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToPerfectPairsScoreboard();
            }
        });
    }


    private void switchToPerfectPairsScoreboard() {
        Intent tmp = new Intent(this, PerfectPairsScoreBoardActivity.class);
        startActivity(tmp);
    }


    /**
     * Activate the autoload button.
     */
    private void addAutoLoadButtonListener() {
        Button autoLoadButton = findViewById(R.id.LoadAutoSaveButton);
        autoLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PerfectPairsMainActivity.autoSaved) {
                    loadFromFile(PerfectPairsMainActivity.AUTO_SAVE_FILENAME);
                    gridManagerChanged = true;

                    makeToastLoadedText();
                    switchToGame();
                } else {
                    makeToastNoGameToLoadText();
                }
            }
        });
    }


    /**
     * Create and switch to a new game when new game button is clicked.
     */
    private void addNewGameButtonListener() {
        Button button = findViewById(R.id.threeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perfectPairsGridManager = new PerfectPairsGridManager();
//                PerfectPairsGrid matchingBoard = perfectPairsGridManager.getPerfectPairsGrid();
                PerfectPairsMainActivity.score = 1000;
                gridManagerChanged = true;
                switchToGame();
            }
        });
    }


    /**
     * Display that a game could not load.
     */
    private void makeToastNoGameToLoadText() {
        Toast.makeText(this, "No saved game to load.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Load the slidingTilesBoard manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {
        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                perfectPairsGridManager = (PerfectPairsGridManager) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the slidingTilesBoard manager to fileName.
     *
     * @param fileName the name of the file
     */
    private void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(perfectPairsGridManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
