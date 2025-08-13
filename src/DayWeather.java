public class DayWeather {
    private String date;
    private double maxTemperature;
    private double avgWindSpeed;

    public DayWeather(String date, double maxTemperature, double avgWindSpeed) {
        this.date = date;
        this.maxTemperature = maxTemperature;
        this.avgWindSpeed = avgWindSpeed;
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

    @Override
    public String toString() {
        return date + " → Max Temp: " + maxTemperature + "°C, Avg Wind: " + avgWindSpeed + " km/h";
    }
}
