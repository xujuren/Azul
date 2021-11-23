# New Code for Deliverable D2D

## < uid > < name >

For Deliverable D2D, I contributed the following new statements of original code:

- Added the [public GameBoard(String sharedState)](https://gitlab.cecs.anu.edu.au/u6225282/comp1110-ass2-tue12g/-/blob/master/src/comp1110/ass2/GameBoard.java#L14-57) 
- Added the [public char drawTileFromBag()](https://gitlab.cecs.anu.edu.au/u6225282/comp1110-ass2-tue12g/-/blob/master/src/comp1110/ass2/GameBoard.java#L76-88) 
- Added the [public String refillFactories()](https://gitlab.cecs.anu.edu.au/u6225282/comp1110-ass2-tue12g/-/blob/master/src/comp1110/ass2/GameBoard.java#L76-88)
- Added the [public boolean isBagEmpty()](https://gitlab.cecs.anu.edu.au/u6225282/comp1110-ass2-tue12g/-/blob/master/src/comp1110/ass2/GameBoard.java#L98-100)
- Added the [public boolean isDiscardEmpty()](https://gitlab.cecs.anu.edu.au/u6225282/comp1110-ass2-tue12g/-/blob/master/src/comp1110/ass2/GameBoard.java#L102-104)
- Added the [public String toString()](https://gitlab.cecs.anu.edu.au/u6225282/comp1110-ass2-tue12g/-/blob/master/src/comp1110/ass2/GameBoard.java#L125-162)
- Added the [public class GameBoardTest](https://gitlab.cecs.anu.edu.au/u6225282/comp1110-ass2-tue12g/-/blob/master/tests/comp1110/ass2/GameBoardTest.java#L7-160)
 
[public GameBoard(String sharedState)] is a class constructor that take sharedState as parameter and converting it to Object Orient.

[public char drawTileFromBag()] is a method that draw a random tile from bag based on Object Orient.

[public String refillFactories()] is a method that refill Factories when factory is empty based on Object Orient.

[public boolean isBagEmpty()] is a method that check if the bag is empty based on Object Orient.

[public boolean isDiscardEmpty()] is a method that check if the discard is empty based on Object Orient.

[public String toString()] is a method that convert the game state from Object Orient to string

[public class GameBoardTest] is a JUnit test file that I created for testing my method in class GameBoard.