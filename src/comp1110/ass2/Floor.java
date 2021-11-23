package comp1110.ass2;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The Class for object Floor
 *
 * author: Juren Xu
 */
public class Floor extends ArrayList<Tile> {
    private static final int MAX_SIZE = 7;
    int remainingSlots;

    public Floor() {
        super();
        remainingSlots = 7;
    }

    /**
     * Add a tile in to floor
     *
     * @param tile, the tile we want to add to bag
     * @return boolean, true if we add successful , false if unsuccessful (the floor is already full)
     */
    public boolean add(Tile tile){
        if(remainingSlots == 0){
            return false;
        }
        super.add(tile);
        remainingSlots -= 1;
        return true;
    }


    /**
     * Add a list of tiles in to floor, when the floor is full, the extra tiles will be stored in a arraylist and
     * move to discard
     * @param tileList, the list of tiles we want to add to floor
     * @return list of tiles need move to discard, can be empty list(means no extra tile)
     */
    public ArrayList<Tile> add(ArrayList<Tile> tileList){
        int itr = 0;

        //check if there is an available slot in floor and the list we want to add is not empty
        while(remainingSlots != 0 && !tileList.isEmpty()){
            super.add(tileList.get(itr));
            tileList.remove(itr);
            remainingSlots--;
        }
        /*When floor is full and there is a first player token in tileList, swap the first player token with the last
          tile in floor
        */
        if (tileList.contains(Tile.GRAY)){
            tileList.set(tileList.indexOf(Tile.GRAY), super.get(tileList.indexOf(Tile.GRAY)));
            super.set(6,Tile.GRAY);
        }
        Collections.sort(this);
        Collections.sort(tileList);
        return tileList;
    }

    /**
     * This function will be used when add tiles from storage
     * @param tile, the color of tile
     * @param count, the number of this color of tile need to add into floor
     * @return list of tiles need move to discard, can be empty list(means no extra tile)
     */
    public ArrayList<Tile> add(Tile tile, int count){
        ArrayList<Tile> extraTiles = new ArrayList<Tile>();
        if(count >remainingSlots){
            int remainder = count - remainingSlots;
            for (int i = 0; i < remainder; i++) {
                extraTiles.add(tile);
                count--;
            }
        }
        for (int i = 0; i < count; i++) {
            super.add(tile);
            remainingSlots--;
        }
        if (extraTiles.contains(Tile.GRAY)) {
            extraTiles.set(extraTiles.indexOf(Tile.GRAY), super.get(6));
            super.set(6, Tile.GRAY);
        }
        Collections.sort(this);
        Collections.sort(extraTiles);
        return extraTiles;
    }

    /**
     * Check if floor is valid
     * @return true if the tiles in floor is less or equal than 7 and greater of equal 0, else false
     */
    public boolean isValid(){
        return super.size() <= MAX_SIZE;
     }

    /**
     * Remove all tiles from floor
     *
     * @return ArrayList<Tile>, the list of all tiles has been removed from floor
     */
    public ArrayList<Tile> clearFloor(){
        ArrayList<Tile> temp = new ArrayList<>(this);
        super.clear();
        remainingSlots = 7;
        return temp;
    }

    /**
     * Remove first player token from floor
     *
     * @return Tile, return first player token
     */
    public Tile removeFirstToken(){
        for (int i = 0; i < super.size(); i++) {
            if(super.get(i) == Tile.GRAY){
                super.remove(i);
                remainingSlots++;
                break;
            }
        }
        return Tile.GRAY;
    }

    /**
     * Convert current floor to string representation
     *
     * @return String, a string representation of current floor
     */
    @Override
    public String toString() {
        StringBuilder state = new StringBuilder();
        state.append("F");
        if (super.size() != 0) {
            for (Tile tile : this) {
                state.append((char)(tile.ordinal() + 'a'));
            }
        }
        return state.toString();
    }

}
