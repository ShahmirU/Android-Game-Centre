package fall2018.csc207.GameCentre.slidingtiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc207.GameCentre.LoginController;
import fall2018.csc207.GameCentre.Player;
import fall2018.csc207.GameCentre.PlayerMenuActivity;
import fall2018.csc207.GameCentre.R;
import fall2018.csc207.GameCentre.SignUpActivity;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class SlidingTilesStartingActivity extends AppCompatActivity implements Observer {

    /**
     * The main save file.
     */
    public static final String SAVE_FILENAME = "save_file.ser";
    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "save_file_tmp.ser";

    /**
     * The slidingTilesBoard manager.
     */
    private SlidingTilesBoardManager slidingTilesBoardManager;

    /**
     * The user-specified number of moves that can be undone throughout the game
     */
    private EditText undoInput;

    /**
     * The complexity of the sliding tiles slidingTilesBoard
     */
    private EditText complexityInput;

    /**
     * The number of undone moves already used up
     */
    public static int undosChosen;

    /**
     * The complexity of the boards
     */
    public static int complexityChosen;

    /**
     * The game the player chose to play
     */
    public static String gameChosen;

    /**
     * Checks whether slidingTilesBoardManager has been changed following page creation.
     */
    private boolean boardManagerChanged = false;

    /**
     * The sliding tiles controller for the starting activity.
     */
    private SlidingTilesStartingController slidingTilesStartingController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        slidingTilesBoardManager = new SlidingTilesBoardManager();
        saveToFile(TEMP_SAVE_FILENAME);

        setContentView(R.layout.activity_slidingtiles_starting_);
        addLoadButtonListener();
        addSaveButtonListener();
        addAutoLoadButtonListener();
        addTilesScoreBoardButtonListener();
        addBackToMenuListener();

        slidingTilesStartingController = new SlidingTilesStartingController();
        slidingTilesStartingController.addObserver(this);

        undoInput = findViewById(R.id.MaxUndos);
        complexityInput = findViewById(R.id.ComplexityInput);
    }


    /**
     * Activate the scoreboard button.
     */
    private void addTilesScoreBoardButtonListener() {
        Button scoreButton = findViewById(R.id.TilesScoreBoardButton);
        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToTileScoreboard();
            }
        });
    }

    /**
     * Switch to the SlidingTilesScoreBoardActivity view to view scores for each type of game.
     */
    private void switchToTileScoreboard() {
        Intent tmp = new Intent(this, SlidingTilesScoreBoardActivity.class);
        startActivity(tmp);
    }

    /**
     * Activate the Back To Menu button.
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
     * Switch to the PlayerMenuActivity view to play a game or view user's scores.
     */
    private void switchToMenu() {
        Intent tmp = new Intent(this, PlayerMenuActivity.class);
        startActivity(tmp);
    }

    /**
     * Update undosChosen with the number of undos the user wants.
     */
    private void getUndos() {
        String undos = undoInput.getText().toString();
        undosChosen = Integer.parseInt(undos);
    }

    /**
     * Activate the start button to create a new sliding tiles game.
     *
     * @param view the view of the new game slidingTilesBoard
     */
    public void newGame(View view) {
        String complexityChosen = complexityInput.getText().toString();
        int complexity = Integer.parseInt(complexityChosen);
        getUndos();
        slidingTilesStartingController.getGame(complexity);
        SlidingTilesGameActivity.score = 10000;

        boolean isValidComplexity = slidingTilesStartingController.isValidComplexity(complexity);

        if (!isValidComplexity) {
            makeToastInvalidComplexity();
        } else {

            slidingTilesBoardManager = slidingTilesStartingController.resetBoardManager(complexity, slidingTilesBoardManager);
            boardManagerChanged = true;
            switchToGame();
        }
    }

    /**
     * Display that an invalid complexity was chosen.
     */
    private void makeToastInvalidComplexity() {
        Toast.makeText(this, "Invalid Complexity", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the autoload button.
     */
    private void addAutoLoadButtonListener() {
        Button autoLoadButton = findViewById(R.id.AutoloadButton);
        autoLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SlidingTilesGameActivity.autoSaved) {
                    loadFromFile(SlidingTilesGameActivity.AUTO_SAVE_FILENAME);
                    boardManagerChanged = true;
                    makeToastLoadedText();
                    switchToGame();
                } else {
                    makeToastNoGameToLoadText();
                }
            }
        });
    }

    /**
     * Activate the load button to load player and game info from a file.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Player> loadedPlayers = loadPlayers();
                slidingTilesStartingController.findCurrentPlayer(loadedPlayers);
                if (LoginController.getCurrentPlayer().getSavedSlidingTilesGame() != null) {
                    slidingTilesBoardManager = slidingTilesStartingController.loadBoardManager(slidingTilesBoardManager);
                    SlidingTilesGameActivity.score = LoginController.getCurrentPlayer().getSavedSlidingTilesGameScore();
                    makeToastLoadedText();
                    boardManagerChanged = true;
                    switchToGame();
                } else {
                    makeToastNoGameToLoadText();
                }
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
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (boardManagerChanged) {
                    ArrayList<Player> loadedPlayers = loadPlayers();
                    loadedPlayers = slidingTilesStartingController.updatePlayer(loadedPlayers, slidingTilesBoardManager);
                    savePlayerData(SignUpActivity.SAVE_FILE, loadedPlayers);
                    makeToastSavedText();
                }
            }
        });
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Read the temporary slidingTilesBoard from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadFromFile(TEMP_SAVE_FILENAME);
    }

    /**
     * Switch to the SlidingTilesGameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, SlidingTilesGameActivity.class);
        saveToFile(SlidingTilesStartingActivity.TEMP_SAVE_FILENAME);
        startActivity(tmp);
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
                slidingTilesBoardManager = (SlidingTilesBoardManager) input.readObject();
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
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(slidingTilesBoardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Save the data of the player in a serialized file
     */
    public void savePlayerData(String fileName, ArrayList<Player> loadedPlayers) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(loadedPlayers);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    /**
     * Load the player's and player info from a serialized file.
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
}



