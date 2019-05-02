package fall2018.csc207.GameCentre;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


/**
 * The initial activity for a player's personal scoreboard.
 */
public class PlayerScoreBoardActivity extends AppCompatActivity implements Observer {

    private static String[] GAMES = {"Sliding Tiles 3x3", "Sliding Tiles 4x4", "Sliding Tiles 5x5", "Tic Tac Toe", "Perfect Pairs"};
    static String[] SCORES = new String[GameOptionsActivity.numGames];

    private PlayerScoreBoardController playerScoreBoardController;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_player_scoreboard);
        addBackToMenuListener();

        playerScoreBoardController = new PlayerScoreBoardController();
        playerScoreBoardController.addObserver(this);

        ArrayList<Player> loadedPlayers = playerScoreBoardController.loadPlayers();

        playerScoreBoardController.setHighScores(loadedPlayers);

        ListView scores = findViewById(R.id.scoreList);
        ViewAdapter viewAdapter = new ViewAdapter();
        scores.setAdapter(viewAdapter);
    }

    static String[] getGames() {
        return GAMES;
    }


    /**
     * Activate the back to menu button
     */
    private void addBackToMenuListener() {
        Button menuButton = findViewById(R.id.BackToMenuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToMenu();
            }
        });
    }

    /**
     * Display the menu activity
     */
    private void switchToMenu() {
        Intent tmp = new Intent(this, PlayerMenuActivity.class);
        startActivity(tmp);
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public class ViewAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return GameOptionsActivity.numGames;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.custom_layout, null);
            TextView textView_game = convertView.findViewById(R.id.textView_game);
            TextView textView_score = convertView.findViewById(R.id.textView_score);

            textView_game.setText(GAMES[position]);
            textView_score.setText(SCORES[position]);

            return convertView;
        }
    }
}
