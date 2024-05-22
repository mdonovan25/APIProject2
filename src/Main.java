import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class Main implements ActionListener {




    private JFrame mainFrame;
    private JPanel panel1;
    private JPanel panel2;
    //    private JPanel panel3;
    //   private JLabel label;
    //   private JLabel pictureLabel;
    //   private JLabel label3;
    private JTextField lat;
    private JTextField lon;
    private JTextField hour;
    private JTextField parameter;
    private JButton go;
    //    private JTextField textField1;
    private JTextArea textArea;
// video to load jar
//https://www.youtube.com/watch?v=QAJ09o3Xl_0



    // Program for print data in JSON format.
    public String longitude  = "42.456";
    String latitude = "-74.0892";

    public void setUpGUI() {
        mainFrame = new JFrame();
        panel1 = new JPanel();
        panel2 = new JPanel();
        lat = new JTextField("lat");
        lon = new JTextField("long");
        hour = new JTextField("hour");
        parameter = new JTextField("parameter");
        textArea = new JTextArea("I'm panel2!");
        go = new JButton("enter");
        lat.addActionListener(this);
        lon.addActionListener(this);
        hour.addActionListener(this);
        parameter.addActionListener(this);
        go.addActionListener(this);
        lat.setPreferredSize(new Dimension(80,60));
        textArea.setPreferredSize(new Dimension(800,300));
        int borderSize = 10;

        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(panel1, BorderLayout.NORTH);
        mainFrame.add(panel2, BorderLayout.CENTER);
        //      mainFrame.add(panel1);
        //panel1.setBorder(BorderFactory.createEmptyBorder(borderSize, borderSize, borderSize, borderSize));
        panel1.setLayout(new GridLayout(1, 5));
        panel1.add(lat);
        panel1.add(lon);
        panel1.add(hour);
        panel1.add(parameter);
        panel1.add(go);
//        panel1.add(panel3, BorderLayout.EAST);

        //    panel2.setSize(500,300);

        textArea.setLineWrap(true);
        panel2.add(textArea);
//        textArea.setSize(500,300);

        lat.setSize(100,100);
        lon.setSize(100,100);
        hour.setSize(100,100);
        parameter.setSize(100,100);
        go.setSize(100,100);


        //       try {
        //           BufferedImage myPicture = ImageIO.read(new File("Sorellina.jpg"));
        //          pictureLabel.setIcon(new ImageIcon(myPicture));
        //     } catch (Exception e) {
        //         e.printStackTrace();
//        }


        //     textField1 = new JTextField();



        //      panel1.add(button1, BorderLayout.EAST);

        //       label3.setLayout(new GridLayout (3,1));
        //       label3.add(button1);

        //     label3.add(textField1);



        textArea.setEditable(false);



        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setSize(800, 800);
    }

    public void actionPerformed(ActionEvent e) {
        Object buttonClicked = e.getSource();

        if(buttonClicked == go) {

            // 1. get the API weather link after inserting a specific latitude and longitude

            String hourlyLink;

            String url = "https://api.weather.gov/points/" + lat.getText() + "," + lon.getText();

            try {
                hourlyLink = getForecastURL(url);
                // 2. after inserting the parameter, have the program find this parameter within the
                //  code for the location within the properties object and periods array
                System.out.println("HEY:" + hourlyLink);
//                String hourInput = hour.getText();
                Long hourInput = Long.parseLong(hour.getText());
                pull(hourlyLink, hourInput);
                System.out.println("hi");
                // 3. get the "number" for the hour

                // 4. print all this to the text area
            } catch (ParseException ex) {
                ex.printStackTrace();
            }



            //        textArea.setText("");
            //         sortByNPeople();
            //      textArea.setText(textArea.getText() + "reservation " + i + ": " + (int)reservations.get(i).getNPeople() + " " + reservations.get(i).getTimePlacedString() + " " + reservations.get(i).getTimeForString() + "\n");

            //          displayOnGUI();
        }

    }

    public static void main(String args[]) throws Exception {
        // In java JSONObject is used to create JSON object
        // which is a subclass of java.util.HashMap.

        Main m = new Main();

    }

    public Main() throws Exception{
//            getForecast();
        setUpGUI();
//        pull(getForecastURL());
    }
    public String getForecastURL(String realUrl) throws ParseException{
        String output = "abc";
        String totalJson = "";


        try {
//            URL url = new URL("https://api.weather.gov/points/" + longitude + "," + latitude);
            URL url = new URL(realUrl);
            System.out.println(url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {

                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));


            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) { //while we still have another line to read
//                    System.out.println(output); //Paste line by line until next line is null
                totalJson += output;
            }

            conn.disconnect(); //close that portal to the internet

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(totalJson);
        JSONParser parser = new JSONParser();
        //System.out.println(str);
        org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) parser.parse(totalJson); //declaring a new variable
        //      System.out.println(jsonObject);
        JSONObject parameter = (JSONObject) jsonObject.get("properties");
        System.out.println(parameter.get("forecastHourly"));

        String link = (String) parameter.get("forecastHourly");
        return link;
    }

    public void pull(String link, Long hourInput) throws ParseException {
        String output = "abc";
        String totalJson = "";
        System.out.println("link: " +link);
        try {
//                URL url = new URL("https://api.weather.gov/gridpoints/TOP/32,81/forecast/hourly");
            URL url = new URL(link);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {

                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));


            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) { //while we still have another line to read
//                    System.out.println(output); //Paste line by line until next line is null
                totalJson += output;
            }

            conn.disconnect(); //close that portal to the internet

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();
        //System.out.println(str);
        org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) parser.parse(totalJson); //declaring a new variable
//            System.out.println(jsonObject);

        try {

            JSONObject parameter = (JSONObject) jsonObject.get("properties");
//                    System.out.println(parameter.get("periods"));
            JSONArray array = (JSONArray) parameter.get("periods");
            System.out.println(array);
            String t = "";


//            try {
//                number = Integer.valueOf(validString);
//                System.out.println("Converted integer: " + number);
//
//                number = Integer.valueOf(invalidString);
//                System.out.println("Converted integer: " + number);
//            } catch (NumberFormatException e) {
//                System.out.println("Invalid integer input");
//            }



            for (int i=0; i<100; i++) {


                JSONObject array1 = (JSONObject) array.get(i);
//                System.out.println(array1.get("number").getClass().getName());

                if (hourInput == array1.get("number")) {
                    String parameterInput = this.parameter.getText();
                    if (Objects.equals(parameterInput, "temperature")) {
                        Long object1 = (Long) array1.get(this.parameter.getText());
                        t = String.valueOf(object1);
                        System.out.println("HELLO: " + t);
                        System.out.println("object1: " + object1);
                        System.out.println("hour number: " + hourInput + ", " + object1);
                    } else if (Objects.equals(parameterInput, "dewpoint")){
                        JSONObject dewpoint = (JSONObject) array1.get("dewpoint");
                        t=String.valueOf(dewpoint.get("value"));
                        System.out.println(dewpoint.get("value"));
                    }else if(Objects.equals(parameterInput, "probabilityOfPrecipitation")){
                        JSONObject probabilityOfPrecipitation = (JSONObject) array1.get("probabilityOfPrecipitation");
                        t=String.valueOf(probabilityOfPrecipitation.get("value"));
                        System.out.println(probabilityOfPrecipitation.get("value"));
                    } else if(Objects.equals(parameterInput, "relativeHumidity")){
                        JSONObject relativeHumidity = (JSONObject) array1.get("relativeHumidity");
                        t=String.valueOf(relativeHumidity.get("value"));
                        System.out.println(relativeHumidity.get("value"));
                    }

                    else if (Objects.equals(parameterInput, "shortForecast")){

                        t = (String) array1.get("shortForecast");
                 //       System.out.println(shortForecast.get("value"));
                    }else if(Objects.equals(parameterInput, "windSpeed")){
                        t= (String) array1.get("windSpeed");
                    }else if(Objects.equals(parameterInput, "windDirection")){
                        t = (String) array1.get("windDirection");
                    }



                }

            }
                textArea.setText("The " + this.parameter.getText() + " at (" + lat.getText() + ", " + lon.getText() + ") " + hourInput + " hours in the future is " + t);



            //              for(JSONOb)


            //         System.out.println()

// Be able to get the weather forecast with a desired parameter and time/hour for a specific location


//           String name = (String)jsonArray.get("name");
//           System.out.println(name);
            //      System.out.println(jsonArray.get("hair color"));


//            org.json.simple.JSONArray msg = (org.json.simple.JSONArray) jsonArray.get("films");
//            int n =   msg.size(); //(msg).length();
//            String test = "";
//            for (int i = 0; i < n; ++i) {
//                test =(String) msg.get(i);
//                System.out.println(test);
////                // System.out.println(person.getInt("key"));
//            }
//    //        String height= (String)jsonArray.get("height");
//            System.out.println(name);
        }

        catch (Exception e) {
            e.printStackTrace();
        }


    }
    public void GUI (){

    }
}

