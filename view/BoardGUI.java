package view;

import model.Coordinate;
import model.TileColor;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

/**
 * Represents the graphical user interface for the game board.
 */
public class BoardGUI {
    private final JButton[][] cellButtonGrid;
    private final JPanel containerPanel;
    private JButton selectedTileButton;

    /**
     * Initializes the BoardGUI with the specified size and cell selection handler.
     *
     * @param size           The size of the game board.
     * @param onCellSelected The consumer function to handle cell selection events.
     */
    public BoardGUI(int size, Consumer<Coordinate> onCellSelected) {
        this.cellButtonGrid = new JButton[size][size];
        this.containerPanel = new JPanel();
        this.containerPanel.setLayout(new GridLayout(size, size));

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                JButton cellButton = new JButton();

                final int finalX = x;
                final int finalY = y;

                cellButton.addActionListener(actionEvent -> onCellSelected.accept(new Coordinate(finalX, finalY)));
                cellButton.setPreferredSize(new Dimension(80, 80));
                cellButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
                cellButton.setOpaque(true);  // Set opaque property to true

                this.cellButtonGrid[x][y] = cellButton;
                this.containerPanel.add(cellButton);
            }
        }
    }

    /**
     * Applies the given tile color grid to update the visual representation of the game board.
     *
     * @param grid The 2D array representing the tile colors on the game board.
     */
    public void applyBoard(TileColor[][] grid) {
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid.length; y++) {
                this.cellButtonGrid[x][y].setBackground(colorFromTileColor(grid[x][y]));
            }
        }
    }

    /**
     * Applies the visual highlighting of the selected cell based on the provided coordinate.
     *
     * @param coordinate The coordinate of the selected cell.
     */
    public void applyTile(Coordinate coordinate) {

        if(coordinate == null && this.selectedTileButton == null) return;
        if(coordinate == null) {
            this.selectedTileButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
            this.selectedTileButton = null;
        }
        else if(this.selectedTileButton == null) {
            this.selectedTileButton = this.cellButtonGrid[coordinate.x][coordinate.y];
            this.selectedTileButton.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 5));
        }
        else {
            this.selectedTileButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
            this.selectedTileButton = this.cellButtonGrid[coordinate.x][coordinate.y];
            this.selectedTileButton.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 5));
        }
    }

    /**
     * Converts a TileColor enum value to its corresponding Color object.
     *
     * @param color The TileColor enum value.
     * @return The Color object corresponding to the TileColor.
     */
    private static Color colorFromTileColor(TileColor color) {
        return switch (color) {
            case RED -> Color.RED;
            case BLUE -> Color.BLUE;
            case GREEN -> Color.GREEN;
            case YELLOW -> Color.YELLOW;
            case ORANGE -> Color.ORANGE;
            case WHITE -> Color.WHITE;
        };
    }

    /**
     * Returns the JPanel containing the graphical representation of the game board.
     *
     * @return The container panel.
     */
    public JPanel getContainer() {
        return this.containerPanel;
    }
}
