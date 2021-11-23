package comp1110.ass2;

import java.util.ArrayList;

/**
 * The Class for object Storage
 *
 * Author: Juren Xu
 */
public class Storage {
    int storageId;
    Tile rowColor;
    int tileCount;
    int MAX_SIZE;


    /**
     * Constructor with the id of storage
     * @param rowId, the id of storage
     *               row 0 has 1 slots
     *               row 1 has 2 slots
     *               row 2 has 3 slots
     *               row 3 has 4 slots
     *               row 4 has 5 slots
     */
    public Storage(int rowId) {
        storageId = rowId;
        MAX_SIZE = rowId + 1;
        rowColor = null;
        tileCount = 0;
    }


    /**
     * Constructor with the id of storage, color of tile in it and number of tile
     * @param rowId, the id of storage
     * @param rowColor, the color of tile in current storage
     * @param number, how many tiles in it
     */
    public Storage(int rowId, Tile rowColor, int number) {
        storageId = rowId;
        MAX_SIZE = rowId + 1;
        if (number == 0){
            this.rowColor = null;
        }else{
            this.rowColor = rowColor;
            tileCount = number;
        }

    }

    /**
     * Check if current storage is full
     * @return boolean, return true if full, false otherwise
     */
    public boolean isFull(){
        return tileCount == storageId + 1;
    }

    /**
     * Check if current storage is empty
     * @return boolean, return true if empty, false otherwise
     */
    public boolean isEmpty(){
        return rowColor == null;
//        return count == 0;
    }


    /**
     * Check if can we add a tile into current storage
     * This function is not complete yet
     * @param tile, the tile we want add
     * @return boolean, true if we can add, false otherwise
     */
    public boolean canAdd(Tile tile){
        return !isFull() && (this.rowColor == tile || this.rowColor == null);
    }

    /**
     * Add a list of tiles into this storage, if the number of list is greater than the storage size, then move
     * extra tiles to floor
     * @param tile, the tile we want to add to this storage
     * @param count, how many we want to add
     * @return extra tiles, which need to move to floor
     */
    public ArrayList<Tile> add(Tile tile, int count){
        ArrayList<Tile> tileList = new ArrayList<Tile>();
        rowColor = tile;
        int total = tileCount + count;
        if(total > MAX_SIZE){
            for (int i = 0; i < total - MAX_SIZE; i++) {
                tileList.add(tile);
            }
            tileCount = MAX_SIZE;
        }else {
            tileCount += count;
        }
        return tileList;
    }


    /**
     * Set current storage to empty
     */
    public void removeAll(){
        rowColor = null;
        tileCount = 0;
    }

    /**
     * Get current color of tile in the storage
     * @return the color of tile in this storage
     */
    public Tile currColor(){
        return rowColor;
    }

    /**
     * Get number of tiles in the storage
     * @return the number of tiles in this storage
     */
    public int getTileCount(){
        if (isEmpty()){
            return 0;
        }
        return tileCount;
    }

    /**
     * Return the storageId of this storage (storageId indicate which row of this storage is placed in storage part)
     * @return storageId
     */
    public int getRow(){
        return storageId;
    }

    /**
     * Check if this storage is valid
     * @return valid if the total count of tile is not greater than its MAX_SIZE
     */
    boolean isValid(){
        return tileCount <= MAX_SIZE;
    }

    /**
     * Convert current storage to string representation
     * @return String, a string representation of current storage state
     */
    @Override
    public String toString() {
        if (rowColor == null) {
            return "";
        }
        return String.valueOf(storageId) + (char) (rowColor.ordinal() + 97) + tileCount;
    }


}
