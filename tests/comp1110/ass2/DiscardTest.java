package comp1110.ass2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Junit test for methods in class Discard
 * author: Juren Xu
 */
public class DiscardTest {

    @Test
    public void addTiles(){
        Discard discard = new Discard();
        ArrayList<Tile> tileList = new ArrayList<Tile>(){{
            add(Tile.BLUE);
            add(Tile.BLUE);
            add(Tile.BLUE);
            add(Tile.BLUE);
            add(Tile.BLUE);

        }};
        discard.addTiles(Tile.BLUE, 5);
        assertEquals(5, discard.size());
        assertEquals(tileList, discard);
    }

    @Test
    public void addTilesList(){
        // Test if tileList be added correctly and if it is sorted after add
        Discard discard = new Discard();
        discard.add(Tile.BLUE);
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
        discard.addTiles(addList);
        assertEquals(6, discard.size());
        assertEquals(tileList, discard);
    }

    @Test
    public void testRemoveAll(){
        Discard discard = new Discard();
        discard.add(Tile.BLUE);
        discard.add(Tile.BLUE);
        discard.add(Tile.GREEN);
        discard.add(Tile.ORANGE);
        discard.add(Tile.PURPLE);
        discard.add(Tile.RED);

        discard.removeAll();
        assertEquals(0, discard.size());
    }

    @Test
    public void testCountTiles(){
        Discard discard = new Discard();
        discard.add(Tile.BLUE);
        discard.add(Tile.BLUE);
        discard.add(Tile.GREEN);
        discard.add(Tile.ORANGE);
        discard.add(Tile.PURPLE);
        discard.add(Tile.RED);

        assertEquals(2, discard.countTiles(Tile.BLUE));
    }
}
