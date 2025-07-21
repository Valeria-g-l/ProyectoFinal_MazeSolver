package models;

import java.awt.Color;

public class CellState {
    public static final CellState EMPTY = new CellState("Empty", Color.WHITE);
    public static final CellState WALL = new CellState("Wall", Color.BLACK);
    public static final CellState START = new CellState("Start", Color.GREEN);
    public static final CellState END = new CellState("End", Color.RED);
    public static final CellState PATH = new CellState("Path", new Color(100, 200, 255));
    public static final CellState VISITED = new CellState("Visited", new Color(200, 200, 255));
    
    private final String name;
    private final Color color;
    
    private CellState(String name, Color color) {
        this.name = name;
        this.color = color;
    }
    
    public Color getColor() {
        return color;
    }
    
    @Override
    public String toString() {
        return name;
    }
}