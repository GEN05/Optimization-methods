package view;

import model.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import static model.Function.*;

public class Window extends JPanel implements ActionListener {
    //  Size of frame
    final int WIDTH = 900;
    final int HEIGHT = 600;
    //  Text field
    JTextField fieldLeftBorder;
    JTextField fieldRightBorder;
    JTextField fieldPreciseness;
    //  Frame
    JFrame frame;
    //  Panels
    JPanel containerPanel;
    JPanel controlPanel;
    JPanel chartJPanel;

    ChartPanel chartPanel;
    //  Labels
    JLabel labelLeftBorder;
    JLabel labelRightBorder;
    JLabel labelIterationsCount;
    //  Buttons
    JButton buttonReset;
    JButton buttonDraw;

    JFreeChart chart;

    JComboBox<Methods> comboBox;

    XYSeries series1;
    XYSeries series2;
    XYSeries series3;
    XYSeries series4;

    XYSeriesCollection dataset;

    GridBagConstraints gridBagConstraints;

    private int counter = 0;

    public Window() {
        frame = new JFrame();
        frame.setSize(WIDTH, HEIGHT);
        frame.setTitle("Методы оптимизации");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.white);
        frame.setForeground(Color.white);
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.NONE;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.anchor = GridBagConstraints.EAST;

        //  Parameters
        labelLeftBorder = new JLabel("Левая граница:");
        controlPanel.add(labelLeftBorder, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        fieldLeftBorder = new JTextField("-6.0");
        fieldLeftBorder.setColumns(5);
        controlPanel.add(fieldLeftBorder, gridBagConstraints);

        labelRightBorder = new JLabel("Правая граница:");
        gridBagConstraints.gridx = 3;
        controlPanel.add(labelRightBorder, gridBagConstraints);

        fieldRightBorder = new JTextField("-4.0");
        fieldRightBorder.setColumns(5);
        gridBagConstraints.gridx = 4;
        controlPanel.add(fieldRightBorder, gridBagConstraints);

        //  Reset
        buttonReset = new JButton("Сбросить");
        gridBagConstraints.gridx = 5;
        controlPanel.add(buttonReset, gridBagConstraints);
        buttonReset.addActionListener(this);

        //  Iterations count
        labelIterationsCount = new JLabel("Точность:");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        controlPanel.add(labelIterationsCount, gridBagConstraints);

        fieldPreciseness = new JTextField("0.001");
        fieldPreciseness.setColumns(5);
        gridBagConstraints.gridx = 2;
        controlPanel.add(fieldPreciseness, gridBagConstraints);
        controlPanel.setBackground(Color.getHSBColor(255, 210, 133));

        Methods[] methods = {Methods.parabola, Methods.dichotomy, Methods.fibonacci, Methods.goldenRatio, Methods.brent};
        comboBox = new JComboBox<>(methods);
        gridBagConstraints.gridx = 3;
        controlPanel.add(comboBox, gridBagConstraints);

        buttonDraw = new JButton("Дальше");
//        buttonDraw.setEnabled(false);
        gridBagConstraints.gridx = 5;
        controlPanel.add(buttonDraw, gridBagConstraints);
        buttonDraw.addActionListener(this);

        XYDataset dataset = createDataset(Double.parseDouble(fieldLeftBorder.getText()), Double.parseDouble(fieldRightBorder.getText()));
        chart = createChart(dataset);
        chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);

        chartJPanel = new JPanel();
        chartJPanel.setLayout(new BorderLayout());
        chartJPanel.add(chartPanel, BorderLayout.NORTH);

        containerPanel = new JPanel();
        containerPanel.setLayout(new BorderLayout());
        containerPanel.add(chartJPanel, BorderLayout.SOUTH);
        containerPanel.add(controlPanel, BorderLayout.NORTH);
        containerPanel.add(this);
        containerPanel.setDoubleBuffered(true);
        frame.setFocusable(true);
        frame.add(containerPanel);
        frame.setVisible(true);
    }

    private XYDataset createDataset(double left, double right) {
        series1 = new XYSeries("function");
        series2 = new XYSeries("left");
        series3 = new XYSeries("right");
        series4 = new XYSeries("minimum");
        for (double i = left; i < right; i += 0.1) {
            series1.add(i, calculate(i, left, right));
        }
        dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        drawBorder(left, right);
        return dataset;
    }

    /**
     * Draws left, right, minimum.
     *
     * @param left  left border.
     * @param right right border.
     */
    private void drawBorder(double left, double right) {
        dataset.removeSeries(series2);
        dataset.removeSeries(series3);
        series2.clear();
        series3.clear();
        double max = series1.getMaxY(),
                min = series1.getMinY();
        for (double i = min; i < max; i += (max - min) / 100) {
            series2.add(left, i);
            series3.add(right, i);
        }
        dataset.addSeries(series2);
        dataset.addSeries(series3);
    }

    private JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createXYLineChart(
                "First laboratory work",
                "X",
                "Y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(0.5f));
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesPaint(2, Color.BLUE);
        renderer.setSeriesPaint(3, Color.GREEN);

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        return chart;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param event the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source.equals(buttonReset)) {
            buttonReset.setFocusable(false);
            buttonDraw.setEnabled(true);
            fieldLeftBorder.setText("-6.0");
            fieldRightBorder.setText("-4.0");
            fieldPreciseness.setText("0.001");
            map.clear();
            makeNullCounter();
            counter = 0;
            drawBorder(getLeft(), getRight());
            chart = createChart(dataset);
        } else if (source.equals(buttonDraw)) {
//            buttonDraw.setFocusable(false);
//            buttonDraw.setEnabled(false);
            map.clear();
            makeNullCounter();
            double left = Double.parseDouble(fieldLeftBorder.getText()),
                    right = Double.parseDouble(fieldRightBorder.getText());
//            updateGraphic(left, right);
            if (Objects.equals(comboBox.getSelectedItem(), Methods.parabola)) {
                new Parabola().calculate(left, right);
                nextIteration();
            } else if (Objects.equals(comboBox.getSelectedItem(), Methods.dichotomy)) {
                Dichotomy dichotomy = new Dichotomy();
                dichotomy.calculate(left, right);
                nextIteration();
            } else if (Objects.equals(comboBox.getSelectedItem(), Methods.goldenRatio)) {
                GoldenRatio goldenRatio = new GoldenRatio();
                goldenRatio.calculate(left, right);
                nextIteration();
            } else if (Objects.equals(comboBox.getSelectedItem(), Methods.fibonacci)) {
                Fibonacci fibonacci = new Fibonacci();
                fibonacci.calculate(left, right);
                nextIteration();
            } else if (Objects.equals(comboBox.getSelectedItem(), Methods.brent)) {
                Brent brent = new Brent();
                brent.calculate(left, right);
                nextIteration();
            }
        }
    }

    private void nextIteration() {
        counter++;
        double l, r, minimum;
        if (counter < map.size()) {
            ChartIteration chartIteration = map.get(counter);
            l = chartIteration.left;
            r = chartIteration.right;
            minimum = chartIteration.minimumX;
            drawBorder(l, r);
            drawMinimum(minimum);
            chart = createChart(dataset);
        } else {
            JOptionPane.showMessageDialog(frame, "Вычисление окончено. Абсолютный минимум: x = "
                    + series4.getMinX()
                    + "y = " + series4.getMinY());
        }
    }

    private void drawMinimum(double minimum) {
        dataset.removeSeries(series4);
        series4.clear();
        double max = series1.getMaxY(), min = series1.getMinY();
        for (double i = min; i < max; i += (max - min) / 100) {
            series4.add(minimum, i);
        }
        dataset.addSeries(series4);
    }
}
