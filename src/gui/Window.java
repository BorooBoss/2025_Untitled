package gui;

import service.DayWeather;
import service.WeatherService;

import javax.swing.*;
import java.awt.*;

public class Window {

    // --- Pomocná metóda na získanie ikon podľa kódu počasia ---
    private static ImageIcon getWeatherIcon(int code) {
        String path = "icons/";
        String file;

        switch (code) {
            case 0: case 1: file = "sun.png"; break;
            case 2: case 3: file = "cloudy.png"; break;
            case 45: case 48: case 56: case 57: file = "cloud.png"; break;
            case 51: case 53: case 55:
            case 61: case 63: case 65:
            case 80: case 81: case 82: file = "rainy.png"; break;
            case 66: case 67: case 71:
            case 73: case 75: case 77:
            case 85: case 86: file = "snow.png"; break;
            case 95: case 96: case 99: file = "storm.png"; break;
            default: file = "unknown.png"; break;
        }

        // zmenšíme ikonku na 40x40 pixelov
        ImageIcon icon = new ImageIcon(path + file);
        Image scaled = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

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
            reset.addActionListener(e -> textField.setText(""));

            // Add panels
            frame.add(centerPanel, BorderLayout.CENTER);
            frame.add(downPanel, BorderLayout.SOUTH);

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // --- SUBMIT LISTENER ---
            submit.addActionListener(e -> {
                String city = textField.getText();
                WeatherService ws = new WeatherService(city);

                JFrame resultFrame = new JFrame("Weather in " + city);
                resultFrame.setSize(600, 600);

                JPanel resultPanel = new GradientPanel();
                resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));


                // --- Actual weather ---
                JPanel currentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                currentPanel.setOpaque(false);
                JLabel currentIcon = new JLabel(getWeatherIcon(ws.getCurrentWeatherCode()));
                JLabel currentText = new JLabel(
                        "<html><b>Actual Weather:</b><br>" +
                                "Temperature: " + ws.getCurrentTemperature() + " °C<br>" +
                                "Wind: " + ws.getCurrentWindSpeed() + " km/h<br>" +
                                "Time: " + ws.getCurrentTime() + "</html>"
                );
                currentText.setFont(new Font("Arial", Font.BOLD, 14));
                currentPanel.add(currentIcon);
                currentPanel.add(currentText);
                resultPanel.add(currentPanel);

                // --- Forecast (next days) ---
                for (DayWeather day : ws.getForecast()) {
                    JPanel dayPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    dayPanel.setOpaque(false);

                    JLabel iconLabel = new JLabel(getWeatherIcon(day.getWeatherCode()));
                    JLabel textLabel = new JLabel(
                            "<html><b>Date:</b> " + day.getDate() +
                                    "<br><b>Temp:</b> " + day.getMaxTemperature() + " °C" +
                                    "<br><b>Wind:</b> " + day.getAvgWindSpeed() + " km/h</html>"
                    );
                    textLabel.setFont(new Font("Arial", Font.PLAIN, 13));

                    dayPanel.add(iconLabel);
                    dayPanel.add(textLabel);
                    resultPanel.add(dayPanel);
                }


                resultFrame.add(new JScrollPane(resultPanel));
                resultFrame.setLocationRelativeTo(null);
                resultFrame.setVisible(true);
            });


        });
    }
}
