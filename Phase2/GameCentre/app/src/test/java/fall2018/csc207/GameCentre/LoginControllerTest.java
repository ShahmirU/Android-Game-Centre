package fall2018.csc207.GameCentre;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LoginControllerTest {

    ArrayList<Player> setUpPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("Bob", "1234", "Bob");
        Player p2 = new Player("Harry", "12", "Harry");
        Player p3 = new Player("Tim", "123", "Tim");

        players.add(p1);
        players.add(p2);
        players.add(p3);

        return players;
    }

    @Test
    public void isValidUserCorrectTest() {
        ArrayList<Player> players = setUpPlayers();
        assertTrue(LoginController.isValidUser("Harry", "12", players));
    }

    @Test
    public void isValidUserIncorrectTest() {
        ArrayList<Player> players = setUpPlayers();
        assertFalse(LoginController.isValidUser("Bill", "12", players));
    }

}

