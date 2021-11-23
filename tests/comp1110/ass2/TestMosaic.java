package comp1110.ass2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestMosaic {

    @Test
    public void testFillMosaic(){
        Mosaic mosaic = new Mosaic();
        mosaic.mosaic = new Tile[][]{
                {null, null, null, Tile.BLUE, null},
                {null, Tile.GREEN, Tile.RED, null, Tile.PURPLE},
                {null, null, null, Tile.PURPLE, null},
                {null, null, null, Tile.RED, null},
                {null, null, null, null, null}
        };

        mosaic.fillMosaic(Tile.ORANGE, 1, 3);
        assertEquals(Tile.ORANGE, mosaic.mosaic[1][3]);
        assertTrue(mosaic.isValid());
        mosaic.fillMosaic(Tile.ORANGE, 1, 3);
        assertFalse(mosaic.isValid());
    }

    @Test
    public void testFindContinuouslyTiles(){
        Mosaic mosaic = new Mosaic();
        mosaic.mosaic = new Tile[][]{
                {null, null, null, Tile.BLUE, null},
                {null, Tile.GREEN, Tile.RED, null, Tile.PURPLE},
                {null, null, null, Tile.PURPLE, null},
                {null, null, null, Tile.RED, null},
                {null, null, null, null, null}
        };
        mosaic.fillMosaic(Tile.ORANGE, 1, 3);
        assertEquals(3, mosaic.checkLeft(1,3));
        assertEquals(2, mosaic.checkRight(1,3));
        assertEquals(2,mosaic.checkUp(1,3));
        assertEquals(3, mosaic.checkDown(1,3));
    }

    @Test
    public void testCompleteRowAndColumnAndColor(){
        Mosaic mosaic = new Mosaic();
        mosaic.mosaic = new Tile[][]{
                {null, null, null, Tile.BLUE, null},
                {Tile.BLUE, Tile.GREEN, Tile.RED, Tile.ORANGE, Tile.PURPLE},
                {null, Tile.BLUE, null, Tile.PURPLE, null},
                {null, null, Tile.BLUE, Tile.RED, null},
                {null, null, null, Tile.GREEN, Tile.BLUE}
        };

        assertEquals(1,mosaic.completedCol());
        assertEquals(1,mosaic.completedRow());
        assertEquals(1,mosaic.completedColor());
    }

    @Test
    public void testIsColorInRowAndColumn(){
        Mosaic mosaic = new Mosaic();
        mosaic.mosaic = new Tile[][]{
                {null, null, null, Tile.BLUE, null},
                {Tile.BLUE, Tile.GREEN, Tile.RED, Tile.ORANGE, Tile.PURPLE},
                {null, Tile.BLUE, null, Tile.PURPLE, null},
                {null, null, Tile.BLUE, Tile.RED, null},
                {null, null, null, Tile.GREEN, Tile.BLUE}
        };

        assertTrue(mosaic.isColorInCol(Tile.BLUE, 3), "There is a blue tile in column 3");
        assertFalse(mosaic.isColorInCol(Tile.GREEN, 0), "There is not a green tile in column 0");
        assertTrue(mosaic.isColorInRow(Tile.BLUE, 0), "There is a blue tile in row 0");
        assertFalse(mosaic.isColorInRow(Tile.GREEN, 3) , "There is not a green tile in row 3    ");
    }

    @Test
    public void testCountTile(){
        Mosaic mosaic = new Mosaic();
        mosaic.mosaic = new Tile[][]{
                {null, null, null, Tile.BLUE, null},
                {Tile.BLUE, Tile.GREEN, Tile.RED, Tile.ORANGE, Tile.PURPLE},
                {null, Tile.BLUE, null, Tile.PURPLE, null},
                {null, null, Tile.BLUE, Tile.RED, null},
                {null, null, null, Tile.GREEN, Tile.BLUE}
        };

        assertEquals(5, mosaic.countTiles(Tile.BLUE));
    }

    @Test
    public void testCountAllTiles(){
        Mosaic mosaic = new Mosaic();
        mosaic.mosaic = new Tile[][]{
                {null, null, null, Tile.BLUE, null},
                {Tile.BLUE, Tile.GREEN, Tile.RED, Tile.ORANGE, Tile.PURPLE},
                {null, Tile.BLUE, null, Tile.PURPLE, null},
                {null, null, Tile.BLUE, Tile.RED, null},
                {null, null, null, Tile.GREEN, Tile.BLUE}
        };

        assertEquals(12, mosaic.countAllTiles());
    }

}
