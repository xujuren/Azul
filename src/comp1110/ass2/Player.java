package comp1110.ass2;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class is used to store a particular information of a player
 * Contains: Game, the reference of ongoing game
 *           PlayerId, the id of current player
 *           Score, the score of current player
 *           Mosaic, the mosaic field of the current player
 *           Storage[], all storage of the current player
 *           Floor, the floor field of the current player
 * Author: Juren Xu
 */
public class Player {
    //[player][score][mosaic][storage][floor]

    private static final int MAX_STORAGE_ROW = 5;
    Game game;
    public int playerId;
    public Score score;
    public Mosaic mosaic;
    public Storage[] storage;
    public Floor floor;

    public Player(){

        //game = new Game();
        playerId = 0;
        score = new Score();

        storage = new Storage[MAX_STORAGE_ROW];
        for (int i = 0; i < MAX_STORAGE_ROW; i++) {
            storage[i] = new Storage(i);
        }

        mosaic = new Mosaic();
        floor = new Floor();
    }

    /**
     * Constructor with string playerstate, int playerId and the reference of the ongoing game
     *
     * @param playerState, a string of player state
     * @param playerId, indicate which player is this
     * @param game, the reference of game(means board here)
     */
    public Player(String playerState, int playerId, Game game){


        this.playerId = playerId;
        this.game = game;
        //Split player state into each score state, mosaic state, storage state and floor state respectively
        String scoreState = playerState.substring(0, playerState.indexOf("M"));
        String mosaicState = playerState.substring(playerState.indexOf("M"), playerState.indexOf("S"));
        String storageState = playerState.substring(playerState.indexOf("S"), playerState.indexOf("F"));
        String floorState = playerState.substring(playerState.indexOf("F"));

        //Convert score
        score = new Score(Integer.parseInt(scoreState));

        //Convert mosaic
        mosaic = new Mosaic();
        if(!mosaicState.equals("M")){
            for (int i = 1; i < mosaicState.length(); i += 3) {
                Tile tile = Tile.fromInt(mosaicState.charAt(i) - 'a');
                int row = Character.getNumericValue(mosaicState.charAt(i + 1));
                int column = Character.getNumericValue(mosaicState.charAt(i + 2));
                mosaic.fillMosaic(tile, row, column);
            }
        }

        //Convert storage
        storage = new Storage[MAX_STORAGE_ROW];
        for (int i = 0; i < MAX_STORAGE_ROW; i++) {
            storage[i] = new Storage(i);
        }

        if (!storageState.equals("S")){
            for (int i = 1; i < storageState.length(); i += 3) {
                int row = Character.getNumericValue(storageState.charAt(i));
                Tile tile = Tile.fromInt(storageState.charAt(i + 1) - 'a');
                int number = Character.getNumericValue(storageState.charAt(i + 2));
                storage[row] = new Storage(row, tile, number);
            }
        }


        //Convert floor
        floor = new Floor();
        if (!floorState.equals("F")) {
            for (int i = 1; i <floorState.length() ; i++) {
                floor.add(Tile.fromInt(floorState.charAt(i) - 'a'));
            }
        }
    }


    /**
     * Calculate bonus marks for current player
     * @return integer, the bonus mark of current player in this round
     */
    public int getBonusPoints(){
        return 2 * mosaic.completedRow() + 7 * mosaic.completedCol() + 10 * mosaic.completedColor();
    }

    /**
     * Check if there is a completed storage of current player
     * @return boolean, true if there is a completed storage, false if not
     */
    public boolean hasCompletedStorage(){
        for (Storage value : storage) {
            if (value.isFull()) {
                return true;
            }
        }
        return false;
    }

    /**
     * The function return if mosaic, storage and floor are valid
     * @return boolean, true if valid, false if not
     */
    public boolean isValid(){
        return isMosaicValid() && isStorageValid() && isFloorValid();
    }

    /**
     * The function check if all storages row valid
     * @return boolean, true if valid, false if not
     */
    public boolean isStorageValid(){
        for (int i = 0; i < storage.length; i++) {
//
            if (!storage[i].isValid() || mosaic.isColorInRow(storage[i].rowColor, i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if mosaic a valid mosaic
     * @return boolean, true if it is valid and false if not
     */
    public boolean isMosaicValid(){
        return mosaic.isValid();
    }

    /**
     * Check if floor a valid floor
     * @return boolean, true if it is valid and false if not
     */
    public boolean isFloorValid(){
        return floor.isValid();
    }

    /**
     * This function will count the score once player move a tile in to mosaic.
     * The scoring part of game
     * @param row, the row that tile is moved to
     * @param column, the column that tile is moved to
     * @return int, return the score that will be added when a tile is moved to mosaic
     */
    public int countScore(int row, int column){
        //Count the
        int right = mosaic.checkRight(row, column);
        int left = mosaic.checkLeft(row, column);
        int up = mosaic.checkUp(row, column);
        int down = mosaic.checkDown(row, column);
        if (right == 1 && left == 1 && up ==1 && down == 1){
            return 1;
        }
        //checkLeft and checkRight both count the moved tile itself, so it cause count 1 more time, so need to deduct it
        int rowCount = left + right - 1;
        //Same reason as rowCount
        int colCount = up + down - 1;

        //If any of rowCount of colCount is 1, means no continuously in that row or column so we return the one who is
        // larger than 1
        if (rowCount == 1 || colCount == 1){
            return rowCount != 1 ? rowCount : colCount;
        }

        //If none of above situations happened, return the sum of them
        return rowCount + colCount;
    }


    /**
     * The function will send a selected tile to a random available storage or floor
     * the tile will be send to floor only if there is not available storage,
     * otherwise it will random choose an available storage and send selected to it
     * @param tile, the selected tile
     * @return "F" or number "0" to "4"
     */
    public String toStorageOrFloor(Tile tile){
        Random rand = new Random();
        // An arraylist used to store the id of available storage
        ArrayList<Integer> availableStorage = new ArrayList<Integer>();
        for (int i = 0; i < storage.length; i++) {
            if (storage[i].canAdd(tile) && !mosaic.isColorInRow(tile, i)){
                availableStorage.add(i);
            }
        }
        // availableStorage is 0 means no available storage, so send to floor
        if(availableStorage.size() == 0 ){
            return "F";
        }
        return availableStorage.get(rand.nextInt(availableStorage.size())).toString();
    }

    /**
     * Convert current player state to string representation
     * @return String, a string representation of current player
     */
    public String getCurrentState(){
        StringBuilder state = new StringBuilder();
        state.append((char)(playerId + 65));
        state.append(score.getScore());
        state.append(mosaic.toString());
        state.append("S");
        for (int i = 0; i < MAX_STORAGE_ROW; i++) {
            state.append(storage[i].toString());
        }
        state.append(floor.toString());

        return state.toString();
    }

    public int countStorageTiles(Tile tile) {
        int count = 0;
        for (Storage value : storage) {
            if (tile == value.rowColor) {
                count += value.tileCount;
            }
        }
        return count;

    }

}
