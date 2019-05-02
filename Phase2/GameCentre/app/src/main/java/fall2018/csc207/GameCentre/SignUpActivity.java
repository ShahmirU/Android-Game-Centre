package fall2018.csc207.GameCentre;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Activity page to sign up as a new user in the game centre
 */
public class SignUpActivity extends AppCompatActivity implements Observer {

    /**
     * The text display of the player's chosen username
     */
    EditText usernameInput;

    /**
     * The text display of the player's chosen password
     */
    EditText passwordInput;

    /**
     * The text display of the player's email
     */
    EditText emailInput;

    /**
     * The serialized file storing the player and corresponding attributes
     */
    public static final String SAVE_FILE = "save_player.ser";

    private SignUpController signUpController;

    private static Activity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpController = new SignUpController();
        signUpController.addObserver(this);

        instance = this;

        usernameInput = findViewById(R.id.Username);
        passwordInput = findViewById(R.id.Password);
        emailInput = findViewById(R.id.Email);
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    /**
     * Allows the user to sign up if proper information is provided
     *
     * @param v the view of the sign up activity
     */
    public void signUp(View v) {
        String user = usernameInput.getText().toString();
        String pass = passwordInput.getText().toString();
        String email = emailInput.getText().toString();
        Player tempPlayer = new Player(user, pass, email);

        ArrayList<Player> loadedPlayers = loadPlayers();

        //check if the player has a unique username out of all the players in the game centre
        boolean usernameUnique = signUpController.checkUsername(user, loadedPlayers);

        //add new player to the list of players in the game centre
        if (usernameUnique) {
            addPlayer(tempPlayer, loadedPlayers, this);
            makeToastSaved();
            switchToGameCentre();
        } else {
            makeToastUserFound();
        }
    }

    /**
     * Display the sign in page
     */
    private void switchToGameCentre() {
        Intent tmp = new Intent(this, LoginActivity.class);
        startActivity(tmp);
    }

    /**
     * Display a sign-up successful message
     */
    private void makeToastSaved() {
        Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display the unsuccessful sign-up message
     */
    private void makeToastUserFound() {
        Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
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
     * Add the player's and player info from a serialized file.
     *
     * @param tempPlayer    the player to be added
     * @param loadedPlayers the list of accounts already existing.
     */
    void addPlayer(Player tempPlayer, ArrayList<Player> loadedPlayers, Context context) {
        loadedPlayers.add(tempPlayer);
        ArrayList<Player> playersCopy = (ArrayList) loadedPlayers.clone();

        //save information to file
        try {
            FileOutputStream fileOut = context.openFileOutput(SAVE_FILE, Activity.MODE_PRIVATE);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOut);
            outputStream.writeObject(playersCopy);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
