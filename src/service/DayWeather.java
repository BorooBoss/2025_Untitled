package service;

public class DayWeather {
    private String date;
    private double maxTemperature;
    private double avgWindSpeed;
    private int weatherCode;

    public DayWeather(String date, double maxTemperature, double avgWindSpeed, int weatherCode) {
        this.date = date;
        this.maxTemperature = maxTemperature;
        this.avgWindSpeed = avgWindSpeed;
        this.weatherCode = weatherCode;
    }

    public String getDate() {
        return date;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public double getAvgWindSpeed() {
        return avgWindSpeed;
    }

    public int getWeatherCode() { return weatherCode; }

    @Override
    public String toString() {
        return date + " → Max Temp: " + maxTemperature + "°C, Avg Wind: " + avgWindSpeed + " km/h";
    }
}
