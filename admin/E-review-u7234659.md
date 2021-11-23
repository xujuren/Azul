## Code Review

Reviewed by: <HongGic, Oh>, <u7234659>

Reviewing code written by: <Xindi Liu> <u6225282>

Component: <https://gitlab.cecs.anu.edu.au/u6225282/comp1110-ass2-tue12g/-/blob/master/src/comp1110/ass2/D2D_Xindi.java#L1-115>

### Comments 

<write your comments here>
What are the best features of this code?
use Map<Character Integer> aeMap = new HashMap<>(); to calculate score.

Is the code well-documented?
No, Xindi can compress his code a lot (especially for and if sentences) because he runs same functions twice for both player A and B. even though compressed code can be difficult to understand, it can be overcomed by comment.
Also, when the last part of method, he writes return ~~ two times. It can be reduced. 

Is the program decomposition (class and method structure) appropriate?
Yes it is. D2D_Xindi,getBonusPoints and getScour is easy to read.(I think this class name is fine in this time because it works seperately with our group code)

Does it follow Java code conventions (for example, are methods and variables properly named), and is the style consistent throughout?
No, he used a,b,ma,sa for Index temporary and I think giving a specific name is better to see and understand like change a to PlayerAIndex,ma to MosaicPlayerAIndex and He sticks his style.

If you suspect an error in the code, suggest a particular situation in which the program will not function correctly.
No, error in here.

This is improved code for him(use Array to reduce for sentences)
using array can reduce for sentences
String[] playerAB = new String[]{playerA, playerB};
String[] mosaicAB = new String[2];
for (int i = 0; i < playerAB.length; i++) {
for (int j = 0; j < playerAB[i].length(); j++) {
int mosaicIndex = playerAB[i].indexOf('M');
int storageIndex = playerAB[i].indexOf('S');
mosaicAB[i] = playerAB[i].substring(mosaicIndex, storageIndex);
}
}
System.out.println(mosaicAB[0]);
System.out.println(mosaicAB[1]);

if (player == 'A'){
return getScour(playerAB[0]);
}
else {
return getScour(playerAB[1]);
}
}
