package views;

import javax.swing.*;

import models.CellState;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MazePanel extends JPanel {
    public enum Mode { SET_START, SET_END, TOGGLE_WALL }

    private int rows;
    private int cols;
    private Mode currentMode = Mode.TOGGLE_WALL;

    private Point start = null;
    private Point end = null;
    private CellState[][] grid;

    public MazePanel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new CellState[cols][rows];
        initializeGrid();

        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleCellClick(e.getX(), e.getY());
            }
        });
    }

    @Override
    public Dimension getPreferredSize() {
        // Puedes ajustar el tamaño base si quieres, pero no es necesario
        return new Dimension(900, 600);
    }

    private void initializeGrid() {
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                grid[x][y] = CellState.EMPTY;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Calcula el tamaño de celda para llenar el panel y que la matriz sea más horizontal
        int cellWidth = getWidth() / cols;
        int cellHeight = getHeight() / rows;

        // Centra la matriz si sobra espacio
        int offsetX = (getWidth() - (cellWidth * cols)) / 2;
        int offsetY = (getHeight() - (cellHeight * rows)) / 2;

        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                int drawX = offsetX + x * cellWidth;
                int drawY = offsetY + y * cellHeight;

                g.setColor(grid[x][y].getColor());
                g.fillRect(drawX, drawY, cellWidth, cellHeight);

                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(drawX, drawY, cellWidth, cellHeight);

                if (grid[x][y] == CellState.START) {
                    g.setColor(Color.WHITE);
                    g.drawString("S", drawX + cellWidth/2 - 3, drawY + cellHeight/2 + 4);
                } else if (grid[x][y] == CellState.END) {
                    g.setColor(Color.WHITE);
                    g.drawString("E", drawX + cellWidth/2 - 3, drawY + cellHeight/2 + 4);
                }
            }
        }
    }

    private void handleCellClick(int mouseX, int mouseY) {
        int cellWidth = getWidth() / cols;
        int cellHeight = getHeight() / rows;
        int x = mouseX / cellWidth;
        int y = mouseY / cellHeight;

        if (x >= cols || y >= rows) return;

        switch (currentMode) {
            case SET_START:
                if (start != null) {
                    grid[start.x][start.y] = CellState.EMPTY;
                }
                start = new Point(x, y);
                grid[x][y] = CellState.START;
                break;

            case SET_END:
                if (end != null) {
                    grid[end.x][end.y] = CellState.EMPTY;
                }
                end = new Point(x, y);
                grid[x][y] = CellState.END;
                break;

            case TOGGLE_WALL:
                if (grid[x][y] == CellState.WALL) {
                    grid[x][y] = CellState.EMPTY;
                } else if (grid[x][y] == CellState.EMPTY) {
                    grid[x][y] = CellState.WALL;
                }
                break;
        }

        repaint();
    }

    public void setMode(Mode mode) {
        this.currentMode = mode;
    }

    public void solveMaze() {
        if (start == null || end == null) {
            JOptionPane.showMessageDialog(this, "Debe establecer un punto de inicio y fin", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                if (grid[x][y] == CellState.PATH) {
                    grid[x][y] = CellState.EMPTY;
                }
            }
        }

        int currentX = start.x;
        int currentY = start.y;

        while (currentX != end.x || currentY != end.y) {
            if (currentX < end.x) currentX++;
            else if (currentX > end.x) currentX--;

            if (currentY < end.y) currentY++;
            else if (currentY > end.y) currentY--;

            if (grid[currentX][currentY] == CellState.WALL) {
                JOptionPane.showMessageDialog(this, "No se puede encontrar un camino (pared en el camino)", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (grid[currentX][currentY] == CellState.EMPTY) {
                grid[currentX][currentY] = CellState.PATH;
            }
        }

        repaint();
    }

    public void stepSolve() {
        solveMaze();
    }

    public void clearMaze() {
        initializeGrid();
        start = null;
        end = null;
        repaint();
    }
}