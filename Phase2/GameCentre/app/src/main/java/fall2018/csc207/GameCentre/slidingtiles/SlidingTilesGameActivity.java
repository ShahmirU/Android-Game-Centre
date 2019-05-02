package fall2018.csc207.GameCentre.slidingtiles;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

/**
 * The Sliding Tiles game activity class.
 */
public class SlidingTilesGameActivity extends AppCompatActivity implements Observer {

    /**
     * The slidingTilesBoard manager.
     */
    private SlidingTilesBoardManager slidingTilesBoardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * The file to keep track of auto_save
     */
    public static final String AUTO_SAVE_FILENAME = "auto_save.ser";

    /**
     * The number of "undos" that the player has used
     */
    public static int undosDone = 0;

    /**
     * The score that appears on the screen.
     */
    TextView scoreText;

    /**
     * A boolean to check if the game has been autosaved.
     */
    public static boolean autoSaved = false;

    /**
     * Tracks the score of the player
     */
    public static int score = 10000;

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;

    /**
     * Integers representing the width and height of each column.
     */
    private static int columnWidth, columnHeight;

    private SlidingTilesGameController slidingTilesGameController;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    public void display() {

        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile(SlidingTilesStartingActivity.TEMP_SAVE_FILENAME);
        createTileButtons(this);
        setContentView(R.layout.activity_main);
        scoreText = findViewById(R.id.score);

        slidingTilesGameController = new SlidingTilesGameController();
        slidingTilesGameController.addObserver(this);

        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(SlidingTilesBoard.getNumCols());
        gridView.setSlidingTilesBoardManager(slidingTilesBoardManager);
        slidingTilesBoardManager.getSlidingTilesBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight() - 220;

                        columnWidth = displayWidth / SlidingTilesBoard.getNumCols();
                        columnHeight = displayHeight / SlidingTilesBoard.getNumRows();

                        display();
                    }
                });
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        SlidingTilesBoard slidingTilesBoard = slidingTilesBoardManager.getSlidingTilesBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != SlidingTilesBoard.getNumRows(); row++) {
            for (int col = 0; col != SlidingTilesBoard.getNumCols(); col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(slidingTilesBoard.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    @SuppressLint("SetTextI18n")
    private void updateTileButtons() {

        SlidingTilesBoard slidingTilesBoard = slidingTilesBoardManager.getSlidingTilesBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / SlidingTilesBoard.getNumRows();
            int col = nextPos % SlidingTilesBoard.getNumCols();
            b.setBackgroundResource(slidingTilesBoard.getTile(row, col).getBackground());
            nextPos++;
        }
        scoreText.setText("Current Score: " + score);
        autoSave(AUTO_SAVE_FILENAME);
    }

    /**
     * Autosave the game.
     *
     * @param fileName the name of the file that is used to autoSave
     */
    public void autoSave(String fileName) {
        if (slidingTilesBoardManager.puzzleSolved()) {
            switchToGameOver();
        }
        autoSaved = true;
        saveToFile(fileName);
    }

    /**
     * Switch to the SlidingTilesGameOverActivity view when a game has ended.
     */
    private void switchToGameOver() {
        Intent tmp = new Intent(this, SlidingTilesGameOverActivity.class);
        startActivity(tmp);
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveToFile(SlidingTilesStartingActivity.TEMP_SAVE_FILENAME);
    }

    /**
     * Undo a move on the slidingTilesBoard by reverse swapping the two tiles made in the previous move.
     *
     * @param view the display view of the tiles being swapped.
     */
    public void undo(View view) {

        boolean undosFinished = slidingTilesGameController.undosFinished(slidingTilesBoardManager.getPositions(), slidingTilesBoardManager);

        if (undosFinished) {
            makeToastNoUndos();
        } else {
            slidingTilesBoardManager.undosUsed += 1;
            score -= 5;
            slidingTilesBoardManager.reverseMove(slidingTilesBoardManager.getPositions().size() - 3);
            undosDone++;
        }
    }

    /**
     * Display that no more undos are available.
     */
    private void makeToastNoUndos() {
        Toast.makeText(this, "No more undo's left", Toast.LENGTH_SHORT).show();
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

    @Override
    public void update(Observable o, Object arg) {
        display();
    }
}
