package comp1110.ass2.PaulD2DTest;

import comp1110.ass2.Azul;
import org.junit.jupiter.api.Test;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
@org.junit.jupiter.api.Timeout(value = 1000, unit = MILLISECONDS)
public class PaulTest {

    @Test
    public void DividePlayerState() {
        String[] playerState = {"A14Mb01d03c13d14d20a22b23e32b34e43S3a14d2FB10Ma00b01b12d14d20e21c30a44S4c4Fbbf",
                "A75Ma00b01d03e04e10a11b12c13d14d20e21a22b23c24e32a33b34d42e43S3c3FB60Ma00b01c02e10a11b12c13d14d20e21a22c24c30d31b40c41a44SF",
                "A14Mb01d03c13d14d20a22b23e32b34e43S3a14d2FB10Ma00b01b12d14d20e21c30a44S4c4FbbfC20Ma00b01b12d14d20e21a22c30a44S4c4Fbb",
                "A14Mb01d03c13d14d20a22b23e32b34e43S3a14d2FB10Ma00b01b12d14d20e21c30a44S4c4FbbfC20Ma00b01b12d14d20e21a22c30a44S4c4FbbD10Ma00b01e10b12d14d20e21c30c41a44S2c23d3F"};
        String[][] next = {{"", "14Mb01d03c13d14d20a22b23e32b34e43S3a14d2F", "10Ma00b01b12d14d20e21c30a44S4c4Fbbf"},
                {"", "75Ma00b01d03e04e10a11b12c13d14d20e21a22b23c24e32a33b34d42e43S3c3F", "60Ma00b01c02e10a11b12c13d14d20e21a22c24c30d31b40c41a44SF"},
                {"","14Mb01d03c13d14d20a22b23e32b34e43S3a14d2F","10Ma00b01b12d14d20e21c30a44S4c4Fbbf","20Ma00b01b12d14d20e21a22c30a44S4c4Fbb"},
                {"","14Mb01d03c13d14d20a22b23e32b34e43S3a14d2F","10Ma00b01b12d14d20e21c30a44S4c4Fbbf","20Ma00b01b12d14d20e21a22c30a44S4c4Fbb","10Ma00b01e10b12d14d20e21c30c41a44S2c23d3F"}
        };
        for (int i = 0; i < playerState.length; i++) {
            String[] out = Azul.getIndividualPlayer(playerState[i]);
                assertArrayEquals(next[i], out, "invalid state");
            }
        }


    @Test
    public void ChooseFactory() {
        String[] gameState = {"AF0aabb1bcdd2abde3bcde4bbddCfB1410141115D0000000000",
                "AF0bdde1aaae2aaad3ccde4abceCfB0712090708D0000000000",
                "AF0aabb1bcde2abdd3bcde4bbddCfB1410141115D0000000000"};
        int[] numPick ={0,1,4};
        String next[][] = {{"aabb","AF1bcdd2abde3bcde4bbddCfB1410141115D0000000000"},{"aaae","AF0bdde2aaad3ccde4abceCfB0712090708D0000000000"},{"bbdd","AF0aabb1bcde2abdd3bcdeCfB1410141115D0000000000"}};
        String out[][] = Azul.chooseFactory(gameState,numPick);
                assertArrayEquals(next,out, "invalid");
            }

    @Test
    public void ChooseTileFromFactory() {
        String[] pickedfactory = {"aabb","bdde","aaae","aaad","ccde","abce"};
        String[] tilePick ={"a","b","a","d","c","e"};
        String next[][] = {{"aa","bb"},{"b","dde"},{"aaa","e"},{"d","aaa"},{"cc","de"},{"e","abc"}};
        String out[][] = Azul.chooseTileFromFactory(pickedfactory,tilePick);
                assertArrayEquals(next, out, "invalid");
            }

    @Test
    public void Tilecheck() {//Checking which tile is used in the factory
        String[]gameState = {"AF0cdde1bbbe2abde3cdee4abccCfB1815161615D0000000000",
                "AF0abbd1abbe2adde3aabe4bddeCfB1409161110D0003010204",
                "AF0bdde1bcde2aaac3acce4bccdCfB1006100707D0407010508",
                "BF0ccce1aace2aade3abde4ccdeCfB0505040402D0609040610",
                "BF0bcdd1bbbc2aaad3acde4abceCfB0000000000D1110100612",
                "AF0bdde1aaae2aaad3ccde4abceCfB0712090708D0000000000"};
        String[][] next = {new String[]{"2", "5", "4", "4", "5"}, new String[]{"5", "6", "0", "5", "4"},new String[]{"4", "3", "6", "4", "3"},new String[]{"5", "1", "6", "3", "5"},new String[]{"5", "5", "4", "4", "2"},new String[]{"7", "2", "3", "4", "4"}};
        String[][] out = Azul.Tilecheck(gameState);
                assertArrayEquals(next, out, "invalid");
            }
        }

