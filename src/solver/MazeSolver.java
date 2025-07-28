package solver;

import models.Cell;
import models.SolveResults;

public interface MazeSolver {
    
    SolveResults getPath(boolean[][] grid, Cell start, Cell end);

    int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    
}
