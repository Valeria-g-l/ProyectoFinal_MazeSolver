package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class MazePanel extends JPanel {
    private enum CellType { EMPTY, WALL, PATH, VISITED }
    
    private static class Cell {
        int row, col;
        CellType type;
        
        Cell(int r, int c, CellType t) { 
            row = r; 
            col = c; 
            type = t; 
        }
    }

    private final int rows = 20, cols = 20, cellSize = 30;
    private final Cell[][] grid = new Cell[rows][cols];
    private Cell startCell, endCell;
    private boolean settingStart, settingEnd, settingWalls;

    public MazePanel() {
        initializeGrid();
        setupMouseListener();
        setPreferredSize(new Dimension(cols * cellSize, rows * cellSize));
    }

    private void initializeGrid() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                grid[row][col] = new Cell(row, col, CellType.EMPTY);
            }
        }
        startCell = endCell = null;
    }

    private void setupMouseListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = e.getX() / cellSize;
                int row = e.getY() / cellSize;
                
                if (row >= 0 && row < rows && col >= 0 && col < cols) {
                    if (settingStart) setStartCell(row, col);
                    else if (settingEnd) setEndCell(row, col);
                    else if (settingWalls) toggleWall(row, col);
                    repaint();
                }
            }
        });
    }

    private void setStartCell(int row, int col) {
        if (grid[row][col].type != CellType.WALL) {
            startCell = grid[row][col];
            settingStart = false;
        }
    }

    private void setEndCell(int row, int col) {
        if (grid[row][col].type != CellType.WALL) {
            endCell = grid[row][col];
            settingEnd = false;
        }
    }

    private void toggleWall(int row, int col) {
        if (grid[row][col] != startCell && grid[row][col] != endCell) {
            grid[row][col].type = grid[row][col].type == CellType.WALL ? CellType.EMPTY : CellType.WALL;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Cell cell = grid[row][col];
                g.setColor(getCellColor(cell));
                g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(col * cellSize, row * cellSize, cellSize, cellSize);
            }
        }
    }

    private Color getCellColor(Cell cell) {
        if (cell == startCell) return Color.GREEN;
        if (cell == endCell) return Color.RED;
        switch (cell.type) {
            case WALL: return Color.BLACK;
            case PATH: return Color.BLUE;
            case VISITED: return new Color(200, 200, 255);
            default: return Color.WHITE;
        }
    }

    public void clearMaze() {
        initializeGrid();
        repaint();
    }

    public boolean solveDFS() {
        boolean[][] visited = new boolean[rows][cols];
        List<Cell> path = new ArrayList<>();
        
        boolean solved = dfs(startCell.row, startCell.col, visited, path);
        
        if (solved) {
            for (Cell cell : path) {
                if (cell != startCell && cell != endCell) {
                    cell.type = CellType.PATH;
                }
            }
            repaint();
        }
        return solved;
    }

    private boolean dfs(int row, int col, boolean[][] visited, List<Cell> path) {
        if (row < 0 || row >= rows || col < 0 || col >= cols || 
            visited[row][col] || grid[row][col].type == CellType.WALL) {
            return false;
        }
        
        visited[row][col] = true;
        path.add(grid[row][col]);
        
        if (grid[row][col] == endCell) return true;
        
        if (dfs(row + 1, col, visited, path) || dfs(row - 1, col, visited, path) ||
            dfs(row, col + 1, visited, path) || dfs(row, col - 1, visited, path)) {
            return true;
        }
        
        path.remove(path.size() - 1);
        return false;
    }

    public void setSettingStart(boolean setting) { 
        settingStart = setting; 
        if (setting) {
            settingEnd = false;
            settingWalls = false;
        }
    }
    
    public void setSettingEnd(boolean setting) { 
        settingEnd = setting; 
        if (setting) {
            settingStart = false;
            settingWalls = false;
        }
    }
    
    public void setSettingWalls(boolean setting) { 
        settingWalls = setting; 
        if (setting) {
            settingStart = false;
            settingEnd = false;
        }
    }
    
    public boolean hasStart() { return startCell != null; }
    public boolean hasEnd() { return endCell != null; }
}