
package solver.solverImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import models.Cell;
import models.AlgorithmResult;
import solver.MazeSolver;

public class MazeSolverDFS implements MazeSolver {
    private boolean[][] grid;
    private List<Cell> path;
    private Map<Cell, Cell> parents;
    private Set<Cell> visited;
    private Cell end;
    private final int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    @Override
    public AlgorithmResult getPath(boolean[][] grid, Cell start, Cell end) {
        this.grid = grid;
        this.path = new ArrayList<>();
        this.parents = new HashMap<>();
        this.visited = new HashSet<>();
        this.end = end;
        if (grid == null || grid.length == 0 || !isInMaze(start) || !isInMaze(end)) {
            return new AlgorithmResult(new ArrayList<>(), new HashSet<>());
        }
        Stack<Cell> stack = new Stack<>();
        stack.push(start);
        visited.add(start);
        parents.put(start, null);

        while (!stack.isEmpty()) {
            Cell current = stack.pop();
            if (current.equals(end)) {
                Cell node = end;
                while (node != null) {
                    path.add(0, node);
                    node = parents.get(node);
                }
                return new AlgorithmResult(path, visited);
            }
            for (int[] dir : directions) {
                Cell nextCell = new Cell(current.row + dir[0], current.col + dir[1]);
                if (isInMaze(nextCell) && isValid(nextCell)) {
                    visited.add(nextCell);
                    parents.put(nextCell, current);
                    stack.push(nextCell);
                }
            }
        }
        return new AlgorithmResult(new ArrayList<>(), visited);
    }

    private boolean isInMaze(Cell current) {
        return current != null && 
               current.row >= 0 && 
               current.col >= 0 && 
               current.row < grid.length && 
               current.col < grid[0].length;
    }
    
    private boolean isValid(Cell current) {
        return grid[current.row][current.col] && !visited.contains(current);
    }
}