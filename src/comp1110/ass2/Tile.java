package comp1110.ass2;

/**
 * Enum type for tile
 *
 * Contain: Blue, Green, Orange, Purple, Red
 * and a static function
 * Gray means first player token
 * authorship: Juren Xu and Honggic,Oh
 */
public enum Tile {
    BLUE(0),
    GREEN(1),
    ORANGE(2),
    PURPLE(3),
    RED(4),
    GRAY(5); // First Player Token

    /**
     * Convert a ascii code of a char to corresponding color
     *
     * author: Juren Xu
     * @param n, an integer, the value between 0 - 5
     *           (int) (character - 'a')
     *           character between a to f
     * @return Tile, the color of tile
     */
    public static Tile fromInt(int n){
        switch (n){
            case 0 -> {
                return Tile.BLUE;
            }
            case 1 -> {
                return Tile.GREEN;
            }
            case 2 -> {
                return Tile.ORANGE;
            }
            case 3 -> {
                return Tile.PURPLE;
            }
            case 4 -> {
                return Tile.RED;
            }
            case 5 -> {
                return Tile.GRAY;
            }
            default -> throw new IllegalArgumentException("No such color " + n);
        }
    }


    /**
     * author: Honggic,Oh
     * @param value
     */
    Tile(int value) {
        this.value = value;
    }
    private final int value;
    public int val() {
        return value;
    }

}
