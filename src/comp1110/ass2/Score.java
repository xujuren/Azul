package comp1110.ass2;

/**
 * The Class for object Score
 *
 * Author: Juren Xu
 */
public class Score {
    int score;

    public Score(){
        score = 0;
    }
    public Score(int score){
        if(score > 999){
            throw new IllegalArgumentException("Invalid score");
        }
        this.score = score;
    }

    /**
     * Update player's score
     * @param number, the changes of the player's score
     */
    public void updateScore(int number){
        score += number;
        if(score < 0){
            score = 0;
        }
    }

    /**
     * Giving a penalty of score according to the number of tiles in floor
     * @param tileInFloor, the number of tiles in current player's floor
     */
    public void penalty(int tileInFloor){
        switch (tileInFloor) {
            case 0 -> updateScore(0);
            case 1 -> updateScore(-1);
            case 2 -> updateScore(-2);
            case 3 -> updateScore(-4);
            case 4 -> updateScore(-6);
            case 5 -> updateScore(-8);
            case 6 -> updateScore(-11);
            default -> updateScore(-14);
        }
    }

    /**
     * Return score
     * @return Integer, the score of current player
     */
    public int getScore() {
        return score;
    }
}
