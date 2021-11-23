package comp1110.ass2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Junit test for methods in class Floor
 * author: Juren Xu
 */
public class FloorTest {

    @Test
    public void testAdd(){
        Floor floor = new Floor();
        floor.add(Tile.BLUE);
        floor.add(Tile.GREEN);
        floor.add(Tile.BLUE);
        floor.add(Tile.GREEN);
        floor.add(Tile.BLUE);
        floor.add(Tile.GREEN);
        floor.add(Tile.RED);

        assertEquals(7, floor.size());
        assertFalse(floor.add(Tile.RED));
        assertEquals(7, floor.size());

    }

    @Test void testAddTileList(){
        Floor floor = new Floor();
        ArrayList<Tile> addList = new ArrayList<Tile>(){{
            add(Tile.BLUE);
            add(Tile.BLUE);
            add(Tile.GREEN);
            add(Tile.GREEN);
            add(Tile.ORANGE);
            add(Tile.RED);
        }};
        floor.add(addList);
        assertEquals(6, floor.size());
        assertEquals(1, floor.remainingSlots);
        ArrayList<Tile> secondList = new ArrayList<Tile>(){{
            add(Tile.BLUE);
            add(Tile.GREEN);
            add(Tile.GRAY);
        }};


        ArrayList<Tile> extraTiles = new ArrayList<Tile>(){{
            add(Tile.BLUE);
            add(Tile.GREEN);
        }};
        assertEquals(extraTiles, floor.add(secondList));
    }

    @Test
    public void addTilesWithCount(){
        Floor floor = new Floor();
        ArrayList<Tile> extraTiles = new ArrayList<Tile>(){{
            add(Tile.BLUE);
            add(Tile.BLUE);
            add(Tile.BLUE);
        }};
        assertEquals(extraTiles,floor.add(Tile.BLUE, 10));
        assertEquals(7, floor.size());
        assertEquals(0, floor.remainingSlots);
    }

    @Test
    public void testClearFloor(){
        Floor floor = new Floor();
        floor.add(Tile.BLUE);
        floor.add(Tile.GREEN);

        ArrayList<Tile> tileList = new ArrayList<Tile>(){{
            add(Tile.BLUE);
            add(Tile.GREEN);
        }};

        assertEquals(tileList, floor.clearFloor());
        assertEquals(7, floor.remainingSlots);
    }

    @Test
    public void testRemoveFirstPlayerToken(){
        Floor floor = new Floor();
        floor.add(Tile.GRAY);

        assertEquals(Tile.GRAY, floor.removeFirstToken());
        assertEquals(7, floor.remainingSlots);
    }
}
