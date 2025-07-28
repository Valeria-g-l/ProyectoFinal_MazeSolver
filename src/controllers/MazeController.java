package controllers;

import models.Cell;
import models.AlgorithmResult;
import solver.solverImpl.MazeSolverBFS;
import solver.solverImpl.MazeSolverDFS;
import solver.solverImpl.MazeSolverRecursivo;
import solver.solverImpl.MazeSolverRecursivoCompleto;
import solver.solverImpl.MazeSolverRecursivoCompletoBT;

public class MazeController {
    private MazeSolverRecursivo recursivo;
    private MazeSolverRecursivoCompleto recursivoCompleto;
    private MazeSolverRecursivoCompletoBT recursivoCompletoBT;
    private MazeSolverBFS bfs;
    private MazeSolverDFS dfs;

    public MazeController() {
        recursivo = new MazeSolverRecursivo();
        recursivoCompleto = new MazeSolverRecursivoCompleto();
        recursivoCompletoBT = new MazeSolverRecursivoCompletoBT();
        bfs = new MazeSolverBFS();
        dfs = new MazeSolverDFS();
    }

    public AlgorithmResult obtainRecursiveSolve(boolean[][] grid, Cell start, Cell end) {
        return recursivo.getPath(grid, start, end);
    }

    public AlgorithmResult obtainCompleteSolve(boolean[][] grid, Cell start, Cell end) {
        return recursivoCompleto.getPath(grid, start, end);
    }

    public AlgorithmResult obtainCompleteBTSolve(boolean[][] grid, Cell start, Cell end) {
        return recursivoCompletoBT.getPath(grid, start, end);
    }

    public AlgorithmResult obtainBFSSolve(boolean[][] grid, Cell start, Cell end) {
        return bfs.getPath(grid, start, end);
    }
    
    public AlgorithmResult obtainDFSSolve(boolean[][] grid, Cell start, Cell end) {
        return dfs.getPath(grid, start, end);
    }
}
