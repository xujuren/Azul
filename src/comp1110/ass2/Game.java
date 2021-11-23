package comp1110.ass2;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.*;

/**
 * This class is used to store the information of a game
 *
 * author: Juren Xu, except generateSmartDraftingMove(), chooseBestMove(), generateSmartTilingMove(),
 *                          calculateMosaicFitness(), calculateBoardFitness, generateSmartAction()
 *
 */
public class Game {
    //    [turn][factories], [centre], [bag] and [discard]
    private static final Random random = new Random();
    public static Player[] players;
    public int curPlayer;
    public static Factory[] factories;
    public Centre centre;
    public Bag bag;
    public Discard discard;
    public boolean isValid;
    public boolean isGameEnd;


    /**
     * A default constructor of Game class
     * author: Juren Xu
     */
    public Game() {

        curPlayer = 0;
        factories = new Factory[5];
        for (int i = 0; i < factories.length; i++) {
            factories[i] = new Factory(i);
        }

        centre = new Centre();
        bag = new Bag();
        discard = new Discard();


    }

    public Game(int playerNum) {

        curPlayer = 0;
        centre = new Centre();
        discard = new Discard();

        ArrayList<Tile> initialBag = new ArrayList<Tile>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 20; j++)
                initialBag.add(Tile.fromInt(i));
        }
        bag = new Bag(initialBag);

        factories = new Factory[5];
        for (int i = 0; i < factories.length; i++) {
            factories[i] = new Factory(i);

        }
        refillFactories();


        players = new Player[playerNum];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player();
        }

        centre.add(Tile.GRAY);
        //A2Me04b11S2c13a34a1FbeeeeB2Mc02d33S1b12e1F
//        players[0]= new Player("2Mb01c02d03e04a11a22a33a44SF",0,this);
//        players[1]= new Player("2Mc02d33SF",1,this);
    }

    /**
     * A constructor of game class that converting game state from string to Object Orient
     *
     * @param gameState, a String array combined with sharedState and playerState
     *
     * author: Juren Xu
     */
    public Game(String[] gameState) {
        // Set current player
        curPlayer = gameState[0].charAt(0) - 'A';
        // Remove first character in gameState, this will make following split more convenient
        String[] temp = gameState[0].substring(1).split("[FCBD]");

        // Split playerState in to a String array that each string in this array represent a single player
        String[] playersCollection = gameState[1].split("[BCD]");
        playersCollection[0] = playersCollection[0].substring(1);
        players = new Player[playersCollection.length];

        // Convert every player state from string to Object Orient, see Class Player
        for (int i = 0; i < playersCollection.length; i++) {
            players[i] = new Player(playersCollection[i], i, this);
        }
        //Convert Factory
        factories = new Factory[5];
        for (int i = 0; i < 5; i++) {
            factories[i] = new Factory(i);
        }
        ArrayList<Integer> flags = new ArrayList<Integer>();
        for (int i = 0; i < temp[1].length(); i++) {
            if (i % 5 == 0) {
                flags.add(Integer.parseInt(Character.toString(temp[1].charAt(i))));
            }
        }
        String[] singleFac = temp[1].split("[0123456789]");
        for (int i = 0; i < flags.size(); i++) {
            for (int j = 0; j < singleFac[i + 1].length(); j++) {
//                factories[flags.get(i)].add(colorMap.get(singleFac[i + 1].charAt(j)));
                factories[flags.get(i)].add(Tile.fromInt(singleFac[i + 1].charAt(j) - 'a'));
            }
        }
        //Convert Centre
        centre = new Centre();
        for (int i = 0; i < temp[2].length(); i++) {
//            centre.add(colorMap.get(temp[2].charAt(i)));
            centre.add(Tile.fromInt(temp[2].charAt(i) - 97));
        }
        //Convert Bag
        bag = new Bag();
        bag.addTiles(Tile.BLUE, Integer.parseInt(temp[3].substring(0, 2)));
        bag.addTiles(Tile.GREEN, Integer.parseInt(temp[3].substring(2, 4)));
        bag.addTiles(Tile.ORANGE, Integer.parseInt(temp[3].substring(4, 6)));
        bag.addTiles(Tile.PURPLE, Integer.parseInt(temp[3].substring(6, 8)));
        bag.addTiles(Tile.RED, Integer.parseInt(temp[3].substring(8, 10)));

        //Convert Discard
        discard = new Discard();
        discard.addTiles(Tile.BLUE, Integer.parseInt(temp[4].substring(0, 2)));
        discard.addTiles(Tile.GREEN, Integer.parseInt(temp[4].substring(2, 4)));
        discard.addTiles(Tile.ORANGE, Integer.parseInt(temp[4].substring(4, 6)));
        discard.addTiles(Tile.PURPLE, Integer.parseInt(temp[4].substring(6, 8)));
        discard.addTiles(Tile.RED, Integer.parseInt(temp[4].substring(8, 10)));

    }

    /**
     * Function random draw a random tile from bag
     *
     * @return Color, return the color of drawn tile
     * author: Juren Xu
     */
    public Tile drawTileFromBag() {
        //Terminate function when bag and discard are all empty
        if (bag.isEmpty() && discard.isEmpty()) {
            return null;
        }
        // If bag is empty but discard is not, then refill bag with discard first
        if (bag.isEmpty()) {
//            bag = new Bag(discard.removeAll());
            bag.addTiles(discard.removeAll());

        }
        int randomIndex = random.nextInt(bag.size());
        // return the color of selected tile and remove this tile from bag
        return bag.remove(randomIndex);
    }


    /**
     * Function that refill all factories
     * author: Juren Xu
     */
    public void refillFactories() {
        // If both centre and all factories are empty we refill the factories, otherwise wo don't refill
        if (centre.isEmpty() && isAllFactoriesEmpty()) {
            for (Factory factory : factories) {
                factory.add(drawTileFromBag());
                factory.add(drawTileFromBag());
                factory.add(drawTileFromBag());
                factory.add(drawTileFromBag());
            }
        }
    }

    /**
     * A function that check should we go next round, and apply action if game is ready for next round
     * author: Juren Xu
     */
    public void nextRound() {
        // Checking if the game is ready for next round. (ready if all factories, centre are empty and all players are ready for next round)
        if (isAllFactoriesEmpty() && centre.isEmpty() && isAllPlayerReadyNextRound()) {
            // If not end of game, clean floor and update score, bonus for each players and refill all factories

            if (!EndGame()) {
                for (int i = 0; i < players.length; i++) {
                    players[i].score.penalty(players[i].floor.size());
                    if (players[i].floor.contains(Tile.GRAY)) {
                        curPlayer = i;
                        centre.add(players[i].floor.removeFirstToken());
                    }
                    discard.addTiles(players[i].floor.clearFloor());
                    refillFactories();
                }
            }

        }else if(bag.isEmpty()){
            drawTileFromBag();
        }

    }


    /**
     * Function that will check if it is end of game, if yes then update score for each players and clean their floor and then return true,
     * if not return false directly
     *
     * @return boolean, true if end of game, else false
     * author: Juren Xu
     */
    public boolean EndGame(){
        //boolean isEndGame = false;
        isGameEnd = false;
        for (Player player:players) {
            // if any player have completed at least 1 row of mosaic, change isGameEnd to true
            if (player.mosaic.completedRow() > 0) {
//                return true;
//                isEndGame = true;
                isGameEnd = true;
                break;
            }
        }
        // If we reached end of game, update score of each player and clear their floor, place first playerToken to centre and return true
        if (isGameEnd){
            for (int i = 0; i < players.length; i++) {
                applyPenalty(i);
                updateBonus(i);
                discard.addTiles(players[i].floor.clearFloor());
            }
//            updatePlayersScore();
            centre.add(Tile.GRAY);
            return true;
        }
        // If not end of game, return false directly
        return false;
    }

    /**
     * Apply the penalty according the number of tiles in floor
     * @param playerId, which player we want to apply penalty
     *
     * author: Juren Xu
     */
    public void applyPenalty(int playerId){
        players[playerId].score.penalty(players[playerId].floor.size());
    }

    /**
     * Apply the the bonus to the specified player
     * @param playerId, which player we want to apply bonus
     *
     * author: Juren Xu
     */
    public void updateBonus(int playerId){

        players[playerId].score.updateScore(2 *  players[playerId].mosaic.completedRow() +
                7 *  players[playerId].mosaic.completedCol() +
                10 *  players[playerId].mosaic.completedColor());

    }

    /**
     * Function that check if all player ready for next round (No storage is full)
     *
     * @return boolean, true if all player are ready for next round, otherwise return false
     * author: Juren Xu
     */
    public boolean isAllPlayerReadyNextRound(){
        for (Player player : players) {
            if (player.hasCompletedStorage())
                return false;
        }
        return true;
    }

    /**
     * Function that check if all factories are full
     *
     * @return boolean, true if all factories are full, and false otherwise
     * author: Juren Xu
     */
    public boolean isAllFactoriesEmpty(){
        for (Factory factory: factories) {
            if (!factory.isEmpty()){
                return false;
            }
        }
        return true;
    }

    /**
     * Check if all player state are valid
     * @return true if all players state are valid, false if not
     * author: Juren Xu
     */
    public boolean isValidState(){
        if (!isTileCountCorrect() || !isCentreValid()) {
            return false;
        }
        for (Player player:players) {
            if (!player.isValid()){
                return false;
            }
        }
        return true;
    }

    /**
     * Check is the move valid
     * @param move a string representation of a move
     * @return true if move is valid, otherwise return false
     * author: Juren Xu
     */
    public boolean isMoveValid(String move){
        // which player apply this move
        int id = move.charAt(0) - 'A';
        // where we took tile
        char from = move.charAt(1);
        // tile we are moved
        Tile movedTile;
        // where the tile moved to
        char to;
        if (id != curPlayer){
            System.out.println("other user turn /curPlayer:" + curPlayer);
            return false;
        }
        // move sting has length of 3 is Tiling Move
        switch(move.length()){
            case 3:
                to = move.charAt(2);
                movedTile = players[id].storage[from - '0'].currColor();
                // we cannot move a tile from storage if it is not full
                if (!players[id].storage[from - '0'].isFull()){
                    return false;
                }
                // if not go to floor than means destination is mosaic
                if (to != 'F'){
                    // if there is a same tile in the corresponding column than it is a invalid move
                    if (players[id].mosaic.isColorInCol(movedTile, to - '0')){
                        return false;
                    }
                    // if the mosaic it moved to already have a mosaic then this is a invalid move
                    return players[id].mosaic.mosaic[from - '0'][to - '0'] == null;
                }else {
                    // If there is a available move to mosaic than we cannot move to floor
                    boolean existsValidMove = false;
                    for (int i = 0; i < 5; i++) {
                        if (!players[id].mosaic.isColorInCol(movedTile, i)
                                && players[id].mosaic.mosaic[from - '0'][i] == null){
                            existsValidMove = true;
                        }
                    }
                    return !existsValidMove;
                }
            // move sting has length of 3 is Drafting Move
            case 4:
                movedTile = Tile.fromInt(move.charAt(2) - 'a');
                to = move.charAt(3);
                //Move from centre
                if(from == 'C'){
                    //If centre does not have this tile than it's a invalid move
                    if (!centre.contains(movedTile)){
                        return false;
                    }
                }else { // move from factories
                    // If the selected factory does not have the tile we want, then it is an invalid move
                    if(!factories[from - '0'].contains(movedTile)){
                        return false;
                    }
                }
                // Move to storage
                if ( to != 'F'){
                    //If we cannot add to this storage then it is a invalid move, canAdd() refer to storage class
                    if (players[id].storage[to - '0'].canAdd(movedTile)){
//                        players[id].storage[to - '0'].rowColor != movedTile && players[id].storage[to - '0'].rowColor != null && players[id].storage[to - '0'].isFull()
                        return !players[id].mosaic.isColorInRow(movedTile, to - '0');
                    }
                    return false;
                }
                return true;
            default: return false;
        }
    }

    /**
     * apply a move to game
     * @param move a string representation of move
     * author: Juren Xu
     */
    public void applyMove(String move){
        int id = move.charAt(0) - 'A';
        char from = move.charAt(1);
        char to;
        Tile movedTile;
        // The list of tiles we picked from centre or factories
        ArrayList<Tile> picked =new ArrayList<Tile>();
        switch (move.length()) {
            case 3 -> {
                to = move.charAt(2);
                movedTile = players[id].storage[from - '0'].currColor();
                if (to == 'F') {
                    discard.addTiles(players[id].floor.add(movedTile,
                            players[id].storage[from - '0'].getTileCount()));
                } else {
                    players[id].mosaic.fillMosaic(movedTile,
                            players[id].storage[from - '0'].getRow(), to - '0');
                    discard.addTiles(movedTile,
                            players[id].storage[from - '0'].getTileCount() - 1);
                    players[id].score.updateScore( players[id].countScore(players[id].storage[from - '0'].getRow(), to - '0'));
                }
                players[id].storage[from - '0'].removeAll();
                // If there is no completed storage than we need to swap player turn
                if (!players[id].hasCompletedStorage()){
                    swapPlayerTurn();
                }

            }
            case 4 -> {
                movedTile = Tile.fromInt(move.charAt(2) - 'a');
                to = move.charAt(3);
                ArrayList<Tile> tileList = new ArrayList<Tile>();
                int count;
                if (from == 'C') {
                    // tileList is the a tile list that are selected from centre
                    tileList = centre.removeTiles(movedTile);
                    if (tileList.contains(Tile.GRAY)){
                        count = tileList.size() - 1;
                    }else{
                        count = tileList.size();
                    }
                } else {
                    // adding other tiles to centre
                    count = factories[from - '0'].removeSelected(movedTile);
                    centre.addTiles(factories[from - '0'].removeAll());
                }

                if (to == 'F') {
                    if(from <= '4') {
                        // Adding extra tiles to discard when floor is full
                        discard.addTiles(players[id].floor.add(movedTile, count));
                    }
                    else{
                        // Adding extra tiles to discard when floor is full
                        discard.addTiles(players[id].floor.add(tileList));
                    }
                } else {
                    // adding tile to floor when the current storage is full (we call floor.add() no matter storage is
                    // full or not(if it is not full, the floor.add() will receive a empty arraylist, which means it will
                    // stay unchanged)
                    players[id].floor.add(players[id].storage[to - '0'].add(movedTile, count));
                    if (tileList.contains(Tile.GRAY)){
                        players[id].floor.add(Tile.GRAY);
                    }
                }
                if (!isAllFactoriesEmpty() || !centre.isEmpty()){
                    swapPlayerTurn();
                }
            }
        }
//        nextRound();
    }

    /**
     * Generating an action (A basic AI)
     * @return a string representation of generated move
     * author: Juren Xu
     */
    public String generateAction(){
        // If factories or centre are not full then we should generate a Drafting move
        if(!isAllFactoriesEmpty() || !centre.isEmpty()){
            return generateDraftingMove();
        }
        // If factories and centre are both full then we should generate a Tiling move
        return generateTilingMove();
    }

    /**
     * Generate a Drafting Move
     * @return a string representation of a valid Drafting move
     * author: Juren Xu
     */
    public String generateDraftingMove(){
        Random rand = new Random();
        // If both factories and centre are not empty, then randomly draw from centre or factories
        if (!isAllFactoriesEmpty() && !centre.isEmpty()){
            if (rand.nextInt(2) == 0) {
                return pickFromFactories();
            }
            return pickFromCentre();
        }

        // If factories are empty then we draw a tile from centre
        if (isAllFactoriesEmpty()) {
            return pickFromCentre();
        }
        // If centre is empty then we draw a tile from factories
        return pickFromFactories();
    }

    /**
     * Random draw a tile from factories
     * @return a string representation of a valid Drafting move
     * author: Juren Xu
     */
    public String pickFromFactories(){
        Random rand = new Random();
        ArrayList<Integer> availableFactories = new ArrayList<Integer>();
        Tile tilePicked;
        for (int i = 0; i < factories.length; i++) {
            if(!factories[i].isEmpty()){
                availableFactories.add(i);
            }
        }
        int factoryPicked = availableFactories.get(rand.nextInt(availableFactories.size()));

        tilePicked = factories[factoryPicked].get(rand.nextInt(4));
        return String.valueOf((char) (curPlayer + 'A')) +
                factoryPicked +
                (char)(tilePicked.ordinal() + 'a')+
                players[curPlayer].toStorageOrFloor(tilePicked);
    }

    /**
     * Random draw a tile from centre
     * @return a string representation of a valid Drafting move
     * author: Juren Xu
     */
    public String pickFromCentre(){
        Random rand = new Random();
        Tile tilePicked;
        if (centre.contains(Tile.GRAY)){
            tilePicked = centre.get(rand.nextInt(centre.size() - 1));
        }else{
            tilePicked = centre.get(rand.nextInt(centre.size()));
        }
        return (char) (curPlayer + 'A') +
                "C" +
                (char)(tilePicked.ordinal() + 'a')+
                players[curPlayer].toStorageOrFloor(tilePicked);
    }


    /**
     * Generate a Tiling Move
     * @return a string representation of a valid Tiling move
     * author: Juren Xu
     */
    public String generateTilingMove(){
        ArrayList<Integer> availableMosColumn = new ArrayList<Integer>();
        Random rand = new Random();
        int rowPicked = 0;
        //Check each full storage in order and fill the tile in random column of corresponding row in mosaic
        for (int i = 0; i < players[curPlayer].storage.length; i++) {
            if (players[curPlayer].storage[i].isFull()){
                rowPicked = i;
                break;
            }
        }
        for (int i = 0; i < 5; i++) {
            if (!players[curPlayer].mosaic.isColorInCol(players[curPlayer].storage[rowPicked].currColor(),i) &&
                    players[curPlayer].mosaic.mosaic[rowPicked][i] == null){
                availableMosColumn.add(i);
            }
        }
        StringBuilder move = new StringBuilder();
        move.append((char)(curPlayer + 'A'));
        move.append(rowPicked);
        // If available mosaic column is 0 means no available slot we could found in mosaic, then move to floor
        if (availableMosColumn.size() == 0){
            move.append("F");
        }else{
            move.append(availableMosColumn.get(rand.nextInt(availableMosColumn.size())));
        }
        return move.toString();
    }


    /**
     * Swap player
     * author: Juren Xu
     */
    public void swapPlayerTurn(){
        //curPlayer = curPlayer == 1 ? 0 : 1;
        curPlayer = (curPlayer+1)%players.length;
    }

    /**
     * Check is the number of tiles is correct (20 for each tiles and 1 for first player token)
     *  The number of tiles in the centre is no greater than 3 * the number of empty factories.
     * @return true if it is correct, else return false
     * author: Juren Xu
     */
    public boolean isTileCountCorrect(){
        // index from 0 to 5 represent the number of tiles from BLUE TO FIRST PLAYER TOKEN
        int[] tileCount = {0, 0, 0, 0, 0, 0};
        //Count tiles in all factories
        for (Factory factory : factories) {
            for (Tile tile : factory) {
                tileCount[tile.ordinal()]++;
            }
        }
        // Count tiles in centre, discard and bag
        for (int i = 0; i < 6; i++) {
            tileCount[i] = tileCount[i] + bag.countTiles(Tile.fromInt(i)) + discard.countTiles(Tile.fromInt(i))
                    + centre.countTiles(Tile.fromInt(i));

        }

        // Count tiles in each players' storage, mosaic and floor
        for (Player player : players) {
            for (int i = 0; i < 5; i++) {
                tileCount[i] += player.mosaic.countTiles(Tile.fromInt(i));
            }

            for (Storage storage:player.storage) {
                if (storage.currColor() != null){
                    tileCount[storage.currColor().ordinal()] += storage.getTileCount();
                }
            }
            for (Tile tile:player.floor) {
                if (tile == Tile.GRAY){
                    tileCount[5]++;
                }else {
                    tileCount[tile.ordinal()]++;
                }
            }
        }

        // Check number of first player token
        if(tileCount[5] != 1){
            return false;
        }

        //Check number of all 5 different tiles
        for (int i = 0; i < 5; i++) {
            if (tileCount[i] != 20){
                return false;
            }
        }
        return true;
    }

    /**
     * Check is the number of tiles in centre is correct
     *  The number of tiles in the centre is no greater than 3 * the number of empty factories.
     * @return true if it is correct, else return false
     * author: Juren Xu
     */
    public boolean isCentreValid(){
        int emptyFactories = 0;
        for (Factory factory:
             factories) {
            if (factory.isEmpty()){
                emptyFactories++;
            }
        }
        return centre.getSize() <= (3 * emptyFactories);
    }

    /**
     * Function that turn a Color to char representation
     *
     * @param tile, the color of a tile
     * @return char, a character from 'a' to 'f'
     * author: Juren Xu
     */
    public char colorToChar(Tile tile){
        return (char)(tile.ordinal() + 'a');
    }

    /**
     * Function that convert board state to string representation
     *
     * @return String, a string representation of current board state
     * author: Juren Xu
     */
    public String getCurrentState(){
        StringBuilder state = new StringBuilder();
        state.append((char)(curPlayer + 65));
        state.append("F");
        for (Factory factory : factories) {
            state.append(factory.toString());

        }
        state.append(centre.toString());
        state.append(bag.toString());
        state.append(discard.toString());

        return state.toString();
    }

    /**
     * Function that convert game state to string representation
     *
     * @return String, a string representation of current game state
     * author: Juren Xu
     */
    public String[] getGameState(){
        String[] gameState = new  String[]{"",""};
        gameState[0] = getCurrentState();
        for (Player player:
             players) {
            gameState[1] += player.getCurrentState();
        }
        return gameState;
    }


    //Xindi Part

    /**
     * author: Xindi Liu
     * @return
     */
    public String generateSmartDraftingMove() {
        ArrayList<Integer> availableFactories = new ArrayList<Integer>();
        int fitness = Integer.MIN_VALUE;
        StringBuilder bestMove = new StringBuilder();

        for (int i = 0; i < factories.length; i++) {
            if (!factories[i].isEmpty()) {
                availableFactories.add(i);
            }
        }
        for (int factoryPicked : availableFactories) {
            Map<Tile, Integer> tileCount = new HashMap<>();
            for (Tile tile : factories[factoryPicked]) {
                tileCount.put(tile, tileCount.getOrDefault(tile, 0) + 1);
            }

            for (Map.Entry<Tile, Integer> tileEntry : tileCount.entrySet()) {
                Tile tilePicked = tileEntry.getKey();
                int count = tileEntry.getValue();
                Map<String,Object> updateRes = chooseBestMove(tilePicked,count,fitness,bestMove,String.valueOf(factoryPicked));
                fitness = (int) updateRes.get("fitness");
                bestMove = (StringBuilder) updateRes.get("move");

            }
        }

        Map<Tile, Integer> tileCount = new HashMap<>();
        for (Tile tile : centre) {
            tileCount.put(tile, tileCount.getOrDefault(tile, 0) + 1);
        }
        tileCount.remove(Tile.GRAY);
        for (Map.Entry<Tile, Integer> tileEntry : tileCount.entrySet()) {
            Tile tilePicked = tileEntry.getKey();
            int count = tileEntry.getValue();
            Map<String,Object> updateRes = chooseBestMove(tilePicked,count,fitness,bestMove,String.valueOf("C"));
            fitness = (int) updateRes.get("fitness");
            bestMove = (StringBuilder) updateRes.get("move");
        }
        return bestMove.toString();
    }


    /**
     * author: Xindi Liu
     * @return
     */
    public String generateSmartAction() {
        if (!isAllFactoriesEmpty() || !centre.isEmpty()) {
            return generateSmartDraftingMove();
        }
        return generateSmartTilingMove();
    }

    public Map<String, Object> chooseBestMove(Tile tilePicked, int count, int fitness, StringBuilder bestMove, String from) {
        int curFitness = -1;
        int storage = -1;
        ArrayList<Integer> availableStorage = new ArrayList<Integer>();
        for (int i = 0; i < players[curPlayer].storage.length; i++) {
            if (players[curPlayer].storage[i].canAdd(tilePicked) && !players[curPlayer].mosaic.isColorInRow(tilePicked, i)) {
                availableStorage.add(i);
            }
        }
        if (availableStorage.size() == 0) {
            curFitness = -1 * count;
        } else {
            for (int storageIndex : availableStorage) {
                int tempFitness = calculateBoardFitness(tilePicked, storageIndex, count);
                if (tempFitness > curFitness) {
                    curFitness = tempFitness;
                    storage = storageIndex;
                }
            }
        }

        if (curFitness > fitness) {
            fitness = curFitness;
            bestMove = new StringBuilder();
            bestMove.append((char) (curPlayer + 'A'));
            bestMove.append(from);
            bestMove.append((char) (tilePicked.ordinal() + 'a'));
            if (storage == -1)
                bestMove.append("F");
            else
                bestMove.append(storage);
        }
        Map<String,Object> res = new HashMap<>();
        res.put("fitness", fitness);
        res.put("move",bestMove);
        return res;
    }

    /**
     * author: Xindi Liu
     * @return
     */
    public String generateSmartTilingMove() {
        ArrayList<Integer> fullRows = new ArrayList<>();
        int maxFitness = Integer.MIN_VALUE;
        StringBuilder bestMove = new StringBuilder();
        for (int i = 0; i < players[curPlayer].storage.length; i++) {
            if (players[curPlayer].storage[i].isFull()) {
                fullRows.add(i);
            }
        }
        for (int row : fullRows) {
            for (int col = 0; col < 5; col++) {
                if (!players[curPlayer].mosaic.isColorInCol(players[curPlayer].storage[row].currColor(), col) &&
                        players[curPlayer].mosaic.mosaic[row][col] == null) {
                    int fitness = calculateMosaicFitness(row, col);
                    if (fitness > maxFitness) {
                        maxFitness = fitness;
                        bestMove = new StringBuilder();
                        bestMove.append((char) (curPlayer + 'A'));
                        bestMove.append(row).append(col);
                    }
                }
            }
        }

        if (bestMove.length() == 0) {
            bestMove.append((char) (curPlayer + 'A'));
            bestMove.append(fullRows.get(0)).append("F");
        }
        return bestMove.toString();
    }

    /**
     * author: Xindi Liu
     * @return
     */
    public int calculateMosaicFitness(int row, int col) {
        int score = players[curPlayer].countScore(row, col);
        int goFloorCount = -1 * row;
        int fitness = 2 * score + goFloorCount;
        return fitness;
    }

    /**
     * author: Xindi Liu
     * @return
     */
    public int calculateBoardFitness(Tile tilePicked, int storageIndex, int count) {
        int currentCount = players[curPlayer].storage[storageIndex].getTileCount();
        int moveToFloorCount = count + currentCount - storageIndex - 1;
        int countScore = 15 - 2*storageIndex;
        int fitness = countScore - moveToFloorCount;
        return fitness;
    }
}
