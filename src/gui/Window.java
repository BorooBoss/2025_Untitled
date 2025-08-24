package gui;

import javax.swing.*;
import java.awt.*;

public class Window {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Weather App");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);
            frame.setLayout(new BorderLayout());

            // ----- CENTER -----
            JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));

            JLabel label = new JLabel("Pick city:");
            JTextField textField = new JTextField(15);
            JButton submit = new JButton("SUBMIT");

            centerPanel.add(label);
            centerPanel.add(textField);
            centerPanel.add(submit);

            // ----- RIGHT-DOWN -----
            JPanel downPanel = new JPanel(new BorderLayout());
            JButton reset = new JButton("RESET");
            downPanel.add(reset, BorderLayout.EAST);

            // Add panels
            frame.add(centerPanel, BorderLayout.CENTER);
            frame.add(downPanel, BorderLayout.SOUTH);

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
