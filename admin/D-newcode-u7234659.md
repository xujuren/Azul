# New Code for Deliverable D2D

## < u7234659 > < HongGic,Oh >

For Deliverable D2D, I contributed the following new statements of original code:

- Added the [TileCheck()](https://gitlab.cecs.anu.edu.au/u6225282/comp1110-ass2-tue12g/-/blob/master/src/comp1110/ass2/Azul.java#L925-942)
  Added the [ChooseFactory()](https://gitlab.cecs.anu.edu.au/u6225282/comp1110-ass2-tue12g/-/blob/master/src/comp1110/ass2/Azul.java#L943-954)
  Added the [chooseTileFromFactory()](https://gitlab.cecs.anu.edu.au/u6225282/comp1110-ass2-tue12g/-/blob/master/src/comp1110/ass2/Azul.java#L956-971)
  
TileCheck() is to check how much and which tile is used in the factory.So, we can check them in array.
chooseFactory() is when we choose one factory to choose tiles, show which tiles are in the factory and delete the selected factory in the gamestate.
chooseTileFromFactory() is after choose factory,we will choose tiles. So, I divide tiles by selected tiles and the other tiles in array.     