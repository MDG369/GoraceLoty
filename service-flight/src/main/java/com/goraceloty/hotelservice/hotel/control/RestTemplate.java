package com.goraceloty.hotelservice.hotel.control;

import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestTemplate {
    public String sendGet(URL url) {
        HttpURLConnection con = null;
        try {
            System.out.println("sendGet: " + url);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return response.toString();
            }
        }
        catch (Exception e){
            return "fail " + e;
        }
        finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return "fail";
    }
}
