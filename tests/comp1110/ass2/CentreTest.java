package comp1110.ass2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


/**
 * Junit test for methods in class Centre
 * author: Juren Xu
 */
public class CentreTest {
    @Test
    public void testAddTileList(){
        // Test if tileList be added correctly and if it is sorted after add
        Centre centre = new Centre();
        centre.add(Tile.BLUE);
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
        centre.addTiles(addList);
        assertEquals(6, centre.size());
        assertEquals(tileList, centre);
    }

    @Test
    public void testGetSize(){
        Centre centre = new Centre();
        centre.add(Tile.BLUE);
        assertEquals(1, centre.getSize());
        centre.add(Tile.GRAY);
        assertEquals(1, centre.getSize());
    }

    @Test
    public void testIsEmpty(){
        Centre centre = new Centre();
        centre.add(Tile.GRAY);
        assertTrue(centre.isEmpty(), "Centre is empty");
        centre.add(Tile.BLUE);
        assertFalse(centre.isEmpty(), "Centre is not empty");
    }

    @Test
    public void testRemoveTiles(){
        Centre centre = new Centre();
        centre.add(Tile.BLUE);
        centre.add(Tile.BLUE);
        centre.add(Tile.GREEN);
        centre.add(Tile.ORANGE);
        centre.add(Tile.PURPLE);
        centre.add(Tile.RED);
        
        centre.removeTiles(Tile.BLUE);

        assertFalse(centre.contains(Tile.BLUE), "There should not have a blue tile in centre");

        ArrayList<Tile> tileList = new ArrayList<Tile>(){{
            add(Tile.GREEN);
            add(Tile.ORANGE);
            add(Tile.PURPLE);
            add(Tile.RED);
        }};

        assertEquals(tileList, centre);
    }

    @Test
    public void testCountTiles(){
        Centre centre = new Centre();
        centre.add(Tile.BLUE);
        centre.add(Tile.BLUE);
        centre.add(Tile.GREEN);
        centre.add(Tile.ORANGE);
        centre.add(Tile.PURPLE);
        centre.add(Tile.RED);

        assertEquals(2, centre.countTiles(Tile.BLUE));
    }
}
