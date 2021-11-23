package comp1110.ass2;

/**
 * The Class for object Mosaic
 *
 * Author: Juren Xu
 */
public class Mosaic {
//    boolean[][] mosaic;
    public Tile[][] mosaic;
    boolean isValidMosaic;

    public Mosaic(){

        mosaic = new Tile[][]{
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
        };
        isValidMosaic = true;
    }


    /**
     * This function will be use when a storage is full and player to the tile to mosaic
     * @param tile, the tile that players move from storage to mosaic
     * @param row, the row it moved to
     * @param column, the column it moved to
     */
    public void fillMosaic(Tile tile, int row, int column){
        /* if the selected position is already have a tile or the current row or column already has same tile, then
            this move is invalid
        */
        if (mosaic[row][column] != null || isColorInRow(tile, row) || isColorInCol(tile, column)){
            isValidMosaic = false;
        }
        mosaic[row][column] = tile;

    }

    /**
     * A recursive function check the numbers of Continuous grid to the right of position the tile is placed
     * @param row, the row that tile is placed
     * @param column, the column that tile is placed
     * @return the numbers of Continuous grid to the right of position the tile is placed
     */
    public int checkRight(int row, int column){
        if(column == 4){
            return 1;
        }
        if(mosaic[row][column + 1] == null){
            return 1;
        }
        return 1 + checkRight(row, column + 1);
    }

    /**
     * A recursive function check the numbers of Continuous grid to the left of position the tile is placed
     * @param row, the row that tile is placed
     * @param column, the column that tile is placed
     * @return the numbers of Continuous grid to the left of position the tile is placed
     */
    public int checkLeft(int row, int column){
        if (column == 0){
            return 1;
        }
        if (mosaic[row][column - 1] == null){
            return 1;
        }
        return 1 + checkLeft(row, column - 1);
    }

    /**
     * A recursive function check the numbers of Continuous grid to the upper of position the tile is placed
     * @param row, the row that tile is placed
     * @param column, the column that tile is placed
     * @return the numbers of Continuous grid to the upper of position the tile is placed
     */
    public int checkUp(int row, int column){
        if (row == 0){
            return 1;
        }
        if (mosaic[row - 1][column ] == null){
            return 1;
        }
        return 1 + checkUp(row - 1, column );
    }

    /**
     * A recursive function check the numbers of Continuous grid to the downside of position the tile is placed
     * @param row, the row that tile is placed
     * @param column, the column that tile is placed
     * @return the numbers of Continuous grid to the downside of position the tile is placed
     */
    public int checkDown(int row, int column){
        if (row == 4){
            return 1;
        }
        if (mosaic[row + 1 ][column] == null){
            return 1;
        }
        return 1 + checkDown(row + 1, column);
    }

    /**
     * Find number of completed row in mosaic of current player
     * @return the number of completed row in mosaic of current player
     */
    public int completedRow(){
        int count = 0;
        for (int i = 0; i < 5; i++) {
            boolean isCompleted = true;
            for (int j = 0; j < 5; j++) {
                if (mosaic[i][j] == null) {
                    isCompleted = false;
                    break;
                }
            }
            if (isCompleted){
                count++;
            }
        }
        return count;
    }

    /**
     * Find number of completed column in mosaic of current player
     * @return the number of completed column in mosaic of current player
     */
    public int completedCol(){
        int count = 0;
        for (int i = 0; i < 5; i++) {
            boolean isCompleted = true;
            for (int j = 0; j < 5; j++) {
                if (mosaic[j][i] == null) {
                    isCompleted = false;
                    break;
                }
            }
            if (isCompleted){
                count++;
            }
        }
        return count;

    }

    /**
     * Find number of completed tile in mosaic of current player(a completed row means
     * there are 5 same tiles in the mosaic)
     * @return the number of completed tile in mosaic of current player
     */
    public int completedColor(){
        int count = 0;
        int[] colorCount = {0, 0, 0, 0, 0};
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (mosaic[i][j] != null) {
                    switch(mosaic[i][j]){
                        case BLUE -> colorCount[0]++;
                        case GREEN -> colorCount[1]++;
                        case ORANGE -> colorCount[2]++;
                        case PURPLE -> colorCount[3]++;
                        case RED -> colorCount[4]++;
                    }
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            count += colorCount[i] == 5 ? 1: 0;
        }
        return count;
    }

    /**
     * Check if there is a same tile in current row
     * @param tile, tile we need to check
     * @param row, which row we are checking
     * @return true if there is a, otherwise false
     */
    public boolean isColorInRow(Tile tile, int row){
        boolean colorInRow = false;
        for (int i = 0; i < 5; i++) {
            if (tile == mosaic[row][i] && tile != null) {
                colorInRow = true;
                break;
            }
        }
        return colorInRow;
    }

    /**
     * Check if there is a same tile in current row
     * @param tile, tile we need to check
     * @param column, which row we are checking
     * @return true if there is a, otherwise false
     */
    public boolean isColorInCol(Tile tile, int column){
        boolean colorInCol = false;
        for (int i = 0; i < 5; i++) {
            if (mosaic[i][column] == tile && tile != null) {
                colorInCol = true;
                break;
            }
        }
        return colorInCol;
    }


    /**
     * Count the number of selected tile in mosaic
     * @param tile, the tile we want to count
     * @return the number of selected tile in the mosaic
     */
    public int countTiles(Tile tile){
        int count = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (tile == mosaic[i][j]){
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Check how many tiles in current mosaic(null means not a tile, which is empty slot)
     * @return the total number of tiles in mosaic
     */
    public int countAllTiles(){
        int count = 0;
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                if(mosaic[i][j]!=null){
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * The function return is the mosaic is valid
     * @return true if it is valid, else false
     */
    public boolean isValid(){
        // return false if it is inValid
        return isValidMosaic;
    }

    /**
     * Convert mosaic to string representation
     * @return the string representation of mosaic
     */
    @Override
    public String toString() {
        StringBuilder state = new StringBuilder();
        state.append("M");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (mosaic[i][j] != null) {
                    state.append((char)(mosaic[i][j].ordinal() + 97));
                    state.append(i);
                    state.append(j);
                }
            }
        }
        return state.toString();
    }



}
