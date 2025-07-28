package solver;

import models.Cell;
import models.AlgorithmResult;

public interface MazeSolver {
    
    AlgorithmResult getPath(boolean[][] grid, Cell start, Cell end);

    int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    
}
