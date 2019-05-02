package fall2018.csc207.GameCentre.perfectpairs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc207.GameCentre.CustomAdapter;
import fall2018.csc207.GameCentre.GestureDetectGridView;
import fall2018.csc207.GameCentre.R;

public class PerfectPairsMainActivity extends AppCompatActivity implements Observer {

    /**
     * The perfectPairsGrid manager.
     */
    private PerfectPairsGridManager perfectPairsGridManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * The GestureDetectGridView.
     */
    private GestureDetectGridView gridView;

    /**
     * Integers representing the width and height of each column.
     */
    private static int columnWidth, columnHeight;

    /**
     * The file to keep track of auto_save
     */
    public static final String AUTO_SAVE_FILENAME = "perfect_pairs_auto_save.ser";

    /**
     * Tracks the score of the player
     */
    public static int score = 1000;

    public static boolean autoSaved = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect_pairs_main);
        loadFromFile(PerfectPairsStartingActivity.TEMP_SAVE_FILENAME);


        createTileButtons(this);
        setContentView(R.layout.activity_perfect_pairs_main);

        TextView scoreTextView = findViewById(R.id.score);
        String newScore = "Score: " + (score);
        scoreTextView.setText(newScore);

        // Add View to activity
        gridView = findViewById(R.id.matchingGrid);
        gridView.setNumColumns(4);
        gridView.setPerfectPairsGridManager(perfectPairsGridManager);
        perfectPairsGridManager.getPerfectPairsGrid().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / 4;
                        columnHeight = displayHeight / 4;

                        display();
                    }
                });
    }


    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    /**
     * Autosave the game.
     *
     * @param fileName the name of the file that is used to autoSave
     */
    public void autoSave(String fileName) {
        if (perfectPairsGridManager.puzzleSolved()) {
            switchToGameOver();
        }
        autoSaved = true;
        saveToFile(fileName);
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context.
     */
    private void createTileButtons(Context context) {
        PerfectPairsGrid board = perfectPairsGridManager.getPerfectPairsGrid();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != 4; row++) {
            for (int col = 0; col != 4; col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        PerfectPairsGrid board = perfectPairsGridManager.getPerfectPairsGrid();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / 4;
            int col = nextPos % 4;
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
            autoSave(AUTO_SAVE_FILENAME);
        }
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
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


    /**
     * Load the perfectPairsGrid manager from fileName.
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

    private void switchToGameOver() {
        Intent tmp = new Intent(this, PerfectPairsGameOverActivity.class);
        startActivity(tmp);
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
        TextView scoreTextView = (TextView) findViewById(R.id.score);
        String newScore = "Score: " + score;
        scoreTextView.setText(newScore);
        if (perfectPairsGridManager.puzzleSolved()) {
            switchToGameOver();
        }
    }

}