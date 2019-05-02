Cloning and Setup Instructions:
To setup and properly clone this app, open up android studio and import a project from version
control, specifying git. Copy the link to our phase 2 project on markus, and paste that link into
the URL box. Test the link to ensure a successful connection and then clone the app.
You may use the default gradle wrapper which appears after specifying the directory to end in
"Group0624/Phase2/GameCentre" This app uses APK 27, Oreo SDK. Once the project has been successfully
cloned, make sure to register a VCS root, and then you may run the app on an android phone emulator.
While this app may work on many phones, it is recommended to run on a google pixel 2 because that
is what was used to test this application.

----------------------------------------------------------------------------------------------------
Welcome To Our Android Game Centre!

We currently have 3 playable games as part of the game centre which include; sliding tiles, perfect
pairs and tic tac toe.

Game Launch Centre:
When the app is first launched, it begins at a login page, in which a player can either sign into
their pre-existing account by entering in their corresponding username and password and clicking
the sign in button, or, if this is the player's first time, they can sign up and create a new game
centre account. On the sign up page, a new player must enter a unique username that does not
already exist in order to successfully be able to create a new account. They also need to
enter their email and create a password for their account.
After successfully signing up, the player can use their newly created account to sign into the game
centre with their username and password.

Once a player is signed in, they have the option to log out of the game centre, look at their own
personal scoreboard, or decide to play a game. Currently, if you choose to look at your scoreboard,
you can see your top score for each of the three versions of the sliding tiles game, as well as
tic tac toe, and perfect pairs. After this, you can click 'Back to Menu' in order to play a game,
select from an option of games, or log out.

Scoreboards:
There are two types of scoreboards implemented in the game centre. The first is the per player
scoreboard which every player has, and can be accessed through the menu activity. This scoreboard
will display the user's highest score for every game in the game centre. We have also implemented
per game scoreboards, which will display the highest score and the player who achieved that score,
specific to each game. The per-game scoreboards can be accessed after selecting a specific game.
----------------------------------------------------------------------------------------------------
--GAME: SLIDING TILES--

How to Play:
The goal of sliding tiles is to order numbers from smallest to greatest, starting from the top
left hand corner to the bottom right, in row-major order. The number of tiles is the length of
the columns and rows of the slidingTilesBoard, multiplied together and subtract one. There is also one empty
tile that needs to be placed at the bottom right in order to win the game.
A player can make moves by swapping any tiles that are neighbouring the blank one.

Sliding Tiles Complexities:
The player can choose to play the game using a 3x3, 4x4, or 5x5 tiled slidingTilesBoard by inputting what
sized slidingTilesBoard they want to play with. Each complexity will constitute a different game, with a
different display on the score slidingTilesBoard.

Undo Feature:
In the sliding tiles game we have implemented an undo button feature, in which we allow the player
to specify before starting a new game; how many times they will be able to undo any of their moves
throughout the game. The player will be able to undo a move as long as there are available moves to
undo (i.e. they have made at least n moves since start of the game before trying to undo n times),
and they have not already used the undo feature more than what they specified for themselves. The
number of undo's completed will be remembered when a saved game is loaded as well. The default number
of undo's is 3.

Sliding Tiles Scoring System:
The scoring system for the sliding tiles puzzle game depends on the number of moves the player takes
to complete the game. Every player begins the game with an initial 10000 points, and 5 points are
deducted for every move they make. We also subtract 5 points every time a move is undone (through
the use of the undo button). Once the player successfully completes the game, their remaining points
becomes their score for the game. Higher remaining points at the end of the game constitutes a
higher score overall. If the player loses all of their points before they win the game, then their
final score will be 0, however, they will be allowed to continue playing the game until they win.

Saving and Loading Features:
The player can choose to save any game state on the sliding tiles starting activity page, and can
then load the saved instance of the game using the load button. The game also has an autosave feature,
which saves the most recent game the player had open. This auto-save feature updates and saves the game
after a single move has been made. If the player decides to, they may also load a game from the
most recent auto-save by using the auto-load button. The player can still go back to their saved game
using the load button provided they don't re-save over their previous file.

--GAME: TIC TAC TOE--

How to Play:
Tic Tac Toe is typically a two player game, but because only one person can be logged into the
Game centre at the same time, the player will play the game against a CPU. This is a turn based game
in which the player's take turns putting down symbols on a 3x3 grid. The first person to get three
of their symbols in a row either horizontally, vertically, or diagonally is the winner. In our
implementation the player has a symbol X and the CPU has the symbol O. The player starts first
but the turn is alternated every game. If neither the player nor the CPU get 3 in a row then the
game ends in a draw.

Undo Feature:
In our game we allow the player to undo the most recent move they have made. When the undo button
is clicked it removes both the player's last move and the CPU's last move that was performed directly
after the player's move. If the player tries to undo again before making another move, then they
will not be able to do so. Thus, a player may undo as many times as they like provided that they
have a made a move to since the start of the game/the last undo.

Tic Tac Toe Scoring System:
Once a game is over (i.e. either a win, loss or draw), the score gets updated up above the game.
At this point, the player has a choice to play another game or end the game. Once the player
selects the end game button their score is calculated by taking the number of games the player has
won, multiplying it by 100 and then subtracting 10 times the number of games lost (with no change in
score if the game was a draw). Thus, in order to get a higher score a player should aim to
win as many times as they can before ending the game while avoiding losses as much as possible.

--GAME: PERFECT PAIRS--

How To Play:
Perfect pairs is a card matching game that uses the same board as the 4x4 sliding tiles game. The
aim of the game is to pair the cards to their corresponding matches in as little moves as possible.
The game begins with 16 face down cards. There are 8 symbols imprinted on the backs, where 2 are the
same. Click on a card to reveal its symbol, and then click on another card. If the symbols are the
same then the cards will stay face up, otherwise they will turn back face down once another
card is clicked. This is essentially a memory game where the player tries to match the pairs of
cards in as little moves as possible.

Perfect Pairs Scoring System:
The scoring system for the perfect pairs game also depends on the number of moves the player takes
to complete the game. Every player begins the game with an initial 1000 points, and 10 points are
deducted for every pair of cards they click on. We also subtract 5 points every time a move is undone
(through the use of the undo button). Once the player successfully completes the game, their
remaining points becomes their score for the game. Higher remaining points at the end of the game
constitutes a higher score overall. If the player loses all of their points before they win the game,
then their final score will be 0, however, they will be allowed to continue playing the game until
they win.

Auto-Saving and Loading Feature:
The perfect pairs game has an auto-save feature in which the game state is being saved after every
card is clicked. If a player leaves the game before it is complete, then they can load an auto-saved
game which will remember the score and cards that were already flipped over/matched, and the player
can pick up the game where they left off.

---------------------------------------------------------------------------------------------------
Overall, our game centre contains all the required features plus some bonus functionality, all of
which are fully functional and working properly. We believe our UI for the app is neat, clear
and easy to follow, so there is no need for special instructions on how to run the program. We have
also created many toasts that tell the user if something is working successfully, or explain to the
user why some functionality may not process if a precondition has not been followed.
