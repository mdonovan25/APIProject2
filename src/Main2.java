import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class Main2 {




        public static void main(String[] args) {
            // API key for OpenWeatherMap
  //          String apiKey = "YOUR_API_KEY";

            // Get user input for location and time (optional)
            String location = "New York";

            // Make API call
            try {
                URL url = new URL("https://api.weather.gov/gridpoints/TOP/32,81/forecast/hourly" + location + "&appid=");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

                String output;
                StringBuilder response = new StringBuilder();
                while ((output = br.readLine()) != null) {
                    response.append(output);
                }

                conn.disconnect();

                // Parse JSON response
   //             ObjectMapper mapper = new ObjectMapper();
    //            JsonNode root = mapper.readTree(response.toString());
     //           String weatherDescription = root.get("weather").get(0).get("description").asText();
     //           double temperature = root.get("main").get("temp").asDouble();

                // Display weather information
                System.out.println("Location: " + location);
        //        System.out.println("Current Weather: " + weatherDescription);
         //       System.out.println("Temperature: " + temperature + " K");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

