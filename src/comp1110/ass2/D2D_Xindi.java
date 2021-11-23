package comp1110.ass2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class D2D_Xindi {

    //This class is only for D2D. Because after discussing, we decide to use Juren's code due to the high quality.
    // For D2D part, I have to create this new class to show my own work.
    /**
     * Given a gameState for a completed game,
     * return bonus points for rows, columns, and sets.
     *
     * @param gameState a completed game state
     * @param player    the player for whom the score is to be returned
     * @return the number of bonus points awarded to this player for rows,
     * columns, and sets
     * TASK 7
     */
    public static int getBonusPoints(String[] gameState, char player) {
        // FIXME Task 7
        String playerState = gameState[1];
        int a = 0, b = 0;
        for (int i = 0; i < playerState.length() - 1; i++) {
            if (playerState.charAt(i) == 'A') {
                a = i;
            } else if (playerState.charAt(i) == 'B') {
                b = i;
            }
        }
        String playerA = playerState.substring(a, b);
        String playerB = playerState.substring(b, playerState.length());
        int ma = 0, sa = 0;
        for (int i = 0; i < playerA.length() - 1; i++) {
            if (playerA.charAt(i) == 'M') {
                ma = i;
            } else if (playerA.charAt(i) == 'S') {
                sa = i;
            }
        }
        String mosaicA = playerA.substring(ma, sa);
        int mb = 0, sb = 0;
        for (int i = 0; i < playerB.length() - 1; i++) {
            if (playerB.charAt(i) == 'M') {
                mb = i;
            } else if (playerB.charAt(i) == 'S') {
                sb = i;
            }
        }
        String mosaicB = playerB.substring(mb, sb);
        if (player=='A')
            return getScour(mosaicA);
        else if (player=='B')
            return getScour(mosaicB);
        return getScour(mosaicA);
    }
    private static int getScour(String mosaicStr) {
        int score = 0;
        if(mosaicStr.length()>0 && mosaicStr.startsWith("M")){
            mosaicStr = mosaicStr.substring(1);
            if(mosaicStr.length()%3 == 0 ){
                // How many tiles in mosaic
                int mosaicLength = mosaicStr.length()/3;
                List<String> mosaicList = new ArrayList<>();
                // Build mosaic list
                for(int i=0; i<mosaicLength; i++){
                    mosaicList.add(mosaicStr.substring(i*3,i*3+3));
                }
                // calculate a-e
                Map<Character,Integer> aeMap = new HashMap<>();
                // calculate row number
                Map<Character,Integer> firstNumMap = new HashMap<>();
                // calculate column number
                Map<Character,Integer> secondNumMap = new HashMap<>();
                for(String mosaic : mosaicList){
                    if(null == aeMap.get(mosaic.charAt(0))){
                        aeMap.put(mosaic.charAt(0), 1);
                    }else {
                        aeMap.put(mosaic.charAt(0), aeMap.get(mosaic.charAt(0))+1);
                    }
                    if(null == firstNumMap.get(mosaic.charAt(1))){
                        firstNumMap.put(mosaic.charAt(1), 1);
                    }else {
                        firstNumMap.put(mosaic.charAt(1), firstNumMap.get(mosaic.charAt(1))+1);
                    }
                    if(null == secondNumMap.get(mosaic.charAt(2))){
                        secondNumMap.put(mosaic.charAt(2), 1);
                    }else {
                        secondNumMap.put(mosaic.charAt(2), secondNumMap.get(mosaic.charAt(2))+1);
                    }
                }
                // calculate score
                for(Map.Entry<Character,Integer> entry : aeMap.entrySet()){
                    if(entry.getValue() >= 5){
                        score+=10;
                    }
                }
                for(Map.Entry<Character,Integer> entry : firstNumMap.entrySet()){
                    if(entry.getValue() >= 5){
                        score+=2;
                    }
                }
                for(Map.Entry<Character,Integer> entry : secondNumMap.entrySet()){
                    if(entry.getValue() >= 5){
                        score+=7;
                    }
                }
                return score;
            }
        }
        return score;
    }
}
