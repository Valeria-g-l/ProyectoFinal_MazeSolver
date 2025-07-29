package solver.solverImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import models.Cell;
import models.AlgorithmResult;
import solver.MazeSolver;

public class MazeSolverBFS implements MazeSolver {
    private boolean[][] grid;
    private List<Cell> path;
    private Map<Cell, Cell> parents;
    private Set<Cell> visited;
    private Cell end;

    public MazeSolverBFS() {
        grid = new boolean[][] {};
        path = new ArrayList<>();
        parents = new HashMap<>();
        visited = new LinkedHashSet<>();
    }

    @Override
    public AlgorithmResult getPath(boolean[][] grid, Cell start, Cell end) {
        path = new ArrayList<>();
        parents = new HashMap<>();
        visited = new LinkedHashSet<>();
        this.grid = grid;
        this.end = end;
        if (grid == null || grid.length == 0) return new AlgorithmResult(path, visited);
        Queue<Cell> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);
        parents.put(start, null);
        while (!queue.isEmpty()) {
            Cell current = queue.poll();
            if (current.equals(end)) {
                while (current != null) {
                    path.add(0, current);
                    current = parents.get(current);
                }
                return new AlgorithmResult(path, visited);
            }
            findPath(current, queue);
        }
        return new AlgorithmResult(new ArrayList<>(), visited);
    }

    private void findPath(Cell current, Queue<Cell> queue) {
        for (int[] dir : directions) {
            Cell nextCell = new Cell(current.row + dir[0], current.col + dir[1]);
            if (isInMaze(nextCell) && isValid(nextCell)) {
                visited.add(nextCell);
                parents.put(nextCell, current);
                queue.add(nextCell);
            }
        }
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