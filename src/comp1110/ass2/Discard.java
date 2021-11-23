package comp1110.ass2;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The Class for object Discard
 * author: Juren Xu
 */
public class Discard extends ArrayList<Tile>  {
    /**
     * Default constructor for Class discard
     */
    public Discard() {
        super();
    }

    /**
     * Remove all tile from discard
     *
     * @return ArrayList<Tile>, all tiles in discard
     */
    public ArrayList<Tile> removeAll(){
        ArrayList<Tile> temp = new ArrayList<>(this);
        super.clear();
        return temp;
    }

    /**
     * Function that add a bunch of tiles to discard
     *
     * @param tile, the color of the tiles
     * @param number, the number of tiles we want to add to discard
     */
    public void addTiles(Tile tile, int number) {
        for (int i = 0; i < number; i++) {
            super.add(tile);
        }
    }

    /**
     * Function that add a list of tile into discard
     *
     * @param tileList, and list of tiles that add need to add to discard (tile can be different color)
     */
    public void addTiles(ArrayList<Tile> tileList) {
        for (Tile tile : tileList) {
            super.add(tile);
        }
        Collections.sort(this);
    }

    /**
     * Count the number of particular color of the tile
     * @param tile, the tile that we want to count
     * @return integer, the number of the selected tile we have in discard
     */
    public int countTiles(Tile tile){
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
     * Convert discard to string representation
     * @return String, a string representation of current state of discard
     */
    @Override
    public String toString() {
        StringBuilder state = new StringBuilder();
        state.append("D");
        for (int i = 0; i < 5; i++) {
            int count = countTiles(Tile.fromInt(i));
            if (count < 10){
                state.append(0);
            }
            state.append(count);
        }
        return state.toString();
    }

}



