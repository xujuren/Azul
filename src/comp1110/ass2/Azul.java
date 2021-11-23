package comp1110.ass2;


import java.util.*;

public class Azul {
    /**
     * The default name for different number of players
     */

    public Azul() {

    }


    /**
     * Given a shared state string, determine if it is well-formed.
     * Note: you don't need to consider validity for this task.
     * A sharedState is well-formed if it satisfies the following conditions.
     * <p>
     * [turn][factories][centre][bag][discard]
     * where [turn][factories], [centre], [bag] and [discard] are replaced by the
     * corresponding small string as described below.
     * <p>
     * 0. [turn] The Turn substring is one character 'A'-'D' representing a
     * player, which indicates that it is this player's turn to make the next
     * drafting move. (In a two-player game, the turn substring can only take
     * the values 'A' or 'B').
     * <p>
     * 1. [factories] The factories substring begins with an 'F'
     * and is followed by a collection of *up to* 5 5-character factory strings
     * representing each factory.
     * Each factory string is defined in the following way:
     * 1st character is a sequential digit '0' to '4' - representing the
     * factory number.
     * 2nd - 5th characters are 'a' to 'e', alphabetically - representing
     * the tiles.
     * A factory may have between 0 and 4 tiles. If a factory has 0 tiles,
     * it does not appear in the factories string.
     * Factory strings are ordered by factory number.
     * For example: given the string "F1aabc2abbb4ddee": Factory 1 has tiles
     * 'aabc', Factory 2 has tiles 'abbb', Factory 4 has tiles 'ddee', and
     * Factories 0 and 4 are empty.
     * <p>
     * 2. [centre] The centre substring starts with a 'C'
     * This is followed by *up to* 15 characters.
     * Each character is 'a' to 'e', alphabetically - representing a tile
     * in the centre.
     * The centre string is sorted alphabetically.
     * For example: "Caaabcdde" The Centre contains three 'a' tiles, one 'b'
     * tile, one 'c' tile, two 'd' tile and one 'e' tile.
     * <p>
     * 3. [bag] The bag substring starts with a 'B'
     * and is followed by 5 2-character substrings
     * 1st substring represents the number of 'a' tiles, from 0 - 20.
     * 2nd substring represents the number of 'b' tiles, from 0 - 20.
     * 3rd substring represents the number of 'c' tiles, from 0 - 20.
     * 4th substring represents the number of 'd' tiles, from 0 - 20.
     * 5th substring represents the number of 'e' tiles, from 0 - 20.
     * <p>
     * For example: "B0005201020" The bag contains zero 'a' tiles, five 'b'
     * tiles, twenty 'c' tiles, ten 'd' tiles and twenty 'e' tiles.
     * 4. [discard] The discard substring starts with a 'D'
     * and is followed by 5 2-character substrings defined the same as the
     * bag substring.
     * For example: "D0005201020" The bag contains zero 'a' tiles, five 'b'
     * tiles, twenty 'c' tiles, ten 'd' tiles, and twenty 'e' tiles.
     *
     * @param sharedState the shared state - factories, bag and discard.
     * @return true if sharedState is well-formed, otherwise return false

     * author: all team members
     * All team members completed this task but we choose to put Juren's code here
     *
     * TASK 2
     */


    public static boolean isSharedStateWellFormed(String sharedState) {
        // FIXME Task 2
        String playerTurn = sharedState.substring(0, 1);
        if (!playerTurn.equals("A") && !playerTurn.equals("B") && !playerTurn.equals("C") && !playerTurn.equals("D")){
            return false;
        }
        //Removing playerTurn from state make the state easier to check
        sharedState = sharedState.substring(1);
        if (!sharedState.contains("F") || !sharedState.contains("C") || !sharedState.contains("B") || !sharedState.contains("D")){
            return false;
        }
        String[] splitSharedState = sharedState.split("[FCBD]"); // Splitting string to [Player][factories][centre][bag][discard]
        // Checking if tempState not contains [Player][factories][centre][bag][discard], return false if not otherwise return true
        if (splitSharedState.length < 5){
            return false;
        }
        // Checking the validity of Factories String

        //Factory state consist of 0abcd1aaaa2....3.... .
        //The flag is the number before tiles, this function is to check whether this flag in order and whether they are in range
        if (!isFactoryFlagOrdered(splitSharedState[1])){
            return false;
        }
        //Checking the validity of each factories
        String[] fac = splitSharedState[1].split("[0123456789]"); // Splitting Factories to individual factory strings
        for (int i = 1; i < fac.length; i++) { // Start from idx 1 because the first string will be an empty string due the property of split function
            if (fac[i].length() != 4 || !isAlphabeticOrder(fac[i])){
                return false;
            }
        }

        //Checking whether the factory contain correct tiles
        char[] factory = splitSharedState[1].toCharArray();
        for (int i = 0; i < factory.length; i++) {
            if(i % 5 == 0){
                if(!Character.toString(factory[i]).matches("[0-9?]")){
                    return false;
                }
            }else{
                if(!Character.toString(factory[i]).matches("[a-e?]")){
                    return false;
                }
            }
        }

        //Checking is the center in alphabetical order
        if (!isAlphabeticOrder(splitSharedState[2])){
            return false;
        }

        // Checking the validity of Centre, Bag and Discard strings
        return splitSharedState[3].length() == 10 && splitSharedState[4].length() == 10;

    }

    /**
     * Given a playerState, determine if it is well-formed.
     * Note: you don't have to consider validity for this task.
     * A playerState is composed of individual playerStrings.
     * A playerState is well-formed if it satisfies the following conditions.
     * <p>
     * A playerString follows this pattern: [player][score][mosaic][storage][floor]
     * where [player], [score], [mosaic], [storage] and [floor] are replaced by
     * a corresponding substring as described below.
     * Each playerString is sorted by Player i.e. Player A appears before Player B.
     * <p>
     * 1. [player] The player substring is one character 'A' to 'D' -
     * representing the Player
     * <p>
     * 2. [score] The score substring is one or more digits between '0' and '9' -
     * representing the score
     * <p>
     * 3. [mosaic] The Mosaic substring begins with a 'M'
     * Which is followed by *up to* 25 3-character strings.
     * Each 3-character string is defined as follows:
     * 1st character is 'a' to 'e' - representing the tile colour.
     * 2nd character is '0' to '4' - representing the row.
     * 3rd character is '0' to '4' - representing the column.
     * The Mosaic substring is ordered first by row, then by column.
     * That is, "a01" comes before "a10".
     * <p>
     * 4. [storage] The Storage substring begins with an 'S'
     * and is followed by *up to* 5 3-character strings.
     * Each 3-character string is defined as follows:
     * 1st character is '0' to '4' - representing the row - each row number must only appear once.
     * 2nd character is 'a' to 'e' - representing the tile colour.
     * 3rd character is '0' to '5' - representing the number of tiles stored in that row.
     * Each 3-character string is ordered by row number.
     * <p>
     * 5. [floor] The Floor substring begins with an 'F'
     * and is followed by *up to* 7 characters in alphabetical order.
     * Each character is 'a' to 'f' - where 'f' represents the first player token.
     * There is only one first player token.
     * <p>
     * An entire playerState for 2 players might look like this:
     * "A20Ma02a13b00e42S2a13e44a1FaabbeB30Mc01b11d21S0e12b2F"
     * If we split player A's string into its substrings, we get:
     * [A][20][Ma02a13b00e42][S2a13e44a1][Faabbe].
     *
     *
     * @param playerState the player state string
     * @return True if the playerState is well-formed,
     * false if the playerState is not well-formed
     *
     * author: all team members
     * All team members completed this task but we choose to put Juren's code here
     *
     * TASK 3
     */
    public static boolean isPlayerStateWellFormed(String playerState) {
        // FIXME Task 3
        String[] player = playerState.split("[ABCD]");

        // Checking whether player are presenting in alphabetic order e.g. player A, player B, player C, player D
        int A_position = playerState.indexOf("A");
        int B_position = playerState.indexOf("B");
        int C_position = playerState.indexOf("C");
        int D_position = playerState.indexOf("D");

        if (A_position != 0){
            return false;
        }
        if (B_position <= A_position){
            return false;
        }
        if (C_position <= B_position && C_position != -1){
            return false;
        }
        if (D_position <= C_position && D_position != -1){
            return false;
        }

        //Starting to check the validity for each player
        for (int i = 1; i < player.length; i++) {
            int mosaicPosi = player[i].indexOf("M");
            int storagePosi = player[i].indexOf("S");
            int floorPosi = player[i].indexOf("F");
            if (mosaicPosi == -1 || storagePosi == -1 || floorPosi == -1) {
                return false;
            }
            String score = player[i].substring(0, mosaicPosi);
            String mosaic = player[i].substring(mosaicPosi + 1, storagePosi);
            String storage = player[i].substring(storagePosi + 1, floorPosi);
            String floor = player[i].substring(floorPosi + 1);

            //Checking the validity of score
            //score must not representing with in 3 digit, which means it can't exceeding 999 marks.
            if(score.length() > 3){
                return false;
            }
            //Checking whether score are all digits
            for (char s:score.toCharArray()) {
                if(!Character.isDigit(s)){
                    return false;
                }
            }
            //Checking the validity of mosaic
            char[] mosaicChar = mosaic.toCharArray();
            //Checking the size of mosaic, should not greater than 75. 5 rows 5 columns, each slot representing by 3 character, total 3*5*5 = 75
            if(mosaicChar.length % 3 != 0 || mosaicChar.length > 75){
                return false;
            }else {
                //Checking the is validity of each character in mosaic state
                // Each slot representing by [character from a to e][digit from 0 to 4][digit from 0 to 4]
                for (int j = 0; j < mosaicChar.length; j++) {
                    if (j % 3 == 0) {
                        if(!Character.toString(mosaicChar[j]).matches("[a-e?]")){
                            return false;
                        }
                    } else {
                        if(!Character.toString(mosaicChar[j]).matches("[0-4?]")){
                            return false;
                        }
                    }
                }
            }

            //Checking the validity of storage
            char[] storageChar = storage.toCharArray();
            //Checking the size of storage, should not greater than 15. 5 rows, each rows representing by 3 character, total 3*5 = 15
            if(storageChar.length % 3 != 0 || storageChar.length > 15){
                return false;
            }else{
                //Checking the is validity of each character in storage state
                // Each storage row representing by [digit from 0 to 4][character from a to e][digit from 0 to 5]
                for (int j = 0; j < storageChar.length; j++) {
                    if (j % 3 == 0) {
                        if (!Character.toString(storageChar[j]).matches("[0-4?]")) {
                            return false;
                        }

                    } else if(j % 3 == 1){
                        if (!Character.toString(storageChar[j]).matches("[a-e?]")) {
                            return false;
                        }
                    }else{
                        if (!Character.toString(storageChar[j]).matches("[0-5?]")) {
                            return false;
                        }
                    }
                }
            }

            //Checking the validity of floor
            char[] floorChar = floor.toCharArray();
            //Checking the length of floor, 7 tiles at most
            if(floorChar.length > 7){
                return false;
            }else{
                //checking the validity of each character of floor, should between a - f and can have 1 'f' at most
                int count = 0;
                for (int j = 0; j < floor.length(); j++) {
                    if (floorChar[j] == 'f'){
                        count++;
                    }
                    if (!Character.toString(floorChar[j]).matches("[a-f?]")){
                        return false;
                    }
                }
                if (count > 1){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Given the gameState, draw a *random* tile from the bag.
     * If the bag is empty, refill the the bag with the discard pile and then draw a tile.
     * If the discard pile is also empty, return 'Z'.
     *
     * author: Juren Xu, HongGic,Oh
     * Juren and HongGic completed this task but we choose to put Juren's code here
     *
     * @param gameState the current game state
     * @return the tile drawn from the bag, or 'Z' if the bag and discard pile are empty.
     * TASK 5
     */
    public static char drawTileFromBag(String[] gameState) {
        // FIXME Task 5
        Game game = new Game(gameState);
        Tile tile = game.drawTileFromBag();
        switch (tile){
            case BLUE -> {
                return 'a';
            }
            case GREEN -> {
                return 'b';
            }
            case ORANGE -> {
                return 'c';
            }
            case PURPLE -> {
                return 'd';
            }
            case RED -> {
                return 'e';
            }
            default -> {
                return 'Z';
            }
        }
    }

    /**
     * Given a state, refill the factories with tiles.
     * If the factories are not all empty, return the given state.
     *
     *
     * @param gameState the state of the game.
     * @return the updated state after the factories have been filled or
     * the given state if not all factories are empty.
     *
     * author: Juren Xu, HongGic,Oh
     * Juren and HongGic completed this task but we choose to put Juren's code here
     *
     * TASK 6
     */
    public static String[] refillFactories(String[] gameState) {
        // FIXME Task 6
        Game game = new Game(gameState);
        game.refillFactories();
        gameState[0] = game.getCurrentState();
        return gameState;

    }

    /**
     * Given a gameState for a completed game,
     * return bonus points for rows, columns, and sets.
     *
     *
     * @param gameState a completed game state
     * @param player    the player for whom the score is to be returned
     * @return the number of bonus points awarded to this player for rows,
     * columns, and sets
     *
     * author: all team members
     * All team members completed this task but we choose to put Juren's code here
     *
     * TASK 7
     */
    public static int getBonusPoints(String[] gameState, char player) {
        // FIXME Task 7
        Game game = new Game(gameState);

        return game.players[player - 'A'].getBonusPoints();
    }

    /**
     * Given a valid gameState prepare for the next round.
     * 1. Empty the floor area for each player and adjust their score accordingly (see the README).
     * 2. Refill the factories from the bag.
     * * If the bag is empty, refill the bag from the discard pile and then
     * (continue to) refill the factories.
     * * If the bag and discard pile do not contain enough tiles to fill all
     * the factories, fill as many as possible.
     * * If the factories and centre contain tiles other than the first player
     * token, return the current state.
     *
     *
     * @param gameState the game state
     * @return the state for the next round.
     *
     * author: Juren Xu
     *
     * TASK 8
     */
    public static String[] nextRound(String[] gameState) {
        // FIXME TASK 8
        // [player][score][mosaic][storage][floor]
        Game game = new Game(gameState);
        game.nextRound();
//        gameState[0] = game.getCurrentState();
//        StringBuilder playerState = new StringBuilder();
//        for (Player player: game.players) {
//            playerState.append(player.getCurrentState());
//        }
//        gameState[1] = playerState.toString();
        return game.getGameState();

    }

    /**
     * Given an entire game State, determine whether the state is valid.
     * A game state is valid if it satisfies the following conditions.
     * <p>
     * [General]
     * 1. The game state is well-formed.
     * 2. There are no more than 20 of each colour of tile across all player
     * areas, factories, bag and discard
     * 3. Exactly one first player token 'f' must be present across all player
     * boards and the centre.
     * <p>
     * [Mosaic]
     * 1. No two tiles occupy the same location on a single player's mosaic.
     * 2. Each row contains only 1 of each colour of tile.
     * 3. Each column contains only 1 of each colour of tile.
     * [Storage]
     * 1. The maximum number of tiles stored in a row must not exceed (row_number + 1).
     * 2. The colour of tile stored in a row must not be the same as a colour
     * already found in the corresponding row of the mosaic.
     * <p>
     * [Floor]
     * 1. There are no more than 7 tiles on a single player's floor.
     * [Centre]
     * 1. The number of tiles in the centre is no greater than 3 * the number of empty factories.
     * [Factories]
     * 1. At most one factory has less than 4, but greater than 0 tiles.
     * Any factories with factory number greater than this factory must contain 0 tiles.
     *
     * @param gameState array of strings representing the game state.
     *                  state[0] = sharedState
     *                  state[1] = playerStates
     * @return true if the state is valid, false if it is invalid.
     * TASK 9
     */
    public static boolean isStateValid(String[] gameState) {
        // FIXME Task 9
        if (!isSharedStateWellFormed(gameState[0]) || !isPlayerStateWellFormed(gameState[1])){
            return false;
        }
        Game game = new Game(gameState);
        return game.isValidState();
    }

    /**
     * Given a valid gameState and a move, determine whether the move is valid.
     * A Drafting move is a 4-character String.
     * A Drafting move is valid if it satisfies the following conditions:
     * <p>
     * 1. The specified factory/centre contains at least one tile of the specified colour.
     * 2. The storage row the tile is being placed in does not already contain a different colour.
     * 3. The corresponding mosaic row does not already contain a tile of that colour.
     * Note that the tile may be placed on the floor.
     * </p>
     * <p>
     * A Tiling move is a 3-character String.
     * A Tiling move is valid if it satisfies the following conditions:
     * 1. The specified row in the Storage area is full.
     * 2. The specified column does not already contain a tile of the same colour.
     * 3. The specified location in the mosaic is empty.
     * 4. If the specified column is 'F', no valid move exists from the
     * specified row into the mosaic.
     * </p>
     *
     * @param gameState the game state.
     * @param move      A string representing a move.
     * @return true if the move is valid, false if it is invalid.
     * TASK 10
     */
    public static boolean isMoveValid(String[] gameState, String move) {
        // FIXME Task 10
        Game game = new Game(gameState);


        return game.isMoveValid(move);
    }

    /**
     * Given a gameState and a move, apply the move to the gameState.
     * If the move is a Tiling move, you must also update the player's score.
     * If the move is a Tiling move, you must also empty the remaining tiles
     * into the discard.
     * If the move is a Drafting move, you must also move any remaining tiles
     * from the specified factory into the centre.
     * If the move is a Drafting move and you must put tiles onto the floor,
     * any tiles that cannot fit on the floor are placed in the discard with
     * the following exception:
     * If the first player tile would be placed into the discard, it is instead
     * swapped with the last tile in the floor, when the floor is sorted
     * alphabetically.
     *
     * @param gameState the game state.
     * @param move      A string representing a move.
     * @return the updated gameState after the move has been applied.
     * TASK 11
     */
    public static String[] applyMove(String[] gameState, String move) {
        // FIXME Task 11
        Game game = new Game(gameState);
        game.applyMove(move);
        return game.getGameState();
    }

    /**
     * Given a valid game state, return a valid move.
     *
     * @param gameState the game state
     * @return a move for the current game state.
     * TASK 13
     */
    public static String generateAction(String[] gameState) {
        // FIXME Task 13
        Game game = new Game(gameState);
        return game.generateAction();
        // FIXME Task 15 Implement a "smart" generateAction()
//        return game.generateSmartAction();
    }

    //Extra function used
    public static String[] getIndividualPlayer(String playerState){
        return playerState.split("[ABCD]");
    }


    //Factory state consist of 0abcd1aaaa2....3.... . The flag is the number before tiles, this function is to check whether this flag in order and whether they are in range
    public static boolean isFactoryFlagOrdered(String factoryState){
        int numOfFac = factoryState.length()/5;
        int[] factoryFlag = new int[numOfFac];
        for (int i = 0; i < numOfFac; i++) {
            factoryFlag[i] = Character.getNumericValue(factoryState.charAt(i*5));
        }
        int[] sortedFlag = Arrays.copyOf(factoryFlag, numOfFac);
        Arrays.sort(sortedFlag);
        return Arrays.equals(sortedFlag, factoryFlag);
    }


    //Function to check if the string passed in is in alphabetic order
    public static boolean isAlphabeticOrder(String str){
        char[] inputStr = str.toCharArray();
        char[] sortedArray = str.toCharArray();
        Arrays.sort(sortedArray);
        return Arrays.equals(inputStr, sortedArray);

     }

    //Add some method for D2D test(Paul)
    public static String[][] Tilecheck(String[] gameState) {//checking which tiles are used from bag to factory
        int [] tileCount;
        String[][] tilenumber = new String[gameState.length][5];
        for (int k = 0; k < gameState.length; k++) {
            tileCount = new int[]{0, 0, 0, 0, 0};//for counting a,b,c,d,e individually
            int factory = gameState[k].indexOf('F'); //factory's index
            int centre = gameState[k].indexOf('C', 2);//centre's index, the reason why it started from 2 is avoiding playerC
            for (int i = factory + 1; i < centre; i++) {
                if( gameState[k].charAt(i) >= 'a' &&  gameState[k].charAt(i) <='e' )//There are factory numbers, So, only checking for tiles
                    tileCount [ gameState[k].charAt(i) - 'a'] ++;//if gameState[k].charAt(i) is a, then a-a =0, so tileCount[0] will increase one
            }
            for(int i=0; i<tileCount.length; i++)
            {
                tilenumber[k][i] = String.valueOf(tileCount[i]);//Save it to array with string type
            }
        }
        return tilenumber;
    }
    public static String[][] chooseFactory(String[] gameState, int[] numpick) {//choose factory one and delete the factory state in gamestate
        String pickedFactory[][] = new String[gameState.length][2];
        for (int k = 0; k < gameState.length; k++) {
            int factoryIndex = gameState[k].indexOf('F');//checking F's location
            int startIndex = gameState[k].indexOf(String.valueOf(numpick[k]),factoryIndex);//selected factory's first index and avoid player's score part
            for (int j = 0; j < 1; j++) {//get tile information by using numpick and substring //using factoryindex because 0~4 can reflect the score
                pickedFactory[k][j] = gameState[k].substring(startIndex + 1, startIndex + 5);
                pickedFactory[k][j+1]=gameState[k].replace(String.valueOf(numpick[k])+pickedFactory[k][j],"");//replace the factory state to ""
            }
        }
        return pickedFactory;
    }
    //choose one tile in the factory and collect the other tiles in array to move it to centre
    public static String[][] chooseTileFromFactory(String[] pickedFactory,String[] tilePick) {

        String tile[][] = new String[pickedFactory.length][2];//String[pickedFactory.length][0] will save picked tiles and String[pickedFactory.length][1] will save the other tiles
        for (int i = 0; i < pickedFactory.length; i++) {
            tile[i][0]="";
            tile[i][1]="";
            for (int k = 0; k < pickedFactory[i].length(); k++) {
                if (pickedFactory[i].charAt(k) == tilePick[i].charAt(0) ) {
                    tile[i][0] = tile[i][0] + pickedFactory[i].charAt(k);
                }else{
                    tile[i][1]=tile[i][1]+(pickedFactory[i].charAt(k));
                }
            }
        }
        return tile;
    }


    public static char PaulTask5(String[] gameState) {
        Random random = new Random();
        int a = 0;
        char k = 0;
        int num = 0;
        int ind_Bag = gameState[0].indexOf("B") + 1;
        int ind_Discard = gameState[0].indexOf("D");
        if (gameState[0].substring(ind_Bag, ind_Discard).equals("0000000000")) {
            if (!gameState[0].substring(ind_Discard + 1).equals("0000000000")) {
                return 'Z';
            }
        }

        a = random.nextInt(5);
        String tmp = gameState[0].substring(ind_Bag + a * 2, ind_Bag + a * 2 + 2);
        num = Integer.parseInt(tmp);

        for (int i = num; i > 0; i--) {
            String test = String.valueOf(i);
            if (i < 10) {
                test = "0" + test;
            }
            gameState[0] = gameState[0].substring(0, ind_Bag + a * 2) + test + gameState[0].substring(ind_Bag + a * 2 + 2);
        }
        switch (a) {
            case 0:
                k= 'a';
                break;
            case 1:
                k= 'b';
                break;
            case 2:
                k= 'c';
                break;
            case 3:
                k= 'd';
                break;
            case 4:
                k= 'e';
                break;
        }
        return  k;
    }
}

