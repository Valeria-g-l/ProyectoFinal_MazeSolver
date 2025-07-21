package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MazeFrame extends JFrame {
    private MazePanel mazePanel;
    private int rows;
    private int cols;
    
    public MazeFrame(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        
        setTitle("MathWorks Maze");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        initUI();
        
        setVisible(true);
    }
    
    private void initUI() {
        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Toolbar superior (Archive)
        JToolBar topToolBar = new JToolBar();
        topToolBar.setFloatable(false);
        
        topToolBar.add(createToolBarButton("Set Start", "Establecer punto de inicio"));
        topToolBar.add(createToolBarButton("Set End", "Establecer punto final"));
        topToolBar.add(createToolBarButton("Toggle Wall", "Alternar pared"));
        
        mainPanel.add(topToolBar, BorderLayout.NORTH);
        
        // Panel del laberinto
        mazePanel = new MazePanel(rows, cols);
        mainPanel.add(mazePanel, BorderLayout.CENTER);
        
        // Toolbar inferior (Diagram)
        JToolBar bottomToolBar = new JToolBar();
        bottomToolBar.setFloatable(false);
        
        // Selector de algoritmo
        JComboBox<String> algoCombo = new JComboBox<>(new String[]{"Recursivo", "Backtracking", "Prim's"});
        bottomToolBar.add(new JLabel("Algoritmo:"));
        bottomToolBar.add(algoCombo);
        
        bottomToolBar.addSeparator();
        bottomToolBar.add(createToolBarButton("Resolver", "Resolver el laberinto"));
        bottomToolBar.add(createToolBarButton("Paso a paso", "Resolver paso a paso"));
        bottomToolBar.add(createToolBarButton("Limpiar", "Limpiar laberinto"));
        
        mainPanel.add(bottomToolBar, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JButton createToolBarButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setToolTipText(tooltip);
        button.addActionListener(this::handleButtonClick);
        return button;
    }
    
    private void handleButtonClick(ActionEvent e) {
        String command = ((JButton)e.getSource()).getText();
        switch (command) {
            case "Set Start":
                mazePanel.setMode(MazePanel.Mode.SET_START);
                break;
            case "Set End":
                mazePanel.setMode(MazePanel.Mode.SET_END);
                break;
            case "Toggle Wall":
                mazePanel.setMode(MazePanel.Mode.TOGGLE_WALL);
                break;
            case "Resolver":
                mazePanel.solveMaze();
                break;
            case "Paso a paso":
                mazePanel.stepSolve();
                break;
            case "Limpiar":
                mazePanel.clearMaze();
                break;
        }
    }
}