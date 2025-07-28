package views;

import javax.swing.*;

import controllers.MazeController;
import models.Cell;
import models.CellState;
import models.SolveResults;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MazePanel extends JPanel {
    public enum Mode { SET_START, SET_END, TOGGLE_WALL }
    private MazeController controller;
    private int rows;
    private int cols;
    private Mode currentMode = Mode.TOGGLE_WALL;
    private Cell start = null;
    private Cell end = null;
    private CellState[][] grid;

    public MazePanel(int rows, int cols) {
        controller = new MazeController();
        this.rows = rows;
        this.cols = cols;
        this.grid = new CellState[rows][cols];
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
        return new Dimension(900, 600);
    }

    private void initializeGrid() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                grid[row][col] = CellState.EMPTY;
            }
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellw = getWidth() / cols;
        int cellh = getHeight() / rows;
        int offsetCol = (getWidth() - (cellw * cols)) / 2;
        int offsetRow = (getHeight() - (cellh * rows)) / 2;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int drawCol = offsetCol + col * cellw;
                int drawRow = offsetRow + row * cellh;

                g.setColor(grid[row][col].getColor());
                g.fillRect(drawCol, drawRow, cellw, cellw);

                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(drawCol, drawRow, cellw, cellh);

                if (grid[row][col] == CellState.START) g.setColor(Color.WHITE);
                else if (grid[row][col] == CellState.END) g.setColor(Color.WHITE);
            }
        }
    }
    
    private void handleCellClick(int mouseX, int mouseY) {  
        int row = mouseY / (getHeight() / rows);
        int col = mouseX / (getWidth() / cols);
        if (col >= cols || row >= rows) return;
        
        switch (currentMode) {
            case SET_START:
                if (start != null) grid[start.row][start.col] = CellState.EMPTY;
                start = new Cell(row, col);
                grid[row][col] = CellState.START;
                break;

            case SET_END:
                if (end != null) grid[end.row][end.col] = CellState.EMPTY;
                end = new Cell(row, col);
                grid[row][col] = CellState.END;
                break;

            case TOGGLE_WALL:
                if (grid[row][col] == CellState.WALL) {
                    grid[row][col] = CellState.EMPTY;
                } else if (grid[row][col] == CellState.EMPTY) {
                    grid[row][col] = CellState.WALL;
                }
                break;
        }
        repaint();
    }

    public void setMode(Mode mode) {
        this.currentMode = mode;
    }

    public void solveMaze(String method) {
        boolean[][] mazeBool = new boolean[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                mazeBool[row][col] = grid[row][col] != CellState.WALL;
            }
        }

        SolveResults solve = controller.obtainRecursiveSolve(mazeBool, start, end);
        System.out.println(solve.getPath());
    }

    public void stepSolve() {
        //solveMaze();
    }

    public void clearMaze() {
        initializeGrid();
        start = null;
        end = null;
        repaint();
    }
}