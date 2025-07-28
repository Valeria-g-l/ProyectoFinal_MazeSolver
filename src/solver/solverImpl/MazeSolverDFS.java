
package solver.solverImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import models.Cell;
import models.SolveResults;
import solver.MazeSolver;

public class MazeSolverDFS implements MazeSolver {
    private boolean[][] grid;
    private List<Cell> path;
    private Map<Cell, Cell> parents;
    private Set<Cell> visited;
    private Cell end;

    public MazeSolverDFS() {
        grid = new boolean[][] {};
        path = new ArrayList<>();
        parents = new HashMap<>();
        visited = new HashSet<>();
    }

    @Override
    public SolveResults getPath(boolean[][] grid, Cell start, Cell end) {
        path = new ArrayList<>();
        parents = new HashMap<>();
        visited = new HashSet<>();
        this.grid = grid;
        this.end = end;
        if (grid == null || grid.length == 0) return new SolveResults(path, visited);
        Stack<Cell> stack = new Stack<>();
        stack.push(start);
        visited.add(start);
        parents.put(start, null);
        while (!stack.isEmpty()) {
            Cell current = stack.pop();
            if (current.equals(end)) {
                while (current != null) {
                    path.add(0, current);
                    current = parents.get(current);
                }
                return new SolveResults(stack, visited);
            }
            findPath(current, stack);
        }
        return new SolveResults(new ArrayList<>(), new HashSet<>());
    }

    private void findPath(Cell current, Stack<Cell> stack) {
        for (int[] dir : directions) {
            Cell nextCell = new Cell(current.row + dir[0], current.col + dir[1]);
            if (isInMaze(nextCell) && isValid(nextCell)) {
                visited.add(nextCell);
                parents.put(nextCell, current);
                stack.push(current);
            }
        }
    }

    private boolean isInMaze(Cell current) {
        int row = current.row;
        int col = current.col;
        return row<grid.length && col<grid[0].length;
    }
    
    private boolean isValid(Cell current) {
        return grid[current.row][current.col] && !visited.contains(current);
    }
}