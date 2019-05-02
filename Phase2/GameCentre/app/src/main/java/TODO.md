TO DO:

Overall game centre:
-Fix player score board so that it is not hard coded, and extensible for many games
-Change ArrayList positions = new ArrayList() to List Positions(?), and private HashMap<String, Integer> userHighScores
-Include proper javadoc for everything
-Create Packages and move classes to their proper packages
-unit test everything (check code coverage)
-update readme files with all new functionality + setup instructions
-update team.md file with meeting notes "go into a bit more detail about why design decisions were made,
                                         and even perhaps the alternatives," plus explain what everyone worked on
                                         /is working on
-refactor! remove as many public/static variables as possible/ make private variables and include getters/setters
-add any extra design  patterns
-make sure to include citations IN the code WITHIN the javadoc


Perfect Pairs:
-Implement scoring system
- decouple
-Make game over page displaying the score
-implement perfect pairs scoreboard
-update player scoreboard with perfect pairs score
-implement autosave feature/save game/load game on the starting page
-write unit test classes for perfect pairs

Tic Tac Toe:
-decouple logic from tic tac toe activity class to a controller class
-add proper javadoc
-implement undo feature to remove the last CPU move and last user move
-write unit tests for tic tac toe

Sliding Tiles:
-continue decoupling activity and controller classes
-fix sliding tiles scoreboard once decoupled
-possibly create a load/save class for sliding tiles/perfect pairs
-unit testing the remaining classes

Presentation:
-update walkthrough.pdf; include:
    What is your unit test coverage?
    What are the most important classes in your program?
    What design patterns did you use? What problems do each of them solve?
    How did you design your scoreboard? Where are high scores stored? How do they get displayed?
(For Later)
-uml design?
-setup for presentation