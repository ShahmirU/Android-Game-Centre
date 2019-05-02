package fall2018.csc207.GameCentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import fall2018.csc207.GameCentre.perfectpairs.PerfectPairsStartingActivity;
import fall2018.csc207.GameCentre.slidingtiles.SlidingTilesStartingActivity;
import fall2018.csc207.GameCentre.tictactoe.TicTacToeStartingActivity;

/**
 * The activity to choose what game the user wants to play.
 */
public class GameOptionsActivity extends AppCompatActivity {

    final static int numGames = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_options);
    }

    /**
     * Switches to the sliding tiles screen to play sliding tiles
     *
     * @param view the view on the activity
     */
    public void playSlidingTiles(View view) {
        switchToSlidingTiles();
    }

    /**
     * Switches to play Tic Tac Toe
     *
     * @param view the view on the activity
     */
    public void playTicTacToe(View view) {
        switchToTicTacToe();
    }


    /**
     * Switches to the game Perfect Pairs
     *
     * @param view the view on the activity
     */
    public void playPerfectPairs(View view) {
        switchToPerfectPairs();
    }

    /**
     * Switches to sliding tiles starting activity
     */
    private void switchToSlidingTiles() {
        Intent tmp = new Intent(this, SlidingTilesStartingActivity.class);
        startActivity(tmp);

    }

    /**
     * Switches to Tic Tac Toe starting activity
     */
    private void switchToTicTacToe() {
        Intent tmp = new Intent(this, TicTacToeStartingActivity.class);
        startActivity(tmp);

    }

    /**
     * Switches to Perfect Pairs starting activity
     */
    private void switchToPerfectPairs() {
        Intent tmp = new Intent(this, PerfectPairsStartingActivity.class);
        startActivity(tmp);

    }
}
