package comp1110.ass2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * The Class for object Bag
 *
 * author: Juren Xu
 */
public class Bag extends ArrayList<Tile> {

    private static final Random random = new Random();

    /**
     * Default constructor for Class bag
     */
    public Bag() {
        super();
    }

    /**
     * Constructor with a parameter ArrayList<Tile>
     *
     * @param tileList, A list of tiles need to add into bag
     */
    public Bag(ArrayList<Tile> tileList){
        super(tileList);
    }

    /**
     * Remove a tile from bag
     *
     * @param idx, the index of tile that needed to remove from bag
     * @return Tile, the tile that removed
     */
    public Tile remove(int idx){
        Tile temp = super.get(idx);
        super.remove(idx);
        return temp;
    }

    /**
     * Function that add a bunch of tiles into bag
     *
     * @param tile, the color of the tiles
     * @param number, the number of tiles we added into
     */

    public void addTiles(Tile tile, int number) {
        for (int i = 0; i < number; i++) {
            super.add(tile);
        }
        Collections.sort(this);
    }

    /**
     * Function that add a list of tile into bag
     *
     * @param tileList, and list of tiles that add need to add in bag (tile can be different color)
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
     * @return integer, the number of the selected tile we have in bag
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
     * @return String, a string representation of current state of bag
     */
    @Override
    public String toString() {
        StringBuilder state = new StringBuilder();
        state.append("B");
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
