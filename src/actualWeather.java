import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class actualWeather {
    public static void main(String[] args) {
        String city = getInputCity();
        String urlAddress = createUrl(city);

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
    public String getApiResponse(String urlString) throws MalformedURLException {
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

}