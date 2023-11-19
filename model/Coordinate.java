package model;

/**
 * Represents a two-dimensional coordinate in a grid.
 * Provides methods for obtaining the next coordinate in a given direction
 * and determining the cardinal direction to another coordinate.
 */
public final class Coordinate {
    public final int x;
    public final int y;

    /**
     * Constructs a new Coordinate with specified x and y values.
     *
     * @param x The x-coordinate value.
     * @param y The y-coordinate value.
     */
    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the cardinal direction from this coordinate to another coordinate.
     *
     * @param that The target coordinate.
     * @return The cardinal direction from this coordinate to the target coordinate.
     *         Returns null if the coordinates are not adjacent.
     */
    public Direction getDirectionTo(Coordinate that) {
        if (this.x == that.x - 1 && this.y == that.y) return Direction.LEFT;
        else if (this.x == that.x + 1 && this.y == that.y) return Direction.RIGHT;
        else if (this.x == that.x && this.y == that.y - 1) return Direction.UP;
        else if (this.x == that.x && this.y == that.y + 1) return Direction.DOWN;
        return null;
    }

}
