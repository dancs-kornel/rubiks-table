package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

/**
 * Represents the graphical user interface (GUI) for the Rubik's Cube game.
 */
public class GameGUI {
    private final JFrame frame;
    private final BoardGUI boardGUI;
    private final JLabel moveCountLabel;

    /**
     * Represents the graphical user interface (GUI) for the Rubik's board game.
     *
     * @param size          The size of the Rubik's board.
     * @param onTileSelected A Consumer that handles cell selection events.
     * @param onNewGame      A Consumer that handles new game events.
     */
    public GameGUI(int size, Consumer<Coordinate> onTileSelected, Consumer<Integer> onNewGame){
        this.frame = new JFrame();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setTitle("Rubik tábla");

        this.boardGUI = new BoardGUI(size, onTileSelected);
        this.frame.add(this.boardGUI.getContainer(), BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu gameMenu = new JMenu("Menü");
        menuBar.add(gameMenu);
        JMenu newGameMenu = new JMenu("Új játék");
        gameMenu.add(newGameMenu);

        this.moveCountLabel = new JLabel("");
        this.moveCountLabel.setHorizontalAlignment(JLabel.LEFT);

        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.add(moveCountLabel, BorderLayout.WEST);
        this.frame.add(infoPanel, BorderLayout.SOUTH);

        int[] boardSizes = {2, 3, 4, 6};

        for (int boardSize : boardSizes) {
            JMenuItem newGameMenuItem = new JMenuItem(boardSize + "x" + boardSize);
            newGameMenu.add(newGameMenuItem);
            newGameMenuItem.addActionListener((ActionEvent actionEvent) -> onNewGame.accept(boardSize));
        }

        JMenuItem exitMenuItem = new JMenuItem("Kilépés");
        gameMenu.add(exitMenuItem);
        exitMenuItem.addActionListener((ActionEvent actionEvent) -> System.exit(0));

        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.setVisible(true);
    }

    /**
     * Displays a game-over message dialog with the provided text.
     *
     * @param text The text to be displayed in the game-over message dialog.
     */
    public void applyGameOver(String text) {
        JOptionPane.showMessageDialog(this.frame, text);
    }

    /**
     * Updates the move count label in the GUI.
     *
     * @param moveCount The current move count to be displayed.
     */
    public void applyMoveCount(int moveCount) {
        this.moveCountLabel.setText(moveCount + " lépés");
    }

    /**
     * Updates the game board in the GUI with the provided grid.
     *
     * @param grid The 2D array representing the current state of the Rubik's Cube board.
     */
    public void applyBoard(TileColor[][] grid) {
        this.boardGUI.applyBoard(grid);
    }

    /**
     * Applies the visual representation of the selected cell on the game board.
     *
     * @param coordinate The coordinates of the selected cell.
     */
    public void applyTile(Coordinate coordinate) {
        this.boardGUI.applyTile(coordinate);
    }

    /**
     * Closes the game GUI window.
     */
    public void dispose() {
        this.frame.dispose();
    }
}
