import javax.swing.*;

import views.MazeFrame;

public class MazeApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String stringRows = JOptionPane.showInputDialog(null, "Ingrese numero de filas:", "Dimesion Maze", JOptionPane.QUESTION_MESSAGE);
            if (stringRows==null) System.exit(0);
            
            String stringCols = JOptionPane.showInputDialog(null, "Ingrese numero de columnas:", "Dimesion Maze", JOptionPane.QUESTION_MESSAGE);
            if (stringCols==null) System.exit(0);
            
            try {
                int rows = Integer.parseInt(stringRows);
                int cols = Integer.parseInt(stringCols);
                if (rows<=0 || cols<=0) {
                    JOptionPane.showMessageDialog(null, "Las dimesiones deben ser valores positivos","Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
                new MazeFrame(rows, cols);
            } catch (NumberFormatException err) {
                JOptionPane.showMessageDialog(null, "Dimensiones No Validas", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }
}