package comp1110.ass2;

import java.util.ArrayList;

/**
 * The Class for object Factory
 *
 * author: Juren Xu
 */
public class Factory extends ArrayList<Tile> {

    int id;

    /**
     * Default constructor
     */
    public Factory (){
        super();
    }

    /**
     * Constructor with id
     * @param id, the id of factory
     */
    public Factory (int id){
        super();
        this.id=id;
    }

    /**
     * Check if the current factory is empty
     * @return true if empty, false if not
     */
    public boolean isEmpty() {
        return super.isEmpty();
    }

    /**
     * Add a tile in to factory
     *
     * @param tile, the tile we want to add to current factory
     * @return boolean, true if we add successful, return false if unsuccessful
     */
    public boolean add(Tile tile){
        if (super.size() == 4) {
            // If current factory is full then return false. Shouldn't happen
            return false;
        }
        return super.add(tile);
    }

    /**
     * Remove selected tile from current factory.
     * @param tile, the color of tile we selected
     * @return the number of tiles of selected color in current factory
     */
        public int removeSelected(Tile tile){
        int count = 0;
        while (super.contains(tile)){
            super.remove(tile);
            count++;
        }
        return count;
    }

    /**
     * Remove all tile in the factories, this function should be used with removeSelected,
     * first we remove selected tile, and the rest tiles will all be throw to centre
     * @return the list of tile need to add to centre
     */
    public ArrayList<Tile> removeAll() {
        ArrayList<Tile> tileList = new ArrayList<>(this);
        super.clear();
        return tileList;
    }

    /**
     * Convert current factory to string representation
     * @return String, a string representation of current factory
     */
    @Override
    public String toString() {
        if (isEmpty()){
            return "";
        }
        StringBuilder state = new StringBuilder();
        state.append(id);
        for (Tile tile:
             this) {
            state.append((char)(tile.ordinal() + 'a'));
        }
        return state.toString();
    }
}
