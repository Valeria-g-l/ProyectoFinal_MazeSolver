package views;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class ResultadosDialog extends JDialog {

    public ResultadosDialog(JFrame parent) {
        super(parent, "Resultados Guardados", true);
        setLayout(new BorderLayout());

        String[] columnas = {"Algoritmo", "Celdas Camino", "Tiempo (ns)"};
        Object[][] datos = {
            {"Recursivo", 8, 146599},
            {"Backtracking", 8, 51400},
            {"BFS", 8, 89000},
            {"Recursivo Completo", 23, 72200},
            {"Recursivo Completo BT", 22, 49901}
        };

        DefaultTableModel tableModel = new DefaultTableModel(datos, columnas);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton limpiarBtn = new JButton("Limpiar Resultados");
        JButton graficarBtn = new JButton("Graficar Resultados");

        limpiarBtn.addActionListener(e -> tableModel.setRowCount(0));
        graficarBtn.addActionListener(e -> {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String algoritmo = tableModel.getValueAt(i, 0).toString();
                Number tiempo = (Number) tableModel.getValueAt(i, 2);
                dataset.addValue(tiempo.doubleValue(), "Tiempo(ns)", algoritmo);
            }
            JFreeChart chart = ChartFactory.createLineChart(
                "Tiempos de Ejecución por Algoritmo",
                "Algoritmo",
                "Tiempo (ns)",
                dataset
            );
            ChartPanel chartPanel = new ChartPanel(chart);
            JDialog chartDialog = new JDialog(this, "Gráfica", true);
            chartDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            chartDialog.setContentPane(chartPanel);
            chartDialog.setSize(600, 400);
            chartDialog.setLocationRelativeTo(this);
            chartDialog.setVisible(true);
        });

        buttonPanel.add(limpiarBtn);
        buttonPanel.add(graficarBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        setSize(500, 300);
        setLocationRelativeTo(parent);
    }
}