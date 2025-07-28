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
        setLocation(900, 400);

        setJMenuBar(createMenuBar());

        initUI();

        pack(); // Ajusta el tamaño automáticamente
        setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem nuevoLaberinto = new JMenuItem("Nuevo Laberinto");
        JMenuItem verResultados = new JMenuItem("Ver resultados");

        nuevoLaberinto.addActionListener(e -> {
        String stringRows = JOptionPane.showInputDialog(this, "Ingrese número de filas:", "Dimensión Maze", JOptionPane.QUESTION_MESSAGE);
        if (stringRows == null) return;
        String stringCols = JOptionPane.showInputDialog(this, "Ingrese número de columnas:", "Dimensión Maze", JOptionPane.QUESTION_MESSAGE);
        if (stringCols == null) return;
        try {
            int newRows = Integer.parseInt(stringRows);
            int newCols = Integer.parseInt(stringCols);
            if (newRows <= 0 || newCols <= 0) {
                JOptionPane.showMessageDialog(this, "Las dimensiones deben ser valores positivos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Cierra la ventana actual y abre una nueva
            SwingUtilities.invokeLater(() -> {
                dispose();
                new MazeFrame(newRows, newCols);
            });
        } catch (NumberFormatException err) {
            JOptionPane.showMessageDialog(this, "Dimensiones no válidas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    });
       

         menuArchivo.add(nuevoLaberinto);
         menuArchivo.add(verResultados);

        JMenu menuAyuda = new JMenu("Ayuda");

        menuBar.add(menuArchivo);
        menuBar.add(menuAyuda);

        return menuBar;
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JToolBar topToolBar = new JToolBar();
        topToolBar.setFloatable(false);

        topToolBar.add(createToolBarButton("Set Start", "Establecer punto de inicio"));
        topToolBar.add(createToolBarButton("Set End", "Establecer punto final"));
        topToolBar.add(createToolBarButton("Toggle Wall", "Alternar pared"));

        mainPanel.add(topToolBar, BorderLayout.NORTH);

        // Panel del laberinto
        mazePanel = new MazePanel(rows, cols);
        mainPanel.add(mazePanel, BorderLayout.CENTER);

        JToolBar bottomToolBar = new JToolBar();
        bottomToolBar.setFloatable(false);

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