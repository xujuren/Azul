## Code Review

Reviewed by: Xindi Liu, u6225282

Reviewing code written by: Juren Xu u7149851

Component: <https://gitlab.cecs.anu.edu.au/u6225282/comp1110-ass2-tue12g/-/blob/master/src/comp1110/ass2/Azul.java#L79-129>

### Comments

In this part I review Juren's code for task 2, isSharedStateWellFormed(). The playerState contains five parts: Turn, Factory, Centre, Bag and Discard. 
In the first, he judged if the Turn part is valid, which should be A, B, C or D. Then after removing the Turn part from the string, he checked if the string contains F, C, B and D, which represents Factory, Centre, Bag and Discard. 
Then the rest of the string is divided into four sub strings. 
For the factory string, Juren constructed two methods, isFactoryFlagOrdered() and isAlphabeticOrder(), for checking the order of factories’ number and tiles in each factory. 
After that, he checked whether the factories contain correct tiles, which means correct colour of tiles and correct number. 
Besides, for the Centre part, the method isAlphabeticOrder() is used again for checking the order of centre string. 
At last, the length of centre, bag and discard is checked, which means the length of centre should be fewer than 15 and the length of bag and discard is both 10. 
Until this, all forms of playerState are checked and this method is completed.

What are the best features of this code?

Using .split to divide sub strings from the string for future actions.

Is the code well-documented?

Yes, it is in the Azul.java file.

Is the program decomposition (class and method structure) appropriate?

Yes. It used appropriate class and method to complete task 2.

Does it follow Java code conventions (for example, are methods and variables properly named), and is the style consistent throughout?

Yes, no unusual name and good format.

If you suspect an error in the code, suggest a particular situation in which the program will not function correctly.

No error here.
