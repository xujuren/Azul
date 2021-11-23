package comp1110.ass2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Junit test for methods in class Storage
 * author: Juren Xu
 */
public class TestStorage {
    @Test
    public void testIsFull(){
        Storage firsStorage = new Storage(0, Tile.BLUE, 1);
        assertTrue(firsStorage.isFull());

        Storage secondStorage = new Storage(1, Tile.BLUE, 0);
        assertFalse(secondStorage.isFull());
    }

    @Test
    public void testIsEmpty(){
        Storage firsStorage = new Storage(0);
        assertTrue(firsStorage.isEmpty());

        Storage secondStorage = new Storage(1, Tile.BLUE, 1);
        assertFalse(secondStorage.isEmpty());
    }

    @Test
    public void testCanAdd(){
        Storage firsStorage = new Storage(0);
        assertTrue(firsStorage.canAdd(Tile.BLUE));

        Storage secondStorage = new Storage(1, Tile.BLUE, 2);
        assertFalse(secondStorage.canAdd(Tile.BLUE));

        Storage thirdStorage = new Storage(2, Tile.BLUE, 2);
        assertFalse(thirdStorage.canAdd(Tile.GREEN));
    }

    @Test
    public void testAdd(){
        Storage storage = new Storage(2);
        ArrayList<Tile> tileList = new ArrayList<Tile>(){{
            add(Tile.BLUE);
            add(Tile.BLUE);
        }};
        assertEquals(tileList, storage.add(Tile.BLUE, 5));
        assertEquals(3, storage.getTileCount());
        assertEquals(Tile.BLUE, storage.currColor());
    }

    @Test
    public void testRemoveAll(){
        Storage storage = new Storage(2, Tile.GREEN, 3);
        storage.removeAll();
        assertEquals(0, storage.getTileCount());
        assertNull(storage.currColor());
    }

    @Test
    public void testGetColor(){
        Storage storage = new Storage(2, Tile.GREEN, 3);
        assertEquals(Tile.GREEN, storage.currColor());
    }

    @Test
    public void testStorageId(){
        Storage storage = new Storage(2, Tile.GREEN, 3);
        assertEquals(2, storage.getRow());
    }
}
