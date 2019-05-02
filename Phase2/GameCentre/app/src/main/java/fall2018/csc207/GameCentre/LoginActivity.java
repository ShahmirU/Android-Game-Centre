package fall2018.csc207.GameCentre;

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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


/**
 * The log in page for the game centre.
 */
public class LoginActivity extends AppCompatActivity implements Observer {

    /**
     * The username of a current player.
     */
    private EditText usernameInput;

    /**
     * The password corresponding to the current player.
     */
    private EditText passwordInput;

    /**
     * The controller for the login class.
     */
    private LoginController loginController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamecentre);

        loginController = new LoginController();
        loginController.addObserver(this);

        this.usernameInput = findViewById(R.id.Username);
        this.passwordInput = findViewById(R.id.Password);
    }

    /**
     * Allows a user to sign in.
     *
     * @param v the view of the sign in
     */
    public void signIn(View v) {

        String user = usernameInput.getText().toString();
        String pass = passwordInput.getText().toString();

        ArrayList<Player> loadedPlayers = loadPlayers();

        //check if the username entered already exists and allow a user a sign in.
        boolean successfulSignIn = loginController.isValidUser(user, pass, loadedPlayers);
        if (successfulSignIn) {
            switchToMenu();
        } else {
            makeToastIncorrectPassword();
        }
    }

    /**
     * Allows a user to sign up for the game centre
     *
     * @param v the view for sign in
     */
    public void signUp(View v) {
        switchToSignUp();
    }


    /**
     * Displays a message when a user inputs an incorrect username or password
     */
    private void makeToastIncorrectPassword() {
        Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
    }

    /**
     * Allows the sign up screen to appear
     */
    private void switchToSignUp() {
        Intent tmp = new Intent(this, SignUpActivity.class);
        startActivity(tmp);
    }

    /**
     * Switches to the user menu
     */
    private void switchToMenu() {
        Intent tmp = new Intent(this, PlayerMenuActivity.class);
        startActivity(tmp);
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

    @Override
    public void update(Observable o, Object arg) {

    }
}
