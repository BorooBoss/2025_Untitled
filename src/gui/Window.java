package gui;

import service.DayWeather;
import service.WeatherService;

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
            submit.addActionListener(e -> {
                String city = textField.getText();
                WeatherService ws = new WeatherService(city);

                JFrame resultFrame = new JFrame("Weather in " + city);
                resultFrame.setSize(800, 800);

                JTextArea area = new JTextArea();
                Font font = new Font("Arial", Font.BOLD, 16); // meno fontu, štýl, veľkosť
                area.setFont(font);
                area.setEditable(false);
                area.append("Actual Weather :\n");
                area.append("Temperature: " + ws.getCurrentTemperature() + " °C\n");
                area.append("Wind: " + ws.getCurrentWindSpeed() + " km/h\n");
                area.append("Time: " + ws.getCurrentTime() + "\n");
                for (DayWeather day : ws.getForecast()){

                    area.append("\n");
                    area.append("Date: " + day.getDate() + "\n");
                    area.append("Temperature: " + day.getMaxTemperature() + " °C\n");
                    area.append("Wind: " + day.getAvgWindSpeed() + " km/h\n");

                }

                resultFrame.add(new JScrollPane(area));
                resultFrame.setLocationRelativeTo(null);
                resultFrame.setVisible(true);
            });

        });
    }
}
