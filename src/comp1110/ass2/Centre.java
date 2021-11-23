package comp1110.ass2;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The Class for object Centre
 *
 * author: Juren Xu
 */
public class Centre extends ArrayList<Tile> {
    public Centre(){
        super();
    }

    /**
     * Function that check if the centre is empty
     * Centre is empty when it is empty or only first player token in it\
     *
     * @return boolean, return true if it is empty, false otherwise
     */
    public boolean isEmpty() {
        return super.isEmpty() || (super.size() == 1 && super.get(0) == Tile.GRAY);
    }


    /**
     * Function that return the size of centre, which means how many tiles in centre
     * @return int, the numbers of tiles in centre
     */
    public int getSize() {
        if (super.contains(Tile.GRAY)){
            return super.size() - 1;
        }
        return super.size();
    }

    /**
     * Remove a bunch of tiles in the centre, this function will be used when a player select a tile from centre, and
     * all tiles with same color will be selected as well
     * @param tile, the color of tile player selected
     * @return ArrayList<Tile>, store all tiles with same color in an arraylist and return this arraylist
     */
    public ArrayList<Tile> removeTiles(Tile tile){
        ArrayList<Tile> tileList = new ArrayList<>();
        while (super.contains(tile)){
            super.remove(tile);
            tileList.add(tile);
        }
        if (super.contains(Tile.GRAY)){
            super.remove(Tile.GRAY);
            tileList.add(Tile.GRAY);
        }
//        Collections.sort(this);
        return tileList;
    }

    /**
     * Function that add a bunch of tiles to centre
     *
     * @param tile, the color of the tiles
     * @param number, the number of tiles we want to add to centre
     */

    public void addTiles(Tile tile, int number) {
        for (int i = 0; i < number; i++) {
            super.add(tile);
        }
        Collections.sort(this);
    }

    /**
     * Add a bunch of tiles in to centre function used when player choose a tile from factories,
     * the tiles of different will be store in an arraylist and add to centre with this function
     * @param tileList, the list of tiles need to add in centre
     */
    public void addTiles(ArrayList<Tile> tileList) {
        for (Tile tile : tileList) {
            super.add(tile);
        }
        Collections.sort(this);
    }

    /**
     * Count the number of tiles of specified color in the centre
     * @param tile, the color of the tile that we want to count
     * @return the number of specified color of tile in the centre
     */
    public int countTiles(Tile tile) {
        int count = 0;
        for (Tile t:
                this) {
            if (t == tile){
                count++;
            }
        }
        return count;
    }

    /**
     * Convert centre state to a string representation
     * @return the string representation centre
     */
    @Override
    public String toString() {
        StringBuilder state = new StringBuilder();
        state.append("C");
        for (Tile tile : this) {
            state.append((char)(tile.ordinal() + 'a'));
        }
        return state.toString();
    }


}
