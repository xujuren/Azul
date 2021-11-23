package comp1110.ass2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Junit test for methods in class Bag
 * author: Juren Xu
 */
public class BagTest {

    @Test
    public void testAddTile(){
        Bag bag = new Bag();
        ArrayList<Tile> tileList = new ArrayList<Tile>(){{
            add(Tile.BLUE);
            add(Tile.BLUE);
            add(Tile.BLUE);
            add(Tile.BLUE);
            add(Tile.BLUE);

        }};
        bag.addTiles(Tile.BLUE, 5);
        assertEquals(5, bag.size());
        assertEquals(tileList, bag);

    }

    @Test
    public void testAddTileList(){
        // Test if tileList be added correctly and if it is sorted after add
        Bag bag = new Bag();
        bag.add(Tile.BLUE);
        ArrayList<Tile> addList = new ArrayList<Tile>(){{
            add(Tile.BLUE);
            add(Tile.GREEN);
            add(Tile.PURPLE);
            add(Tile.ORANGE);
            add(Tile.RED);
        }};
        ArrayList<Tile> tileList = new ArrayList<Tile>(){{
            add(Tile.BLUE);
            add(Tile.BLUE);
            add(Tile.GREEN);
            add(Tile.ORANGE);
            add(Tile.PURPLE);
            add(Tile.RED);
        }};
        bag.addTiles(addList);
        assertEquals(6, bag.size());
        assertEquals(tileList, bag);
    }

    @Test
    public void testRemove(){
        Bag bag = new Bag(){{
            add(Tile.BLUE);
            add(Tile.BLUE);
            add(Tile.GREEN);
            add(Tile.PURPLE);
            add(Tile.ORANGE);
            add(Tile.RED);
        }};

        ArrayList<Tile> tileList = new ArrayList<Tile>(){{
            add(Tile.BLUE);
            add(Tile.BLUE);
            add(Tile.GREEN);
            add(Tile.PURPLE);
            add(Tile.RED);
        }};

        bag.remove(4);
        assertEquals(5, bag.size());
        assertEquals(Tile.RED, bag.get(4));
        assertEquals(tileList, bag);

    }
}
