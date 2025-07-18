package solver.solverImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import models.Cell;
import solver.MazeSolver;

public class MazeSolverRecursivoCompletoBT implements MazeSolver {
    private boolean[][] grid;
    private List<Cell> path;
    private Set<Cell> visited;
    private Cell end;

    public MazeSolverRecursivoCompletoBT() {
        grid = new boolean[][] {};
        path = new ArrayList<>();
        visited = new HashSet<>();
    }

    @Override
    public List<Cell> getPath(boolean[][] grid, Cell start, Cell end) {
        path.clear();
        this.path = new ArrayList<>();
        this.grid = grid;
        this.end = end;
        if (grid == null || grid.length == 0) return path;
        if (findPath(start)) return path;
        return new ArrayList<>();
    }

    private boolean findPath(Cell current) {
        if (!isInMaze(current) || !isValid(current)) return false;
        visited.add(current);
        if (current.equals(end)) {
            path.add(current);
            return true;
        }
        if (findPath(new Cell(current.row+1, current.col))) {
            path.add(current);
            return true;
        }
        if (findPath(new Cell(current.row, current.col+1))) {
            path.add(current);
            return true;
        }
        if (findPath(new Cell(current.row-1, current.col))) {
            path.add(current);
            return true;
        }
        if (findPath(new Cell(current.row, current.col-1))) {
            path.add(current);
            return true;
        }
        path.removeLast();
        return false;
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
