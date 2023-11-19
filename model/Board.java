package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the game board containing a grid of colored tiles.
 * Provides methods for generating the initial grid, making moves,
 * checking game over conditions, and obtaining information about the board.
 */
public final class Board {
    private final TileColor[][] grid;
    private final int size;

    /**
     * Constructs a new Board with the specified size.
     *
     * @param size The size of the square grid.
     */
    public Board(int size) {
        this.size = size;
        this.grid = makeGrid(size);
    }

    /**
     * Generates the initial grid of colored tiles.
     * The tiles are randomly assigned colors based on the size of the grid.
     *
     * @param size The size of the square grid.
     * @return The generated grid of colored tiles.
     */
    private static TileColor[][] makeGrid(int size) {
        TileColor[][] grid = new TileColor[size][size];
        List<TileColor> colorList = generateColors(size);

        List<Coordinate> coordinates = new ArrayList<>(size * size);

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                coordinates.add(new Coordinate(x, y));
            }
        }
        Collections.shuffle(coordinates);

        for (int i = 0; i < size * size; i++) {
            Coordinate coordinate = coordinates.get(i);
            grid[coordinate.x][coordinate.y] = colorList.get(i % colorList.size());
        }

        return grid;
    }

    /**
     * Generates a list of colors based on the size of the grid.
     * The colors are cycled through the available TileColor values.
     *
     * @param size The size of the square grid.
     * @return The list of colors for the tiles.
     */
    private static List<TileColor> generateColors(int size) {
        List<TileColor> colors = new ArrayList<>();
        TileColor[] tileColors = TileColor.values();

        for (int i = 0; i < size; i++) {
            colors.add(tileColors[i % tileColors.length]);
        }
        return colors;
    }

    /**
     * Prints the current state of the game board to the console.
     * Each tile's color is represented by its corresponding TileColor value.
     */
    public void printBoard() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                System.out.print(grid[x][y] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Makes a move in the specified direction for the specified index (row or column).
     * The tiles in the selected row or column shift one position in the given direction,
     * cycling back to the start if necessary.
     *
     * @param direction The direction in which to make the move (UP, DOWN, LEFT, or RIGHT).
     * @param i The index of the row or column for the move.
     */
    public void makeMove(Direction direction, int i) {
        switch (direction) {
            case UP:
                moveUp(i);
                break;
            case DOWN:
                moveDown(i);
                break;
            case LEFT:
                moveLeft(i);
                break;
            case RIGHT:
                moveRight(i);
                break;
        }
    }

    /**
     * Shifts the tiles in the specified column upwards.
     * The top tile moves to the bottom end of the row, and all other tiles shift upwards.
     *
     * @param col The index of the col to move.
     */
    public void moveUp(int col) {
        TileColor temp = grid[col][0];
        for (int i = 0; i < size - 1; i++) {
            grid[col][i] = grid[col][i + 1];
        }
        grid[col][size - 1] = temp;
    }

    /**
     * Shifts the tiles in the specified column downwards.
     * The bottom tile moves to the top of the column, and all other tiles shift to down.
     *
     * @param col The index of the column to move.
     */
    public void moveDown(int col) {
        TileColor temp = grid[col][size - 1];
        for (int i = size - 1; i > 0; i--) {
            grid[col][i] = grid[col][i - 1];
        }
        grid[col][0] = temp;
    }

    /**
     * Shifts the tiles in the specified row to the left.
     * The left tile moves to the end of the row, and all other tiles shift left.
     *
     * @param row The index of the row to move.
     */

    public void moveLeft(int row) {
        TileColor temp = grid[0][row];
        for (int i = 0; i < size - 1; i++) {
            grid[i][row] = grid[i + 1][row];
        }
        grid[size - 1][row] = temp;
    }

    /**
     * Shifts the tiles in the specified row to the right.
     * The right tile moves to the start of the row, and all other tiles shift right.
     *
     * @param row The index of the row to move.
     */
    public void moveRight(int row) {
        TileColor temp = grid[size - 1][row];
        for (int i = size - 1; i > 0; i--) {
            grid[i][row] = grid[i - 1][row];
        }
        grid[0][row] = temp;
    }

    /**
     * Checks if the game is over by verifying if all tiles in any row or column are of the same color.
     *
     * @return True if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        boolean rows = true;
        for (int i = 0; i < size; i++) {
            TileColor firstElementInRow = grid[i][0];
            for (int j = 1; j < size; j++) {
                if (grid[i][j] != firstElementInRow) {
                    rows = false;
                    break;
                }
            }
        }

        boolean columns = true;
        for (int i = 0; i < size; i++) {
            TileColor firstElementInColumn = grid[0][i];
            for (int j = 1; j < size; j++) {
                if (grid[j][i] != firstElementInColumn) {
                    columns = false;
                    break;
                }
            }
        }

        return (rows || columns);
    }

    /**
     * Gets the size of the game board.
     *
     * @return The size of the square grid.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Gets a copy of the current grid of colored tiles.
     *
     * @return The current grid of colored tiles.
     */
    public TileColor[][] getGrid(){
        return this.grid.clone();
    }
}
