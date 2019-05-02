package fall2018.csc207.GameCentre.perfectpairs;

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

public class PerfectPairsGameOverController extends Observable {

    /**
     * The number representing the player's current highest score for this game.
     */
    private int currentHighScore = 0;


    void updateHighScore(int myScore) {
        this.currentHighScore = LoginController.getCurrentPlayer().getHighScore("Perfect Pairs");
        if (myScore >= this.currentHighScore) {
            setHighScore(myScore);
        }
        setChanged();
        notifyObservers();
    }

    private void setHighScore(int score) {
        LoginController.getCurrentPlayer().setHighScore("Perfect Pairs", score);
    }

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

    ArrayList<Player> updatePlayerScore(ArrayList<Player> loadedPlayers, int score) {
        for (int i = 0; i < loadedPlayers.size(); i++) {
            if (loadedPlayers.get(i).getUsername().equals(LoginController.getCurrentPlayer().getUsername())) {
                if (score > loadedPlayers.get(i).getHighScore("Perfect Pairs"))
                    loadedPlayers.get(i).setHighScore("Perfect Pairs", score);
            }
        }
        return loadedPlayers;
    }
}
