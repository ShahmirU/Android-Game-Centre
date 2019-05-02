package fall2018.csc207.GameCentre.tictactoe;

/*
Adapted from: https://github.com/karansthr/Tic-Tac-Toe.git
 */

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import fall2018.csc207.GameCentre.LoginController;
import fall2018.csc207.GameCentre.R;

/**
 * Class to represent the main tic tac toe game.
 */
public class TicTacToeGameActivity extends AppCompatActivity {

    /**
     * Store a random number between 0 to 2
     */
    private Random r = new Random();

    /**
     * represents the row of the last move made by the player
     */
    int pPositionX;

    /**
     * represents the column of the last move made by the player
     */
    int pPositionY;

    /**
     * represents the row of the last move made by the CPU
     */
    int cPositionX;

    /**
     * represents the column of the move made by the CPU
     */
    int cPositionY;

    /**
     * Keeps track of which symbol to put on the board and who goes first in each game
     */
    private int symbolChecker = 0;

    /**
     * Represents if the Tic Tac Toe board is solved or not
     */
    private int win = 0;

    /**
     * To update boardTracker with player one's moves
     */
    private int playerOneMoveChecker;

    /**
     * To update boardTracker with player two's moves
     */
    private int playerTwoMoveChecker;

    /**
     * A loop variable counter.
     */
    private int i;

    /**
     * Number of games played
     */
    private int game = 1;

    /**
     * Keeps track of the number of moves made
     */
    private int moveCounter = 0;

    /**
     * Keeps track of the total score of player one.
     */
    private int score1 = 0;

    /**
     * Keeps track of the total score of player two.
     */
    private int score2 = 0;

    /**
     * Checks if the game ended on a draw or not.
     */
    private int drawChecker = 0;


    /**
     * Keeps track of the moves made on the board.
     */
    private static int[][] boardTracker = new int[3][3];

    /**
     * Keeps track of the positions of the moves made on the board.
     */
    private int[] sum = new int[8];

    /**
     * Keeps track of the buttons pressed on the board.
     */
    private static int[][] buttonPressed = new int[3][3];

    /**
     * Represents the final score of the current player.
     */
    public static int score = 0;

    /**
     * Checks if a move has been made after an undo.
     */
    boolean moveMade = false;

    /**
     * View of player one's username
     */
    TextView p1;

    /**
     * View of player two's username (CPU)
     */
    TextView p2;

    /**
     * String representing the player's username
     */
    String player1;

    /**
     * String representing the CPU's username
     */
    String player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tictactoe_content_afterstart);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        playerOneMoveChecker = 1;
        playerTwoMoveChecker = 10;


        player1 = LoginController.getCurrentPlayer().getUsername();
        player2 = "CPU";
        p1 = findViewById(R.id.playerone);
        p2 = findViewById(R.id.playertwo);

        p1.setText(player1);
        p2.setText(player2);

        Toast.makeText(this, "" + player1 + "\'s turn", Toast.LENGTH_SHORT).show();

    }

    /**
     * Allow the user to undo their last move and the CPU's last move.
     */
    public void undo(View view) {
        if (moveMade) {
            boardTracker[cPositionX][cPositionY] -= playerTwoMoveChecker;
            boardTracker[pPositionX][pPositionY] -= playerOneMoveChecker;
            printBoard();
            symbolChecker -= 2;
            buttonPressed[pPositionX][pPositionY]--;
            buttonPressed[cPositionX][cPositionY]--;
            moveCounter -= 2;
            moveMade = false;
        } else {
            makeToastMakeAMoveBeforeUndo();
        }
    }

    /**
     * Display that no more undos are available until a new move has been made.
     */
    private void makeToastMakeAMoveBeforeUndo() {
        Toast.makeText(this, "Make another move to undo.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Pressing the top left button.
     *
     * @param view the current view of the game
     */
    public void topLeft(View view) {


        if (win == 0 && buttonPressed[0][0] == 0) {
            if (symbolChecker % 2 == 0)
                boardTracker[0][0] = playerOneMoveChecker;
            else
                boardTracker[0][0] = playerTwoMoveChecker;

            printBoard();
            winchecker();
            cpuplay();
            symbolChecker++;
            buttonPressed[0][0]++;
            pPositionX = 0;
            pPositionY = 0;
            moveMade = true;
        }
    }

    /**
     * Pressing the top centre button.
     *
     * @param view the current view of the game
     */
    public void topCentre(View view) {

        if (win == 0 && buttonPressed[0][1] == 0) {
            if (symbolChecker % 2 == 0) boardTracker[0][1] = playerOneMoveChecker;
            else boardTracker[0][1] = playerTwoMoveChecker;

            printBoard();
            winchecker();
            cpuplay();
            buttonPressed[0][1]++;
            symbolChecker++;
            pPositionX = 0;
            pPositionY = 1;
            moveMade = true;
        }
    }

    /**
     * Pressing the top right button.
     *
     * @param view the current view of the game
     */
    public void topRight(View view) {
        if (win == 0 && buttonPressed[0][2] == 0) {
            if (symbolChecker % 2 == 0) boardTracker[0][2] = playerOneMoveChecker;
            else boardTracker[0][2] = playerTwoMoveChecker;

            printBoard();
            winchecker();
            cpuplay();
            buttonPressed[0][2]++;
            symbolChecker++;
            pPositionX = 0;
            pPositionY = 2;
            moveMade = true;
        }
    }

    /**
     * Pressing the middle left button.
     *
     * @param v the current view of the game
     */
    public void middleLeft(View v) {
        if (win == 0 && buttonPressed[1][0] == 0) {
            if (symbolChecker % 2 == 0) boardTracker[1][0] = playerOneMoveChecker;
            else boardTracker[1][0] = playerTwoMoveChecker;

            printBoard();
            winchecker();
            cpuplay();

            ++buttonPressed[1][0];
            symbolChecker++;
            pPositionX = 1;
            pPositionY = 0;
            moveMade = true;
        }
    }

    /**
     * Pressing the middle centre button.
     *
     * @param v the current view of the game
     */
    public void middleCentre(View v) {
        if (win == 0 && buttonPressed[1][1] == 0) {
            if (symbolChecker % 2 == 0) boardTracker[1][1] = playerOneMoveChecker;
            else boardTracker[1][1] = playerTwoMoveChecker;
            printBoard();
            winchecker();
            cpuplay();
            ++buttonPressed[1][1];
            symbolChecker++;
            pPositionX = 1;
            pPositionY = 1;
            moveMade = true;
        }
    }

    /**
     * Pressing the middle right button.
     *
     * @param v the current view of the game
     */
    public void middleRight(View v) {
        if (win == 0 && buttonPressed[1][2] == 0) {
            if (symbolChecker % 2 == 0) boardTracker[1][2] = playerOneMoveChecker;
            else boardTracker[1][2] = playerTwoMoveChecker;

            printBoard();
            winchecker();
            cpuplay();
            ++buttonPressed[1][2];
            symbolChecker++;
            pPositionX = 1;
            pPositionY = 2;
            moveMade = true;
        }
    }

    /**
     * Pressing the bottom left button.
     *
     * @param v the current view of the game
     */
    public void bottomLeft(View v) {
        if (win == 0 && buttonPressed[2][0] == 0) {
            if (symbolChecker % 2 == 0) boardTracker[2][0] = playerOneMoveChecker;
            else boardTracker[2][0] = playerTwoMoveChecker;

            printBoard();
            winchecker();
            cpuplay();
            ++buttonPressed[2][0];
            symbolChecker++;
            pPositionX = 2;
            pPositionY = 0;
            moveMade = true;
        }
    }

    /**
     * Pressing the bottom centre button.
     *
     * @param v the current view of the game
     */
    public void bottomCentre(View v) {
        if (win == 0 && buttonPressed[2][1] == 0) {
            if (symbolChecker % 2 == 0) boardTracker[2][1] = playerOneMoveChecker;
            else boardTracker[2][1] = playerTwoMoveChecker;
            printBoard();
            winchecker();
            cpuplay();
            ++buttonPressed[2][1];
            symbolChecker++;
            pPositionX = 2;
            pPositionY = 1;
            moveMade = true;
        }
    }

    /**
     * Pressing the bottom right button.
     *
     * @param v the current view of the game
     */
    public void bottomRight(View v) {
        if (win == 0 && buttonPressed[2][2] == 0) {
            if (symbolChecker % 2 == 0) boardTracker[2][2] = playerOneMoveChecker;
            else boardTracker[2][2] = playerTwoMoveChecker;

            printBoard();
            winchecker();
            cpuplay();
            ++buttonPressed[2][2];
            symbolChecker++;
            pPositionX = 2;
            pPositionY = 2;
            moveMade = true;
        }
    }

    /**
     * Allowing the cpu to make a move following the player's move.
     */
    public void cpuplay() {
        if (win == 0) {
            if (ifcpuwin()) ;
            else emptyany();
            printBoard();
            winchecker();

            symbolChecker++;
        }
    }

    /**
     * If possible allows the CPU to make a move that will win the game
     */
    public boolean ifcpuwin() {
        for (i = 0; i < 8; i++) {
            if (sum[i] == 2 * playerTwoMoveChecker) {
                if (i == 0) {
                    for (int x = 0; x < 3; x++)
                        if (boardTracker[0][x] == 0) {
                            boardTracker[0][x] = playerTwoMoveChecker;
                            cPositionX = 0;
                            cPositionY = x;
                        }
                }
                if (i == 1) {
                    for (int x = 0; x < 3; x++)
                        if (boardTracker[1][x] == 0) {
                            boardTracker[1][x] = playerTwoMoveChecker;
                            cPositionX = 1;
                            cPositionY = x;
                        }
                }
                if (i == 2) {
                    for (int x = 0; x < 3; x++)
                        if (boardTracker[2][x] == 0) {
                            boardTracker[2][x] = playerTwoMoveChecker;
                            cPositionX = 2;
                            cPositionY = x;
                        }
                }

                if (i == 3) {
                    for (int x = 0; x < 3; x++)
                        if (boardTracker[x][0] == 0) {
                            boardTracker[x][0] = playerTwoMoveChecker;
                            cPositionX = x;
                            cPositionY = 0;
                        }
                }

                if (i == 4) {

                    for (int x = 0; x < 3; x++)
                        if (boardTracker[x][1] == 0) {
                            boardTracker[x][1] = playerTwoMoveChecker;
                            cPositionX = x;
                            cPositionY = 1;
                        }
                }

                if (i == 5) {

                    for (int x = 0; x < 3; x++)
                        if (boardTracker[x][2] == 0) {
                            boardTracker[x][2] = playerTwoMoveChecker;
                            cPositionX = x;
                            cPositionY = 2;
                        }
                }
                if (i == 6) {

                    for (int y = 0; y < 3; y++)
                        for (int x = 0; x < 3; x++)
                            if (x == y)
                                if (boardTracker[x][y] == 0) {
                                    boardTracker[x][y] = playerTwoMoveChecker;
                                    cPositionX = x;
                                    cPositionY = y;
                                }
                }
                if (i == 7) {
                    if (boardTracker[0][2] == 0) {
                        boardTracker[0][2] = playerTwoMoveChecker;
                        cPositionX = 0;
                        cPositionY = 2;
                    } else if (boardTracker[1][1] == 0) {
                        boardTracker[1][1] = playerTwoMoveChecker;
                        cPositionX = 1;
                        cPositionY = 1;
                    } else {
                        boardTracker[2][0] = playerTwoMoveChecker;
                        cPositionX = 2;
                        cPositionY = 0;
                    }

                }
                return true;
            }
        }
        return false;
    }

    /**
     * Allows the cpu to go to make a move at any empty space on the board.
     */
    public void emptyany() {

        if (moveCounter == 0)
            while (true) {
                int x = rand();
                int y = rand();

                if (boardTracker[x][y] == 0) {
                    boardTracker[x][y] = playerTwoMoveChecker;
                    buttonPressed[x][y]++;
                    cPositionX = x;
                    cPositionY = y;
                    return;

                }
            }

        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++)
                if (boardTracker[x][y] == 0) {
                    boardTracker[x][y] = playerTwoMoveChecker;
                    buttonPressed[x][y]++;
                    cPositionX = x;
                    cPositionY = y;
                    return;
                }


    }

    /**
     * return a random variable between 0 to 2.
     */
    public int rand() {
        return r.nextInt(3);
    }

    /**
     * Printing the board.
     */
    public void printBoard() {
        ImageView q1, q2, q3, q4, q5, q6, q7, q8, q9;

        q1 = (ImageView) findViewById(R.id.tzz);
        q2 = (ImageView) findViewById(R.id.tzo);
        q3 = (ImageView) findViewById(R.id.tzt);
        q4 = (ImageView) findViewById(R.id.toz);
        q5 = (ImageView) findViewById(R.id.too);
        q6 = (ImageView) findViewById(R.id.tot);
        q7 = (ImageView) findViewById(R.id.ttz);
        q8 = (ImageView) findViewById(R.id.tto);
        q9 = (ImageView) findViewById(R.id.ttt);


        if (boardTracker[0][0] == 1) q1.setImageResource(R.drawable.x);
        else if (boardTracker[0][0] == 10) q1.setImageResource(R.drawable.oo);
        else q1.setImageResource(R.drawable.tile_25);


        if (boardTracker[0][1] == 1) q2.setImageResource(R.drawable.x);
        else if (boardTracker[0][1] == 10) q2.setImageResource(R.drawable.oo);
        else q2.setImageResource(R.drawable.tile_25);

        if (boardTracker[0][2] == 1) q3.setImageResource(R.drawable.x);
        else if (boardTracker[0][2] == 10) q3.setImageResource(R.drawable.oo);
        else q3.setImageResource(R.drawable.tile_25);

        if (boardTracker[1][0] == 1) q4.setImageResource(R.drawable.x);
        else if (boardTracker[1][0] == 10) q4.setImageResource(R.drawable.oo);
        else q4.setImageResource(R.drawable.tile_25);

        if (boardTracker[1][1] == 1) q5.setImageResource(R.drawable.x);
        else if (boardTracker[1][1] == 10) q5.setImageResource(R.drawable.oo);
        else q5.setImageResource(R.drawable.tile_25);

        if (boardTracker[1][2] == 1) q6.setImageResource(R.drawable.x);
        else if (boardTracker[1][2] == 10) q6.setImageResource(R.drawable.oo);
        else q6.setImageResource(R.drawable.tile_25);

        if (boardTracker[2][0] == 1) q7.setImageResource(R.drawable.x);
        else if (boardTracker[2][0] == 10) q7.setImageResource(R.drawable.oo);
        else q7.setImageResource(R.drawable.tile_25);

        if (boardTracker[2][1] == 1) q8.setImageResource(R.drawable.x);
        else if (boardTracker[2][1] == 10) q8.setImageResource(R.drawable.oo);
        else q8.setImageResource(R.drawable.tile_25);

        if (boardTracker[2][2] == 1) q9.setImageResource(R.drawable.x);
        else if (boardTracker[2][2] == 10) q9.setImageResource(R.drawable.oo);
        else q9.setImageResource(R.drawable.tile_25);
    }


    /**
     * Shows the dialog in the tic tac toe game.
     *
     * @param whoWon    who won the current game
     * @param scoreWon  the score of the winning player
     * @param whoLose   who lost the current game
     * @param scoreLose the score of the losing player
     */
    public void showDialog(String whoWon, String scoreWon, String whoLose, String scoreLose) {

        final Dialog dialog = new Dialog(TicTacToeGameActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.tictactoe_dialog_layout);
        TextView titleText = dialog.findViewById(R.id.title_text);
        dialog.setCancelable(false);
        dialog.show();

        titleText.setText(whoWon);
        Button playAgainButton = dialog.findViewById(R.id.play_again_button);
        score = score1 * 100 - score2 * 10;
        Button endButton = dialog.findViewById(R.id.end_game_button);

        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                switchToGameOver();
            }
        });

        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                playmore();
            }
        });
    }

    /**
     * Switches the game to a state of game over.
     */
    public void switchToGameOver() {
        Intent tmp = new Intent(this, TicTacToeGameOverActivity.class);
        startActivity(tmp);
    }

    /**
     * Checks if anyone has won the game.
     */
    public void winchecker() {
        moveCounter++;
        sum[0] = boardTracker[0][0] + boardTracker[0][1] + boardTracker[0][2];
        sum[1] = boardTracker[1][0] + boardTracker[1][1] + boardTracker[1][2];
        sum[2] = boardTracker[2][0] + boardTracker[2][1] + boardTracker[2][2];
        sum[3] = boardTracker[0][0] + boardTracker[1][0] + boardTracker[2][0];
        sum[4] = boardTracker[0][1] + boardTracker[1][1] + boardTracker[2][1];
        sum[5] = boardTracker[0][2] + boardTracker[1][2] + boardTracker[2][2];
        sum[6] = boardTracker[0][0] + boardTracker[1][1] + boardTracker[2][2];
        sum[7] = boardTracker[0][2] + boardTracker[1][1] + boardTracker[2][0];


        for (int i = 0; i < 8; i++)
            if (sum[i] == 3 || sum[i] == 30) {
                win++;
                if ((sum[i] == 3)) {
                    score1++;
                    TextView q1 = (TextView) findViewById(R.id.p1score);
                    q1.setText("" + score1);
                    showDialog("" + player1 + " won!", "" + score1, "" + player2, "" + score2);

                }

                if ((sum[i] == 30)) {
                    score2++;
                    TextView q1 = (TextView) findViewById(R.id.p2score);
                    q1.setText("" + score2);
                    showDialog("" + player2 + " won!", "" + score2, "" + player1, "" + score1);
                }

            }

        if ((moveCounter == 9) && (win == 0)) {
            showDialog("This is a draw !", "" + score1, "" + player2, "" + score2);
            drawChecker++;
        }


    }

    /**
     * Allows a user to play another game after one ends.
     */
    private void playmore() {
        if ((drawChecker > 0) || (win > 0)) {
            game++;
            TextView qq = (TextView) findViewById(R.id.gamenumber);
            qq.setText("" + game);

            for (int i = 0; i < 8; i++)
                sum[i] = 0;

            drawChecker = 0;


            ImageView q1, q2, q3, q4, q5, q6, q7, q8, q9;
            q1 = (ImageView) findViewById(R.id.tzz);
            q2 = (ImageView) findViewById(R.id.tzo);
            q3 = (ImageView) findViewById(R.id.tzt);
            q4 = (ImageView) findViewById(R.id.toz);
            q5 = (ImageView) findViewById(R.id.too);
            q6 = (ImageView) findViewById(R.id.tot);
            q7 = (ImageView) findViewById(R.id.ttz);
            q8 = (ImageView) findViewById(R.id.tto);
            q9 = (ImageView) findViewById(R.id.ttt);
            q1.setImageDrawable(null);
            q2.setImageDrawable(null);
            q3.setImageDrawable(null);
            q4.setImageDrawable(null);
            q5.setImageDrawable(null);
            q6.setImageDrawable(null);
            q7.setImageDrawable(null);
            q8.setImageDrawable(null);
            q9.setImageDrawable(null);

            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    buttonPressed[i][j] = 0;

            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    boardTracker[i][j] = 0;


            if ((game + 1) % 2 == 0)
                Toast.makeText(this, "" + player1 + "\'s turn", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "" + player2 + "\'s turn", Toast.LENGTH_SHORT).show();
            win = 0;
            moveCounter = 0;
            symbolChecker = (game + 1) % 2;

            if (game % 2 == 0)
                cpuplay();
        }
    }

    /**
     * Shows the exit dialog when a user tries to leave.
     */
    private void showExitDialog() {
        final Dialog dialog = new Dialog(TicTacToeGameActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.tictactoe_dialog_layout_exit);
        dialog.setCancelable(false);

        dialog.show();

        Button exit = dialog.findViewById(R.id.yes_button);
        final Button dismiss = dialog.findViewById(R.id.no_button);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        showExitDialog();
    }

}


