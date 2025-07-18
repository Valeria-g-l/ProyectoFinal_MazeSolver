package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MazeFrame extends JFrame {
    private final MazePanel mazePanel;
    
    public MazeFrame() {
        setTitle("Maze Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        mazePanel = new MazePanel();
        add(createToolbar(), BorderLayout.NORTH);
        add(mazePanel, BorderLayout.CENTER);
        add(createToolbar(), BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private JToolBar createToolbar() {
        JToolBar toolBar = new JToolBar();
        
        toolBar.add(createButton("Set Start", e -> mazePanel.setSettingStart(true)));
        toolBar.add(createButton("Set End", e -> mazePanel.setSettingEnd(true)));
        toolBar.add(createButton("Toggle Wall", e -> mazePanel.setSettingWalls(true)));
        toolBar.add(createButton("Clear", e -> mazePanel.clearMaze()));
        toolBar.addSeparator();
        toolBar.add(createButton("Solve (DFS)", e -> {
            if (!mazePanel.hasStart() || !mazePanel.hasEnd()) {
                JOptionPane.showMessageDialog(this, "Set start and end points first!");
                return;
            }
            if (!mazePanel.solveDFS()) {
                JOptionPane.showMessageDialog(this, "No solution found!");
            }
        }));
        
        return toolBar;
    }
    
    private JButton createButton(String text, ActionListener action) {
        JButton btn = new JButton(text);
        btn.addActionListener(action);
        return btn;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MazeFrame().setVisible(true));
    }
}