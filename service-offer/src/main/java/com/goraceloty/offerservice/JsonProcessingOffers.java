package com.goraceloty.offerservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.format.DateTimeFormatter;

public class JsonProcessingOffers {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/svc_offer";
        String user = "gl_pg_user";
        String password = "g0r4c3_l0ty";
        String filePath = "..\\data\\offers.json";
        String sql = "INSERT INTO offers(id, transportID, hotelID, city_arrival, city_departure, date_start, date_end ) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT (id) DO UPDATE SET " +
                "transportID = EXCLUDED.transportID, " +
                "hotelID = EXCLUDED.hotelID, " +
                "city_arrival = EXCLUDED.city_arrival, " +
                "city_departure = EXCLUDED.city_departure, " +
                "date_start = EXCLUDED.date_start, " +
                "date_end = EXCLUDED.date_end;";


        processJsonData(url, filePath, sql, user, password);
    }

    public static void processJsonData(String url, String filePath, String sql, String user, String password) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File jsonFile = new File(filePath);
            JsonNode transportsArray = mapper.readTree(jsonFile);
            System.out.println(transportsArray.toString());


            // Establish database connection
            Connection conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false); // Use transaction control

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            System.out.println(preparedStatement);
            System.out.println(transportsArray.isArray());

            // Loop through JSON array // id, transportID, hotelID, cityArrival, cityDeparture, dateStart, dateEnd
            if (transportsArray.isArray()) {
                System.out.println("początek pętli");
                for (JsonNode transport : transportsArray) {
                    preparedStatement.setLong(1, transport.get("id").longValue());
                    preparedStatement.setLong(2, transport.get("transportID").longValue());
                    preparedStatement.setLong(3, transport.get("hotelID").longValue());
                    preparedStatement.setString(4, transport.get("cityArrival").textValue());
                    preparedStatement.setString(5, transport.get("cityDeparture").textValue());
                    preparedStatement.setString(6, transport.get("dateStart").textValue());
                    preparedStatement.setString(7, transport.get("dateEnd").textValue());
                    System.out.println("Executing SQL: " + sql);
                    System.out.println("With parameters: " +
                            transport.get("id").longValue() + ", " +
                            transport.get("transportID").textValue() + ", " +
                            transport.get("hotelID").intValue() + ", " +
                            transport.get("cityArrival").textValue() + ", " +
                            transport.get("cityDeparture").textValue() + ", " +
                            transport.get("dateStart").booleanValue() + ", " +
                            transport.get("dateEnd").textValue());
                    // Execute upsert
                    preparedStatement.executeUpdate();
                }
                conn.commit(); // Commit all changes
            }

            // Clean up
            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Nie wyszło");
            e.printStackTrace();
        }
    }
}