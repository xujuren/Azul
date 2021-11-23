package comp1110.ass2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Junit test for methods in class Score
 * author: Juren Xu
 */
public class TestScore {

    @Test
    public void testUpdateScore(){
        Score score = new Score();
        score.updateScore(5);
        assertEquals(5, score.getScore());
        score.updateScore(8);
        assertEquals(13, score.getScore());
    }

    @Test
    public void testPenalty(){
        Score score = new Score(50);
        score.penalty(0);
        assertEquals(50, score.getScore());
        score.penalty(1);
        assertEquals(49, score.getScore());
        score.penalty(2);
        assertEquals(47, score.getScore());
        score.penalty(3);
        assertEquals(43, score.getScore());
        score.penalty(4);
        assertEquals(37, score.getScore());
        score.penalty(5);
        assertEquals(29, score.getScore());
        score.penalty(6);
        assertEquals(18, score.getScore());
        score.penalty(7);
        assertEquals(4, score.getScore());
    }
}
