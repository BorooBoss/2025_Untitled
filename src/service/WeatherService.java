package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.JSONObject;
import org.json.JSONArray;

public class WeatherService {
    private double latitude;
    private double longitude;
    private String currentURL;
    private String currentTime;
    private double currentTemperature;
    private int currentWindSpeed;
    public WeatherService(){
        String city = getInputCity();
        String urlAddress = createUrl(city);
        String response = getApiResponse(urlAddress);
        //System.out.println("API Response: ");
        //System.out.println(response);
        System.out.println("");

        double[] coords = extractCoordinates(response);
        this.latitude = coords[0]; this.longitude = coords[1];
        System.out.println("Latitude: " + latitude);
        System.out.println("Longitude: " + longitude);
        this.currentURL = "https://api.open-meteo.com/v1/forecast?latitude="
                + latitude
                + "&longitude=" + longitude
                + "&current_weather=true"
                + "&daily=temperature_2m_max,windspeed_10m_mean"
                + "&timezone=Europe/Bratislava";
        System.out.println(currentURL + " current url");
        JSONObject weatherJson = fetchCurrentWeather();

        System.out.println("Temperature: " + currentTemperature + " °C");
        System.out.println("Wind speed: " + currentWindSpeed + " km/h");
        System.out.println("Time: " + currentTime);

        JSONArray dates = weatherJson.getJSONObject("daily").getJSONArray("time");
        JSONArray temps = weatherJson.getJSONObject("daily").getJSONArray("temperature_2m_max");
        JSONArray winds = weatherJson.getJSONObject("daily").getJSONArray("windspeed_10m_mean");

        List<DayWeather> forecast = new ArrayList<>();
        for (int i = 0; i < dates.length(); i++) {
            forecast.add(new DayWeather(
                    dates.getString(i),
                    temps.getDouble(i),
                    winds.getDouble(i)
            ));
        }

        for (DayWeather day : forecast) {
            System.out.println(day);
        }


    }

    public static void main(String[] args) {
        WeatherService actualWeather = new WeatherService();
    }
    public static String getInputCity(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Pick city: ");
        String city = scanner.nextLine();

        System.out.println("Searching actual weather for : " + city);

        scanner.close();
        return city;
    }
    public static String createUrl(String city){
        StringBuilder urlAddress = new StringBuilder("https://geocoding-api.open-meteo.com/v1/search?name=");
        urlAddress.append(city);
        System.out.println("URL " + urlAddress);
        return urlAddress.toString();
    }
    public static String getApiResponse(String urlString){
        StringBuilder response = new StringBuilder();
        try{
            URL currentUrl = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) currentUrl.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()) // reading "connection"
            );

            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            connection.disconnect();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.toString();
    }
    public static double[] extractCoordinates(String json) {
        JSONObject root = new JSONObject(json); // main JSON object
        JSONArray results = root.getJSONArray("results"); // arrray of results
        if (results.isEmpty()) {
            throw new RuntimeException("Place not found");
        }
        JSONObject firstResult = results.getJSONObject(0); // main result

        double latitude = firstResult.getDouble("latitude"); //important results
        double longitude = firstResult.getDouble("longitude");

        return new double[]{latitude, longitude};
    }

    public JSONObject fetchCurrentWeather() {
        String weatherResponse = getApiResponse(this.currentURL);

        JSONObject weatherJson = new JSONObject(weatherResponse);
        JSONObject currentWeather = weatherJson.getJSONObject("current_weather");

        this.currentTemperature = currentWeather.getDouble("temperature");
        this.currentWindSpeed = currentWeather.getInt("windspeed");
        this.currentTime = currentWeather.getString("time");

        return weatherJson;
    }



}