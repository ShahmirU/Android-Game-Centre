package fall2018.csc207.GameCentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


/**
 * The initial activity for the player's startup page after logging into the game centre.
 */

public class PlayerMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);
        addPlayGameListener();
        addScoreboardListener();
        addLogoutListener();
    }

    /**
     * Activate the "play games" button
     */
    private void addPlayGameListener() {
        Button PlayGamesButton = findViewById(R.id.PlayGames);
        PlayGamesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGames();
            }
        });
    }

    /**
     * Activate the scoreboard button to view per-user scoreboards
     */
    private void addScoreboardListener() {
        Button ScoreboardButton = findViewById(R.id.ScoreboardButton);
        ScoreboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToPlayerScoreBoard();
            }
        });
    }

    /**
     * Activate the logout button
     */
    private void addLogoutListener() {
        Button LogoutButton = findViewById(R.id.LogoutButton);
        LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGameCentre();
            }
        });
    }

    /**
     * Display the list of games to play
     */
    private void switchToGames() {
        Intent tmp = new Intent(this, GameOptionsActivity.class);
        startActivity(tmp);
    }

    /**
     * Display the per-user scoreboard of the current player
     */
    private void switchToPlayerScoreBoard() {
        Intent tmp = new Intent(this, PlayerScoreBoardActivity.class);
        startActivity(tmp);
    }

    /**
     * Display the sign in page when player "logs out"
     */
    private void switchToGameCentre() {
        Intent tmp = new Intent(this, LoginActivity.class);
        startActivity(tmp);
    }


}
