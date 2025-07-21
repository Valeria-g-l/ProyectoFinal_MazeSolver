package solver;

import java.util.List;

import models.Cell;

public interface MazeSolver {
    
    List<Cell> getPath(boolean[][] grid, Cell start, Cell end);

    int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    
}
