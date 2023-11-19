package controller;

import model.*;
import view.*;

/**
 * Controller class that manages the game logic and interactions between the model and view.
 * Tracks the current state of the game, including the game board, selected tiles, move count,
 * and game over status.
 */
public class gameController {
    private Board board;
    private GameGUI gameGUI;
    private Coordinate selected;
    private int moveCount;
    private boolean isGameOver;

    /**
     * Constructs a GameController with the specified initial board size and initializes a new game.
     *
     * @param boardSize The initial size of the game board.
     */
    public gameController(int boardSize) {
        this.newGame(boardSize);
    }

    /**
     * Handles the event when a cell on the game board is clicked.
     * Manages the selection of tiles, verifies valid moves, and triggers corresponding actions.
     *
     * @param coordinate The coordinates of the clicked cell.
     */
    private void onCellClicked(Coordinate coordinate) {
        if (this.isGameOver) return;
        if (selected == null) {
            this.selected = coordinate;
            this.gameGUI.applyTile(coordinate);
        } else {
            Direction direction = coordinate.getDirectionTo(this.selected);
            if (direction == null) {
                this.selected = null;
                this.gameGUI.applyTile(null);
                this.onCellClicked(coordinate);
            } else this.makeMove(direction);
        }
    }

    /**
     * Makes a move in the specified direction based on the selected cell and updates the game state.
     * If the direction is null or no cell is selected, the method returns without making a move.
     *
     * @param direction The direction in which to make the move (UP, DOWN, LEFT, or RIGHT).
     */
    private void makeMove(Direction direction) {
        if (direction == null) return;
        if (this.selected == null) return;
        int index;
        if (direction == Direction.UP || direction == Direction.DOWN) {
            index = selected.x;
        } else {
            index = selected.y;
        }

        this.board.makeMove(direction, index);

        this.selected = null;
        this.moveCount += 1;
        this.gameGUI.applyBoard(this.board.getGrid());
        this.gameGUI.applyTile(null);
        this.gameGUI.applyMoveCount(moveCount);

        isGameOver = board.isGameOver();
        if (isGameOver) gameOver();
    }

    /**
     * Handles the end of the game by displaying a game-over message, resetting the board, and starting a new game.
     */
    public void gameOver(){
        this.gameGUI.applyGameOver("VÉGE!\nLépések: "+moveCount);
        this.newGame(this.board.getSize());
    }


    /**
     * Starts a new game with the specified board size.
     * Initializes a new game board, resets game-related variables, and updates the view accordingly.
     *
     * @param boardSize The size of the new game board.
     */
    private void newGame(int boardSize){
        do {
            this.board = new Board(boardSize);
            this.selected = null;
            this.moveCount = 0;
            this.isGameOver = false;

            if (this.gameGUI != null) this.gameGUI.dispose();
            this.gameGUI = new GameGUI(boardSize, this::onCellClicked, this::newGame);
            this.gameGUI.applyBoard(this.board.getGrid());
            this.gameGUI.applyMoveCount(this.moveCount);
        } while (board.isGameOver());
    }
}
