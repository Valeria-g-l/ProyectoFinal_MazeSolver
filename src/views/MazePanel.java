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
    private int cellSize;
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
        
        int width = getWidth();
        int height = getHeight();
        
        // Calcular tamaño de celda para que quepa todo el laberinto
        cellSize = Math.min(width / cols, height / rows);
        
        // Dibujar celdas
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                int drawX = x * cellSize;
                int drawY = y * cellSize;
                
                // Dibujar fondo de celda
                g.setColor(grid[x][y].getColor());
                g.fillRect(drawX, drawY, cellSize, cellSize);
                
                // Borde de celda
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(drawX, drawY, cellSize, cellSize);
                
                // Dibujar símbolos especiales para START y END
                if (grid[x][y] == CellState.START) {
                    g.setColor(Color.WHITE);
                    g.drawString("S", drawX + cellSize/2 - 3, drawY + cellSize/2 + 4);
                } else if (grid[x][y] == CellState.END) {
                    g.setColor(Color.WHITE);
                    g.drawString("E", drawX + cellSize/2 - 3, drawY + cellSize/2 + 4);
                }
            }
        }
    }
    
    private void handleCellClick(int mouseX, int mouseY) {
        int x = mouseX / cellSize;
        int y = mouseY / cellSize;
        
        if (x >= cols || y >= rows) return;
        
        switch (currentMode) {
            case SET_START:
                // Limpiar el START anterior si existe
                if (start != null) {
                    grid[start.x][start.y] = CellState.EMPTY;
                }
                start = new Point(x, y);
                grid[x][y] = CellState.START;
                break;
                
            case SET_END:
                // Limpiar el END anterior si existe
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
        // Implementación simple de solución (solo para demostración)
        if (start == null || end == null) {
            JOptionPane.showMessageDialog(this, "Debe establecer un punto de inicio y fin", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Marcar el camino solución (algoritmo simple para demostración)
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                if (grid[x][y] == CellState.PATH) {
                    grid[x][y] = CellState.EMPTY;
                }
            }
        }
        
        // Simular un camino solución (en realidad esto debería ser un algoritmo de búsqueda)
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
        // Implementación de solución paso a paso
        // Similar a solveMaze pero con animación
        // Para simplificar, aquí solo llamamos a solveMaze
        solveMaze();
    }
    
    public void clearMaze() {
        initializeGrid();
        start = null;
        end = null;
        repaint();
    }
}