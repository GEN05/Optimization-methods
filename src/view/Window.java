package view;

import model.Methods;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends OptimizationMethods implements ActionListener {
    //  Text field
    static JTextField fieldLeftBorder;
    static JTextField fieldRightBorder;
    static JTextField fieldPreciseness;
    //  Size of frame
    final int WIDTH = 900;
    final int HEIGHT = 600;
    //  Frame
    JFrame frame;
    //  Panels
    JPanel containerPanel;
    JPanel controlPanel;
    //  Labels
    JLabel labelLeftBorder;
    JLabel labelRightBorder;
    JLabel labelIterationsCount;
    //  Buttons
    JButton buttonReset;
    JButton buttonDraw;

    JComboBox<Methods> comboBox;

    GridBagConstraints gridBagConstraints;

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

        buttonDraw = new JButton("Нарисовать");
        gridBagConstraints.gridx = 5;
        controlPanel.add(buttonDraw, gridBagConstraints);
        buttonDraw.addActionListener(this);

        containerPanel = new JPanel();
        containerPanel.setLayout(new BorderLayout());
        containerPanel.add(controlPanel, BorderLayout.NORTH);
        containerPanel.add(this, BorderLayout.CENTER);
        containerPanel.setDoubleBuffered(true);
        frame.setFocusable(true);
        frame.add(containerPanel);
        frame.setVisible(true);
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
            fieldLeftBorder.setText("-6.0");
            fieldRightBorder.setText("-4.0");
            fieldPreciseness.setText("0.001");
            //draw();
            repaint();
        } else if (source.equals(buttonDraw)) {
            //draw();
            repaint();
        }
    }
}
