package comp1110.ass2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Junit test for methods in class Factory
 * author: Juren Xu
 */
public class FactoryTest {

    @Test
    public void testAdd(){
        Factory factory = new Factory(0);
        factory.add(Tile.BLUE);
        assertEquals(1, factory.size());
        factory.add(Tile.BLUE);
        assertEquals(2, factory.size());
        factory.add(Tile.ORANGE);
        factory.add(Tile.RED);
        assertEquals(4, factory.size());

        assertFalse(factory.add(Tile.ORANGE));
        assertEquals(4, factory.size());

        ArrayList<Tile> tileList = new ArrayList<Tile>(){{
            add(Tile.BLUE);
            add(Tile.BLUE);
            add(Tile.ORANGE);
            add(Tile.RED);
        }};

        assertEquals(tileList, factory);

    }

    @Test
    public void removeSelectedAndRemoveALl(){
        Factory factory = new Factory(0);
        factory.add(Tile.BLUE);
        factory.add(Tile.BLUE);
        factory.add(Tile.ORANGE);
        factory.add(Tile.RED);

        assertEquals(2, factory.removeSelected(Tile.BLUE));

        ArrayList<Tile> tileList = new ArrayList<Tile>(){{
            add(Tile.ORANGE);
            add(Tile.RED);
        }};

        assertEquals(tileList, factory.removeAll());
    }
}
