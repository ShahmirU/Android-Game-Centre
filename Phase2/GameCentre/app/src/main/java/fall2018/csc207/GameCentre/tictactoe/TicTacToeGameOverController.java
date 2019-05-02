package fall2018.csc207.GameCentre.tictactoe;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;

import fall2018.csc207.GameCentre.LoginController;
import fall2018.csc207.GameCentre.Player;
import fall2018.csc207.GameCentre.SignUpActivity;

/**
 * The game over controller for tic tac toe.
 */
public class TicTacToeGameOverController extends Observable {

    /**
     * The number representing the player's current highest score for this game.
     */
    private int currentHighScore = 0;

    /**
     * Update current player's high score for tic tac toe
     *
     * @param myScore the score of the player
     */
    void updateHighScore(int myScore) {
        this.currentHighScore = LoginController.getCurrentPlayer().getHighScore("Tic Tac Toe");
        if (myScore >= this.currentHighScore) {
            setHighScore(myScore);
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Set a player's high score
     *
     * @param score the score of the player
     */
    private void setHighScore(int score) {
        LoginController.getCurrentPlayer().setHighScore("Tic Tac Toe", score);
    }

    /**
     * Save the list of players from the game centre to a serialized file
     *
     * @param loadedPlayers the arraylist of players
     * @param context       the context of the app
     */
    void savePlayers(ArrayList<Player> loadedPlayers, Context context) {

        ArrayList<Player> playersCopy = (ArrayList) loadedPlayers.clone();

        //save information to file
        try {
            FileOutputStream fileOut = context.openFileOutput(SignUpActivity.SAVE_FILE, Activity.MODE_PRIVATE);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOut);
            outputStream.writeObject(playersCopy);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

    }

    /**
     * Load a list the list of players in the game centre from a serialized file
     *
     * @return the list of players
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
     * Update the score of a player with the highest score.
     *
     * @return the list of players in the game centre
     */
    ArrayList<Player> updatePlayerScore(ArrayList<Player> loadedPlayers, int score) {
        for (int i = 0; i < loadedPlayers.size(); i++) {
            if (loadedPlayers.get(i).getUsername().equals(LoginController.getCurrentPlayer().getUsername())) {
                if (score > loadedPlayers.get(i).getHighScore("Tic Tac Toe"))
                    loadedPlayers.get(i).setHighScore("Tic Tac Toe", score);
            }
        }
        return loadedPlayers;
    }
}
