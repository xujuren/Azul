## Code Review

Reviewed by: Juren Xu, u7149851

Reviewing code written by: HongGic, Oh, u7234659

Component: < https://gitlab.cecs.anu.edu.au/u6225282/comp1110-ass2-tue12g/-/blob/master/src/comp1110/ass2/Azul.java#L969-1011>

### Comments
What are the best features of this code?
- Used switch statement to link the different tiles with a random number from 0 to 4.

Is the code well-documented?
- No, there are no comments in HongGic's code, it is hard for readers to understand the meaning of code


Is the program decomposition (class and method structure) appropriate?
- Yes, the structure of this method is appropriate, the steps of this method did not mess up.

Does it follow Java code conventions (for example, are methods and variables properly named), and is the style consistent throughout?
- No, the variable is not named properly, the name of variables in this method is meaningless and so there isn't a style in this method. 

If you suspect an error in the code, suggest a particular situation in which the program will not function correctly.
- No, the error could be easy to found.
  

- First, this method does not check the situation when bag is empty but discard is not. So the random draw will cause a negative number of tiles in the bag.
  

- Second, this method does not consider that the bag does not contain a tile that have same color with it drawn.
  
   For example, if the random drawn number is 0 (means draw a blue tile from the bag), but the bag does not contain a blue tile.
  This will also cause a negative number of corresponding tile be negative. 

- Solution to first issue: 

   if(bag_is_empty && !discard_is_empty) {Refill bag with the discard}

- Solution to first issue
   
   After generate a tile, check it there is a corresponding one in the bag, if not, generate it again.
   
   Or, use Object Orient Based programing to solve this.


Here are some suggestions to this method:

1. From line 971 to line 973 declared three variables, where their name is meaningless, change the variable to some meaningful name.


2. From line 976 to line 980 it used a nested if statement to check if both bag and discard are empty. It will be better to write
   these two condition in one if statement such as "if (bag_is_empty && discard_is_empty) "
   
   
3. Function does not check the situation when bag is empty but discard is not.


4. From line 993 to line 1010, Paul uses a switch case to assign a character(a ~ f)to variable k and return k at the end of function. 
    It is better to return character directly in case instead of set a char variable k = character and return it outside switch case statement


5. Using object orient base programming to solve this task instead of direct playing with string.


